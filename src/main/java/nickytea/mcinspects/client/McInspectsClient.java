package nickytea.mcinspects.client;

import net.fabricmc.api.ClientModInitializer;
import nickytea.mcinspects.client.utils.ItemInspectRender;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import nickytea.mcinspects.McInspects;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;


public class McInspectsClient implements ClientModInitializer {

    private static final KeyBinding inspectBinding = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("keys.mcinspects.inspectitem",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
                    "keys.category.mcinspects"));

    private static float inspectProgress = 0.0f;
    private static boolean isInspecting = false;
    private static final float ANIMATION_SPEED = 0.025f;

    // The inspect animation will be in five parts:
    // Part 0: bringing from rest to in front of camera
    // Part 1: pause
    // Part 2: flip over
    // Part 3: pause
    // Part 4: return

    private static int animationStage = 0;

    public static boolean isInspecting() {
        if (isInspecting) {
            inspectProgress = Math.min(inspectProgress + ANIMATION_SPEED, 1.0f);
            if (inspectProgress >= 1.0f) {
                 //stage complete
                inspectProgress = 0f;
                animationStage++;
                if (animationStage > 4) {
                    //full animation complete
                    stopInspect();
                }
            }
        }

        return (inspectProgress > 0f) || (animationStage > 0);
    }

    public static float getInspectProgress() {
        return inspectProgress;
    }

    public static int getAnimationStage() {
        return animationStage;
    }

    private static void startInspect() {
        isInspecting = true;
        inspectProgress = 0f;
        animationStage = 0;
        McInspects.LOGGER.info("Starting inspect animation");
    }

    private static void stopInspect() {
        isInspecting = false;
        inspectProgress = 0f;
        animationStage = 0;
        McInspects.LOGGER.info("Stopping inspect animation");
    }

    @Override
    public void onInitializeClient() {
        McInspects.LOGGER.info("MCInspects client initializing...");


        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            //If we pressed the inspect button and are not currently inspecting, start the inspect animation
            if (inspectBinding.wasPressed() && !isInspecting) {
                McInspects.LOGGER.info("User pressed the inspect button");
                startInspect();
            }

        });
    }
}
