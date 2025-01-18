package nickytea.mcinspects.client;

import net.fabricmc.api.ClientModInitializer;
import nickytea.mcinspects.McInspects;

public class McInspectsClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        McInspects.LOGGER.info("client go brrr");
    }
}
