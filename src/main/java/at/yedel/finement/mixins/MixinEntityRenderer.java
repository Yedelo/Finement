package at.yedel.finement.mixins;




import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;



@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
	@ModifyVariable(
		method = "getFOVModifier",
		at = @At(value = "STORE", ordinal = 1),
		ordinal = 1
	)
	private float finement$perspectiveDependantFOVs(float originalFOV) {
		return FinementConfig.getInstance().getFOVModifier(originalFOV);
	}

	@ModifyArg(method = "hurtCameraEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 2), index = 0)
	private float finement$changeHurtcamIntensity(float original) {
		if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().damageTilt) return original * FinementConfig.getInstance().damageTiltStrength / 100;
		else {
			return original;
		}
	}
}

