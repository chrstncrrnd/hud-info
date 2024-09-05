package io.github.chrstncrrnd.render;

import net.minecraft.text.Text;
import net.minecraft.util.TranslatableOption;

public enum RenderPosition implements TranslatableOption {
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight;

    public boolean isTop() {
        return this.toString().startsWith("Top");
    }

    public boolean isLeft() {
        return this.toString().endsWith("Left");
    }


    @Override
    public int getId() {
        return this.ordinal();
    }

    public static RenderPosition byId(int id) {
        return RenderPosition.values()[id];
    }

    @Override
    public String getTranslationKey() {
        return this.toString();
    }

    @Override
    public Text getText() {
        return TranslatableOption.super.getText();
    }
}
