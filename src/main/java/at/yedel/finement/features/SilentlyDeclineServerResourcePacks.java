package at.yedel.finement.features;



import java.util.Random;

import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S48PacketResourcePackSend;

import static at.yedel.finement.Finement.minecraft;



// class names are getting out of hand
public class SilentlyDeclineServerResourcePacks {
	private SilentlyDeclineServerResourcePacks() {}
	private static final SilentlyDeclineServerResourcePacks instance = new SilentlyDeclineServerResourcePacks();

	private static final Random random = new Random();

	public static SilentlyDeclineServerResourcePacks getInstance() {
		return instance;
	}

	@Subscribe
	public void handleServerResourcePackPacket(ReceivePacketEvent event) {
		if (!FinementConfig.getInstance().silentlyDeclineServerResourcePacks) return;
		if (event.packet instanceof S48PacketResourcePackSend) {
			String hash = ((S48PacketResourcePackSend) event.packet).getHash();
			minecraft.getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.ACCEPTED));
			minecraft.getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.SUCCESSFULLY_LOADED));
			event.isCancelled = true;
			if (FinementConfig.getInstance().sdsrpChatMessage) {
				UChat.chat(colorUp("&!&l[Finement] &!Silently declined server resource pack."));
			}
		}
	}

	private static final String allColorCodes = "0421356789abcdef";

	private String colorUp(String string) {
		char randomColorCode = allColorCodes.charAt(random.nextInt(allColorCodes.length()));
		return string.replace("&!", "&" + randomColorCode);
	}
}
