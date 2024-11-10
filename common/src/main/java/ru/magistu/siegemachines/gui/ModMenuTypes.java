package ru.magistu.siegemachines.gui;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import ru.magistu.siegemachines.SiegeMachines;
import net.minecraft.world.inventory.MenuType;

import ru.magistu.siegemachines.gui.workbench.SiegeWorkbenchContainer;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(SiegeMachines.ID, Registries.MENU);

	public static final RegistrySupplier<MenuType<SiegeWorkbenchContainer>> SIEGE_WORKBENCH_CONTAINER = MENUS.register("siege_workbench",() -> new MenuType<>(SiegeWorkbenchContainer::new, FeatureFlags.VANILLA_SET));

	public static void register() {
		MENUS.register();
	}
}
