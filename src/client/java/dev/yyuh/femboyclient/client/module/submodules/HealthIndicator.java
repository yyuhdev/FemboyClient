package dev.yyuh.femboyclient.client.module.submodules;

import dev.yyuh.femboyclient.client.gui.hud.HudHealthDisplay;
import dev.yyuh.femboyclient.client.util.ConfigUtils;
import net.minecraft.entity.LivingEntity;

public class HealthIndicator {
    public static HudHealthDisplay HEALTH_DISPLAY;

    private static final String CONFIG_PATH = "modules.health_indicator";

    public HealthIndicator() {
        ConfigUtils.HudPosition position = ConfigUtils.getPosition(CONFIG_PATH);
        HEALTH_DISPLAY = new HudHealthDisplay(position.getX(), position.getY());
    }

    public static void save() {
        ConfigUtils.saveModuleStateAndPosition(CONFIG_PATH, true, HEALTH_DISPLAY.getX(), HEALTH_DISPLAY.getY());
    }

    public void setTarget(LivingEntity target) {
        HEALTH_DISPLAY.setTarget(target);
    }

    public HudHealthDisplay getHudHealthDisplay() {
        return HEALTH_DISPLAY;
    }
}