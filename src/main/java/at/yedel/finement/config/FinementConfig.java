package at.yedel.finement.config;



import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;



public class FinementConfig extends cc.polyfrost.oneconfig.config.Config {
	public FinementConfig() {
		super(new Mod("Finement", ModType.UTIL_QOL, "assets/finement/finement.png"), "finement.json");
		initialize();
		addDependency("onlyEnableOnRealPlayers", "clientSideHurtAnimation");
		addDependency("sdsrpChatMessage", "silentlyDeclineServerResourcePacks");
	}

	public static FinementConfig instance;

	public static FinementConfig getInstance() {
		return instance;
	}

	@Switch(
		name = "Client-Side Hurt Animation",
		description = "Plays the hurt animation client-side when attacking entities. Note that this occurs on all entities, use 'Only Enable on Real Players' to reduce it to just other players.",
		subcategory = "Gameplay"
	)
	public boolean clientSideHurtAnimation = false;
	@Switch(
		name = "Only Enable on Real Players",
		description = "Only play the client-side hurt animation on real players.",
		subcategory = "Gameplay"
	)
	public boolean onlyEnableOnRealPlayers = true;
	@Switch(
		name = "Silently Decline Server Resource Packs",
		description = "Silently decline server resource packs, but tell the server that you successfully downloaded them.",
		subcategory = "Other"
	)
	public boolean silentlyDeclineServerResourcePacks = false;
	@Switch(
		name = "Chat Message Indicator",
		description = "Sends a chat message when you silently decline a server resource pack.",
		subcategory = "Other"
	)
	public boolean sdsrpChatMessage = true;
	@Switch(
		name = "Unformat Chat Logs",
		description = "Properly removes formatting from chat messages before logging them.",
		subcategory = "Other"
	)
	public boolean unformatChatLogs = true;
	@Switch(
		name = "Hide Missing Signature Errors",
		description = "Hides \"Signature is missing from textures payload\" errors from being logged.",
		subcategory = "Other"
	)
	public boolean hideMissingSignatureErrors = true;
}
