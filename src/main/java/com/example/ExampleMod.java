package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

    private static KeyBinding pearlKey;
    private static KeyBinding toggleVisualsKey;

    public static boolean customSwordViewModel = true;

    @Override
    public void onInitializeClient() {
        pearlKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Бинд на Эндер-Перл", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "PvP Visuals Mod"
        ));

        toggleVisualsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Визуалы меча (ViewModel)", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "PvP Visuals Mod"
        ));

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient) {
                for (int i = 0; i < 12; i++) {
                    world.addParticle(ParticleTypes.CRIT, entity.getX(), entity.getY() + 1.0, entity.getZ(),
                            (Math.random() - 0.5) * 0.5, Math.random() * 0.5, (Math.random() - 0.5) * 0.5);
                    world.addParticle(ParticleTypes.HEART, entity.getX(), entity.getY() + 1.5, entity.getZ(),
                            (Math.random() - 0.5) * 0.2, 0.1, (Math.random() - 0.5) * 0.2);
                }
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.interactionManager == null) return;

            if (client.player.forwardSpeed > 0 && !client.player.isSneaking() && !client.player.horizontalCollision) {
                client.player.setSprinting(true);
            }

            client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));

            while (pearlKey.wasPressed()) {
                throwPearl(client);
            }
            while (toggleVisualsKey.wasPressed()) {
                customSwordViewModel = !customSwordViewModel;
                client.player.sendMessage(Text.literal("§b[PvP] §fВизуалы меча: " + (customSwordViewModel ? "§aВКЛ" : "§cВЫКЛ")), true);
            }
        });
    }

    private void throwPearl(net.minecraft.client.MinecraftClient client) {
        int previousSlot = client.player.getInventory().selectedSlot;
        for (int i = 0; i < 9; i++) {
            if (client.player.getInventory().getStack(i).isOf(Items.ENDER_PEARL)) {
                client.player.getInventory().selectedSlot = i;
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                client.player.swingHand(Hand.MAIN_HAND);
                client.player.getInventory().selectedSlot = previousSlot;
                return;
            }
        }
        client.player.sendMessage(Text.literal("§c[PvP] Нет Эндер-Перла в хотбаре!"), true);
    }
}
