package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExampleModClient implements ClientModInitializer {

    // Настройки функций
    public static boolean swordVisuals = true;
    public static int swingMode = 0; // 0: Плавный PvP, 1: Слэш, 2: Спин 360, 3: Выкл
    public static int scaleMode = 1; // 0: 60%, 1: 75%, 2: 90%, 3: 115%
    public static int posMode = 0;   // 0: Далеко PvP, 1: Низко, 2: Близко, 3: Стандарт
    public static boolean autoSprint = true;
    public static boolean fullBright = true;

    // Кнопки биндов (по умолчанию: Меню = Right Shift, Перл = Z, Визуалы = V)
    public static int menuBind = GLFW.GLFW_KEY_RIGHT_SHIFT;
    public static int pearlBind = GLFW.GLFW_KEY_Z;
    public static int visualsBind = GLFW.GLFW_KEY_V;
    public static int sprintBind = 0;
    public static int brightBind = 0;

    private static final Map<Integer, Boolean> keyStates = new HashMap<>();

    @Override
    public void onInitializeClient() {
    }

    public static void onClientTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;

        // Авто-спринт
        if (autoSprint && mc.options.keyUp.isDown() && !mc.player.isShiftKeyDown() && !mc.player.horizontalCollision) {
            mc.player.setSprinting(true);
        }

        // FullBright (Ночное зрение)
        if (fullBright) {
            mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
        } else if (mc.player.hasEffect(MobEffects.NIGHT_VISION)) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
        }

        long window = GLFW.glfwGetCurrentContext();
        if (window == 0L || mc.screen != null) return;

        // Открытие меню на Right Shift (или выбранный бинд)
        if (isJustPressed(window, menuBind)) {
            mc.setScreen(new TuftuMenuScreen());
            return;
        }

        // Срабатывание биндов в игре
        if (isJustPressed(window, pearlBind)) {
            throwPearl(mc);
        }
        if (isJustPressed(window, visualsBind)) {
            swordVisuals = !swordVisuals;
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fВизуалы меча: " + (swordVisuals ? "§aВКЛ" : "§cВЫКЛ")), false);
        }
        if (isJustPressed(window, sprintBind)) {
            autoSprint = !autoSprint;
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fAutoSprint: " + (autoSprint ? "§aВКЛ" : "§cВЫКЛ")), false);
        }
        if (isJustPressed(window, brightBind)) {
            fullBright = !fullBright;
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fFullBright: " + (fullBright ? "§aВКЛ" : "§cВЫКЛ")), false);
        }
    }

    private static boolean isJustPressed(long window, int key) {
        if (key == 0) return false;
        boolean down;
        if (key < 0) { // Боковые кнопки мыши
            down = GLFW.glfwGetMouseButton(window, -key) == GLFW.GLFW_PRESS;
        } else {
            down = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
        }
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
                mc.player.swing(InteractionHand.MAIN_HAND);
                setSelectedSlot(mc, prevSlot);
                mc.gui.setOverlayMessage(Component.literal("§d[Tuftu Visuals] §fЭндер-Перл брошен!"), false);
                return;
            }
        }
        mc.gui.setOverlayMessage(Component.literal("§c[Tuftu Visuals] Нет Эндер-Перла в хотбаре!"), false);
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
            case GLFW.GLFW_KEY_RIGHT_SHIFT: return "§bR-SHIFT";
            case GLFW.GLFW_KEY_LEFT_SHIFT: return "§bL-SHIFT";
            case GLFW.GLFW_KEY_LEFT_CONTROL: return "§bL-CTRL";
            case GLFW.GLFW_KEY_RIGHT_CONTROL: return "§bR-CTRL";
            case GLFW.GLFW_KEY_LEFT_ALT: return "§bL-ALT";
            case GLFW.GLFW_KEY_SPACE: return "§bSPACE";
            case GLFW.GLFW_KEY_TAB: return "§bTAB";
            case GLFW.GLFW_KEY_CAPS_LOCK: return "§bCAPS";
            default:
                String name = GLFW.glfwGetKeyName(key, 0);
                return name != null ? "§b" + name.toUpperCase() : "§bKEY_" + key;
        }
    }

    // ================= МЕНЮШКА НА RIGHT SHIFT =================
    public static class TuftuMenuScreen extends Screen {
        private int waitingForBind = 0; // 0 = нет, 1 = Перл, 2 = Визуалы, 3 = Спринт, 4 = Яркость, 5 = Меню

        public TuftuMenuScreen() {
            super(Component.literal("§b§lTuftu Visuals PvP Menu"));
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int startY = this.height / 2 - 85;

            String[] swingNames = {"§dПлавный PvP", "§cЖесткий Слэш", "§eСпин 360°", "§7Классика"};
            String[] scaleNames = {"§bМини (60%)", "§aPvP (75%)", "§eСредний (90%)", "§cБольшой (115%)"};
            String[] posNames = {"§aДалеко PvP", "§bНизко", "§eПо центру", "§7Стандарт"};

            // 1. Визуалы меча + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("Визуалы Меча: " + (swordVisuals ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { swordVisuals = !swordVisuals; refresh(); }
            ).bounds(centerX - 155, startY, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 2 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(visualsBind)),
                    btn -> { waitingForBind = 2; refresh(); }
            ).bounds(centerX + 50, startY, 105, 20).build());

            // 2. Анимация удара меча
            this.addRenderableWidget(Button.builder(
                    Component.literal("Анимация удара: " + swingNames[swingMode]),
                    btn -> { swingMode = (swingMode + 1) % swingNames.length; refresh(); }
            ).bounds(centerX - 155, startY + 24, 310, 20).build());

            // 3. Размер меча
            this.addRenderableWidget(Button.builder(
                    Component.literal("Размер меча: " + scaleNames[scaleMode]),
                    btn -> { scaleMode = (scaleMode + 1) % scaleNames.length; refresh(); }
            ).bounds(centerX - 155, startY + 48, 152, 20).build());

            // 4. Позиция меча
            this.addRenderableWidget(Button.builder(
                    Component.literal("Позиция: " + posNames[posMode]),
                    btn -> { posMode = (posMode + 1) % posNames.length; refresh(); }
            ).bounds(centerX + 3, startY + 48, 152, 20).build());

            // 5. Бинд на Эндер-Перл
            this.addRenderableWidget(Button.builder(
                    Component.literal("§d§lБросок Эндер-Перла (Авто-свап)"),
                    btn -> { waitingForBind = 1; refresh(); }
            ).bounds(centerX - 155, startY + 76, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 1 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(pearlBind)),
                    btn -> { waitingForBind = 1; refresh(); }
            ).bounds(centerX + 50, startY + 76, 105, 20).build());

            // 6. AutoSprint + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("AutoSprint (Авто-бег): " + (autoSprint ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { autoSprint = !autoSprint; refresh(); }
            ).bounds(centerX - 155, startY + 100, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 3 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(sprintBind)),
                    btn -> { waitingForBind = 3; refresh(); }
            ).bounds(centerX + 50, startY + 100, 105, 20).build());

            // 7. FullBright + Бинд
            this.addRenderableWidget(Button.builder(
                    Component.literal("FullBright (Яркость): " + (fullBright ? "§a§lВКЛ" : "§c§lВЫКЛ")),
                    btn -> { fullBright = !fullBright; refresh(); }
            ).bounds(centerX - 155, startY + 124, 200, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 4 ? "§e> Нажмите кнопку <" : "Бинд: " + getKeyName(brightBind)),
                    btn -> { waitingForBind = 4; refresh(); }
            ).bounds(centerX + 50, startY + 124, 105, 20).build());

            // 8. Кнопка открытия меню + Закрыть
            this.addRenderableWidget(Button.builder(
                    Component.literal(waitingForBind == 5 ? "§e> Нажмите кнопку меню <" : "Кнопка меню: " + getKeyName(menuBind)),
                    btn -> { waitingForBind = 5; refresh(); }
            ).bounds(centerX - 155, startY + 152, 180, 20).build());

            this.addRenderableWidget(Button.builder(
                    Component.literal("§a§lГотово"),
                    btn -> Minecraft.getInstance().setScreen(null)
            ).bounds(centerX + 30, startY + 152, 125, 20).build());
        }

        private void refresh() {
            this.clearWidgets();
            this.init();
        }

        @Override
        public void tick() {
            if (waitingForBind == 0) return;
            long window = GLFW.glfwGetCurrentContext();
            if (window == 0L) return;

            // Отмена/очистка бинда на ESC или DELETE
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                if (waitingForBind != 5) applyBind(0);
                waitingForBind = 0;
                refresh();
                return;
            }

            // Проверка нажатия любой клавиши клавиатуры
            for (int key = 32; key <= 348; key++) {
                if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                    applyBind(key);
                    keyStates.put(key, true);
                    waitingForBind = 0;
                    refresh();
                    return;
                }
            }

            // Проверка боковых кнопок мыши (Mouse 3, 4, 5)
            for (int m = 2; m <= 4; m++) {
                if (GLFW.glfwGetMouseButton(window, m) == GLFW.GLFW_PRESS) {
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
        }
    }
}
