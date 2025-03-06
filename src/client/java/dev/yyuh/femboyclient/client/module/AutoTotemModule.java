package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.ClientPlayerTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotemModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.auto_totem";
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private int delay;
    private boolean holdingTotem;

    public AutoTotemModule() {
        super("Auto Totem", enabled);
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

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);
    }

    @Subscribe
    public void onUpdate(ClientPlayerTickEvent event) {
        if(!enabled) return;

        if(mc.player.isDead()) holdingTotem = false;

        if (holdingTotem && mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            delay = 2;
        }

        holdingTotem = mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;

        if (delay > 0) {
            delay--;
            return;
        }

        if (holdingTotem || (!mc.player.getOffHandStack().isEmpty())) {
            return;
        }

        for (int i = 9; i < 45; i++) {
            if (mc.player.getInventory().getStack(i >= 36 ? i - 36 : i).getItem() == Items.TOTEM_OF_UNDYING) {
                boolean itemInOffhand = !mc.player.getOffHandStack().isEmpty();
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);

                if (itemInOffhand)
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);

                delay = 2;
                return;
            }
        }
    }


    public static boolean isToggled() {
        return enabled;
    }
}
