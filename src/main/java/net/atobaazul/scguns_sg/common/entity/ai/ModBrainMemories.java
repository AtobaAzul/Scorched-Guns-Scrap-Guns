package net.atobaazul.scguns_sg.common.entity.ai;

import java.util.Optional;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.atobaazul.scguns_sg.ScrapGuns;
import net.atobaazul.scguns_sg.common.entity.gun.SimulatedGun;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author: Krei(github: rekales)
 */
public class ModBrainMemories {
        private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister
            .create(ForgeRegistries.MEMORY_MODULE_TYPES, ScrapGuns.MOD_ID);

    public static final Supplier<MemoryModuleType<Integer>> AIM_TICKS = MEMORY_MODULE_TYPES
            .register("aim_ticks", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final Supplier<MemoryModuleType<Float>> WEAPON_IDEAL_RANGE = MEMORY_MODULE_TYPES
            .register("approach_dist", () -> new MemoryModuleType<>(Optional.of(Codec.FLOAT)));
    public static final Supplier<MemoryModuleType<Float>> WEAPON_MAX_RANGE = MEMORY_MODULE_TYPES
            .register("weapon_range", () -> new MemoryModuleType<>(Optional.of(Codec.FLOAT)));
    public static final Supplier<MemoryModuleType<SimulatedGun>> SIMULATED_GUN = MEMORY_MODULE_TYPES
            .register("simulated_gun", () -> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<Boolean>> STUNNED = MEMORY_MODULE_TYPES
            .register("stunned", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final Supplier<MemoryModuleType<Boolean>> STUNNED_COOLING_DOWN = MEMORY_MODULE_TYPES
            .register("stunned_cooling_down", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final Supplier<MemoryModuleType<Integer>> HEADSHOT_COUNT = MEMORY_MODULE_TYPES
            .register("headshot_count", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final Supplier<MemoryModuleType<Long>> DELAYED_MELEE = MEMORY_MODULE_TYPES
            .register("delayed_melee", () -> new MemoryModuleType<>(Optional.of(Codec.LONG)));

        public static final int MAX_AIM_TICKS = 60;
        public static final int MIN_AIM_TICKS = -20;

    public static void register(IEventBus modEventBus) {
        MEMORY_MODULE_TYPES.register(modEventBus);
    }

}
