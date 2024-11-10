package ru.magistu.siegemachines;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class SiegeMachines {

    public static final String ID = "siegemachines";
    public static final String MOD_NAME = "ExampleMod";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final int RENDER_UPDATE_RANGE = 128;
    public static final int RENDER_UPDATE_TIME = 20;
    public static final int RENDER_UPDATE_RANGE_SQR = RENDER_UPDATE_RANGE * RENDER_UPDATE_RANGE;

    public static void init() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID,path);
    }

}