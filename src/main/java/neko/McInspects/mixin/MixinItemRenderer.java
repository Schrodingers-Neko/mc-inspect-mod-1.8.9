package neko.McInspects.mixin;

import neko.McInspects.client.ItemInspectHandler;
import neko.McInspects.client.ItemInspectRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public class MixinItemRenderer {

    @SuppressWarnings("deprecation")
    @Inject(method = {"renderItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void onRenderItem(EntityLivingBase entityIn,
                              ItemStack heldStack,
                              ItemCameraTransforms.TransformType transform,
                              CallbackInfo ci) {
        // heldStack already not null
        if (ItemInspectHandler.isInspecting() &&
                entityIn instanceof EntityPlayerSP &&
                transform == ItemCameraTransforms.TransformType.FIRST_PERSON
        ) {
            ItemInspectRenderer.inspectHeldItem();
        }
    }
}
