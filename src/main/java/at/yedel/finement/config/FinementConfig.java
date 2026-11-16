package at.yedel.finement.config;



import at.yedel.finement.mixins.AccessorFontRenderer;
//? if v0 {
/*import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
*///?} else {
import org.polyfrost.compose.render.PolyColor;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.*;
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindHelper;
import org.polyfrost.oneconfig.api.ui.v1.keybind.OneConfigKeybind;
import org.polyfrost.oneconfig.internal.legacy.InputConstants;
import org.polyfrost.oneconfig.utils.v1.dsl.ScreensKt;
//?}
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;




//~ config_bridge
public class FinementConfig extends Config {
	private static final FinementConfig INSTANCE = new FinementConfig();

	public static FinementConfig getInstance() {
		return INSTANCE;
	}

	public final int[] originalColorCodes = new int[32];
	public final int[] customColorCodes = new int[32];

	private FinementConfig() {
		//? if v0 {
		/*super(new Mod("Finement", ModType.UTIL_QOL, "/assets/finement/finement.png"), "finement.json", true, true);
		initialize();
		registerKeyBind(zerothPersonPerspectiveKeybind, () -> {
			// it is a bit misleading
			Minecraft.getMinecraft().gameSettings.thirdPersonView = -1;
		});
		*///?} else {
		super("finement", "assets/finement/finement.png", "Finement", Category.QOL);
		//?}

		setupColorCodes();
		addDependency("onlyEnableOnRealPlayers", "clientSideHurtAnimation");
		addDependency("rotateSwordInThirdPerson", "clientSideAutoBlock");
		addDependency("firstPersonFOV", "perspectiveDependantFOVs");
		addDependency("secondPersonFOV", "perspectiveDependantFOVs");
		addDependency("thirdPersonFOV", "perspectiveDependantFOVs");
		addDependency("zerothPersonFOV", "perspectiveDependantFOVs");
		addDependency("sdsrpChatMessage", "silentlyDeclineServerResourcePacks");
		addDependency("damageTiltStrength", "damageTilt");
		DontObfuscateText.configInitialized();
	}

	public void open() {
		//? if v0
		//openGui();
		//? else
		ScreensKt.openUI(this);
	}

	//? if v1 {
	@Switch(
		title = "Enabled",
		description = "Global toggle for the mod."
	)
	public boolean enabled = true;
	//?}

	@Switch(
		title = "Client-Side Hurt Animation",
		description = "Plays the hurt animation client-side when attacking entities. Note that this occurs on all entities, use 'Only Enable on Real Players' to reduce it to just other players.",
		category = "Gameplay",
		subcategory = "Combat"
	)
	public boolean clientSideHurtAnimation = false;

	@Switch(
		title = "Only Enable on Real Players",
		description = "Only play the client-side hurt animation on real players.",
		category = "Gameplay",
		subcategory = "Combat"
	)
	public boolean onlyEnableOnRealPlayers = true;

	@Switch(
		title = "Client-Side Auto-Block",
		description = "Always shows the blocking animation client-side.",
		category = "Gameplay",
		subcategory = "Combat"
	)
	public boolean clientSideAutoBlock = false;

	@Switch(
		title = "Rotate Sword in Third Person",
		description = "Applies sword rotations for third-person auto-blocking. Potentially fixes rotation issues with OverflowAnimations.",
		category = "Gameplay",
		subcategory = "Combat"
	)
	public boolean rotateSwordInThirdPerson = true;

	@Keybind(
		title = "0th Person Perspective",
		description = "Press to go into an inaccessible and freaky looking perspective mode.",
		category = "Gameplay",
		subcategory = "Perspective"
	)
	public OneConfigKeybind zerothPersonPerspectiveKeybind =
	//? if v0 {
		 //new OneConfigKeybind(Keyboard.KEY_O);
	//?} else {
		KeybindHelper.builder().key(InputConstants.KEY_O).action(() -> Minecraft.getMinecraft().gameSettings.thirdPersonView = -1).register();
	//?}

	@Switch(
		title = "Perspective Dependant FOVs",
		description = "Allows you to set specific FOVs for different perspectives.",
		category = "Gameplay",
		subcategory = "Perspective"
	)
	public boolean perspectiveDependantFOVs = false;

	@Slider(
		title = "First Person FOV",
		category = "Gameplay",
		subcategory = "Perspective",
		min = 30F,
		max = 110F
	)
	public float firstPersonFOV = Minecraft.getMinecraft().gameSettings.fovSetting;

	@Slider(
		title = "Second Person FOV",
		category = "Gameplay",
		subcategory = "Perspective",
		min = 30F,
		max = 110F
	)
	public float secondPersonFOV = Minecraft.getMinecraft().gameSettings.fovSetting;

	@Slider(
		title = "Third Person FOV",
		category = "Gameplay",
		subcategory = "Perspective",
		min = 30F,
		max = 110F
	)
	public float thirdPersonFOV = Minecraft.getMinecraft().gameSettings.fovSetting;

	@Slider(
		title = "Zeroth Person FOV",
		category = "Gameplay",
		subcategory = "Perspective",
		min = 30F,
		max = 110F
	)
	public float zerothPersonFOV = Minecraft.getMinecraft().gameSettings.fovSetting;

	public float getFOVModifier(float originalFov) {
		if (enabled && perspectiveDependantFOVs) {
			switch (Minecraft.getMinecraft().gameSettings.thirdPersonView) {
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
		title = "Smooth Slot Hovering",
		description = "Draws square highlights at your mouse instead of snapping to slots.",
		category = "Interface",
		subcategory = "Inventory"
	)
	public boolean smoothSlotHovering = false;

	@Switch(
		title = "Snap Item Rendering",
		description = "Snaps your held item in inventories to the currently hovered slot.",
		category = "Interface",
		subcategory = "Inventory"
	)
	public boolean snapItemRendering = false;

	@Switch(
		title = "Don't Render Empty Tooltips",
		description = "Don't render empty tooltips. Requires advanced tooltips (F3+H) to be off.",
		category = "Interface",
		subcategory = "Inventory"
	)
	public boolean dontRenderEmptyTooltips = true;

	@Switch(
		title = "Don't Obfuscate Text",
		description = "Don't show text in the obfuscated style. Text will likely still be meaningless.",
		category = "Interface",
		subcategory = "Other"
	)
	public boolean dontObfuscateText = false;

	@Switch(
		title = "Silently Decline Server Resource Packs",
		description = "Silently decline server resource packs, but tell the server that you successfully downloaded them.",
		category = "Interface",
		subcategory = "Other"
	)
	public boolean silentlyDeclineServerResourcePacks = false;

	@Switch(
		title = "Chat Message Indicator",
		description = "Sends a chat message when you silently decline a server resource pack.",
		category = "Interface",
		subcategory = "Other"
	)
	public boolean sdsrpChatMessage = true;

	@Switch(
		title = "Unformat Chat Logs",
		description = "Properly removes formatting from chat messages before logging them.",
		category = "Interface",
		subcategory = "Other"
	)
	public boolean unformatChatLogs = true;

	@Switch(
		title = "Hide Missing Signature Errors",
		description = "Hides \"Signature is missing from textures payload\" errors from being logged.",
		category = "Interface",
		subcategory = "Other"
	)
	public boolean hideMissingSignatureErrors = true;

	@Switch(
		title = "Book Background (1.14+)",
		description = "Draws the default dark background in book GUIs.",
		category = "Modern Features",
		subcategory = "General"
	)
	public boolean bookBackground = true;

	@Switch(
		title = "Keep Chat History on Chat Clear (1.15.2+)",
		description = "When clearing your chat (F3 + D), keep your message history (from pressing up arrow key).",
		category = "Modern Features",
		subcategory = "General"
	)
	public boolean keepChatHistoryOnChatClear = true;

	@Switch(
		title = "Change Window Title (1.15.2+)",
		description = "Changes the window title on world and server join. Can also be changed with /finement settitle [title].",
		category = "Modern Features",
		subcategory = "General"
	)
	public boolean changeWindowTitle = false;

	@Switch(
		title = "Damage Tilt (1.19.4+)",
		description = "Allows you to customize how much your screen hurts when being damaged.",
		category = "Modern Features",
		subcategory = "General"
	)
	public boolean damageTilt = false;

	@Slider(
		title = "Damage Tilt Strength",
		description = "The amount of camera shake caused by being hurt.",
		category = "Modern Features",
		subcategory = "General",
		min = 0,
		max = 100,
		step = 1
	)
	public int damageTiltStrength = 1;

	@Switch(
		title = "Item Uses (1.15+)",
		description = "Swings your hand when using certain items, such as snowballs, eggs and armor.",
		category = "Modern Features",
		subcategory = "Hand Swings"
	)
	public boolean itemUseSwings = false;

	@Switch(
		title = "Item Drops (1.15+)",
		description = "Swings your hand when dropping an item.",
		category = "Modern Features",
		subcategory = "Hand Swings"
	)
	public boolean itemDropSwings = false;

	// he tryna ignore it

	private void setupColorCodes() {
		// Keep the original color codes so that when custom color codes are toggled, these values can be copied back to the original array
		System.arraycopy(((AccessorFontRenderer) Minecraft.getMinecraft().fontRendererObj).finement$getColorCodeArray(), 0, originalColorCodes, 0, 32);
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
			addCallback(string, this::loadCustomColorCodes);
			addDependency(string, "colorCodeToggle");
		}
		addCallback("colorCodeToggle", () -> {
			System.arraycopy(colorCodeToggle ? customColorCodes : originalColorCodes, 0, ((AccessorFontRenderer) Minecraft.getMinecraft().fontRendererObj).finement$getColorCodeArray(), 0, 32);
		});

	}

	//~ if v1 ' = ' -> ' = 0x00FFFFFF & ' {
	private void loadCustomColorCodes() {
		customColorCodes[0] = 0x00FFFFFF & mainColor$0.getArgb();
		customColorCodes[1] = 0x00FFFFFF & mainColor$1.getArgb();
		customColorCodes[2] = 0x00FFFFFF & mainColor$2.getArgb();
		customColorCodes[3] = 0x00FFFFFF & mainColor$3.getArgb();
		customColorCodes[4] = 0x00FFFFFF & mainColor$4.getArgb();
		customColorCodes[5] = 0x00FFFFFF & mainColor$5.getArgb();
		customColorCodes[6] = 0x00FFFFFF & mainColor$6.getArgb();
		customColorCodes[7] = 0x00FFFFFF & mainColor$7.getArgb();
		customColorCodes[8] = 0x00FFFFFF & mainColor$8.getArgb();
		customColorCodes[9] = 0x00FFFFFF & mainColor$9.getArgb();
		customColorCodes[10] = 0x00FFFFFF & mainColor$a.getArgb();
		customColorCodes[11] = 0x00FFFFFF & mainColor$b.getArgb();
		customColorCodes[12] = 0x00FFFFFF & mainColor$c.getArgb();
		customColorCodes[13] = 0x00FFFFFF & mainColor$d.getArgb();
		customColorCodes[14] = 0x00FFFFFF & mainColor$e.getArgb();
		customColorCodes[15] = 0x00FFFFFF & mainColor$f.getArgb();
		customColorCodes[16] = 0x00FFFFFF & shadowColor$0.getArgb();
		customColorCodes[17] = 0x00FFFFFF & shadowColor$1.getArgb();
		customColorCodes[18] = 0x00FFFFFF & shadowColor$2.getArgb();
		customColorCodes[19] = 0x00FFFFFF & shadowColor$3.getArgb();
		customColorCodes[20] = 0x00FFFFFF & shadowColor$4.getArgb();
		customColorCodes[21] = 0x00FFFFFF & shadowColor$5.getArgb();
		customColorCodes[22] = 0x00FFFFFF & shadowColor$6.getArgb();
		customColorCodes[23] = 0x00FFFFFF & shadowColor$7.getArgb();
		customColorCodes[24] = 0x00FFFFFF & shadowColor$8.getArgb();
		customColorCodes[25] = 0x00FFFFFF & shadowColor$9.getArgb();
		customColorCodes[26] = 0x00FFFFFF & shadowColor$a.getArgb();
		customColorCodes[27] = 0x00FFFFFF & shadowColor$b.getArgb();
		customColorCodes[28] = 0x00FFFFFF & shadowColor$c.getArgb();
		customColorCodes[29] = 0x00FFFFFF & shadowColor$d.getArgb();
		customColorCodes[30] = 0x00FFFFFF & shadowColor$e.getArgb();
		customColorCodes[31] = 0x00FFFFFF & shadowColor$f.getArgb();
		
		System.arraycopy(customColorCodes, 0, ((AccessorFontRenderer) Minecraft.getMinecraft().fontRendererObj).finement$getColorCodeArray(), 0, 32);
	}
	//~}

	private static PolyColor randomColor() {
		return new PolyColor(/*? if v1 {*/ 0xFF000000 | /*?}*/ (int) (Math.random() * 0xFFFFFF));
	}

	@Switch(
		title = "Color Code Toggle",
		category = "Custom Color Codes",
		subcategory = "General"
		/*? if v0 {*//*, size = 2 *//*?}*/
	)
	public boolean colorCodeToggle = false;

	@Button(
		title = "Randomize Color Codes",
		text = "Randomize",
		subcategory = "General",
		category = "Custom Color Codes"
	)
	private void randomizeColorCodes() {
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

	//~ if v1 'new OneColor(' -> 'new PolyColor(0xFF000000 | ' {
	@Button(
		title = "Reset Color Codes",
		text = "Reset",
		category = "Custom Color Codes",
		subcategory = "General"
	)
	private void resetColorCodes() {
		mainColor$0 = new PolyColor(0xFF000000 | 0);
		mainColor$1 = new PolyColor(0xFF000000 | 170);
		mainColor$2 = new PolyColor(0xFF000000 | 43520);
		mainColor$3 = new PolyColor(0xFF000000 | 43690);
		mainColor$4 = new PolyColor(0xFF000000 | 11141120);
		mainColor$5 = new PolyColor(0xFF000000 | 11141290);
		mainColor$6 = new PolyColor(0xFF000000 | 16755200);
		mainColor$7 = new PolyColor(0xFF000000 | 11184810);
		mainColor$8 = new PolyColor(0xFF000000 | 5592405);
		mainColor$9 = new PolyColor(0xFF000000 | 5592575);
		mainColor$a = new PolyColor(0xFF000000 | 5635925);
		mainColor$b = new PolyColor(0xFF000000 | 5636095);
		mainColor$c = new PolyColor(0xFF000000 | 16733525);
		mainColor$d = new PolyColor(0xFF000000 | 16733695);
		mainColor$e = new PolyColor(0xFF000000 | 16777045);
		mainColor$f = new PolyColor(0xFF000000 | 16777215);
		shadowColor$0 = new PolyColor(0xFF000000 | 0);
		shadowColor$1 = new PolyColor(0xFF000000 | 42);
		shadowColor$2 = new PolyColor(0xFF000000 | 10752);
		shadowColor$3 = new PolyColor(0xFF000000 | 10794);
		shadowColor$4 = new PolyColor(0xFF000000 | 2752512);
		shadowColor$5 = new PolyColor(0xFF000000 | 2752554);
		shadowColor$6 = new PolyColor(0xFF000000 | 2763264);
		shadowColor$7 = new PolyColor(0xFF000000 | 2763306);
		shadowColor$8 = new PolyColor(0xFF000000 | 1381653);
		shadowColor$9 = new PolyColor(0xFF000000 | 1381695);
		shadowColor$a = new PolyColor(0xFF000000 | 1392405);
		shadowColor$b = new PolyColor(0xFF000000 | 1392447);
		shadowColor$c = new PolyColor(0xFF000000 | 4134165);
		shadowColor$d = new PolyColor(0xFF000000 | 4134207);
		shadowColor$e = new PolyColor(0xFF000000 | 4144917);
		shadowColor$f = new PolyColor(0xFF000000 | 4144959);
		if (colorCodeToggle) loadCustomColorCodes();
	};

	@Color(
		title = "#0 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$0 = new PolyColor(0xFF000000 | 0);

	@Color(
		title = "#0 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$0 = new PolyColor(0xFF000000 | 0);

	@Color(
		title = "#1 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$1 = new PolyColor(0xFF000000 | 170);

	@Color(
		title = "#1 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$1 = new PolyColor(0xFF000000 | 42);

	@Color(
		title = "#2 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$2 = new PolyColor(0xFF000000 | 43520);

	@Color(
		title = "#2 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$2 = new PolyColor(0xFF000000 | 10752);

	@Color(
		title = "#3 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$3 = new PolyColor(0xFF000000 | 43690);

	@Color(
		title = "#3 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$3 = new PolyColor(0xFF000000 | 10794);

	@Color(
		title = "#4 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$4 = new PolyColor(0xFF000000 | 11141120);

	@Color(
		title = "#4 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$4 = new PolyColor(0xFF000000 | 2752512);

	@Color(
		title = "#5 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$5 = new PolyColor(0xFF000000 | 11141290);

	@Color(
		title = "#5 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$5 = new PolyColor(0xFF000000 | 2752554);

	@Color(
		title = "#6 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$6 = new PolyColor(0xFF000000 | 16755200);

	@Color(
		title = "#6 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$6 = new PolyColor(0xFF000000 | 2763264);

	@Color(
		title = "#7 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$7 = new PolyColor(0xFF000000 | 11184810);

	@Color(
		title = "#7 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$7 = new PolyColor(0xFF000000 | 2763306);

	@Color(
		title = "#8 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$8 = new PolyColor(0xFF000000 | 5592405);

	@Color(
		title = "#8 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$8 = new PolyColor(0xFF000000 | 1381653);

	@Color(
		title = "#9 Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$9 = new PolyColor(0xFF000000 | 5592575);

	@Color(
		title = "#9 Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$9 = new PolyColor(0xFF000000 | 1381695);

	@Color(
		title = "#a Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$a = new PolyColor(0xFF000000 | 5635925);

	@Color(
		title = "#a Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$a = new PolyColor(0xFF000000 | 1392405);

	@Color(
		title = "#b Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$b = new PolyColor(0xFF000000 | 5636095);

	@Color(
		title = "#b Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$b = new PolyColor(0xFF000000 | 1392447);

	@Color(
		title = "#c Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$c = new PolyColor(0xFF000000 | 16733525);

	@Color(
		title = "#c Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$c = new PolyColor(0xFF000000 | 4134165);

	@Color(
		title = "#d Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$d = new PolyColor(0xFF000000 | 16733695);

	@Color(
		title = "#d Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$d = new PolyColor(0xFF000000 | 4134207);

	@Color(
		title = "#e Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$e = new PolyColor(0xFF000000 | 16777045);

	@Color(
		title = "#e Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$e = new PolyColor(0xFF000000 | 4144917);

	@Color(
		title = "#f Color (Main)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor mainColor$f = new PolyColor(0xFF000000 | 16777215);

	@Color(
		title = "#f Color (Shadow)",
		alpha = false,
		category = "Custom Color Codes",
		subcategory = "Colors"
	)
	private PolyColor shadowColor$f = new PolyColor(0xFF000000 | 4144959);
	//~}

	@Info(
		//? if v0 {
		/*text = "Finement is not responsible for any bans that occur because of this feature.",
		type = InfoType.WARNING,
		*///?} else {
		title = "Warning",
		description = "Finement is not responsible for any bans that occur because of this feature.",
		//?}
		category = "Advanced"
	)
	public static int empty$0 = 0;

	@Switch(
		title = "Allow GUIs in Portals",
		category = "Advanced",
		description = "Allow opening normal GUIs such as the inventory when inside nether portals."
	)
	public boolean allowGuisInPortals = false;
}
