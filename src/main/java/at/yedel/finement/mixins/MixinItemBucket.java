package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.utils.SwingItemDuck;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(ItemBucket.class)
public abstract class MixinItemBucket {
    @Inject(method = "onItemRightClick", at = @At("RETURN"))
    private void finement$swingOnBucketUse(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> cir) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().itemUseSwings) {
            if (itemStackIn != cir.getReturnValue()) {
                ((SwingItemDuck) playerIn).finement$swingItemLocally();
            }
        }
    }
}
