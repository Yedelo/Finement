package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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
	@Unique private Slot finement$slotUnderMouse;

	@Inject(method = "drawScreen", at = @At("HEAD"))
	private void finement$storeMousePositions(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		this.finement$mouseX = mouseX;
		this.finement$mouseY = mouseY;
		if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().snapItemRendering) {
			finement$slotUnderMouse = getSlotUnderMouse();
		}
	}

	@ModifyArgs(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGradientRect(IIIIII)V"))
	private void finement$smoothSlotHovering(Args args) {
		if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().smoothSlotHovering) {
			args.set(0, finement$mouseX - guiLeft - 8);
			args.set(1, finement$mouseY - guiTop - 8);
			args.set(2, finement$mouseX - guiLeft + 8);
			args.set(3, finement$mouseY - guiTop + 8);
		}
	}

	// can't use multiple ModifyArgs because crash ):
	@ModifyArg(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawItemStack(Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"), index = 1)
	private int finement$snapItemRendering$x(int x) {
		return finement$slotUnderMouse != null ? finement$slotUnderMouse.xDisplayPosition : x;
	}

	@ModifyArg(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawItemStack(Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"), index = 2)
	private int finement$snapItemRendering$y(int y) {
		return finement$slotUnderMouse != null ? finement$slotUnderMouse.yDisplayPosition : y;
	}
}
