package neko.McInspects.client;

import neko.McInspects.client.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemInspectConfig {

    private static final String path = Utils.getModPath() + "mc-inspects\\config\\";
    private static final String fileName = "rc-item-list.txt";
    private static final String DEBUG_STRING = "DEBUG";
    public static final boolean DEBUG = Files.exists(Paths.get(path, DEBUG_STRING));

    public static final ArrayList<String> inspectRCItems =
            new ArrayList<>();

    public static void initialize() {

        new File(path).mkdirs();
        File config = Paths.get(path, fileName).toFile();

        try {
            if (config.createNewFile()) {
                return;
            }
            Scanner scanner = new Scanner(config);
            while (scanner.hasNextLine()) {
                inspectRCItems.add(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException ignored) {
        }
    }
}
