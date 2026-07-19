package at.yedel.finement.mixins;



import at.yedel.finement.config.DontObfuscateText;
import club.sk1er.patcher.mixins.accessors.FontRendererAccessor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;



@Pseudo
@Mixin(targets = "club.sk1er.patcher.hooks.FontRendererHook", remap = false)
public abstract class MixinFontRendererHook {
    @Dynamic("Patcher")
    @Redirect(method = "Lclub/sk1er/patcher/hooks/FontRendererHook;renderStringAtPos(Ljava/lang/String;Z)Z", at = @At(value = "INVOKE", target = "Lclub/sk1er/patcher/mixins/accessors/FontRendererAccessor;isRandomStyle()Z"))
    private boolean finement$dontObfuscateText(FontRendererAccessor instance) {
        if (DontObfuscateText.isEnabled()) {
            return false;
        }
        return instance.isRandomStyle();
    }
}
