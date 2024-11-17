package ru.magistu.siegemachines.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.renderer.*;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.entity.machine.Machine;
import ru.magistu.siegemachines.gui.machine.crosshair.Crosshair;
import ru.magistu.siegemachines.item.MachineItem;
import ru.magistu.siegemachines.item.ModItems;
import ru.magistu.siegemachines.network.C2SPacketMachineUse;
import ru.magistu.siegemachines.network.PacketHandler;
import ru.magistu.siegemachines.network.PacketOpenMachineInventory;

public class ClientProxyForge {
    static LayeredDraw.Layer layer = (guiGraphics, deltaTracker) -> {
        Minecraft mc = Minecraft.getInstance();
        Options settings = mc.options;
        LocalPlayer player = mc.player;

        if ((!settings.hideGui && !player.isReducedDebugInfo() && !settings.reducedDebugInfo().get()) || settings.getCameraType().compareTo(CameraType.FIRST_PERSON) != 0) {
            return;
        }

        Entity vehicle = player.getVehicle();
        Crosshair crosshair = ClientProxy.CROSSHAIR_FACTORIES.get(vehicle.getType());
        if (crosshair != null) {
            crosshair.render(guiGraphics, deltaTracker);
        }
    };

    public static void setup(IEventBus modEventBus) {
        modEventBus.addListener(ClientProxyForge::clientSetup);
        modEventBus.addListener(ClientProxyForge::registerRenderers);
        modEventBus.addListener(ClientProxyForge::extensions);
        modEventBus.addListener(ClientProxyForge::registerOverlay);
        modEventBus.addListener(ClientProxyForge::onKeyRegister);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        ClientProxy.setup();
        NeoForge.EVENT_BUS.addListener(ClientProxyForge::onKeyPressedEvent);
        NeoForge.EVENT_BUS.addListener(ClientProxyForge::onRenderOverlay);
    }

    public static void extensions(RegisterClientExtensionsEvent event) {
        for (RegistrySupplier<Item> supplier : ModItems.ITEMS) {
            Item item = supplier.get();
            if (item instanceof MachineItem<?> machineItem) {
                event.registerItem(new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return machineItem.getRenderer();
                    }
                }, machineItem);
            }
        }
    }

    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.MORTAR.get(), MortarGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CULVERIN.get(), CulverinGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.TREBUCHET.get(), TrebuchetGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CATAPULT.get(), CatapultGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BALLISTA.get(), BallistaGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BATTERING_RAM.get(), BatteringRamGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SIEGE_LADDER.get(), SiegeLadderGeoRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.CANNONBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GIANT_STONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.STONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GIANT_ARROW.get(), GiantArrowRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.SEAT.get(), SeatRenderer::new);
    }

    public static void onKeyPressedEvent(InputEvent.Key ev) {
        if (KeyBindings.MACHINE_USE.isDown()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isPassenger() && player.getVehicle() instanceof Machine machine && machine.usekey == KeyBindings.MACHINE_USE) {
                PacketHandler.sendToServer(new C2SPacketMachineUse());
            }
        }

        if (KeyBindings.LADDER_CLIMB.isDown()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isPassenger() && player.getVehicle() instanceof Machine machine && machine.usekey == KeyBindings.LADDER_CLIMB) {
                PacketHandler.sendToServer(new C2SPacketMachineUse());
            }
        }

        if (KeyBindings.MACHINE_INVENTORY.isDown()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isPassenger() && player.getVehicle() instanceof Machine) {
                PacketHandler.sendToServer(new PacketOpenMachineInventory());
            }
        }
    }

    public static void onRenderOverlay(RenderGuiLayerEvent.Pre ev) {
        ResourceLocation name = ev.getName();
        if (name.equals(VanillaGuiLayers.CROSSHAIR)) {
            LocalPlayer player = Minecraft.getInstance().player;
            Entity entity = player.getVehicle();
            if (ClientProxy.CROSSHAIR_FACTORIES.containsKey(entity.getType())) {
                ev.setCanceled(true);
            }
        }
    }

    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, SiegeMachines.id("crosshair"), layer);
    }

    public static void onKeyRegister(RegisterKeyMappingsEvent ev) {
        ev.register(KeyBindings.MACHINE_USE);
        ev.register(KeyBindings.LADDER_CLIMB);
        ev.register(KeyBindings.MACHINE_INVENTORY);
    }
}
