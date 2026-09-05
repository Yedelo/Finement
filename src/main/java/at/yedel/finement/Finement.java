package at.yedel.finement;



import at.yedel.finement.features.FinementCommand;
import at.yedel.finement.features.SilentlyDeclineServerResourcePacks;
import at.yedel.finement.features.modern.ChangeWindowTitle;

//? if forge {
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
//?} else {
//import net.fabricmc.api.ClientModInitializer;
//?}
//? if v0 {
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
//?} else {
/*
import org.polyfrost.oneconfig.api.commands.v1.CommandManager;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
*///?}

import java.util.Map;




//? if forge {
@Mod(
	modid = "@MOD_ID@",
	name = "@MOD_NAME@",
	version = Finement.VERSION,
	clientSideOnly = true,
	acceptedMinecraftVersions = "1.8.9"
)
//?}
public class Finement /*? if ornithe {*/ /*implements ClientModInitializer *//*?}*/ {
	public static final String VERSION = "@MOD_VERSION@";
	public static final String FINEMARK = "§6§l< §3§lFinement §6§l>§r";

	private void initialize() {
		CommandManager.register(FinementCommand.getInstance());
		EventManager.INSTANCE.register(SilentlyDeclineServerResourcePacks.getInstance());
	}

	//? if ornithe {
	/*@Override
	public void onInitializeClient() {
		initialize();
		ChangeWindowTitle.getInstance();
	}
	*///?}

	//? if forge {
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		initialize();
		MinecraftForge.EVENT_BUS.register(ChangeWindowTitle.getInstance());
	}

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
	//?}
}
