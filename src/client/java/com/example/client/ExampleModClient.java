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
                    Class<?> animClass = m.getParameterTypes()[1];
                    Object[] consts = animClass.getEnumConstants();
                    Object anim = (consts != null && consts.length > 0) ? consts[0] : null;
                    m.invoke(mc.player, InteractionHand.MAIN_HAND, anim, false);
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void showStatus(Minecraft mc, String msg) {
        try {
            Component comp = Component.literal(msg);
            for (Method m : mc.player.getClass().getMethods()) {
                if (m.getName().equals("displayClientMessage") && m.getParameterCount() == 2) {
                    m.invoke(mc.player, comp, true);
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static boolean isAnyScreenOpen(Minecraft mc) {
        try {
            for (Field f : Minecraft.class.getDeclaredFields()) {
                if (Screen.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    if (f.get(mc) != null) return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    private static void openMenuScreen(Minecraft mc, Screen screen) {
        try {
            for (Method m : Minecraft.class.getMethods()) {
                if (m.getParameterCount() == 1 && m.getParameterTypes()[0].isAssignableFrom(Screen.class)) {
                    m.invoke(mc, screen);
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static int getSelectedSlot(Minecraft mc) {
        try {
            Method m = mc.player.getInventory().getClass().getMethod("getSelectedSlot");
            return (int) m.invoke(mc.player.getInventory());
        } catch (Exception e) {
            try {
                Field f = mc.player.getInventory().getClass().getDeclaredField("selected");
                f.setAccessible(true);
                return f.getInt(mc.player.getInventory());
            } catch (Exception ex) {
                return 0;
            }
        }
    }

    private static void setSelectedSlot(Minecraft mc, int slot) {
        try {
            Method m = mc.player.getInventory().getClass().getMethod("setSelectedSlot", int.class);
            m.invoke(mc.player.getInventory(), slot);
        } catch (Exception e) {
            try {
                Field f = mc.player.getInventory().getClass().getDeclaredField("selected");
                f.setAccessible(true);
                f.setInt(mc.player.getInventory(), slot);
            } catch (Exception ignored) {
            }
        }
    }

    public static String getKeyName(int key) {
        if (key == 0) return "§7НЕТ";
        if (key < 0) return "§bMOUSE " + (-key + 1);
        switch (key) {
            case KEY_RIGHT_SHIFT: return "§bR-SHIFT";
            case KEY_LEFT_SHIFT: return "§bL-SHIFT";
            case KEY_LEFT_CONTROL: return "§bL-CTRL";
            case KEY_RIGHT_CONTROL: return "§bR-CTRL";
            case KEY_LEFT_ALT: return "§bL-ALT";
            case KEY_SPACE: return "§bSPACE";
            case KEY_TAB: return "§bTAB";
            case KEY_CAPS_LOCK: return "§bCAPS";
            default:
                try {
                    String name = glfwGetKeyName != null ? (String) glfwGetKeyName.invoke(null, key, 0) : null;
                    if (name != null) return "§b" + name.toUpperCase();
                } catch (Exception ignored) {
                }
                return "§bKEY_" + key;
        }
    }

    // ================= МЕНЮШКА НА RIGHT SHIFT =================
    public static class TuftuMenuScreen extends Screen {
        private int waitingForBind = 0;

        public TuftuMenuScreen() {
            super(Component.literal("§b§lTuftu Visuals PvP Menu"));
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int startY = this.height / 2 - 85;

            String[] swingNames = {"§dПлавный взмах", "§cБыстрый слэш", "§aБез КД", "§7Стандарт"};
            String[] posNames = {"§aНизко PvP", "§bОчень низко", "§eВысоко", "§7Стандарт"};

            // 1. Визуалы меча + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("Визуалы Меча: " + (swordVisuals ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { swordVisuals = !swordVisuals; refresh(); }
            ).bounds(centerX - 155, startY, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 2 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(visualsBind)),
                    btn -> { waitingForBind = 2; refresh(); }
            ).bounds(centerX + 50, startY, 105, 20).build());

            // 2. Взмах меча и Позиция меча
            this.addRenderableWidget(Button.builder(
                    Component.literal("Взмах: " + swingNames[swingMode]),
                    btn -> { swingMode = (swingMode + 1) % swingNames.length; refresh(); }
            ).bounds(centerX - 155, startY + 24, 152, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal("Позиция: " + posNames[posMode]),
                    btn -> { posMode = (posMode + 1) % posNames.length; refresh(); }
            ).bounds(centerX + 3, startY + 24, 152, 20).build());

            // 3. NoHurtCam + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("NoHurtCam (Анти-тряска): " + (noHurtCam ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { noHurtCam = !noHurtCam; refresh(); }
            ).bounds(centerX - 155, startY + 48, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 6 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(hurtCamBind)),
                    btn -> { waitingForBind = 6; refresh(); }
            ).bounds(centerX + 50, startY + 48, 105, 20).build());

            // 4. Бинд на Эндер-Перл
            this.addRenderableWidget(Button.builder(
                    Component.literal("§d§lБросок Эндер-Перла (Авто-свап)"),
                    btn -> { waitingForBind = 1; refresh(); }
            ).bounds(centerX - 155, startY + 76, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 1 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(pearlBind)),
                    btn -> { waitingForBind = 1; refresh(); }
            ).bounds(centerX + 50, startY + 76, 105, 20).build());

            // 5. AutoSprint + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("AutoSprint (Авто-бег): " + (autoSprint ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { autoSprint = !autoSprint; refresh(); }
            ).bounds(centerX - 155, startY + 100, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 3 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(sprintBind)),
                    btn -> { waitingForBind = 3; refresh(); }
            ).bounds(centerX + 50, startY + 100, 105, 20).build());

            // 6. FullBright + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("FullBright (Яркость): " + (fullBright ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { fullBright = !fullBright; refresh(); }
            ).bounds(centerX - 155, startY + 124, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 4 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(brightBind)),
                    btn -> { waitingForBind = 4; refresh(); }
            ).bounds(centerX + 50, startY + 124, 105, 20).build());

            // 7. Кнопка открытия меню + Закрыть
            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 5 ? "§e> Нажмите кнопку меню <" : "Кнопка меню: " + getKeyName(menuBind)),
                    btn -> { waitingForBind = 5; refresh(); }
            ).bounds(centerX - 155, startY + 152, 180, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal("§a§lГотово"),
                    btn -> this.onClose()
            ).bounds(centerX + 30, startY + 152, 125, 20).build());
        }

        private void refresh() {
            this.clearWidgets();
            this.init();
        }

        @Override
        public void tick() {
            if (waitingForBind == 0) return;
            long window = getWindow();
            if (window == 0L) return;

            if (getKeyState(window, KEY_ESCAPE) == KEY_PRESS || getKeyState(window, KEY_DELETE) == KEY_PRESS) {
                if (waitingForBind != 5) applyBind(0);
                waitingForBind = 0;
                refresh();
                return;
            }

            for (int key = 32; key <= 348; key++) {
                if (getKeyState(window, key) == KEY_PRESS) {
                    applyBind(key);
                    keyStates.put(key, true);
                    waitingForBind = 0;
                    refresh();
                    return;
                }
            }

            for (int m = 2; m <= 4; m++) {
                if (getMouseState(window, m) == KEY_PRESS) {
                    applyBind(-m);
                    keyStates.put(-m, true);
                    waitingForBind = 0;
                    refresh();
                    return;
                }
            }
        }

        private void applyBind(int key) {
            if (waitingForBind == 1) pearlBind = key;
            else if (waitingForBind == 2) visualsBind = key;
            else if (waitingForBind == 3) sprintBind = key;
            else if (waitingForBind == 4) brightBind = key;
            else if (waitingForBind == 5 && key != 0) menuBind = key;
            else if (waitingForBind == 6) hurtCamBind = key;
        }
    }
}
