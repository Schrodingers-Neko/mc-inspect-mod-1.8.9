package nickytea.mcinspects.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import nickytea.mcinspects.McInspects;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;


public class McInspectsClient implements ClientModInitializer {

    private static final KeyBinding inspectBinding = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("keys.mcinspects.inspectitem",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
                    "keys.category.mcinspects"));

    private static double currentStageElapsedTime = 0.0f;
    private static float currentStageTime = 0f;
    private static boolean isInspecting = false;

    //The various animation speed variables control the playback speed of the animation
    private static final float BASE_ANIMATION_TIME = 0.025f;
    private static final float S0_ANIMATION_TIME = 0.35f;
    private static final float PAUSE_ANIMATION_TIME = 0.6f; //used for both Stage 1 & Stage 3
    private static final float S2_ANIMATION_TIME = 0.4f;
    private static final float S4_ANIMATION_TIME = 0.45f;


    // The inspect animation will be in five parts:
    // Part 0: bringing from rest to in front of camera
    // Part 1: pause
    // Part 2: flip over
    // Part 3: pause
    // Part 4: return
    private static final int ANIMATION_STAGE_COUNT = 5;
    private static int animationStage = 0;

    private static long lastTime = 0;

    public static boolean isInspecting() {

        long currentTime = System.nanoTime();
        double deltaTime = (double) (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        if (isInspecting) {

            McInspects.LOGGER.info(String.valueOf(currentStageElapsedTime));
            updateAnimationTime();
            //currentStageElapsedTime = Math.min(currentStageElapsedTime +, 1.0f);
            currentStageElapsedTime+=deltaTime;

            if (currentStageElapsedTime >= currentStageTime) {
                 //stage complete
                currentStageElapsedTime = 0f;
                McInspects.LOGGER.info("Stage complete");
                animationStage++;
                if (animationStage >= ANIMATION_STAGE_COUNT) {
                    //full animation complete
                    stopInspect();
                }
            }
        }

        return isInspecting;
    }

    private static void updateAnimationTime() {
        switch (animationStage) {
            case 0:
                currentStageTime = S0_ANIMATION_TIME;
                break;
            case 1:
            case 3:
                currentStageTime = PAUSE_ANIMATION_TIME;
                break;
            case 2:
                currentStageTime = S2_ANIMATION_TIME;
                break;
            case 4:
                currentStageTime = S4_ANIMATION_TIME;
                break;
            default:
                currentStageTime = BASE_ANIMATION_TIME;
        }
    }
    public static double getCurrentStageElapsedTime() {
        return currentStageElapsedTime;
    }

    public static float getCurrentStageTime() {
        return currentStageTime;
    }

    public static int getAnimationStage() {
        return animationStage;
    }

    private static void startInspect() {
        isInspecting = true;
        currentStageElapsedTime = 0f;
        animationStage = 0;
        currentStageTime = BASE_ANIMATION_TIME;
        McInspects.LOGGER.info("Starting inspect animation");
    }

    private static void stopInspect() {
        isInspecting = false;
        currentStageElapsedTime = 0f;
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
