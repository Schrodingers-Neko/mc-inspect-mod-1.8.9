package nickytea.mcinspects.client.utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import nickytea.mcinspects.client.McInspectsClient;

public class ItemInspectRender {

    public static void inspectHeldItem(MatrixStack matrices) {
       // matrices.translate(-0.15f, 0.16f, 0.15f);
        // -18 82 112
     // matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
       // matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F));  // Positive z: clockwise relative to camera
     //   matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(112f));
        //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90f));

        float progress = McInspectsClient.getInspectProgress();

        float interpX;
        float interpY;
        float interpZ;

        float xRot;
        float yRot;
        float zRot;

        switch (McInspectsClient.getAnimationStage()) {
            case 0:
                //stage 0: blocking animation

                //interp position
                interpX = lerp(progress, 0f, -0.15f);
                interpY = lerp(progress, 0f, 0.16f);
                interpZ = lerp(progress, 0f, 0.15f);
                matrices.translate(interpX, interpY, interpZ);

                //interp rotation
                yRot = lerp(progress, 0f, -18f);
                zRot = lerp(progress, 0f, 82f);
                xRot = lerp(progress, 0f, 112f);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(xRot));
                break;

            case 1:
                matrices.translate(-0.15f, 0.16f, 0.15f); //final position from previous stage

                // interp rotation
                yRot = lerp(progress, -18f, 270f);
                zRot = lerp(progress, 82f, 0f);
                xRot = lerp(progress, 112f, 0f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(xRot));
                break;
            case 2:

                //interp position
                interpX = lerp(progress, -0.15f, 0f);
                interpY = lerp(progress, 0.16f, 0f);
                interpZ = lerp(progress, 0.15f, 0f);
                matrices.translate(interpX, interpY, interpZ);


                // interp rotation
                yRot = lerp(progress, 270f, 0f);
                zRot = lerp(progress, 0f, 0f);
                xRot = lerp(progress, 0f, 0f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(xRot));
                break;
        }

    }

    private static float lerp(float progress, float start, float end) {
        return start + progress * (end - start);
    }
}
