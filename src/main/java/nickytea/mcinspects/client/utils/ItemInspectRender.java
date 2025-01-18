package nickytea.mcinspects.client.utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import nickytea.mcinspects.client.McInspectsClient;

public class ItemInspectRender {

    public static void inspectHeldItem(MatrixStack matrices) {
        //matrices.translate(-0.15f, 0.16f, 0.15f);
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-18.0F));
        //matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(82.0F));
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(112.0F));

        float progress = McInspectsClient.getInspectProgress();

        //interp position
        float interpX = lerp(progress, 0f, -0.15f);
        float interpY = lerp(progress, 0f, 0.16f);
        float interpZ = lerp(progress, 0f, 0.15f);
        matrices.translate(interpX, interpY, interpZ);

        //interp rotation
        float yRot = lerp(progress, 0f, -18f);
        float zRot = lerp(progress, 0f, 82f);
        float xRot = lerp(progress, 0f, 112f);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(xRot));


    }

    private static float lerp(float progress, float start, float end) {
        return start + progress * (end - start);
    }
}
