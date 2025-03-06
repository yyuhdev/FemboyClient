package dev.yyuh.femboyclient.client.gui.render;

import dev.yyuh.femboyclient.client.module.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ClickGui {
    private final List<Window> windows = new ArrayList<>();
    private static final int WINDOW_SPACING = 5;
    private static final Identifier ASTOLFO_TEXTURE = Identifier.of("femboyclient", "astolfo-textur.png");
    public static List<Component> renderModules = new ArrayList<>();
    public static List<Component> pvpModules = new ArrayList<>();
    public static List<Component> utilityModules = new ArrayList<>();
    public static List<Component> movementModules = new ArrayList<>();
    public static List<Component> animesuchtModules = new ArrayList<>();

    public ClickGui() {

        renderModules.add(new NoFoodBarModule());
        renderModules.add(new NoArmorBarModule());
        renderModules.add(new HideItemToolTipModule());
        renderModules.add(new ItemSizeModule());
        renderModules.add(new PlayerESPModule());
        renderModules.add(new EntityESPModule());
        renderModules.add(new ItemESPModule());
        renderModules.add(new FullbrightModule());
        Window render = new Window("Rendering", renderModules);

        pvpModules.add(new TriggerBotModule());
//        pvpModules.add(new CrystalOptimizerModule());
        pvpModules.add(new AutoTotemModule());
        pvpModules.add(new TimerModule());
        pvpModules.add(new FastAnchorModule());
        pvpModules.add(new QuickAirPlaceModule());
        pvpModules.add(new KillAuraModule());
        Window pvp = new Window("Combat", pvpModules);

        movementModules.add(new AutoSprintModule());
        movementModules.add(new AutoWalkModule());
        movementModules.add(new FlightModule());
        movementModules.add(new NoFallModule());
        Window movement = new Window("Movement", movementModules);

        utilityModules.add(new AutoRespawnModule());
        utilityModules.add(new AstolfoHudModule());
        utilityModules.add(new HackListModule());
        utilityModules.add(new HudEditorModule());
        Window utility = new Window("Utility", utilityModules);

        Window animesucht = new Window("Animesucht", animesuchtModules);

        windows.add(render);
        windows.add(pvp);
        windows.add(movement);
        windows.add(utility);
        windows.add(animesucht);

        arrangeWindows();
    }

    private void arrangeWindows() {
        int x = 10;
        int y = 10;

        for (Window window : windows) {
            window.setPosition(x, y);
            x += window.getWidth() + WINDOW_SPACING;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        for (Window window : windows) {
            window.render(context, mouseX, mouseY, partialTicks);
        }

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int originalTextureWidth = 739;
        int originalTextureHeight = 1000;

        float scaleFactor = 0.15f;
        int scaledWidth = (int) (originalTextureWidth * scaleFactor);
        int scaledHeight = (int) (originalTextureHeight * scaleFactor);

        int x = screenWidth - scaledWidth;
        int y = 0;

        context.drawTexture(ASTOLFO_TEXTURE, x, y, scaledWidth, scaledHeight, 0, 0, originalTextureWidth, originalTextureHeight, originalTextureWidth, originalTextureHeight);
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (Window window : windows) {
            window.handleMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        for (Window window : windows) {
            window.handleMouseRelease(mouseX, mouseY, mouseButton);
        }
    }

    public void handleMouseScroll(double mouseX, double mouseY, double delta) {
        for (Window window : windows) {
            window.handleMouseScroll(mouseX, mouseY, delta);
        }
    }
}