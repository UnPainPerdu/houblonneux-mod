package com.unpainperdu.houblonneux.datagen.data;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            //root
            generateRootAdvancement();
        }

        /**
         * description component will be "advancements.houblonneux.main.root.description"
         * title component will be "advancements.houblonneux.main.root.title"
         */
        private void generateRootAdvancement()
        {
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.display(
                    ModItemRegister.HOP_FLOWER,
                    Component.translatable("advancements.houblonneux.main.root.title"),
                    Component.translatable("advancements.houblonneux.main.root.description"),
                    Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/advancements/backgrounds/main.png"),
                    AdvancementType.TASK,
                    false,
                    false,
                    true
            );
            builder.addCriterion("get_hop_flower", InventoryChangeTrigger.TriggerInstance.hasItems(ModItemRegister.HOP_FLOWER));
            builder.requirements(AdvancementRequirements.allOf(List.of("get_hop_flower")));
            builder.save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "main/root"));
        }

        /**
         * description component will be "advancements.houblonneux.\advancementName/.description"
         * title component will be "advancements.houblonneux.\advancementName/.title"
         */
        private void generateAdvancementWithMainAsRoot(ItemLike itemToDisplay, String advancementName, String parentName, AdvancementType advancementType, Map<String, Criterion<?>> condition)
        {
            generateAdvancement("main", "houblonneux:main/" + parentName, itemToDisplay, advancementName, advancementType, condition, null);
        }

        private void generateAdvancementWithMainAsRoot(ItemLike itemToDisplay, String advancementName, String parentName, AdvancementType advancementType, Map<String, Criterion<?>> condition, AdvancementRewards.Builder rewardsBuilder)
        {
            generateAdvancement("main", "houblonneux:main/" + parentName, itemToDisplay, advancementName, advancementType, condition, rewardsBuilder);
        }

        private void generateAdvancement(String page, String parent, ItemLike itemToDisplay, String advancementName, AdvancementType advancementType, Map<String, Criterion<?>> conditions, AdvancementRewards.Builder rewardsBuilder)
        {
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.parent(AdvancementSubProvider.createPlaceholder(parent));
            builder.display(
                    itemToDisplay,
                    Component.translatable("advancements.houblonneux." + advancementName + ".title"),
                    Component.translatable("advancements.houblonneux." + advancementName + ".description"),
                    null,
                    advancementType,
                    true,
                    true,
                    false
            );
            conditions.forEach(builder::addCriterion);
            List<String> requirement = new ArrayList<>(conditions.keySet());
            builder.requirements(AdvancementRequirements.allOf(requirement));
            if (rewardsBuilder != null)
            {
                builder.rewards(rewardsBuilder);
            }
            builder.save(this.saver, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, page + "/" + advancementName));
        }
    }
}
