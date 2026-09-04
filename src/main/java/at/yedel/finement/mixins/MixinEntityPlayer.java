package at.yedel.finement.mixins;



import at.yedel.finement.features.ClientSideHurtAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    private void finement$onAttackEntity(Entity targetEntity, CallbackInfo ci) {
        ClientSideHurtAnimation.getInstance().doClientSideHurtAnimation(targetEntity);
    }
}
