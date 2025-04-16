package neko.McInspects.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ItemInspectHandler {

    private final KeyBinding inspectKey = new KeyBinding(
            "Inspect", Keyboard.KEY_F, "McInspects"
    );

    private static final boolean DEBUG = ItemInspectConfig.DEBUG;

    private static float currentStageElapsedTime = 0.0f;
    private static float currentStageTime = 0f;
    private static boolean isInspecting = false;

    //The various animation time variables control how long each stage of the animation takes.
    private static final float BASE_ANIMATION_TIME = 0.025f;
    private static final float S0_ANIMATION_TIME = 0.35f;
    private static final float S1_PAUSE_ANIMATION_TIME = 0.6f; //used for both Stage 1 & Stage 3
    private static final float S3_PAUSE_ANIMATION_TIME = 0.6f; //used for both Stage 1 & Stage 3
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

    public ItemInspectHandler() {
        ClientRegistry.registerKeyBinding(inspectKey);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isInspecting() {

        long currentTime = System.currentTimeMillis();
        float deltaTime = (float) (currentTime - lastTime) / 1000.0f;
        lastTime = currentTime;

        if (isInspecting) {

            updateAnimationTime();
            currentStageElapsedTime += deltaTime;

            if (currentStageElapsedTime >= currentStageTime) {
                //stage complete
                currentStageElapsedTime = 0f;
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
                currentStageTime = S1_PAUSE_ANIMATION_TIME;
                break;
            case 2:
                currentStageTime = S2_ANIMATION_TIME;
                break;
            case 3:
                currentStageTime = S3_PAUSE_ANIMATION_TIME;
                break;
            case 4:
                currentStageTime = S4_ANIMATION_TIME;
                break;
            default:
                currentStageTime = BASE_ANIMATION_TIME;
        }
    }

    public static float getCurrentStageElapsedTime() {
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
        addChat("Starting inspect animation...");
    }

    public static void stopInspect() {
        stopInspect("");
    }

    public static void stopInspect(String message) {
        if (isInspecting) {
            isInspecting = false;
            currentStageElapsedTime = 0f;
            animationStage = 0;
            addChat("Inspect animation complete" + message);
        }
    }

    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (inspectKey.isPressed() && !isInspecting) {
            startInspect();
        }
    }

    public static void addChat(String message) {
        if (!DEBUG) {
            return;
        }

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (player != null) {
            player.addChatMessage(new ChatComponentTranslation(message));
        }
    }
}