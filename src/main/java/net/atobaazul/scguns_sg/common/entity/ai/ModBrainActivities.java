package net.atobaazul.scguns_sg.common.entity.ai;

import net.atobaazul.scguns_sg.ScrapGuns;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author: Krei(github: rekales)
 */
public class ModBrainActivities {

    private static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister
            .create(ForgeRegistries.ACTIVITIES, ScrapGuns.MOD_ID);

    public static final Supplier<Activity> STUNNED = ACTIVITIES
            .register("stunned", () -> new Activity("stunned"));


    public static void register(IEventBus modEventBus) {
        ACTIVITIES.register(modEventBus);
    }
}
