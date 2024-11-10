package ru.magistu.siegemachines.event;

import ru.magistu.siegemachines.SiegeMachines;
import net.minecraftforge.eventbus.api.EventPriority;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.entity.machine.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SiegeMachines.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.MORTAR.get(), Mortar.setEntityAttributes(MachineType.MORTAR).build());
        event.put(ModEntityTypes.CULVERIN.get(), Mortar.setEntityAttributes(MachineType.CULVERIN).build());
        event.put(ModEntityTypes.TREBUCHET.get(), Trebuchet.setEntityAttributes(MachineType.TREBUCHET).build());
        event.put(ModEntityTypes.CATAPULT.get(), Catapult.setEntityAttributes(MachineType.CATAPULT).build());
        event.put(ModEntityTypes.BALLISTA.get(), Ballista.setEntityAttributes(MachineType.BALLISTA).build());
        event.put(ModEntityTypes.BATTERING_RAM.get(), BatteringRam.setEntityAttributes(MachineType.BATTERING_RAM).build());
        event.put(ModEntityTypes.SIEGE_LADDER.get(), SiegeLadder.setEntityAttributes(MachineType.SIEGE_LADDER).build());
    }
}
