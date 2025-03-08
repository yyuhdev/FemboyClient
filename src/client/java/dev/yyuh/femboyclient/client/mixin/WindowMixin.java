package dev.yyuh.femboyclient.client.mixin;

import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.Taskbar;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(Window.class)
public abstract class WindowMixin {

    @Inject(method = "setIcon", at = @At("HEAD"), cancellable = true)
    private void onSetIcon(CallbackInfo ci) {
        // Cancel the original method to prevent Minecraft from setting its default icon
        ci.cancel();
        setIconGLFW();
    }

    private boolean setIconGLFW() {
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("assets/yourmodid/icon/icon.png")) {
            if (iconStream == null) {
                throw new IOException("Custom icon not found!");
            }

            BufferedImage image = ImageIO.read(iconStream);
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
                    buffer.put((byte) ((pixel >> 8) & 0xFF));  // Green
                    buffer.put((byte) (pixel & 0xFF));         // Blue
                    buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
                }
            }
            buffer.flip();

            GLFWImage glfwImage = GLFWImage.malloc();
            GLFWImage.Buffer glfwImageBuffer = GLFWImage.malloc(1);
            glfwImage.set(image.getWidth(), image.getHeight(), buffer);
            glfwImageBuffer.put(0, glfwImage);

            long windowHandle = ((Window) (Object) this).getHandle();
            GLFW.glfwSetWindowIcon(windowHandle, glfwImageBuffer);

            glfwImageBuffer.free();
            glfwImage.free();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}