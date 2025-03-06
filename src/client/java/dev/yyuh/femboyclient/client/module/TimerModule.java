package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.BeginRenderTickEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class TimerModule extends Button {
    public static boolean enabled = false;
    private static final String CONFIG_PATH = "modules.timer";
    public static MinecraftClient mc = MinecraftClient.getInstance();

    private float lastFrameDuration;
    private float tickDelta;
    private long prevTimeMillis;
    private float tickTime = 20;

    public TimerModule() {
        super("Timer", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Subscribe
    public void onUpdate(BeginRenderTickEvent event) {
        if(!enabled) return;
        if(mc.player == null) return;

        this.lastFrameDuration =  (((event.getTimeMillis() - this.prevTimeMillis) / this.tickTime));
        this.prevTimeMillis = event.getTimeMillis();
        this.tickDelta += this.lastFrameDuration;
        int i = (int) this.tickDelta;
        this.tickDelta -= i;

        event.getCir().setReturnValue(i);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    public static boolean isToggled() {
        return enabled;
    }
}
