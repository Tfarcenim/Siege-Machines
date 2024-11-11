package ru.magistu.siegemachines.network;

import io.netty.channel.ChannelHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import ru.magistu.siegemachines.entity.machine.Machine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

@ChannelHandler.Sharable
public record PacketMachineInventorySlot(int entityid, int slot, ItemStack itemstack) implements S2CModPacket<RegistryFriendlyByteBuf> {

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketMachineControl> STREAM_CODEC =
            ModPacket.streamCodec(PacketMachineControl::read);


    public static final CustomPacketPayload.Type<PacketMachineControl> TYPE = ModPacket.type(PacketMachineControl.class);

    public static PacketMachineInventorySlot read(FriendlyByteBuf buf)
    {
        return new PacketMachineInventorySlot(buf.readInt(), buf.readInt(), buf.readItem());
    }

    public static void write(PacketMachineInventorySlot message, FriendlyByteBuf buf)
    {

    }

    @Override
    public void handleClient() {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) {
            return;
        }

        Entity entity = player.level().getEntity(entityid);
        if (!(entity instanceof Machine))
        {
            return;
        }
        Machine machine = (Machine) entity;

        machine.inventory.setItem(slot, itemstack);
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(entityid);
        buf.writeInt(slot);
        buf.writeItemStack(itemstack, false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
