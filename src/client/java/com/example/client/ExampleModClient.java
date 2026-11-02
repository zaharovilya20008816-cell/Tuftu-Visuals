package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExampleModClient implements ClientModInitializer {

    // Коды клавиш
    public static final int KEY_PRESS = 1;
    public static final int KEY_ESCAPE = 256;
    public static final int KEY_DELETE = 261;
    public static final int KEY_RIGHT_SHIFT = 344;
    public static final int KEY_LEFT_SHIFT = 340;
    public static final int KEY_LEFT_CONTROL = 341;
    public static final int KEY_RIGHT_CONTROL = 345;
    public static final int KEY_LEFT_ALT = 342;
    public static final int KEY_SPACE = 32;
    public static final int KEY_TAB = 258;
    public static final int KEY_CAPS_LOCK = 280;
    public static final int KEY_Z = 90;
    public static final int KEY_V = 86;
    public static final int KEY_B = 66;

    // Настройки PvP функций
    public static boolean swordVisuals = true;
    public static int swingMode = 0; // 0: Плавный взмах, 1: Быстрый взмах, 2: Без КД, 3: Стандарт
    public static int posMode = 0;   // 0: Низко PvP, 1: Очень низко, 2: Высоко, 3: Стандарт
    public static boolean noHurtCam = true;
    public static boolean autoSprint = true;
    public static boolean fullBright = true;

    // Бинды по умолчанию (Меню = Right Shift, Перл = Z, Визуалы = V, Анти-тряска = B)
    public static int menuBind = KEY_RIGHT_SHIFT;
    public static int pearlBind = KEY_Z;
    public static int visualsBind = KEY_V;
    public static int hurtCamBind = KEY_B;
    public static int sprintBind = 0;
    public static int brightBind = 0;

    private static final Map<Integer, Boolean> keyStates = new HashMap<>();
    private static int slowSwingTick = 0;

    private static Method glfwGetCurrentContext;
    private static Method glfwGetKey;
    private static Method glfwGetMouseButton;
    private static Method glfwGetKeyName;

    static {
        try {
            Class<?> glfw = Class.forName("org.lwjgl.glfw.GLFW");
            glfwGetCurrentContext = glfw.getMethod("glfwGetCurrentContext");
            glfwGetKey = glfw.getMethod("glfwGetKey", long.class, int.class);
            glfwGetMouseButton = glfw.getMethod("glfwGetMouseButton", long.class, int.class);
            glfwGetKeyName = glfw.getMethod("glfwGetKeyName", int.class, int.class);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onInitializeClient() {
    }

    public static void onClientTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;

        // 1. Авто-спринт
        if (autoSprint && mc.options.keyUp.isDown() && !mc.player.isShiftKeyDown() && !mc.player.horizontalCollision) {
            mc.player.setSprinting(true);
        }

        // 2. FullBright (Вечное ночное зрение)
        if (fullBright) {
            mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
        } else if (mc.player.hasEffect(MobEffects.NIGHT_VISION)) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
        }

        // 3. NoHurtCam (Отключение тряски экрана при получении урона)
        if (noHurtCam) {
            setIntField(mc.player, "hurtTime", 0);
        }

        // 4. Визуалы и движение меча в руке
        if (swordVisuals) {
            applySwordVisuals(mc);
        }

        long window = getWindow();
        if (window == 0L || isAnyScreenOpen(mc)) return;

        // 5. Открытие менюшки на Right Shift
        if (isJustPressed(window, menuBind)) {
            openMenuScreen(mc, new TuftuMenuScreen());
            return;
        }

        // 6. Срабатывание биндов в игре
        if (isJustPressed(window, pearlBind)) {
            throwPearl(mc);
        }
        if (isJustPressed(window, visualsBind)) {
            swordVisuals = !swordVisuals;
            showStatus(mc, "§b[Tuftu Visuals] §fВизуалы меча: " + (swordVisuals ? "§aВКЛ" : "§cВЫКЛ"));
        }
        if (isJustPressed(window, hurtCamBind)) {
            noHurtCam = !noHurtCam;
            showStatus(mc, "§b[Tuftu Visuals] §fNoHurtCam: " + (noHurtCam ? "§aВКЛ" : "§cВЫКЛ"));
        }
        if (isJustPressed(window, sprintBind)) {
            autoSprint = !autoSprint;
            showStatus(mc, "§b[Tuftu Visuals] §fAutoSprint: " + (autoSprint ? "§aВКЛ" : "§cВЫКЛ"));
        }
        if (isJustPressed(window, brightBind)) {
            fullBright = !fullBright;
            showStatus(mc, "§b[Tuftu Visuals] §fFullBright: " + (fullBright ? "§aВКЛ" : "§cВЫКЛ"));
        }
    }

    private static void applySwordVisuals(Minecraft mc) {
        // Настройка анимации взмаха
        if (swingMode == 0) { // Плавный PvP взмах
            int st = getIntField(mc.player, "swingTime");
            if (st > 0) {
                slowSwingTick++;
                if (slowSwingTick % 2 == 0) {
                    setIntField(mc.player, "swingTime", st - 1);
                }
            }
        } else if (swingMode == 1) { // Быстрый слэш
            int st = getIntField(mc.player, "swingTime");
            if (st > 0 && st < 4) {
                setIntField(mc.player, "swingTime", st + 1);
            }
        } else if (swingMode == 2) { // Без опускания меча (Без КД)
            setIntField(mc.player, "attackStrengthTicker", 100);
        }

        // Настройка высоты меча на экране
        float targetHeight = 1.0F;
        if (posMode == 0) targetHeight = 0.78F;      // Низко PvP
        else if (posMode == 1) targetHeight = 0.58F; // Очень низко
        else if (posMode == 2) targetHeight = 1.15F; // Высоко

        if (posMode != 3) {
            try {
                for (Field f : Minecraft.class.getDeclaredFields()) {
                    f.setAccessible(true);
                    Object obj = f.get(mc);
                    if (obj != null && obj.getClass().getSimpleName().contains("ItemInHand")) {
                        setFloatField(obj, "mainHandHeight", targetHeight);
                        setFloatField(obj, "oMainHandHeight", targetHeight);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static int getIntField(Object obj, String name) {
        try {
            Class<?> c = obj.getClass();
            while (c != null) {
                try {
                    Field f = c.getDeclaredField(name);
                    f.setAccessible(true);
                    return f.getInt(obj);
                } catch (NoSuchFieldException e) {
                    c = c.getSuperclass();
                }
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    private static void setIntField(Object obj, String name, int val) {
        try {
            Class<?> c = obj.getClass();
            while (c != null) {
                try {
                    Field f = c.getDeclaredField(name);
                    f.setAccessible(true);
                    f.setInt(obj, val);
                    return;
                } catch (NoSuchFieldException e) {
                    c = c.getSuperclass();
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void setFloatField(Object obj, String name, float val) {
        try {
            Class<?> c = obj.getClass();
            while (c != null) {
                try {
                    Field f = c.getDeclaredField(name);
                    f.setAccessible(true);
                    f.setFloat(obj, val);
                    return;
                } catch (NoSuchFieldException e) {
                    c = c.getSuperclass();
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static long getWindow() {
        try {
            return glfwGetCurrentContext != null ? (long) glfwGetCurrentContext.invoke(null) : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    private static int getKeyState(long window, int key) {
        try {
            return glfwGetKey != null ? (int) glfwGetKey.invoke(null, window, key) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int getMouseState(long window, int button) {
        try {
            return glfwGetMouseButton != null ? (int) glfwGetMouseButton.invoke(null, window, button) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static boolean isJustPressed(long window, int key) {
        if (key == 0) return false;
        boolean down = (key < 0) ? (getMouseState(window, -key) == KEY_PRESS) : (getKeyState(window, key) == KEY_PRESS);
        boolean wasDown = keyStates.getOrDefault(key, false);
        keyStates.put(key, down);
        return down && !wasDown;
    }

    private static void throwPearl(Minecraft mc) {
        int prevSlot = getSelectedSlot(mc);
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getItem(i).is(Items.ENDER_PEARL)) {
                setSelectedSlot(mc, i);
                mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
                swingHand(mc);
                setSelectedSlot(mc, prevSlot);
                showStatus(mc, "§d[Tuftu Visuals] §fЭндер-Перл брошен!");
                return;
            }
        }
        showStatus(mc, "§c[Tuftu Visuals] Нет Эндер-Перла в хотбаре!");
    }

    private static void swingHand(Minecraft mc) {
        try {
            for (Method m : mc.player.getClass().getMethods()) {
                if (m.getName().equals("swing") && m.getParameterCount() == 3) {
                    Class<?> 
