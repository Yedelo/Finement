package at.yedel.finement;



import java.util.Map;

import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.features.ClientSideHurtAnimation;
import at.yedel.finement.features.FinementCommand;
import at.yedel.finement.features.SilentlyDeclineServerResourcePacks;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;



@Mod(
	modid = "finement",
	name = "Finement",
	version = Finement.VERSION,
	clientSideOnly = true,
	acceptedMinecraftVersions = "1.8.9",
	guiFactory = "at.yedel.finement.config.forge.FinementGuiFactory"
)
public class Finement {
	public static final String VERSION = "#VERSION#";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		FinementConfig.getInstance().preload();
		CommandManager.register(new FinementCommand());
		MinecraftForge.EVENT_BUS.register(ClientSideHurtAnimation.getInstance());
		EventManager.INSTANCE.register(SilentlyDeclineServerResourcePacks.getInstance());
	}

	@NetworkCheckHandler
	public boolean permitPlayers(Map<String, String> modMap, Side side) {
		return true;
	}
}
