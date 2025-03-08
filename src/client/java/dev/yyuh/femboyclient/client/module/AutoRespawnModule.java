// AutoRespawnModule.java
package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.ClickGUIScreen;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class AutoRespawnModule extends Button {
    public static boolean enabled = false;
    public static double respawnDelay = 0.0; // In seconds
    private static final String CONFIG_ENABLED = "modules.auto_respawn.enabled";
    private static final String CONFIG_DELAY = "modules.auto_respawn.delay";
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private int deathTickCounter = 0;

    public AutoRespawnModule() {
        super("Auto Respawn", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
    }

    @Override
    public Screen createSettingsPanel() {
        System.out.println("Auto Respawn");
        return new AutoRespawnScreen();
    }

    @Override
    public void save() {
        ConfigUtils.save(CONFIG_ENABLED, enabled);
        ConfigUtils.save(CONFIG_DELAY, respawnDelay);
    }

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if (!enabled) return;

        if (mc.player.isDead()) {
            deathTickCounter++;
            if (deathTickCounter >= respawnDelay * 20) {
                mc.player.requestRespawn();
                deathTickCounter = 0;
            }
        } else {
            deathTickCounter = 0;
        }
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_ENABLED);
        enabled = this.toggled;
        respawnDelay = ConfigUtils.getInt(CONFIG_DELAY, 0);
    }

    public static boolean isToggled() {
        return enabled;
    }


    public static class AutoRespawnScreen extends Screen {
        private SliderWidget delaySlider;
        private int windowX, windowY, windowWidth, windowHeight;
        private static final int PADDING = 5;
        private static final int TITLE_BAR_HEIGHT = 20;
        private static final int COMPONENT_SPACING = 5;
        private static final int BORDER_COLOR = 0xFFFF69B4;
        private static final int DARK_GRAY = 0x80333333;

        public AutoRespawnScreen() {
            super(Text.of("Auto Respawn Settings"));
        }

        @Override
        protected void init() {
            windowWidth = 180;
            windowHeight = TITLE_BAR_HEIGHT + (20 * 2) + (COMPONENT_SPACING * 1) + (PADDING * 2);
            windowX = (this.width - windowWidth) / 2;
            windowY = (this.height - windowHeight) / 2;

            int componentY = windowY + TITLE_BAR_HEIGHT + PADDING;

            delaySlider = new SliderWidget(
                    windowX + PADDING, componentY,
                    windowWidth - PADDING * 2, 20,
                    Text.of("Delay: " + String.format("%.1f", AutoRespawnModule.respawnDelay) + "s"),
                    AutoRespawnModule.respawnDelay
            ) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of("Delay: " + String.format("%.1f", AutoRespawnModule.respawnDelay) + "s"));
                }

                @Override
                protected void applyValue() {
                    AutoRespawnModule.respawnDelay = this.value;
                    ConfigUtils.save(CONFIG_DELAY, respawnDelay);
                }

                @Override
                public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                    context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, DARK_GRAY);
                    // Filled portion
                    context.fill(this.getX(), this.getY(), (int)(this.getX() + this.value * this.width), this.getY() + this.height, BORDER_COLOR);
                    // Border
                    context.drawBorder(this.getX(), this.getY(), this.width, this.height, BORDER_COLOR);
                    // Text
                    context.drawCenteredTextWithShadow(
                            MinecraftClient.getInstance().textRenderer,
                            this.getMessage(),
                            this.getX() + this.width / 2,
                            this.getY() + (this.height - 8) / 2,
                            0xFFFFFF
                    );
                }
            };

            componentY += 20 + COMPONENT_SPACING;

            ButtonWidget backButton = new ButtonWidget(
                    windowX + PADDING, componentY,
                    windowWidth - PADDING * 2, 20,
                    Text.of("Back"),
                    button -> this.client.setScreen(new ClickGUIScreen()), null) {
                @Override
                public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                    int bgColor = isHovered() ? 0x20FFFFFF : DARK_GRAY;
                    context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, bgColor);

                    context.drawBorder(this.getX(), this.getY(), this.width, this.height, BORDER_COLOR);

                    context.drawCenteredTextWithShadow(
                            mc.textRenderer,
                            this.getMessage(),
                            this.getX() + this.width / 2,
                            this.getY() + (this.height - 8) / 2,
                            0xFFFFFF
                    );
                }
            };

            this.addDrawableChild(delaySlider);
            this.addDrawableChild(backButton);
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
            super.render(context, mouseX, mouseY, partialTicks);
            context.fill(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 0x80333333);

            context.drawBorder(windowX, windowY, windowWidth, windowHeight, BORDER_COLOR);

            context.drawText(
                    this.textRenderer,
                    this.getTitle(),
                    windowX + PADDING,
                    windowY + 6,
                    0xFFFFFF,
                    false
            );

        }

        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}