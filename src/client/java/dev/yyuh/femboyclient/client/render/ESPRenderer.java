package dev.yyuh.femboyclient.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ESPRenderer {

    public static final RenderLayer ESP_LINES = CustomRenderLayer.createESPLines();

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawBox(Box box, Color4b color, float lineWidth, int colorHex) {
        if (mc.world == null || mc.player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        MatrixStack matrices = new MatrixStack();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        VertexConsumerProvider vertexConsumers = mc.getBufferBuilders().getEntityVertexConsumers();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ESP_LINES);

        RenderSystem.setShaderColor(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, color.a / 255.0F);

        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth(lineWidth);

        drawOutlinedBox(matrices, vertexConsumer, box, colorHex);

        RenderSystem.disableBlend();
    }

    public static void drawOutlinedBox(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, int color) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        drawLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.minX, box.minY, box.maxZ, color); // Back left
        drawLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.minX, box.maxY, box.minZ, color); // Back bottom
        drawLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.maxX, box.minY, box.minZ, color); // Back right
        drawLine(vertexConsumer, matrix, box.maxX, box.minY, box.minZ, box.maxX, box.maxY, box.minZ, color); // Front right
        drawLine(vertexConsumer, matrix, box.maxX, box.minY, box.minZ, box.maxX, box.minY, box.maxZ, color); // Front bottom
        drawLine(vertexConsumer, matrix, box.maxX, box.minY, box.maxZ, box.minX, box.minY, box.maxZ, color); // Back right
        drawLine(vertexConsumer, matrix, box.minX, box.minY, box.maxZ, box.minX, box.maxY, box.maxZ, color); // Back top left
        drawLine(vertexConsumer, matrix, box.minX, box.maxY, box.minZ, box.minX, box.maxY, box.maxZ, color); // Top left
        drawLine(vertexConsumer, matrix, box.maxX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ, color); // Top right
        drawLine(vertexConsumer, matrix, box.minX, box.maxY, box.maxZ, box.maxX, box.maxY, box.maxZ, color); // Top front
        drawLine(vertexConsumer, matrix, box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.minZ, color); // Top back
        drawLine(vertexConsumer, matrix, box.maxX, box.maxY, box.minZ, box.maxX, box.minY, box.minZ, color); // Right back
        drawLine(vertexConsumer, matrix, box.maxX, box.minY, box.maxZ, box.maxX, box.maxY, box.maxZ, color); // Right front
        drawLine(vertexConsumer, matrix, box.minX, box.maxY, box.maxZ, box.minX, box.minY, box.maxZ, color); // Left front
    }

    private static void drawLine(VertexConsumer vertexConsumer, Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, int color) {
        int overlay = 0;
        int light = 0xF000F0;
        float normalX = 0.0f;
        float normalY = 1.0f;
        float normalZ = 0.0f;

        vertexConsumer.vertex(matrix, (float) x1, (float) y1, (float) z1)
                .color(color)
                .overlay(overlay)
                .light(light)
                .normal(normalX, normalY, normalZ);

        vertexConsumer.vertex(matrix, (float) x2, (float) y2, (float) z2)
                .color(color)
                .overlay(overlay)
                .light(light)
                .normal(normalX, normalY, normalZ);
    }

    public static class Color4b {
        public final int r, g, b, a;

        public Color4b(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }
    }
}