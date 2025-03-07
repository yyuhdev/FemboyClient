package dev.yyuh.femboyclient.client;

import dev.yyuh.femboyclient.client.event.Event;
import dev.yyuh.femboyclient.client.event.EventBus;
import dev.yyuh.femboyclient.client.gui.ClickGUIScreen;
import dev.yyuh.femboyclient.client.gui.HudEditorScreen;
import dev.yyuh.femboyclient.client.gui.render.ClickGui;
import dev.yyuh.femboyclient.client.module.HideItemToolTipModule;
import dev.yyuh.femboyclient.client.module.NoArmorBarModule;
import dev.yyuh.femboyclient.client.module.NoFoodBarModule;
import dev.yyuh.femboyclient.client.module.submodules.HealthIndicator;
import dev.yyuh.femboyclient.client.util.FontScreenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FemboyClient implements ClientModInitializer {

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("femboy_client.json");
    public static ClickGui clickGui;
    static MinecraftClient mc = MinecraftClient.getInstance();

    public static FontScreenRenderer FONT_SCREEN_RENDERER;

    public static EventBus EVENT_BUS;

    public static HealthIndicator HEALTH_INDICATOR = new HealthIndicator();

    private KeyBinding OPEN_GUI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Open ClickGUI",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "Femboy Client"
    ));

    private KeyBinding OPEN_HUD_EDITOR = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Open Hud Editor",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_CONTROL,
            "Femboy Client"
    ));

    @Override
    public void onInitializeClient() {
        EVENT_BUS = new EventBus();
        clickGui =  new ClickGui();

        try {
            FONT_SCREEN_RENDERER = new FontScreenRenderer(Font.createFont(0, new File(Identifier.of("femboyclient", "fonts/font_yuhu.ttf").getPath())), 1);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_GUI.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new ClickGUIScreen());
            }
            while (OPEN_HUD_EDITOR.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new HudEditorScreen());
            }
        });
    }
}