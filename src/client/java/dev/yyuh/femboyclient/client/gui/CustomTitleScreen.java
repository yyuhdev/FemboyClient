package dev.yyuh.femboyclient.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;

public class CustomTitleScreen extends Screen {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of("femboyclient", "background.png");
    private static final Identifier ICON = Identifier.of("femboyclient", "femboyclient_logo.png");
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 4;
    private static final int BUTTON_TEXT_COLOR = 0xFFFFFFFF;
    private static final int BUTTON_COLOR = 0x80333333;
    private static final int BUTTON_BORDER_COLOR = 0xFFFF69B4;

    public CustomTitleScreen() {
        super(Text.literal("FemboyClient - Main Menu"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 50;

        addStyledButton(centerX - BUTTON_WIDTH / 2, startY, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Singleplayer", button -> this.client.setScreen(new SelectWorldScreen(this)));

        addStyledButton(centerX - BUTTON_WIDTH / 2, startY + BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Servers", button -> this.client.setScreen(new MultiplayerScreen(this)));

        addStyledButton(centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_SPACING) * 2, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Settings", button -> this.client.setScreen(new OptionsScreen(this, this.client.options)));

        addStyledButton(centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_SPACING) * 3, BUTTON_WIDTH, BUTTON_HEIGHT,
                "ClickGUI", button -> this.client.setScreen(new ClickGUIScreen()));

        addStyledButton(centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_SPACING) * 5, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit", button -> this.client.scheduleStop());
    }

    private void addStyledButton(int x, int y, int width, int height, String text, ButtonWidget.PressAction action) {
        ButtonWidget customButton = new ButtonWidget(
                x, y,
                width, height,
                Text.of(text),
                action, null) {

            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                // Background
                context.fill(x, y, x + width, y + height, BUTTON_COLOR);

                // Border
                context.fill(x, y, x + width, y + 1, BUTTON_BORDER_COLOR);
                context.fill(x, y + height - 1, x + width, y + height, BUTTON_BORDER_COLOR);
                context.fill(x, y, x + 1, y + height, BUTTON_BORDER_COLOR);
                context.fill(x + width - 1, y, x + width, y + height, BUTTON_BORDER_COLOR);

                if(isHovered()) context.fill(x, y, x + width, y + height, 0x20FFFFFF);

                // Text
                int textX = x + (width - textRenderer.getWidth(Text.of(text))) / 2;
                int textY = y + (height - 8) / 2;
                context.drawText(MinecraftClient.getInstance().textRenderer, Text.of(text), textX, textY, BUTTON_TEXT_COLOR, false);
            }

        };

        this.addDrawableChild(customButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int textureWidth = 1920;
        int textureHeight = 1080;
        context.drawTexture(BACKGROUND_TEXTURE, 0, 0, 0, 0, this.width, this.height, textureWidth, textureHeight);

        super.render(context, mouseX, mouseY, delta);

        int iconWidth = 128;
        int iconHeight = 128;
        int x = (width - iconWidth) / 2;
        int y = (height - iconHeight) / 4;

        context.drawTexture(ICON, x, y - 30, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);

        String version = "Copyright Mojang AB";
        String buildInfo = "Build date 2025/01/12 15:58";
        context.drawText(this.textRenderer, version, this.width - this.textRenderer.getWidth(version) - 5, this.height - 20, 0xFFFFFFFF, false);
        context.drawText(this.textRenderer, buildInfo, this.width - this.textRenderer.getWidth(buildInfo) - 5, this.height - 10, 0xFFFFFFFF, false);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}