package at.yedel.finement.features.modern;



import at.yedel.finement.config.FinementConfig;
import at.yedel.finement.utils.SwingItemDuck;
//? if v0
 import cc.polyfrost.oneconfig.events.event.SendPacketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

import java.util.Objects;



public class ItemSwings {
    private static final ItemSwings INSTANCE = new ItemSwings();

    public static ItemSwings getInstance() {
        return INSTANCE;
    }

    private static final ImmutableList<String> SWING_ITEMS = ImmutableList.<String>builder()
        .add("minecraft:egg")
        .add("minecraft:ender_eye")
        .add("minecraft:experience_bottle")
        .add("minecraft:snowball")
        .build();

    private ItemSwings() {}

    public void swingOnSwingableUse(EntityPlayer player) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().itemUseSwings) {
            ItemStack itemStack = player.getHeldItem();
            if (itemStack == null) {
                return;
            }
            Item item = itemStack.getItem();
            String registryName = Item.itemRegistry.getNameForObject(item).toString();
            if (SWING_ITEMS.contains(registryName)) {
                swing();
            }
            else if (Objects.equals(registryName, "minecraft:potion") && ItemPotion.isSplash(itemStack.getMetadata())) {
                swing();
            }
            else if (Objects.equals(registryName, "minecraft:ender_pearl") && !Minecraft.getMinecraft().playerController.isInCreativeMode()) {
                swing();
            }
            else if (item instanceof ItemArmor) {
                int slot = EntityLiving.getArmorPosition(itemStack) - 1;
                if (player.getCurrentArmor(slot) == null) {
                    swing();
                }
            }
        }
    }

    @Subscribe
    public void swingOnDrop(SendPacketEvent event) {
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().itemDropSwings) {
            if (event.packet instanceof C07PacketPlayerDigging) {
                C07PacketPlayerDigging.Action action = ((C07PacketPlayerDigging) event.packet).getStatus();
                if ((action == C07PacketPlayerDigging.Action.DROP_ALL_ITEMS || action == C07PacketPlayerDigging.Action.DROP_ITEM) && Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    swing();
                }
            }
        }
    }

    private void swing() {
        ((SwingItemDuck) Minecraft.getMinecraft().thePlayer).finement$swingItemLocally();
    }
}
