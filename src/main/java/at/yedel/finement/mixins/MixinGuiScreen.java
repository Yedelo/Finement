package at.yedel.finement.mixins;



import java.util.List;

import at.yedel.finement.Finement;
import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;



@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	@Inject(
		method = "renderToolTip",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/item/ItemStack;getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Z)Ljava/util/List;"
		),
		locals = LocalCapture.CAPTURE_FAILSOFT,
		cancellable = true
	)
	private void finement$dontRenderEmptyTooltip(ItemStack stack, int x, int y, CallbackInfo ci, List<String> list) {
		if (FinementConfig.getInstance().dontRenderEmptyTooltips) {
			if (list.stream().map(Finement::removeFormatting).allMatch(string -> string.trim().isEmpty())) {
				ci.cancel();
			}
		}
	}
}
