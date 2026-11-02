package net.atobaazul.scguns_sg.common.entity.ai;

import net.atobaazul.scguns_sg.ScrapGuns;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author: Krei(github: rekales)
 */
public class ModBrainSensors {

    private static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister
            .create(ForgeRegistries.SENSOR_TYPES, ScrapGuns.MOD_ID);

    public static final Supplier<SensorType<HeldGunSensor>> HELD_GUN = SENSOR_TYPES
            .register("held_gun", () -> new SensorType<>(HeldGunSensor::new));

    // Improved versions of vanilla sensors
    public static final Supplier<SensorType<VarRangePlayerSensor>> EXTREMELY_LONG_RANGE_PLAYER = SENSOR_TYPES
            .register("extremely_long_range_player", () -> new SensorType<>(() -> new VarRangePlayerSensor(60, 128)));

    public static void register(IEventBus modEventBus) {
        SENSOR_TYPES.register(modEventBus);
    }
}
