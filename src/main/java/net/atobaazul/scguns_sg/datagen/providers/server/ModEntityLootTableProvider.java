package net.atobaazul.scguns_sg.datagen.providers.server;

import net.atobaazul.scguns_sg.registries.ModEntities;
import net.atobaazul.scguns_sg.registries.ModItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModEntityLootTableProvider extends EntityLootSubProvider{
    public ModEntityLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags(), FeatureFlagSet.of());
    }

    @Override
    public void generate() {
        basicLoot(ModEntities.PIGLIN_JUNKER.get())
        .constant(ModItems.SCRAP_CORE.get(), 1)
        .range(top.ribs.scguns.init.ModItems.ANTHRALITE_INGOT.get(), 3, 6);
    }
    private LootTableEntryBuilder basicLoot(EntityType<?> type) {
        LootTable.Builder lootTableRef = LootTable.lootTable();
        add(type, lootTableRef);
        return LootTableEntryBuilder.of(lootTableRef);
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    private static class LootTableEntryBuilder {

        private final LootTable.Builder lootTableRef;

        private LootTableEntryBuilder(LootTable.Builder lootTableRef) {
            this.lootTableRef = lootTableRef;
        }

        public static LootTableEntryBuilder of(LootTable.Builder lootTableRef) {
            return new LootTableEntryBuilder(lootTableRef);
        }

        public LootTable.Builder getLootTableRef() {
            return this.lootTableRef;
        }

        public LootTableEntryBuilder constant(Item item, int count) {
            this.lootTableRef.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))
                    )
            );

            return this;
        }

        public LootTableEntryBuilder range(Item item, int min, int max) {
            this.lootTableRef.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add( LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                    )
            );
            return this;
        }
    }
}
