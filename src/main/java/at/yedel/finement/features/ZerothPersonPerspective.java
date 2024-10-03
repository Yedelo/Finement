package at.yedel.finement.features;



import at.yedel.finement.Finement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import static at.yedel.finement.Finement.minecraft;



public class ZerothPersonPerspective {
	private ZerothPersonPerspective() {}
	private static final ZerothPersonPerspective instance = new ZerothPersonPerspective();

	public static ZerothPersonPerspective getInstance() {
		return instance;
	}

	@SubscribeEvent
	public void toggleZerothPersonPerspective(KeyInputEvent event) {
		if (Finement.getInstance().getZerothPersonPerspectiveKey().isPressed()) {
			// it is a bit misleading
			minecraft.gameSettings.thirdPersonView = -1;
		}
	}
}
