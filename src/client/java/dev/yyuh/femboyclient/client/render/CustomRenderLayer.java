package dev.yyuh.femboyclient.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.OptionalDouble;

public class CustomRenderLayer extends RenderLayer {
    private CustomRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer createESPLines() {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(RenderLayer.LINES_PROGRAM)
                .lineWidth(new LineWidth(OptionalDouble.of(2)))
                .layering(RenderLayer.VIEW_OFFSET_Z_LAYERING)
                .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                .target(RenderLayer.ITEM_ENTITY_TARGET)
                .writeMaskState(RenderLayer.ALL_MASK)
                .depthTest(RenderLayer.ALWAYS_DEPTH_TEST)
                .cull(RenderLayer.DISABLE_CULLING)
                .build(false);

        return RenderLayer.of("femboy:esp_lines", VertexFormats.LINES, VertexFormat.DrawMode.LINES, 1536, false, true, multiPhaseParameters);
    }
}