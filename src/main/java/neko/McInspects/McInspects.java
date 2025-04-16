package neko.McInspects;

import neko.McInspects.client.ItemInspectConfig;
import neko.McInspects.client.ItemInspectHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "mc-inspects", name = "McInspects", version = "1.0", clientSideOnly = true, acceptedMinecraftVersions = "[1.8,1.9)")
public class McInspects {

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        ItemInspectConfig.initialize();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        ItemInspectHandler handler = new ItemInspectHandler();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
    }


}
