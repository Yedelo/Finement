package at.yedel.finement.config.forge;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.GuiScreen;



public class FinementConfigBridge extends GuiScreen {
	public FinementConfigBridge(GuiScreen parentScreen) {}

	@Override
	public void initGui() {
		FinementConfig.getInstance().openGui();
	}
}
