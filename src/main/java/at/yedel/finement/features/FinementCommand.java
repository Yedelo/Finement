package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.ChatColor;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;



@Command(value = "finement", description = "Opens the Finement config.", chatColor = ChatColor.DARK_AQUA)
public class FinementCommand {
	@Main
	private void main() {
		FinementConfig.getInstance().openGui();
	}
}
