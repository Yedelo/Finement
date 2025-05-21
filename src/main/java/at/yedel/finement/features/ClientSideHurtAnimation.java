package at.yedel.finement.features;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class ClientSideHurtAnimation {
	private static final ClientSideHurtAnimation INSTANCE = new ClientSideHurtAnimation();

	public static ClientSideHurtAnimation getInstance() {
		return INSTANCE;
	}

	private ClientSideHurtAnimation() {}

	@SubscribeEvent
	public void doClientSideHurtAnimation(AttackEntityEvent event) {
		if (!FinementConfig.getInstance().enabled || !FinementConfig.getInstance().clientSideHurtAnimation) return;
		if (!(event.target instanceof EntityLivingBase)) return;
		EntityLivingBase targetEntity = (EntityLivingBase) event.target;
		if (FinementConfig.getInstance().onlyEnableOnRealPlayers && !isRealPlayer(targetEntity)) return;
		if (targetEntity.hurtTime <= 0) {
			targetEntity.performHurtAnimation();
		}
	}

	private boolean isRealPlayer(Entity entity) {
		return entity instanceof EntityPlayer && entity.getUniqueID().version() == 4;
	}
}
