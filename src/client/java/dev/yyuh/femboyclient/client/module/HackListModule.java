package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.HudRenderEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.gui.render.ClickGui;
import dev.yyuh.femboyclient.client.gui.render.Component;
import dev.yyuh.femboyclient.client.gui.hud.HudTextList;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HackListModule extends Button {
    public static boolean enabled = false;
    public static final String CONFIG_PATH = "modules.hacklist";
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static HudTextList HACK_LIST;

    public HackListModule() {
        super("Hack List", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
    }

    @Override
    public void save() {
        ConfigUtils.saveModuleStateAndPosition(CONFIG_PATH, enabled, HACK_LIST.getX(), HACK_LIST.getY());
    }

    public static void updatePosition(ConfigUtils.HudPosition hudPosition) {
        HACK_LIST.setPosition(hudPosition.getX(), hudPosition.getY());
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = this.toggled;
        HACK_LIST = new HudTextList(10, 10);

        ConfigUtils.HudPosition position = ConfigUtils.getPosition(CONFIG_PATH);
        updatePosition(position);
    }

    @Override
    public Screen createSettingsPanel() {
        return null;
    }

    @Subscribe
    public void onUpdate(HudRenderEvent event) {
        if (!enabled) return;
        DrawContext context = event.getDrawContext();

        HACK_LIST.clearText();

        List<Component> hax = new ArrayList<>();
        hax.addAll(ClickGui.movementModules);
        hax.addAll(ClickGui.pvpModules);
        hax.addAll(ClickGui.renderModules);
        hax.addAll(ClickGui.utilityModules);
        hax.addAll(ClickGui.animesuchtModules);

        for (Component component : hax) {
            if (component instanceof Button button && button.toggled) {
                HACK_LIST.addText(Text.of(button.label));
            }
        }

        HACK_LIST.setTextColor(0xFFFF69B4);
        HACK_LIST.render(context);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    public static boolean isToggled() {
        return enabled;
    }
}