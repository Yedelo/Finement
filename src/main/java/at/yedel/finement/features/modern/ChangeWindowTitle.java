package at.yedel.finement.features.modern;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.opengl.Display;

import java.util.Objects;



public class ChangeWindowTitle {
    private static final ChangeWindowTitle INSTANCE = new ChangeWindowTitle();

    public static ChangeWindowTitle getInstance() {
        return INSTANCE;
    }

    private ChangeWindowTitle() {}

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().changeWindowTitle) {
            UMinecraft.getMinecraft().addScheduledTask(() -> {
                if (event.isLocal) {
                    Display.setTitle("Minecraft 1.8.9 - Singleplayer");
                    return;
                }
                ServerData serverData = UMinecraft.getMinecraft().getCurrentServerData();
                if (Objects.equals(serverData.serverName, "Minecraft Server")) { // Direct connect
                    Display.setTitle("Minecraft 1.8.9 - " + serverData.serverIP);
                }
                else {
                    Display.setTitle("Minecraft 1.8.9 - " + serverData.serverName + " - " + serverData.serverIP);
                }
            });
        }
    }

    @SubscribeEvent
    public void onDisconnectFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        UMinecraft.getMinecraft().addScheduledTask(() -> {
            if ((FinementConfig.getInstance().enabled && FinementConfig.getInstance().changeWindowTitle) || !Objects.equals(Display.getTitle(), "Minecraft 1.8.9")) {
                Display.setTitle("Minecraft 1.8.9");
            }
        });
    }
}
