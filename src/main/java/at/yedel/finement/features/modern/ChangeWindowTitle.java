package at.yedel.finement.features.modern;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
//? if forge {
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
//?} else {
//import net.ornithemc.osl.networking.api.client.ClientConnectionEvents;
//?}
import org.lwjgl.opengl.Display;

import java.util.Objects;



public class ChangeWindowTitle {
    private static final ChangeWindowTitle INSTANCE = new ChangeWindowTitle();

    public static ChangeWindowTitle getInstance() {
        return INSTANCE;
    }

    private ChangeWindowTitle() {
        //? if ornithe {
        /*
        ClientConnectionEvents.PLAY_READY.register((minecraft) -> onServerJoin());
        ClientConnectionEvents.DISCONNECT.register((minecraft) -> onServerDisconnect());
        *///?}
    }

    //? if forge {
    @SubscribeEvent
    public void onForgeJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        onServerJoin();
    }

    @SubscribeEvent
    public void onForgeDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        onServerDisconnect();
    }
    //?}

    public void onServerJoin() {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().changeWindowTitle) {
                if (Minecraft.getMinecraft().isSingleplayer()) {
                    Display.setTitle("Minecraft 1.8.9 - Singleplayer");
                    return;
                }
                ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
                if (Objects.equals(serverData.serverName, "Minecraft Server")) { // Direct connect
                    Display.setTitle("Minecraft 1.8.9 - " + serverData.serverIP);
                }
                else {
                    Display.setTitle("Minecraft 1.8.9 - " + serverData.serverName + " - " + serverData.serverIP);
                }
            }
        });
    }


    public void onServerDisconnect() {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if ((FinementConfig.getInstance().enabled && FinementConfig.getInstance().changeWindowTitle) || !Objects.equals(Display.getTitle(), "Minecraft 1.8.9")) {
                Display.setTitle("Minecraft 1.8.9");
            }
        });
    }
}
