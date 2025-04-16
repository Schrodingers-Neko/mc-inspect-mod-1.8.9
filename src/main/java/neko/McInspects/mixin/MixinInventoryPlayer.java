package neko.McInspects.mixin;

import neko.McInspects.client.ItemInspectHandler;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer {

    @Inject(method = "changeCurrentItem", at = @At("HEAD"))
    public void onChangeCurrentItem(int direction, CallbackInfo ci) {
        if (direction != 0) {
            ItemInspectHandler.stopInspect(" - mouse scroll");
        }
    }
}