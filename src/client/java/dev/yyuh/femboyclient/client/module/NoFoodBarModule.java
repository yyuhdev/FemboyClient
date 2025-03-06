package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.RenderArmorBarEvent;
import dev.yyuh.femboyclient.client.events.RenderFoodEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;

public class NoFoodBarModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.no_food_bar";

    public NoFoodBarModule() {
        super("No Hungerbar", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    @Override
    public void save() {
        ConfigUtils.save(CONFIG_PATH, enabled);
    }

    @Subscribe
    public void onUpdate(RenderFoodEvent event) {
        if(!enabled) return;
        event.getCi().cancel();
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);
    }

    public static boolean isToggled() {
        return enabled;
    }
}