package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;



@Mixin(YggdrasilMinecraftSessionService.class)
public abstract class MixinYggdrasilMinecraftSessionService {
	@Redirect(method = "getTextures", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V", ordinal = 0), remap = false)
	private void finement$hideMissingSignatureErrors(Logger instance, String s) {
		if (FinementConfig.getInstance().hideMissingSignatureErrors) {
			return;
		}
		else {
			instance.error(s);
		}
	}
}
