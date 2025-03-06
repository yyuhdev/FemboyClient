package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class RenderArmorBarEvent extends Event {
    private final DrawContext context;
    private final PlayerEntity player;
    private final int i;
    private final int j;
    private final int k;
    private final int x;


    public RenderArmorBarEvent(DrawContext context, PlayerEntity player, int i, int j, int k, int x) {
        this.context = context;
        this.player = player;
        this.i = i;
        this.j = j;
        this.k = k;
        this.x = x;
    }

    public DrawContext getContext() {
        return context;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public int getI() {
        return i;
    }

    public int getK() {
        return k;
    }

    public int getX() {
        return x;
    }

    public int getJ() {
        return j;
    }
}
