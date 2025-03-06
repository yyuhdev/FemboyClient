package dev.yyuh.femboyclient.client.render;

import net.minecraft.client.render.RenderPhase;

import java.util.OptionalDouble;

public class CustomRenderPhase extends RenderPhase {
    public static final LineWidth LINE_WIDTH_2 = new LineWidth(OptionalDouble.of(2));

    private CustomRenderPhase(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    public static class LineWidth extends RenderPhase.LineWidth {
        public LineWidth(OptionalDouble width) {
            super(width);
        }
    }
}