package nickytea.mcinspects.client.utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import nickytea.mcinspects.client.McInspectsClient;

public class ItemInspectRender {

    public static void inspectHeldItem(MatrixStack matrices) {

        float stageTime = McInspectsClient.getCurrentStageTime();
        double elapsedTime = McInspectsClient.getCurrentStageElapsedTime();
        float progress = (float) (elapsedTime / stageTime);

        float interpX = 0f;
        float interpY = 0f;
        float interpZ = 0f;

        float xRot = 0f;
        float yRot = 0f;
        float zRot = 0f;

        // The way this mod works is we have a handful of predetermined poses for the held item, and depending on
        // the animation stage we lerp between them.
        switch (McInspectsClient.getAnimationStage()) {
            case 0:
                //stage 0: blocking animation

                //interp position
                interpX = lerp(progress, 0f, -0.15f);
                interpY = lerp(progress, 0f, 0.16f);
                interpZ = lerp(progress, 0f, 0.15f);
                matrices.translate(interpX, interpY, interpZ);

                //interp rotation
                xRot = lerp(progress, 0f, 112f);
                yRot = lerp(progress, 0f, -18f);
                zRot = lerp(progress, 0f, 82f);

                break;
            case 1:
                // stage 1: pause
                matrices.translate(-0.15f, 0.16f, 0.15f); //final position from previous stage
                xRot = 112f;
                yRot = -18f;
                zRot = 82f;
                break;

            case 2:
                // stage 2: flip
                matrices.translate(-0.15f, 0.16f, 0.15f); //final position from previous stage

                // interp rotation
                xRot = lerp(progress, 112f, 0f);
                yRot = lerp(progress, -18f, 270f);
                zRot = lerp(progress, 82f, 0f);
                break;
            case 3:
                //stage 3: pause
                matrices.translate(-0.15f, 0.16f, 0.15f); //final position from previous stage
                xRot = 0f;
                yRot = 270f;
                zRot = 0f;
                break;
            case 4:
                //stage 4: return to base

                //interp position
                interpX = lerp(progress, -0.15f, 0f);
                interpY = lerp(progress, 0.16f, 0f);
                interpZ = lerp(progress, 0.15f, 0f);
                matrices.translate(interpX, interpY, interpZ);


                // interp rotation
                yRot = lerp(progress, 270f, 0f);
                zRot = lerp(progress, 0f, 0f);
                xRot = lerp(progress, 0f, 0f);
                break;
        }

        //apply any rotations
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(xRot));

    }

    private static float lerp(float progress, float start, float end) {
        return start + progress * (end - start);
    }
}
