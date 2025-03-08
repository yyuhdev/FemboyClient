package dev.yyuh.femboyclient.client;

import dev.yyuh.femboyclient.client.event.EventBus;
import dev.yyuh.femboyclient.client.gui.ClickGUIScreen;
import dev.yyuh.femboyclient.client.gui.HudEditorScreen;
import dev.yyuh.femboyclient.client.gui.render.ClickGui;
import dev.yyuh.femboyclient.client.module.submodules.HealthIndicator;
import dev.yyuh.femboyclient.client.util.FontScreenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class FemboyClient implements ClientModInitializer {

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("femboy_client.json");
    private static final Identifier ICON = Identifier.of("femboyclient", "femboyclient_logo.png");

    public static ClickGui clickGui;
    static MinecraftClient mc = MinecraftClient.getInstance();

    public static FontScreenRenderer HEADER_FONT_RENDERER;
    public static FontScreenRenderer FONT_RENDERER;

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
        clickGui = new ClickGui();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (HEADER_FONT_RENDERER == null) {
                HEADER_FONT_RENDERER = new FontScreenRenderer(FontScreenRenderer.loadFont("NotoSans-VariableFont_wdth,wght.ttf", 15), 15);
                FONT_RENDERER = new FontScreenRenderer(FontScreenRenderer.loadFont("NotoSans-VariableFont_wdth,wght.ttf", 10), 10);
            }
        });


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