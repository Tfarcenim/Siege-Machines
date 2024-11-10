package ru.magistu.siegemachines;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModTags {
    public static class DamageTypes {
        public static final TagKey<DamageType> MACHINE_IMMUNE_TO = mod("machine_immune_to");

        static TagKey<DamageType> mod(String path) {
            return TagKey.create(Registries.DAMAGE_TYPE,SiegeMachines.id(path));
        }
    }
}
