package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;

public class ItemSizeModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.small_items";

    public ItemSizeModule() {
        super("Small Items", enabled);
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

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);
    }

    public static boolean isToggled() {
        return enabled;
    }
}
