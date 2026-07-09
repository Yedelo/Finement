package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;



// Priority is 1024 to apply before mixins of Antimations < 2.2.1
@Mixin(value = ItemRenderer.class, priority = 1024)
public abstract class MixinItemRenderer {
    @Shadow
    private ItemStack itemToRender;

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I"))
    private int finement$firstPersonAutoBlock(AbstractClientPlayer instance) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().clientSideAutoBlock && itemToRender.getItemUseAction() == EnumAction.BLOCK) {
            return 1;
        }
        return instance.getItemInUseCount();
    }
}