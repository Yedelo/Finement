package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.wrappers.message.UTextComponent;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;



@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat {
	@ModifyArg(method = "printChatMessageWithOptionalDeletion", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", remap = false))
	private String finement$unformatChatLogs(String message) {
		if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().unformatChatLogs) return UTextComponent.Companion.stripFormatting(message);
		else return message;
	}

	@Redirect(
		method = "clearChatMessages",
		at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", ordinal = 2)
	)
	private void finement$keepChatHistory(List<String> sentMessages) {
		if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().keepChatHistoryOnChatClear) {
			return;
		}
		sentMessages.clear();
	}
}
