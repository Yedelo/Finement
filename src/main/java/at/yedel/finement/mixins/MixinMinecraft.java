package at.yedel.finement.mixins;



import at.yedel.finement.features.modern.ItemSwings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow public EntityPlayerSP thePlayer;

    @Inject(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;", ordinal = 1))
    private void finement$swing(CallbackInfo ci) {
        ItemSwings.getInstance().onSwingableUse(thePlayer);
    }
}
