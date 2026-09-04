package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(GuiScreenBook.class)
public abstract class MixinGuiScreenBook extends GuiScreen {
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void finement$drawBookBackground(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().bookBackground) {
            drawWorldBackground(1);
        }
    }
}
