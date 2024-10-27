package at.yedel.finement.config;



import at.yedel.finement.mixins.AccessorFontRenderer;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Info;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import org.lwjgl.input.Keyboard;

import static at.yedel.finement.Finement.minecraft;



public class FinementConfig extends Config {
	private static final FinementConfig instance = new FinementConfig();

	public static FinementConfig getInstance() {
		return instance;
	}

	public final int[] originalColorCodes = new int[32];
	public final int[] customColorCodes = new int[32];

	private FinementConfig() {
		super(new Mod("Finement", ModType.UTIL_QOL, "assets/finement/finement.png"), "finement.json");
		initialize();
		setupColorCodes();
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
		subcategory = "UI",
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

	private void setupColorCodes() {
		// Keep the original color codes so that when custom color codes are toggled, these values can be copied back to the original array
		System.arraycopy(((AccessorFontRenderer) minecraft.fontRendererObj).finement$getColorCodeArray(), 0, originalColorCodes, 0, 32);
		if (colorCodeToggle) loadCustomColorCodes();

		for (String string: new String[] {
			"mainColor$0",
			"mainColor$1",
			"mainColor$2",
			"mainColor$3",
			"mainColor$4",
			"mainColor$5",
			"mainColor$6",
			"mainColor$7",
			"mainColor$8",
			"mainColor$9",
			"mainColor$a",
			"mainColor$b",
			"mainColor$c",
			"mainColor$d",
			"mainColor$e",
			"mainColor$f",
			"shadowColor$0",
			"shadowColor$1",
			"shadowColor$2",
			"shadowColor$3",
			"shadowColor$4",
			"shadowColor$5",
			"shadowColor$6",
			"shadowColor$7",
			"shadowColor$8",
			"shadowColor$9",
			"shadowColor$a",
			"shadowColor$b",
			"shadowColor$c",
			"shadowColor$d",
			"shadowColor$e",
			"shadowColor$f"
		}) {
			addListener(string, this::loadCustomColorCodes);
			addDependency(string, "colorCodeToggle");
		}
		addListener("colorCodeToggle", () -> {
			System.arraycopy(colorCodeToggle ? customColorCodes : originalColorCodes, 0, ((AccessorFontRenderer) minecraft.fontRendererObj).finement$getColorCodeArray(), 0, 32);
		});
	}

	private void loadCustomColorCodes() {
		customColorCodes[0] = mainColor$0.getRGB();
		customColorCodes[1] = mainColor$1.getRGB();
		customColorCodes[2] = mainColor$2.getRGB();
		customColorCodes[3] = mainColor$3.getRGB();
		customColorCodes[4] = mainColor$4.getRGB();
		customColorCodes[5] = mainColor$5.getRGB();
		customColorCodes[6] = mainColor$6.getRGB();
		customColorCodes[7] = mainColor$7.getRGB();
		customColorCodes[8] = mainColor$8.getRGB();
		customColorCodes[9] = mainColor$9.getRGB();
		customColorCodes[10] = mainColor$a.getRGB();
		customColorCodes[11] = mainColor$b.getRGB();
		customColorCodes[12] = mainColor$c.getRGB();
		customColorCodes[13] = mainColor$d.getRGB();
		customColorCodes[14] = mainColor$e.getRGB();
		customColorCodes[15] = mainColor$f.getRGB();
		customColorCodes[16] = shadowColor$0.getRGB();
		customColorCodes[17] = shadowColor$1.getRGB();
		customColorCodes[18] = shadowColor$2.getRGB();
		customColorCodes[19] = shadowColor$3.getRGB();
		customColorCodes[20] = shadowColor$4.getRGB();
		customColorCodes[21] = shadowColor$5.getRGB();
		customColorCodes[22] = shadowColor$6.getRGB();
		customColorCodes[23] = shadowColor$7.getRGB();
		customColorCodes[24] = shadowColor$8.getRGB();
		customColorCodes[25] = shadowColor$9.getRGB();
		customColorCodes[26] = shadowColor$a.getRGB();
		customColorCodes[27] = shadowColor$b.getRGB();
		customColorCodes[28] = shadowColor$c.getRGB();
		customColorCodes[29] = shadowColor$d.getRGB();
		customColorCodes[30] = shadowColor$e.getRGB();
		customColorCodes[31] = shadowColor$f.getRGB();
		
		System.arraycopy(customColorCodes, 0, ((AccessorFontRenderer) minecraft.fontRendererObj).finement$getColorCodeArray(), 0, 32);
	}

	private static OneColor randomColor() {
		return new OneColor((int) (Math.random() * 16777215));
	}

	@Info(
		text = "Use /finement colors to view the different color codes.",
		type = InfoType.INFO,
		size = 2,
		category = "Custom Color Codes"
	)
	private Object info = null;

	@Switch(
		name = "Color Code Toggle",
		category = "Custom Color Codes",
		size = 2
	)
	public boolean colorCodeToggle = false;

	@Button(
		name = "Randomize Color Codes",
		text = "Randomize",
		category = "Custom Color Codes"
	)
	private Runnable randomizeColorCodes = () -> {
		mainColor$0 = randomColor();
		mainColor$1 = randomColor();
		mainColor$2 = randomColor();
		mainColor$3 = randomColor();
		mainColor$4 = randomColor();
		mainColor$5 = randomColor();
		mainColor$6 = randomColor();
		mainColor$7 = randomColor();
		mainColor$8 = randomColor();
		mainColor$9 = randomColor();
		mainColor$a = randomColor();
		mainColor$b = randomColor();
		mainColor$c = randomColor();
		mainColor$d = randomColor();
		mainColor$e = randomColor();
		mainColor$f = randomColor();
		shadowColor$0 = randomColor();
		shadowColor$1 = randomColor();
		shadowColor$2 = randomColor();
		shadowColor$3 = randomColor();
		shadowColor$4 = randomColor();
		shadowColor$5 = randomColor();
		shadowColor$6 = randomColor();
		shadowColor$7 = randomColor();
		shadowColor$8 = randomColor();
		shadowColor$9 = randomColor();
		shadowColor$a = randomColor();
		shadowColor$b = randomColor();
		shadowColor$c = randomColor();
		shadowColor$d = randomColor();
		shadowColor$e = randomColor();
		shadowColor$f = randomColor();
		if (colorCodeToggle) loadCustomColorCodes();
	};

	@Button(
		name = "Reset Color Codes",
		text = "Reset",
		category = "Custom Color Codes"
	)
	private Runnable resetColorCodes = () -> {
		mainColor$0 = new OneColor(0);
		mainColor$1 = new OneColor(170);
		mainColor$2 = new OneColor(43520);
		mainColor$3 = new OneColor(43690);
		mainColor$4 = new OneColor(11141120);
		mainColor$5 = new OneColor(11141290);
		mainColor$6 = new OneColor(16755200);
		mainColor$7 = new OneColor(11184810);
		mainColor$8 = new OneColor(5592405);
		mainColor$9 = new OneColor(5592575);
		mainColor$a = new OneColor(5635925);
		mainColor$b = new OneColor(5636095);
		mainColor$c = new OneColor(16733525);
		mainColor$d = new OneColor(16733695);
		mainColor$e = new OneColor(16777045);
		mainColor$f = new OneColor(16777215);
		shadowColor$0 = new OneColor(0);
		shadowColor$1 = new OneColor(42);
		shadowColor$2 = new OneColor(10752);
		shadowColor$3 = new OneColor(10794);
		shadowColor$4 = new OneColor(2752512);
		shadowColor$5 = new OneColor(2752554);
		shadowColor$6 = new OneColor(2763264);
		shadowColor$7 = new OneColor(2763306);
		shadowColor$8 = new OneColor(1381653);
		shadowColor$9 = new OneColor(1381695);
		shadowColor$a = new OneColor(1392405);
		shadowColor$b = new OneColor(1392447);
		shadowColor$c = new OneColor(4134165);
		shadowColor$d = new OneColor(4134207);
		shadowColor$e = new OneColor(4144917);
		shadowColor$f = new OneColor(4144959);
		if (colorCodeToggle) loadCustomColorCodes();
	};

	@Color(
		name = "§0 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$0 = new OneColor(0);

	@Color(
		name = "§0 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$0 = new OneColor(0);

	@Color(
		name = "§1 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$1 = new OneColor(170);

	@Color(
		name = "§1 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$1 = new OneColor(42);

	@Color(
		name = "§2 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$2 = new OneColor(43520);

	@Color(
		name = "§2 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$2 = new OneColor(10752);

	@Color(
		name = "§3 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$3 = new OneColor(43690);

	@Color(
		name = "§3 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$3 = new OneColor(10794);

	@Color(
		name = "§4 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$4 = new OneColor(11141120);

	@Color(
		name = "§4 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$4 = new OneColor(2752512);

	@Color(
		name = "§5 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$5 = new OneColor(11141290);

	@Color(
		name = "§5 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$5 = new OneColor(2752554);

	@Color(
		name = "§6 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$6 = new OneColor(16755200);

	@Color(
		name = "§6 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$6 = new OneColor(2763264);

	@Color(
		name = "§7 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$7 = new OneColor(11184810);

	@Color(
		name = "§7 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$7 = new OneColor(2763306);

	@Color(
		name = "§8 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$8 = new OneColor(5592405);

	@Color(
		name = "§8 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$8 = new OneColor(1381653);

	@Color(
		name = "§9 Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$9 = new OneColor(5592575);

	@Color(
		name = "§9 Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$9 = new OneColor(1381695);

	@Color(
		name = "§a Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$a = new OneColor(5635925);

	@Color(
		name = "§a Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$a = new OneColor(1392405);

	@Color(
		name = "§b Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$b = new OneColor(5636095);

	@Color(
		name = "§b Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$b = new OneColor(1392447);

	@Color(
		name = "§c Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$c = new OneColor(16733525);

	@Color(
		name = "§c Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$c = new OneColor(4134165);

	@Color(
		name = "§d Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$d = new OneColor(16733695);

	@Color(
		name = "§d Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$d = new OneColor(4134207);

	@Color(
		name = "§e Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$e = new OneColor(16777045);

	@Color(
		name = "§e Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$e = new OneColor(4144917);

	@Color(
		name = "§f Color (Main)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor mainColor$f = new OneColor(16777215);

	@Color(
		name = "§f Color (Shadow)",
		allowAlpha = false,
		category = "Custom Color Codes"
	)
	private OneColor shadowColor$f = new OneColor(4144959);
}
