package ru.magistu.siegemachines.client.gui;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.gui.machine.MachineContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import ru.magistu.siegemachines.client.gui.workbench.SiegeWorkbenchContainer;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(SiegeMachines.ID, Registries.MENU);

	public static final RegistrySupplier<MenuType<MachineContainer>> MACHINE_CONTAINER = MENUS.register("machine", () -> IMenuTypeExtension.create(MachineContainer::new));
	public static final RegistrySupplier<MenuType<SiegeWorkbenchContainer>> SIEGE_WORKBENCH_CONTAINER = registerMenuType(SiegeWorkbenchContainer::new, "siege_workbench");

	private static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}

	public static void register() {
		MENUS.register();
	}
}
