package io.github.chrstncrrnd.render;

import io.github.chrstncrrnd.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.world.dimension.DimensionTypes;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Render {

    static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void renderHud(DrawContext drawContext, RenderTickCounter tickCounter) {
        RenderPosition position = Config.instance.renderPosition;
        ArrayList<String> textBuffer = new ArrayList<>();
//        FPS
        if(Config.instance.showFps){
            int fps = mc.getCurrentFps();
            textBuffer.add("FPS: " + fps);
        }
//        Server TPS
        if (Config.instance.showTps){
            float serverTPS = mc.world.getTickManager().getTickRate();
            textBuffer.add("TPS: " + serverTPS);
        }
//        Position (x,y,z)
        if (Config.instance.showCoords || Config.instance.showDimensionCoords){

            double positionX = mc.player.getPos().x;
            double positionY = mc.player.getPos().y;
            double positionZ = mc.player.getPos().z;
            if (Config.instance.showCoords){
                textBuffer.add("X: " + round(positionX) + " Y: " + round(positionY) + " Z: " + round(positionZ));
            }
            if (Config.instance.showDimensionCoords){
//        Position in nether (x,y,z)
                if (mc.world.getDimensionEntry().matchesId(DimensionTypes.OVERWORLD_ID)){
                    textBuffer.add("Nether position:");
                    textBuffer.add("X: " + round(positionX / 8) + " Y: " + round(positionY) + " Z: " + round(positionZ / 8));
                }else if (mc.world.getDimensionEntry().matchesId(DimensionTypes.THE_NETHER_ID)){
                    textBuffer.add("Overworld position:");
                    textBuffer.add("X: " + round(positionX * 8) + " Y: " + round(positionY) + " Z: " + round(positionZ * 8));
                }
//        skip end dimension
            }
        }
        TextRenderer tr = mc.textRenderer;

        int vertSpacing = tr.fontHeight + 1;
        int yStart;
        int windowHeight = drawContext.getScaledWindowHeight();
        int windowWidth = drawContext.getScaledWindowWidth();
        if (position.isTop()){
            yStart = 0;
        }else{
           yStart = (windowHeight - textBuffer.size() * vertSpacing);
        }


        if (position.isLeft()) {
            for (int i = 0; i < textBuffer.size(); i ++) {
                String text = textBuffer.get(i);
                drawContext.drawText(tr, text, 0, yStart + (i * vertSpacing), 0xFFFFFF, false);
            }
        }else{
            for (int i = 0; i < textBuffer.size(); i ++) {
                String text = textBuffer.get(i);
                int textWidth = tr.getWidth(text);
                drawContext.drawText(tr, text, windowWidth - textWidth,yStart + (i * vertSpacing),  0xFFFFFF, false);
            }
        }

    }

    private static String round(double num){
        return new DecimalFormat("#.00").format(num);
    }


}
