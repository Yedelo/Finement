package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.ChatColor;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand;



@Command(value = "finement", description = "Opens the Finement config.", chatColor = ChatColor.DARK_AQUA)
public class FinementCommand {
	@Main
	private void main() {
		FinementConfig.getInstance().openGui();
	}

	@SubCommand(description = "Shows color codes in chat.")
	public void colors() {
		UChat.chat("§00§11§22§33§44§55§66§77§88§99§aa§bb§cc§dd§ee§ff");
	}
}
