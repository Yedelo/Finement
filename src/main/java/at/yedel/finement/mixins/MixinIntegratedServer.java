package at.yedel.finement.mixins;



import at.yedel.finement.config.FinementConfig;
import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.server.integrated.IntegratedServer;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;



@Mixin(IntegratedServer.class)
public abstract class MixinIntegratedServer {
    @ModifyArg(method = "shareToLAN", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkSystem;addLanEndpoint(Ljava/net/InetAddress;I)V"), index = 1)
    private int finement$setLanPortTitle(int port) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().changeWindowTitle) {
            UMinecraft.getMinecraft().addScheduledTask(() -> {
                Display.setTitle("Minecraft 1.8.9 - LAN Singleplayer - " + port);
            });
        }
        return port;
    }
}
