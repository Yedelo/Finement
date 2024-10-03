package at.yedel.finement.mixins;




import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
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
}

