package ru.magistu.siegemachines.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;

public abstract class CommonPartEntity<T extends Entity> extends Entity {
    private final T parent;

    public CommonPartEntity(T parent) {
        super(parent.getType(), parent.level());
        this.parent = parent;
    }

    public T getParent() {
        return parent;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        throw new UnsupportedOperationException();
    }
}
