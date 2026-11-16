package at.yedel.finement.features;



import java.util.Random;

import at.yedel.finement.config.FinementConfig;
import org.polyfrost.oneconfig.api.event.v1.events.PacketEvent;
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe;
//? if v0 {
 //import cc.polyfrost.oneconfig.libs.universal.UChat;
//?} else {
import org.polyfrost.oneconfig.api.platform.v1.Platform;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S48PacketResourcePackSend;

import static at.yedel.finement.launch.FinementConstants.FINEMARK;



// class names are getting out of hand
public class SilentlyDeclineServerResourcePacks {
	private static final SilentlyDeclineServerResourcePacks INSTANCE = new SilentlyDeclineServerResourcePacks();

	public static SilentlyDeclineServerResourcePacks getInstance() {
		return INSTANCE;
	}

	private SilentlyDeclineServerResourcePacks() {}

	@Subscribe
	public void handleServerResourcePackPacket(PacketEvent.Receive event) {
		if (!FinementConfig.getInstance().enabled || !FinementConfig.getInstance().silentlyDeclineServerResourcePacks) return;
		if (event.getPacket() instanceof S48PacketResourcePackSend) {
			String hash = ((S48PacketResourcePackSend) event.getPacket()).getHash();
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.ACCEPTED));
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.SUCCESSFULLY_LOADED));
			event.cancelled = true;
			if (FinementConfig.getInstance().sdsrpChatMessage) {
				Platform.compatibility().displayChatMessage(FINEMARK + " §7Silently declined server resource pack.");
			}
		}
	}
}
