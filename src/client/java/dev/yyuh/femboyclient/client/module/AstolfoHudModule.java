package dev.yyuh.femboyclient.client.module;

import dev.yyuh.femboyclient.client.FemboyClient;
import dev.yyuh.femboyclient.client.event.Subscribe;
import dev.yyuh.femboyclient.client.events.HudRenderEvent;
import dev.yyuh.femboyclient.client.gui.render.Button;
import dev.yyuh.femboyclient.client.gui.render.HudElement;
import dev.yyuh.femboyclient.client.gui.render.HudImage;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class AstolfoHudModule extends Button {
    public static boolean enabled = false;
    public static final String CONFIG_PATH = "modules.astolfo_hud";
    private static final Identifier ASTOLFO_TEXTURE = Identifier.of("femboyclient", "astolfo-textur.png");

    public static HudImage ASTOLFO_HUD_ELEMENT;

    public AstolfoHudModule() {
        super("Astolfo Hud", enabled);
        FemboyClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onClick() {
        this.toggled = !this.toggled;
        enabled = this.toggled;
    }

    @Override
    public void save() {
        ConfigUtils.saveModuleStateAndPosition(CONFIG_PATH, true, ASTOLFO_HUD_ELEMENT.getX(), ASTOLFO_HUD_ELEMENT.getY());
    }

    public static void updatePosition(ConfigUtils.HudPosition hudPosition) {
        ASTOLFO_HUD_ELEMENT = new HudImage(hudPosition.getX(), hudPosition.getY(), 128, 128, ASTOLFO_TEXTURE);
    }

    @Override
    public void load() {
        this.toggled = ConfigUtils.get(CONFIG_PATH);
        enabled = ConfigUtils.get(CONFIG_PATH);

        ConfigUtils.get(CONFIG_PATH);
        ConfigUtils.HudPosition hudPosition = ConfigUtils.getPosition(CONFIG_PATH);
        updatePosition(hudPosition);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
    }

    @Subscribe
    public void onHudRender(HudRenderEvent event) {
        if(!enabled) return;

        ASTOLFO_HUD_ELEMENT.render(event.getDrawContext());

//        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
//        int originalTextureWidth = 739;
//        int originalTextureHeight = 1000;
//
//        float scaleFactor = 0.15f;
//        int scaledWidth = (int) (originalTextureWidth * scaleFactor);
//        int scaledHeight = (int) (originalTextureHeight * scaleFactor);
//
//        int x = screenWidth - scaledWidth;
//        int y = 0;
//
//        event.getDrawContext().drawTexture(ASTOLFO_TEXTURE, x, y, scaledWidth, scaledHeight, 0, 0, originalTextureWidth, originalTextureHeight, originalTextureWidth, originalTextureHeight);
    }

    public static boolean isToggled() {
        return enabled;
    }
}
