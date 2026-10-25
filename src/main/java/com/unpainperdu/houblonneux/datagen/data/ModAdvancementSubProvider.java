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
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;

public class ModAdvancementSubProvider extends AdvancementSubProvider
{
    protected final HolderGetter<Item> items;

    public ModAdvancementSubProvider(BootstrapContext<Advancement> output)
    {
        super(output);
        this.items = output.lookup(Registries.ITEM);
    }

    @Override
    public void generate()
    {
        //root
        AdvancementHolder rootAdv = Advancement.Builder.advancement()
                .rootDisplay(
                        new ItemStackTemplate(ModItemRegister.HOP_FLOWER.asItem()),
                        Component.translatable(getAdvancementTitle("root")),
                        Component.translatable(getAdvancementDescription("root")),
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "gui/advancements/backgrounds/root"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_hop_flower", InventoryChangeTrigger.TriggerInstance.hasItems(ModItemRegister.HOP_FLOWER))
                .requirements(AdvancementRequirements.allOf(List.of("get_hop_flower")))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/root"));
        //main
        //  first_beer
        String advancementName = "first_beer";
        AdvancementHolder firstBeerAdv = Advancement.Builder.advancement()
                .parent(rootAdv)
                .display(
                        new ItemStackTemplate(ModItemRegister.EMERALD_CALL_BOTTLE),
                        Component.translatable(getAdvancementTitle(advancementName)),
                        Component.translatable(getAdvancementDescription(advancementName)),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_beer", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(this.items, ModItemTags.BEER)))
                .requirements(AdvancementRequirements.allOf(List.of("has_beer")))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
        //  all_mug_beer
        advancementName = "all_mug_beer";
        AdvancementHolder allMugBeerAdv = Advancement.Builder.advancement()
                .parent(firstBeerAdv)
                .display(
                        new ItemStackTemplate(ModItemRegister.EMERALD_CALL_MUG),
                        Component.translatable(getAdvancementTitle(advancementName)),
                        Component.translatable(getAdvancementDescription(advancementName)),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("has_all_mug", AllItemItemTagTrigger.Instance.instance(new AllItemItemTagPredicate(this.items, ModItemTags.BEER_MUG)))
                .requirements(AdvancementRequirements.allOf(List.of("has_all_mug")))
                .rewards(AdvancementRewards.Builder.experience(160))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
        //  use_beer_dispenser
        advancementName = "use_beer_dispenser";
        AdvancementHolder useBeerDispenser = Advancement.Builder.advancement()
                .parent(rootAdv)
                .display(
                        new ItemStackTemplate(ModBlockRegister.BEER_DISPENSER.asItem()),
                        Component.translatable(getAdvancementTitle(advancementName)),
                        Component.translatable(getAdvancementDescription(advancementName)),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_traded_wtih_dispenser_trade", AnyDispenserTradeTrigger.Instance.instance())
                .requirements(AdvancementRequirements.allOf(List.of("has_traded_wtih_dispenser_trade")))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
        //  get_hit_hard_as_dwarf
        advancementName = "get_hit_hard_as_dwarf";
        AdvancementHolder getHitHardAsDwarf = Advancement.Builder.advancement()
                .parent(firstBeerAdv)
                .display(
                        new ItemStackTemplate(ModItemRegister.REAL_DWARVE_MUG.asItem()),
                        Component.translatable(getAdvancementTitle(advancementName)),
                        Component.translatable(getAdvancementDescription(advancementName)),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("getting_hit_hard_as_dwarf", DamageWithEffectTrigger.Instance.instance(true, 20.0F, ModMobEffectRegister.REAL_DWARVE))
                .requirements(AdvancementRequirements.allOf(List.of("getting_hit_hard_as_dwarf")))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
        //  hit_hard_as_giant
        advancementName = "hit_hard_as_giant";
        AdvancementHolder hitHardAsGiant = Advancement.Builder.advancement()
                .parent(firstBeerAdv)
                .display(
                        new ItemStackTemplate(ModItemRegister.PIED_DE_GEANTS_MUG.asItem()),
                        Component.translatable(getAdvancementTitle(advancementName)),
                        Component.translatable(getAdvancementDescription(advancementName)),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_hit_hard_as_giant", DamageWithEffectTrigger.Instance.instance(false, 20.0F, ModMobEffectRegister.PIED_DE_GEANTS))
                .requirements(AdvancementRequirements.allOf(List.of("has_hit_hard_as_giant")))
                .save(this.output, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/" + advancementName));
    }

    public static String getAdvancementTitle(String name)
    {
        return "advancements.houblonneux." + name + ".title";
    }

    public static String getAdvancementDescription(String name)
    {
        return "advancements.houblonneux." + name + ".description";
    }
}
