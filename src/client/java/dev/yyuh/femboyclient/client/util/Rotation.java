package dev.yyuh.femboyclient.client.util;

import net.minecraft.util.math.MathHelper;

public class Rotation {
    public final float yaw;
    public final float pitch;

    public Rotation(float yaw, float pitch)
    {
        this.yaw = MathHelper.wrapDegrees(yaw);
        this.pitch = MathHelper.wrapDegrees(pitch);
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getPitch()
    {
        return pitch;
    }

    public float pitch() {
        return this.pitch;
    }

    public float yaw() {
        return this.yaw;
    }
}
