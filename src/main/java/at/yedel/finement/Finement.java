package at.yedel.finement;



import java.util.Map;

import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.features.ClientSideHurtAnimation;
import at.yedel.finement.features.FinementCommand;
import at.yedel.finement.features.SilentlyDeclineServerResourcePacks;
import at.yedel.finement.features.modern.BookBackground;
import at.yedel.finement.features.modern.ChangeWindowTitle;
import at.yedel.finement.features.modern.ItemSwings;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;



@Mod(
	modid = "@MOD_ID@",
	name = "@MOD_NAME@",
	version = Finement.VERSION,
	clientSideOnly = true,
	acceptedMinecraftVersions = "1.8.9"
)
public class Finement {
	public static final String VERSION = "@MOD_VERSION@";
	public static final String FINEMARK = "§6§l< §3§lFinement §6§l>§r";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		FinementConfig.getInstance().preload();
		CommandManager.register(FinementCommand.getInstance());
		MinecraftForge.EVENT_BUS.register(ClientSideHurtAnimation.getInstance());
		EventManager.INSTANCE.register(SilentlyDeclineServerResourcePacks.getInstance());
		registerEventListeners(
			ClientSideHurtAnimation.getInstance(),
			SilentlyDeclineServerResourcePacks.getInstance(),

			BookBackground.getInstance(),
			ChangeWindowTitle.getInstance(),
			ItemSwings.getInstance()
		);
	}

	// i guess bro
	@NetworkCheckHandler
	public boolean permitPlayers(Map<String, String> modMap, Side side) {
		return true;
	}

	private void registerEventListeners(Object... eventListeners) {
		for (Object eventListener: eventListeners) {
			MinecraftForge.EVENT_BUS.register(eventListener);
			EventManager.INSTANCE.register(eventListener);
		}
	}
}
