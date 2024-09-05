package io.github.chrstncrrnd.config;

import com.mojang.serialization.Codec;
import io.github.chrstncrrnd.render.RenderPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Arrays;

public class ConfigScreen extends GameOptionsScreen {
    public ConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.of("Config"));
    }

    @Override
    protected void addOptions() {
        this.body.addAll(new SimpleOption<>(
                "Render Position",
                SimpleOption.emptyTooltip(),
                SimpleOption.enumValueText(),
                new SimpleOption.PotentialValuesBasedCallbacks<RenderPosition>(
                        Arrays.asList(RenderPosition.values()),
                        Codec.INT.xmap(RenderPosition::byId, RenderPosition::getId)),
                Config.instance.renderPosition,
                renderPosition -> {
                    Config.instance.renderPosition = renderPosition;
                }),
                SimpleOption.ofBoolean("Show FPS", Config.instance.showFps, b -> Config.instance.showFps = b),
                SimpleOption.ofBoolean("Show TPS", Config.instance.showTps, b -> Config.instance.showTps = b),
                SimpleOption.ofBoolean("Show Coords", Config.instance.showCoords, b -> Config.instance.showCoords = b),
                SimpleOption.ofBoolean("Show Dimension Coords", Config.instance.showDimensionCoords, b -> Config.instance.showDimensionCoords = b)
                );

    }
}
