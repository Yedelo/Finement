package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.features.modern.ItemSwings;
import at.yedel.finement.utils.SwingItemDuck;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends EntityLivingBase implements SwingItemDuck {
    private MixinEntityPlayerSP(World worldIn) {
        super(worldIn);
    }

    public void finement$swingItemLocally() {
        super.swingItem();
    }

    @Inject(method = "dropOneItem", at = @At("HEAD"))
    private void finement$onDrop(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        ItemSwings.getInstance().onDrop();
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;doesGuiPauseGame()Z"))
    private boolean finement$allowGuisInPortals(GuiScreen instance) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().allowGuisInPortals) {
            return true;
        }
        return instance.doesGuiPauseGame();
    }
}