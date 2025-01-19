package nickytea.mcinspects.client;

import net.fabricmc.api.ClientModInitializer;
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

    private static final float BASE_ANIMATION_SPEED = 0.025f;
    private static final float S0_ANIMATION_SPEED = 0.06f;
    private static final float PAUSE_ANIMATION_SPEED = 0.025f; //used for both Stage 1 & Stage 3
    private static final float S2_ANIMATION_SPEED = 0.035f;
    private static final float S4_ANIMATION_SPEED = 0.05f;

    private static float animationSpeed = BASE_ANIMATION_SPEED;

    // The inspect animation will be in five parts:
    // Part 0: bringing from rest to in front of camera
    // Part 1: pause
    // Part 2: flip over
    // Part 3: pause
    // Part 4: return

    private static int animationStage = 0;

    public static boolean isInspecting() {
        if (isInspecting) {
            updateAnimationSpeed();
            inspectProgress = Math.min(inspectProgress + animationSpeed, 1.0f);
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

    private static void updateAnimationSpeed() {
        switch (animationStage) {
            case 0:
                animationSpeed = S0_ANIMATION_SPEED;
                break;
            case 1:
            case 3:
                animationSpeed = PAUSE_ANIMATION_SPEED;
                break;
            case 2:
                animationSpeed = S2_ANIMATION_SPEED;
                break;
            case 4:
                animationSpeed = S4_ANIMATION_SPEED;
                break;
            default:
                animationSpeed = BASE_ANIMATION_SPEED;
        }
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
        animationSpeed = BASE_ANIMATION_SPEED;
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
