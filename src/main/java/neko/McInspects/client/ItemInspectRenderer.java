package neko.McInspects.client;

import net.minecraft.client.renderer.GlStateManager;

public class ItemInspectRenderer {

    public static float[] translation = {-0.15f, 0.16f, 0.15f};
    public static float[] firstStop = {-75.0f, 50f, -50.0f};
    public static float[] secondStop = {0.0f, 270f, 15.0f};

    public static void inspectHeldItem() {

        float stageTime = ItemInspectHandler.getCurrentStageTime();
        float elapsedTime = ItemInspectHandler.getCurrentStageElapsedTime();
        float progress = elapsedTime / stageTime;

        float xRot, yRot, zRot;
        xRot = yRot = zRot = 0f;

        switch (ItemInspectHandler.getAnimationStage()) {
            case 0:
                // Stage 0: blocking animation (lerp into the blocking position)

                GlStateManager.translate(
                        lerp(progress, 0f, translation[0]),
                        lerp(progress, 0f, translation[1]),
                        lerp(progress, 0f, translation[2])
                );

                xRot = lerp(progress, 0f, firstStop[0]);
                yRot = lerp(progress, 0f, firstStop[1]);
                zRot = lerp(progress, 0f, firstStop[2]);
                break;
            case 1:
                // Stage 1: pause; use final values from previous stage
                GlStateManager.translate(translation[0], translation[1], translation[2]);

                xRot = firstStop[0];
                yRot = firstStop[1];
                zRot = firstStop[2];
                break;
            case 2:
                // Stage 2: flip; still use final position but interpolate rotation back to new values
                GlStateManager.translate(translation[0], translation[1], translation[2]);

                xRot = lerp(progress, firstStop[0], secondStop[0]);
                yRot = lerp(progress, firstStop[1], secondStop[1]);
                zRot = lerp(progress, firstStop[2], secondStop[2]);
                break;
            case 3:
                // Stage 3: pause after flip
                GlStateManager.translate(translation[0], translation[1], translation[2]);

                xRot = secondStop[0];
                yRot = secondStop[1];
                zRot = secondStop[2];
                break;
            case 4:
                // Stage 4: return to base position; interpolate back to origin

                GlStateManager.translate(
                        lerp(progress, translation[0], 0f),
                        lerp(progress, translation[1], 0f),
                        lerp(progress, translation[2], 0f)
                );

                xRot = lerp(progress, secondStop[0], 0f);
                yRot = lerp(progress, secondStop[1], 0f);
                zRot = lerp(progress, secondStop[2], 0f);
                break;
        }

        GlStateManager.rotate(yRot, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(zRot, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(xRot, 1.0F, 0.0F, 0.0F);
    }

    // Linear interpolation helper.
    private static float lerp(float progress, float start, float end) {
        return start + progress * (end - start);
    }
}