package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RenderHealthBarEvent extends Event {
    private final DrawContext context;
    private final PlayerEntity player;
    private final int x;
    private final int y;
    private final int lines;
    private final int regeneratingHeartIndex;
    private final float maxHealth;
    private final int lastHealth;
    private final int health;
    private final int absorption;
    private final boolean blinking;
    private final CallbackInfo ci;

    public RenderHealthBarEvent(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        this.context = context;
        this.player = player;
        this.x = x;
        this.y = y;
        this.lines = lines;
        this.regeneratingHeartIndex = regeneratingHeartIndex;
        this.maxHealth = maxHealth;
        this.lastHealth = lastHealth;
        this.health = health;
        this.absorption = absorption;
        this.blinking = blinking;
        this.ci = ci;
    }

    public DrawContext getContext() {
        return context;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLines() {
        return lines;
    }

    public int getRegeneratingHeartIndex() {
        return regeneratingHeartIndex;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public int getLastHealth() {
        return lastHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getAbsorption() {
        return absorption;
    }

    public boolean isBlinking() {
        return blinking;
    }

    public CallbackInfo getCi() {
        return ci;
    }
}