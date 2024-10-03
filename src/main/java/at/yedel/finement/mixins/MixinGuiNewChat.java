package at.yedel.finement.mixins;



import at.yedel.finement.Finement;
import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;



@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat {
	@ModifyArg(method = "printChatMessageWithOptionalDeletion", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", remap = false))
	private String finement$unformatChatLogs(String message) {
		if (FinementConfig.getInstance().unformatChatLogs) return Finement.removeFormatting(message);
		else return message;
	}
}
