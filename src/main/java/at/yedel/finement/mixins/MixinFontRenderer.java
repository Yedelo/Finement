package at.yedel.finement.mixins;



import at.yedel.finement.config.DontObfuscateText;
import net.minecraft.client.gui.FontRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;



@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
    @Shadow private boolean randomStyle;

    @Redirect(method = "renderStringAtPos", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/FontRenderer;randomStyle:Z", opcode = Opcodes.GETFIELD))
    private boolean finement$dontObfuscateText(FontRenderer instance) {
        if (DontObfuscateText.isEnabled()) {
            return false;
        }
        return randomStyle;
    }
}
