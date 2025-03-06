package dev.yyuh.femboyclient.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

// copied from wurst & cwhack FUCK YALL NIGGAS

public class RotationUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();


    public static Vec3d getEyesPos()
    {
        return getEyesPos(mc.player);
    }

    public static Vec3d getEyesPos(PlayerEntity player) {
        return new Vec3d(player.getX(),
                player.getY() + player.getEyeHeight(player.getPose()),
                player.getZ());
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        float f = 0.017453292F;
        float pi = (float)Math.PI;

        float f1 = MathHelper.cos(-player.getYaw() * f - pi);
        float f2 = MathHelper.sin(-player.getYaw() * f - pi);
        float f3 = -MathHelper.cos(-player.getPitch() * f);
        float f4 = MathHelper.sin(-player.getPitch() * f);

        return new Vec3d(f2 * f3, f4, f1 * f3).normalize();
    }

    public static Vec3d getClientLookVec()
    {
        return getPlayerLookVec(mc.player);
    }


    public static Rotation getNeededRotations(Vec3d from, Vec3d vec) {
        Vec3d eyesPos = from;

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }

    public static Rotation getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }

    public static double getAngleToLookVec(Vec3d vec) {
        return getAngleToLookVec(mc.player, vec);
    }

    public static double getAngleToLookVec(PlayerEntity player, Vec3d vec) {
        Rotation needed = getNeededRotations(vec);

        float currentYaw = MathHelper.wrapDegrees(player.getYaw());
        float currentPitch = MathHelper.wrapDegrees(player.getPitch());

        float diffYaw = currentYaw - needed.yaw;
        float diffPitch = currentPitch - needed.pitch;

        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static float limitAngleChange(float current, float intended) {
        float currentWrapped = MathHelper.wrapDegrees(current);
        float intendedWrapped = MathHelper.wrapDegrees(intended);

        float change = MathHelper.wrapDegrees(intendedWrapped - currentWrapped);

        return current + change;
    }

    public static float[] getYawAndPitch(Vec3d targetPos) {
        Vec3d eyesPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());

        double diffX = targetPos.x - eyesPos.x;
        double diffY = targetPos.y - eyesPos.y;
        double diffZ = targetPos.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

    /**
     * Fake turns the player to look at a target entity.
     *
     * @param target The target entity to look at.
     */
    public static void fakeLookAtEntity(Entity target) {
        if (target == null || mc.player == null) return;

        Vec3d targetPos = target.getBoundingBox().getCenter();
        float[] angles = getYawAndPitch(targetPos);

        // Send a packet to the server to update the player's rotation
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                angles[0], angles[1], mc.player.isOnGround()
        ));
    }
}