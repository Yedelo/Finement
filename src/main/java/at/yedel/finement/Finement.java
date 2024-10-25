package at.yedel.finement;



import java.util.Map;

import at.yedel.finement.features.ClientSideHurtAnimation;
import at.yedel.finement.features.FinementCommand;
import at.yedel.finement.features.SilentlyDeclineServerResourcePacks;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;



@Mod(
	modid = "finement",
	name = "Finement",
	version = Finement.version,
	clientSideOnly = true,
	acceptedMinecraftVersions = "1.8.9",
	guiFactory = "at.yedel.finement.config.forge.FinementGuiFactory"
)
public class Finement {
	public static final String version = "#version#";
	public static final Minecraft minecraft = Minecraft.getMinecraft();

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		CommandManager.register(new FinementCommand());
		MinecraftForge.EVENT_BUS.register(ClientSideHurtAnimation.getInstance());
		EventManager.INSTANCE.register(SilentlyDeclineServerResourcePacks.getInstance());
	}

	@NetworkCheckHandler
	public boolean permitPlayers(Map modMap, Side side) {
		return true;
	}

	public static String removeFormatting(String string) {
		return string.replaceAll("§[0123456789abcdefklnor]", "");
	}
}
