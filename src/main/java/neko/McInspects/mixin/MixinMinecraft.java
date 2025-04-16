package neko.McInspects.mixin;

import neko.McInspects.client.ItemInspectConfig;
import neko.McInspects.client.ItemInspectHandler;
import neko.McInspects.client.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public abstract class MixinMinecraft {

    @Shadow
    public EntityPlayerSP thePlayer;


    @Redirect(
            method = "runTick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void redirectSetCurrentItem(InventoryPlayer player, int newValue) {
        //ItemInspectHandler.addChat(player.currentItem + ", " + newValue);
        if(player.currentItem != newValue) {
            ItemInspectHandler.stopInspect(" - item changed");
        }
        player.currentItem = newValue;
    }

    @Inject(method = {"clickMouse"}, at = @At("HEAD"))
    private void onLeftClick(final CallbackInfo ci) {
        ItemInspectHandler.stopInspect(" - lc");
    }

    @Inject(method = {"rightClickMouse"}, at = @At("HEAD"))
    private void onRightClick(final CallbackInfo ci) {
        ItemStack heldItem = thePlayer.getHeldItem();
        if (heldItem == null || ItemInspectConfig.inspectRCItems.stream().noneMatch(
                item -> TextUtils.stripSymbolAndTrim(heldItem.getDisplayName()).contains(item))) {
            ItemInspectHandler.stopInspect(" - rc");
        }
    }



}
