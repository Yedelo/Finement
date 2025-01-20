package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;



@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {
	@Shadow public abstract Slot getSlotUnderMouse();

	@Shadow protected int guiLeft;
	@Shadow protected int guiTop;
	@Unique private int finement$mouseX;
	@Unique private int finement$mouseY;

	@Inject(method = "drawScreen", at = @At("HEAD"))
	private void finement$storeMousePositions(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		this.finement$mouseX = mouseX;
		this.finement$mouseY = mouseY;
	}

	@ModifyArgs(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGradientRect(IIIIII)V"))
	private void finement$smoothSlotHovering(Args args) {
		if (FinementConfig.getInstance().smoothSlotHovering) {
			args.set(1, finement$mouseX - guiLeft - 8);
			args.set(2, finement$mouseY - guiTop - 8);
			args.set(3, finement$mouseX - guiLeft + 8);
			args.set(4, finement$mouseY - guiTop + 8);
		}
	}

	@ModifyArgs(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawItemStack(Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"))
	private void finement$snapItemRendering(Args args) {
		if (FinementConfig.getInstance().snapItemRendering) {
			Slot slotUnderMouse = getSlotUnderMouse();
			if (slotUnderMouse != null) {
				args.set(1, slotUnderMouse.xDisplayPosition);
				args.set(2, slotUnderMouse.yDisplayPosition);
			}
		}
	}
}
