package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.ChatColor;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.libs.universal.wrappers.message.UTextComponent;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Greedy;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand;
import net.minecraft.event.HoverEvent;
import org.lwjgl.opengl.Display;

import static at.yedel.finement.Finement.FINEMARK;



@Command(value = "finement", description = "Opens the Finement config.", chatColor = ChatColor.DARK_AQUA)
public class FinementCommand {
	private static final FinementCommand INSTANCE = new FinementCommand();

	public static FinementCommand getInstance() {
		return INSTANCE;
	}

	private FinementCommand() {}

	private static final String FORMATTING_CODES =
		"§cC§6o§el§ao§9r §1c§5o§dd§be§3s§r:" + // "Color codes:" (in rainbow)
			"\n§8Black: §8&0     §4Dark Red: §4&4     §2Dark Green: §2&2     §1Dark Blue: §1&1" +
			"\n§3Dark Aqua: §3&3     §5Dark Purple: §5&5     §6Gold: §6&6     §7Gray: §7&7" +
			"\n§8Dark Gray: §8&8     §9Blue: §9&9     §aGreen: §a&a     §bAqua: §b&b" +
			"\n§cRed: §c&c     §dLight Purple: §d&d     §eYellow: §e&e     §fWhite: §f&f" +
			"\n" +
			"\n§lStyle §ncodes§r:" +
			"\n§kObfuscated§r: &k     §r§lBold: §l&l     §r§mStrikethrough: §m&m" +
			"\n§nUnderline: §n&n§r     §r§oItalic: §o&o    §rReset: §r&r";
	private static final UTextComponent FORMATTING_GUIDE_MESSAGE =
		new UTextComponent(FINEMARK + " §e§nHover to view the formatting guide.").setHover(HoverEvent.Action.SHOW_TEXT, FORMATTING_CODES);

	@Main
	private void main() {
		FinementConfig.getInstance().openGui();
	}

	@SubCommand(description = "Shows formatting codes in chat.")
	public void formatting() {
		UChat.chat(FORMATTING_GUIDE_MESSAGE);
	}

	@SubCommand(description = "Sets the title of the game window.")
	public void settitle(@Greedy String title) {
		Display.setTitle(title);
		UChat.chat(FINEMARK + " §eSet display title to \"§f" + title + "§e\"!");
	}

}
