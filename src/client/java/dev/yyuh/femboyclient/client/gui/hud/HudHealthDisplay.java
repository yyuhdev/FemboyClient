package dev.yyuh.femboyclient.client.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class HudHealthDisplay extends HudElement {
    private LivingEntity target;
    private int backgroundColor = 0xAA111111;
    private int textColor = 0xFFFFFF;
    private int healthColor = 0xFF5555;
    private int barBackgroundColor = 0x66000000;
    private int barForegroundColor = 0xFF55FF55;

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public HudHealthDisplay(int x, int y) {
        super(x, y, 100, 40);
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void render(DrawContext context) {
        if(target == null) {

            float health = 20F;
            float maxHealth = 20F;
            String targetName = "Player";

            context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor);

            int textX = getX() + 6;
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of(targetName), textX, getY() + 6, textColor);

            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of(String.format("%.1f ❤", health)), textX, getY() + 18, healthColor);

            int barWidth = 60;
            int barHeight = 6;
            int barX = textX;
            int barY = getY() + 30;

            int currentBarWidth = (int) ((health / maxHealth) * barWidth);
            context.fill(barX, barY, barX + barWidth, barY + barHeight, barBackgroundColor);
            context.fill(barX, barY, barX + currentBarWidth, barY + barHeight, barForegroundColor);

            return;
        }

        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        String targetName = target.getDisplayName().getString();

        int boxWidth = 100;
        int boxHeight = 40;

        int backgroundColor = 0xAA111111;
        context.fill(getX(), getY(), getX() + boxWidth, getY() + boxHeight, backgroundColor);

        int textX = getX() + 6;
        context.drawTextWithShadow(mc.textRenderer, Text.of(targetName), textX, getY() + 6, 0xFFFFFF);
        context.drawTextWithShadow(mc.textRenderer, Text.of(String.format("%.1f ❤", health)), textX, getY() + 18, 0xFF5555);

        int barWidth = 60;
        int barHeight = 6;
        int barX = textX;
        int barY = getY() + 30;

        int currentBarWidth = (int) ((health / maxHealth) * barWidth);
        context.fill(barX, barY, barX + barWidth, barY + barHeight, 0x66000000);
        context.fill(barX, barY, barX + currentBarWidth, barY + barHeight, 0xFF55FF55);
    }
}