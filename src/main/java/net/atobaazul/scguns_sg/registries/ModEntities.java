package net.atobaazul.scguns_sg.registries;


import net.atobaazul.scguns_sg.common.entity.PiglinJunker.PiglinJunkerEntity;
import net.atobaazul.scguns_sg.common.entity.PiglinJunker.PiglinJunkerRenderer;
import net.atobaazul.scguns_sg.common.entity.parts.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import top.ribs.scguns.common.BoundingBoxManager;

import java.util.function.BiFunction;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    //bullet projectiles example
    //public static final RegistryObject<EntityType<HexRoundProjectileEntity>> HEX_ROUND_PROJECTILE = registerBasic("hex_round_projectile", HexRoundProjectileEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerBasic(String id, BiFunction<EntityType<T>, Level, T> function) {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(100).setUpdateInterval(1).noSummon().fireImmune().noSave().setShouldReceiveVelocityUpdates(true).build(id));
    }

    //mobs example
    /*public static final RegistryObject<EntityType<GravekeeperGhoulEntity>> GRAVEKEEPER_GHOUL = REGISTER.register("gravekeeper_ghoul", () -> EntityType.Builder.of(GravekeeperGhoulEntity::new, MobCategory.MONSTER)
            .sized(0.8f,2.0f)
            .build(new ResourceLocation(MOD_ID, "gravekeeper_ghoul").toString())
    );*/

    public static final RegistryObject<EntityType<PiglinJunkerEntity>> PIGLIN_JUNKER = REGISTER.register("piglin_junker", () -> EntityType.Builder.of(PiglinJunkerEntity::new, MobCategory.MONSTER)
            .sized(2.6f,3.375f)
            .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "piglin_junker").toString())
    );

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
        modEventBus.addListener(ModEntities::registerAttributes);
        modEventBus.addListener(ModEntities::onCommonSetup);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ModEntities::onClientSetup);
        }
    }
    private static void onCommonSetup(FMLCommonSetupEvent event) {
        BoundingBoxManager.registerHeadshotBox(ModEntities.PIGLIN_JUNKER.get(), new OffsetRotatedHeadshotBox<>(
            16, 
            18, 
            10, 
            0, 
            false, 
            true
        ));
        WeakPointBoxManager.registerWeakPointBox(ModEntities.PIGLIN_JUNKER.get(), 
            new WeakPointBox<>(
                new OffsetRotatedHeadshotBox<>(
                    6.0, 
                    30.0, 
                    30.0, 
                    0.0F, 
                    -4.0, 
                    -25f,
                    false, 
                    true, 
                    true
                )
            ),
            new WeakPointBox<>(
                new OffsetRotatedHeadshotBox<>(
                    6.0, 
                    30.0, 
                    38.0, 
                    0.0F, 
                    5.0, 
                    10,
                    false, 
                    true, 
                    true
                )
            )
        );
    }
    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(PIGLIN_JUNKER.get(), PiglinJunkerEntity.createAttributes().build());
    }

    @OnlyIn(value = Dist.CLIENT)
    private static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PIGLIN_JUNKER.get(), (ctx) -> new PiglinJunkerRenderer<>(ctx, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "piglin_junker/piglin_junker"))).hasGlowLayer().noDeathTilt().noDeathRedTint());
    }
}
