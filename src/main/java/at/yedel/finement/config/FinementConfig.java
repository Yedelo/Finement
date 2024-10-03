package at.yedel.finement.config;



import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import org.lwjgl.input.Keyboard;

import static at.yedel.finement.Finement.minecraft;



public class FinementConfig extends Config {
	private FinementConfig() {
		super(new Mod("Finement", ModType.UTIL_QOL, "assets/finement/finement.png"), "finement.json");
		initialize();
		addDependency("onlyEnableOnRealPlayers", "clientSideHurtAnimation");
		addDependency("sdsrpChatMessage", "silentlyDeclineServerResourcePacks");
		addDependency("firstPersonFOV", "perspectiveDependantFOVs");
		addDependency("secondPersonFOV", "perspectiveDependantFOVs");
		addDependency("thirdPersonFOV", "perspectiveDependantFOVs");
		addDependency("zerothPersonFOV", "perspectiveDependantFOVs");
		registerKeyBind(zerothPersonPerspectiveKeybind, () -> {
			// it is a bit misleading
			minecraft.gameSettings.thirdPersonView = -1;
		});
	}
	private static final FinementConfig instance = new FinementConfig();

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

	@KeyBind(
		name = "0th Person Perspective",
		description = "Press to go into an inaccessible and freaky looking perspective mode.",
		subcategory = "Gameplay"
	)
	public OneKeyBind zerothPersonPerspectiveKeybind = new OneKeyBind(Keyboard.KEY_O);

	@Switch(
		name = "Perspective Dependant FOVs",
		description = "Allows you to set specific FOVs for different perspectives.",
		subcategory = "Gameplay"
	)
	public boolean perspectiveDependantFOVs = false;

	@Slider(
		name = "First Person FOV",
		subcategory = "Gameplay",
		min = 30F,
		max = 110F
	)
	public float firstPersonFOV = minecraft.gameSettings.fovSetting;

	@Slider(
		name = "Second Person FOV",
		subcategory = "Gameplay",
		min = 30F,
		max = 110F
	)
	public float secondPersonFOV = minecraft.gameSettings.fovSetting;

	@Slider(
		name = "Third Person FOV",
		subcategory = "Gameplay",
		min = 30F,
		max = 110F
	)
	public float thirdPersonFOV = minecraft.gameSettings.fovSetting;

	@Slider(
		name = "Zeroth Person FOV",
		subcategory = "Gameplay",
		min = 30F,
		max = 110F
	)
	public float zerothPersonFOV = minecraft.gameSettings.fovSetting;

	public float getFOVModifier(float originalFov) {
		if (perspectiveDependantFOVs) {
			switch (minecraft.gameSettings.thirdPersonView) {
				case -1:
					return zerothPersonFOV;
				case 0:
					return firstPersonFOV;
				case 1:
					return secondPersonFOV;
				case 2:
					return thirdPersonFOV;
			}
		}
		return originalFov;
	}

	@Switch(
		name = "Don't Render Empty Tooltips",
		subcategory = "Other",
		description = "Don't render empty tooltips. Requires advanced tooltips (F3+H) to be off."
	)
	public boolean dontRenderEmptyTooltips = true;

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
