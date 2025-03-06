package dev.yyuh.femboyclient.client.module;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.RenderEntityEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.render.ESPRenderer;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;

public class ItemESPModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.item_esp";

    public ItemESPModule() {
        super("ItemESP", enabled);
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
    public void onUpdate(RenderEntityEvent event) {
        if(!enabled) return;

        Entity entity = event.getEntity();
        if (entity instanceof ItemEntity) {
            Box box = entity.getBoundingBox();
            ESPRenderer.Color4b color = new ESPRenderer.Color4b(0, 255, 0, 255); // Green color for items
            ESPRenderer.drawBox(box, color, 2.0f, 0xFFFF69B4);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
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