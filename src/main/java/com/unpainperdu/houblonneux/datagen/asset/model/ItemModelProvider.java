package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.Optional;

public class ItemModelProvider
{
    private final ItemModelGenerators itemModelsGenerator;
    private final ModelProvider provider;

    public ItemModelProvider(ItemModelGenerators itemModelsGenerator, ModelProvider provider)
    {
        this.itemModelsGenerator = itemModelsGenerator;
        this.provider = provider;
        generateModel();
    }

    public void generateModel()
    {
        this.itemModelsGenerator.generateFlatItem(ModItemRegister.HOP_FLOWER.get(), ModelTemplates.FLAT_ITEM);
        this.itemModelsGenerator.generateFlatItem(ModItemRegister.HOP_LUPULIN.get(), ModelTemplates.FLAT_ITEM);
        this.itemModelsGenerator.generateFlatItem(ModItemRegister.LOCKER.get(), ModelTemplates.FLAT_ITEM);
        //beer
        //  empty
        this.generateItemWithCustomModel(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE.get(), "empty_polymorphic_bottle");
        this.generateItemWithCustomModel(ModItemRegister.EMPTY_POLYMORPHIC_GLASS.get(), "empty_polymorphic_glass");
        this.generateItemWithCustomModel(ModItemRegister.EMPTY_POLYMORPHIC_MUG.get(), "empty_polymorphic_mug");
        //  emerald_call
        this.generateItemWithCustomModel(ModItemRegister.EMERALD_CALL_BOTTLE.get(), "emerald_call_bottle");
        this.generateItemWithCustomModel(ModItemRegister.EMERALD_CALL_GLASS.get(), "emerald_call_glass");
        this.generateItemWithCustomModel(ModItemRegister.EMERALD_CALL_MUG.get(), "emerald_call_mug");
        //  worm_hole
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_BOTTLE.get(), "worm_hole_bottle");
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_GLASS.get(), "worm_hole_glass");
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_MUG.get(), "worm_hole_mug");
        //  gros_gueuleton
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_BOTTLE.get(), "gros_gueuleton_bottle");
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_GLASS.get(), "gros_gueuleton_glass");
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_MUG.get(), "gros_gueuleton_mug");
        //  pain_dieux
        this.generateItemWithCustomModel(ModItemRegister.PAIN_DIEUX_BOTTLE.get(), "pain_dieux_bottle");
        this.generateItemWithCustomModel(ModItemRegister.PAIN_DIEUX_GLASS.get(), "pain_dieux_glass");
        this.generateItemWithCustomModel(ModItemRegister.PAIN_DIEUX_MUG.get(), "pain_dieux_mug");
        //  real_dwarve
        this.generateItemWithCustomModel(ModItemRegister.REAL_DWARVE_BOTTLE.get(), "real_dwarve_bottle");
        this.generateItemWithCustomModel(ModItemRegister.REAL_DWARVE_GLASS.get(), "real_dwarve_glass");
        this.generateItemWithCustomModel(ModItemRegister.REAL_DWARVE_MUG.get(), "real_dwarve_mug");
        //  pied_de_geants
        this.generateItemWithCustomModel(ModItemRegister.PIED_DE_GEANTS_BOTTLE.get(), "pied_de_geants_bottle");
        this.generateItemWithCustomModel(ModItemRegister.PIED_DE_GEANTS_GLASS.get(), "pied_de_geants_glass");
        this.generateItemWithCustomModel(ModItemRegister.PIED_DE_GEANTS_MUG.get(), "pied_de_geants_mug");
        //  la_blanche_de_chez_nous
        this.generateItemWithCustomModel(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE.get(), "la_blanche_de_chez_nous_bottle");
        this.generateItemWithCustomModel(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_GLASS.get(), "la_blanche_de_chez_nous_glass");
        this.generateItemWithCustomModel(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_MUG.get(), "la_blanche_de_chez_nous_mug");
    }

    private void generateItemWithCustomModel(Item item, String modelLocation)
    {
        this.generateItemWithCustomModel(item, modelLocation, Map.of());
    }

    /**
     * @param modelLocation            houblonneux:item/ added in method
     * @param textureSlotAndTextureLoc houblonneux:item/ added in method for texture loc
     */
    private void generateItemWithCustomModel(Item item, String modelLocation, Map<TextureSlot, String> textureSlotAndTextureLoc)
    {
        ModelTemplate model = new ModelTemplate(
                Optional.of(ModelLocationUtils.decorateItemModelLocation("houblonneux:" + modelLocation)),
                Optional.of("_generated"),
                textureSlotAndTextureLoc.keySet().toArray(new TextureSlot[0])
        );

        TextureMapping textureMapping = new TextureMapping();
        textureSlotAndTextureLoc.forEach((slot, textureLoc) -> textureMapping.put(slot, new Material(ModelLocationUtils.decorateItemModelLocation("houblonneux:" + textureLoc))));

        Identifier modelAndTexture = model.create(
                item,
                textureMapping,
                this.itemModelsGenerator.modelOutput);

        this.itemModelsGenerator.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelAndTexture));
    }
}