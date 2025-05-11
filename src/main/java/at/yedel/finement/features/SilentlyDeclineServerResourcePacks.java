package at.yedel.finement.features;



import java.util.Random;

import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S48PacketResourcePackSend;



// class names are getting out of hand
public class SilentlyDeclineServerResourcePacks {
	private SilentlyDeclineServerResourcePacks() {}
	private static final SilentlyDeclineServerResourcePacks INSTANCE = new SilentlyDeclineServerResourcePacks();

	private static final Random RANDOM = new Random();

	public static SilentlyDeclineServerResourcePacks getInstance() {
		return INSTANCE;
	}

	@Subscribe
	public void handleServerResourcePackPacket(ReceivePacketEvent event) {
		if (!FinementConfig.getInstance().silentlyDeclineServerResourcePacks) return;
		if (event.packet instanceof S48PacketResourcePackSend) {
			String hash = ((S48PacketResourcePackSend) event.packet).getHash();
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.ACCEPTED));
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C19PacketResourcePackStatus(hash, Action.SUCCESSFULLY_LOADED));
			event.isCancelled = true;
			if (FinementConfig.getInstance().sdsrpChatMessage) {
				char randomColorCode = ALL_COLOR_CODES.charAt(RANDOM.nextInt(ALL_COLOR_CODES.length()));
				UChat.chat("&!&l[Finement] &!Silently declined server resource pack.".replace("&!", "&" + randomColorCode));
			}
		}
	}

	private static final String ALL_COLOR_CODES = "0421356789abcdef";
}
