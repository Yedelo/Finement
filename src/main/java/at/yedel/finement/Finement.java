package at.yedel.finement;



import java.util.Map;

import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.features.ClientSideHurtAnimation;
import at.yedel.finement.features.FinementCommand;
import at.yedel.finement.features.SilentlyDeclineServerResourcePacks;
import at.yedel.finement.features.ZerothPersonPerspective;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;



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

	@Instance
	private static Finement instance;

	public static Finement getInstance() {
		return instance;
	}

	private KeyBinding zerothPersonPerspectiveKey;

	public KeyBinding getZerothPersonPerspectiveKey() {
		return zerothPersonPerspectiveKey;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		FinementConfig.getInstance();
		CommandManager.register(new FinementCommand());
		MinecraftForge.EVENT_BUS.register(ClientSideHurtAnimation.getInstance());
		MinecraftForge.EVENT_BUS.register(ZerothPersonPerspective.getInstance());
		EventManager.INSTANCE.register(SilentlyDeclineServerResourcePacks.getInstance());
		zerothPersonPerspectiveKey = new KeyBinding("0th Person Perspective", Keyboard.KEY_O, "Finement");
		ClientRegistry.registerKeyBinding(zerothPersonPerspectiveKey);
	}

	@NetworkCheckHandler
	public boolean permitPlayers(Map modMap, Side side) {
		return true;
	}

	public static String removeFormatting(String string) {
		return string.replaceAll("§[0123456789abcdefklnor]", "");
	}
}
