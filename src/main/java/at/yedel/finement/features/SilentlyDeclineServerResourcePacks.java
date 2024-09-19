package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S48PacketResourcePackSend;

import static at.yedel.finement.Finement.minecraft;



// class names are getting out of hand
public class SilentlyDeclineServerResourcePacks {
	private SilentlyDeclineServerResourcePacks() {}
	private static final SilentlyDeclineServerResourcePacks instance = new SilentlyDeclineServerResourcePacks();

	public static SilentlyDeclineServerResourcePacks getInstance() {
		return instance;
	}

	@Subscribe
	public void handleServerResourcePackPacket(ReceivePacketEvent event) {
		if (!FinementConfig.getInstance().silentlyDeclineServerResourcePacks) return;
		if (event.packet instanceof S48PacketResourcePackSend) {
			event.isCancelled = true;
			String hash = ((S48PacketResourcePackSend) event.packet).getHash();
			minecraft.getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.ACCEPTED));
			minecraft.getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.SUCCESSFULLY_LOADED));
		}
	}
}
