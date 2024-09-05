package io.github.chrstncrrnd;

import io.github.chrstncrrnd.config.Config;
import io.github.chrstncrrnd.config.ConfigScreen;
import io.github.chrstncrrnd.render.Render;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Hudinfo implements ClientModInitializer {
	public static final String MOD_ID = "hud-info";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {

		Config.instance.loadConfig();

        LOGGER.info("Loading HUD info");
		HudRenderCallback.EVENT.register((Render::renderHud));

		KeyBinding configScreenKb = new KeyBinding("Open Config", GLFW.GLFW_KEY_N, "HUD Info");
		KeyBindingHelper.registerKeyBinding(configScreenKb);

		MinecraftClient mc = MinecraftClient.getInstance();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(configScreenKb.isPressed()) {
				mc.setScreen(new ConfigScreen(mc.currentScreen));
			}
		});

		ClientLifecycleEvents.CLIENT_STOPPING.register((c) -> Config.instance.saveConfig());

	}

}