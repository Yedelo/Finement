package at.yedel.finement.mixins;



import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;



@Mixin(FontRenderer.class)
public interface AccessorFontRenderer {
	@Accessor("colorCode")
	int[] finement$getColorCodeArray();
}
