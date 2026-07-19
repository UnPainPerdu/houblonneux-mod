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
        //  emerald_call
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_BOTTLE.get(), "emerald_call_bottle"); //TODO real model
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_GLASS.get(), "emerald_call_glass"); //TODO real model
        this.generateItemWithCustomModel(ModItemRegister.WORM_HOLE_MUG.get(), "emerald_call_mug"); //TODO real model
        //  emerald_call
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_BOTTLE.get(), "emerald_call_bottle"); //TODO real model
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_GLASS.get(), "emerald_call_glass"); //TODO real model
        this.generateItemWithCustomModel(ModItemRegister.GROS_GUEULETON_MUG.get(), "emerald_call_mug"); //TODO real model
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