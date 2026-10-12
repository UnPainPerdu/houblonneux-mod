package com.unpainperdu.houblonneux.datagen.data;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.data.tag.ModItemTags;
import com.unpainperdu.houblonneux.level.advancements.criterion.AllItemItemTagPredicate;
import com.unpainperdu.houblonneux.level.advancements.criterion.AllItemItemTagTrigger;
import com.unpainperdu.houblonneux.level.advancements.criterion.AnyDispenserTradeTrigger;
import com.unpainperdu.houblonneux.level.advancements.criterion.DamageWithEffectTrigger;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider
{
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, List.of(new ModAdvancementGenerator()));
    }

    private static final class ModAdvancementGenerator implements AdvancementSubProvider
    {
        private Consumer<AdvancementHolder> saver;

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> saver)
        {
            this.saver = saver;
            HolderGetter<Item> itemHolderGetter = provider.lookupOrThrow(Registries.ITEM);
            //root
            Advancement.Builder.advancement()
            .display(
                    ModItemRegister.HOP_FLOWER,
                    Component.translatable("advancements.houblonneux.main.root.title"),
                    Component.translatable("advancements.houblonneux.main.root.description"),
                    Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/advancements/backgrounds/main.png"),
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("get_hop_flower", InventoryChangeTrigger.TriggerInstance.hasItems(ModItemRegister.HOP_FLOWER))
            .requirements(AdvancementRequirements.allOf(List.of("get_hop_flower")))
            .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/root"));
            //main
            //  first_beer
            String advancementName = "first_beer";
            Advancement.Builder.advancement()
            .parent(AdvancementSubProvider.createPlaceholder("houblonneux:main/root"))
            .display(
                    ModItemRegister.EMERALD_CALL_BOTTLE,
                    Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                    Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("has_beer", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemHolderGetter, ModItemTags.BEER)))
            .requirements(AdvancementRequirements.allOf(List.of("has_beer")))
            .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
            //  all_mug_beer
            advancementName = "all_mug_beer";
            Advancement.Builder.advancement()
            .parent(AdvancementSubProvider.createPlaceholder("houblonneux:main/first_beer"))
            .display(
                    ModItemRegister.EMERALD_CALL_MUG,
                    Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                    Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                    null,
                    AdvancementType.CHALLENGE,
                    true,
                    true,
                    false
            )
            .addCriterion("has_all_mug", AllItemItemTagTrigger.Instance.instance(new AllItemItemTagPredicate(itemHolderGetter, ModItemTags.BEER_MUG)))
            .requirements(AdvancementRequirements.allOf(List.of("has_all_mug")))
            .rewards(AdvancementRewards.Builder.experience(160))
            .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
            //  use_beer_dispenser
            advancementName = "use_beer_dispenser";
            Advancement.Builder.advancement()
            .parent(AdvancementSubProvider.createPlaceholder("houblonneux:main/root"))
            .display(
                    ModBlockRegister.BEER_DISPENSER,
                    Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                    Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("has_traded_wtih_dispenser_trade", AnyDispenserTradeTrigger.Instance.instance())
            .requirements(AdvancementRequirements.allOf(List.of("has_traded_wtih_dispenser_trade")))
            .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
            //  get_hit_hard_as_dwarf
            advancementName = "get_hit_hard_as_dwarf";
            Advancement.Builder.advancement()
            .parent(AdvancementSubProvider.createPlaceholder("houblonneux:main/first_beer"))
            .display(
                    ModItemRegister.REAL_DWARVE_MUG,
                    Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                    Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("getting_hit_hard_as_dwarf", DamageWithEffectTrigger.Instance.instance(true, 20.0F, ModMobEffectRegister.REAL_DWARVE))
            .requirements(AdvancementRequirements.allOf(List.of("getting_hit_hard_as_dwarf")))
            .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
            //  hit_hard_as_giant
            advancementName = "hit_hard_as_giant";
            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("houblonneux:main/first_beer"))
                    .display(
                            ModItemRegister.PIED_DE_GEANTS_MUG,
                            Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                            Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_hit_hard_as_giant", DamageWithEffectTrigger.Instance.instance(false, 20.0F, ModMobEffectRegister.PIED_DE_GEANTS))
                    .requirements(AdvancementRequirements.allOf(List.of("has_hit_hard_as_giant")))
                    .save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
        }
    }
}
