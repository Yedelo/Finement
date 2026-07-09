package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.UScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class FavoriteServerButton {
    private static final FavoriteServerButton INSTANCE = new FavoriteServerButton();

    public static FavoriteServerButton getInstance() {
        return INSTANCE;
    }

    private GuiButton favoriteServerButton;

    private FavoriteServerButton() {}

    @SubscribeEvent
    public void addFavoriteServerButton(GuiScreenEvent.InitGuiEvent event) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().favoriteServerButton && event.gui instanceof GuiMainMenu) {
            event.buttonList.add(favoriteServerButton = new GuiButton(1600, 5, 5, 125, 20, "Join Favorite Server"));
        }
    }

    @SubscribeEvent
    public void joinFavoriteServer(GuiScreenEvent.ActionPerformedEvent event) {
        if (event.button == favoriteServerButton) {
            FMLClientHandler.instance().connectToServer(new GuiMultiplayer(UScreen.getCurrentScreen()), new ServerData("Favorite Server", FinementConfig.getInstance().specifiedServer, false));
        }
    }
}