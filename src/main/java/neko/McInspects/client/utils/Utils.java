package neko.McInspects.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;

public class Utils {
    private static String modPath;

    public static String getModPath() {
        if (modPath == null) {
            String className = new Exception().getStackTrace()[0].getClassName();
            try {
                String filepath = Class.forName(className).getProtectionDomain().getCodeSource().getLocation().toString();
                filepath = filepath.split("!")[0];
                filepath = filepath.substring(filepath.indexOf("/") + 1, filepath.lastIndexOf("/") + 1);
                modPath = filepath;
                return filepath;
            } catch (ClassNotFoundException e) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(e.toString()));
                return null;
            }
        } else {
            return modPath;
        }
    }
}
