package dev.yyuh.femboyclient.client.events;

import dev.yyuh.femboyclient.client.event.Event;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class UseOnBlockEvent extends Event {

    private final ItemUsageContext context;
    private final CallbackInfoReturnable<ActionResult> cir;

    public UseOnBlockEvent(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        this.context = context;
        this.cir = cir;
    }

    public ItemUsageContext getContext() {
        return this.context;
    }

    public CallbackInfoReturnable<ActionResult> getCir() {
        return this.cir;
    }
}
