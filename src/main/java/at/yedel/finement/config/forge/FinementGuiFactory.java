package at.yedel.finement.config.forge;



import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;



public class FinementGuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraft) {}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return FinementConfigBridge.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement runtimeOptionCategoryElement) {
		return null;
	}

	public boolean hasConfigGui() {
		return true;
	}

	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new FinementConfigBridge(parentScreen);
	}
}