package com.unpainperdu.houblonneux.datagen.data.recipe;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipeBuilder;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipeBuilder;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.fluid.ModFluidRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import com.unpainperdu.houblonneux.util.StringHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider
{
    protected final HolderGetter<Fluid> fluids;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
        this.fluids = registries.lookupOrThrow(Registries.FLUID);
    }

    @Override
    protected void buildRecipes()
    {
        craftingTableRecipes();
        brewingRecipe();
        pompRecipe();
    }

    public void craftingTableRecipes()
    {
        //block
        this.shapeless(RecipeCategory.MISC, ModBlockRegister.COASTER)
                .requires(ItemTags.WOODEN_BUTTONS)
                .unlockedBy("has_button", this.has(ItemTags.WOODEN_BUTTONS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModBlockRegister.BEER_DISPENSER, 1)
                .define('x', Tags.Items.INGOTS_IRON)
                .define('y', Tags.Items.GEMS_EMERALD)
                .pattern("xxx")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
        // brewing_barrel
        this.brewingBarrelRecipe(ModBlockRegister.OAK_BREWING_BARREL, Blocks.OAK_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.SPRUCE_BREWING_BARREL, Blocks.SPRUCE_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.BIRCH_BREWING_BARREL, Blocks.BIRCH_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.JUNGLE_BREWING_BARREL, Blocks.JUNGLE_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.ACACIA_BREWING_BARREL, Blocks.ACACIA_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.DARK_OAK_BREWING_BARREL, Blocks.DARK_OAK_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.MANGROVE_BREWING_BARREL, Blocks.MANGROVE_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.CHERRY_BREWING_BARREL, Blocks.CHERRY_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.PALE_OAK_BREWING_BARREL, Blocks.PALE_OAK_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.BAMBOO_BREWING_BARREL, Blocks.BAMBOO_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.CRIMSON_BREWING_BARREL, Blocks.CRIMSON_PLANKS);
        this.brewingBarrelRecipe(ModBlockRegister.WARPED_BREWING_BARREL, Blocks.WARPED_PLANKS);
        //item
        this.shapeless(RecipeCategory.MISC, ModItemRegister.HOP_LUPULIN)
                .requires(ModItemRegister.HOP_FLOWER)
                .unlockedBy("has_houblon_flower", this.has(ModItemRegister.HOP_FLOWER))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.LOCKER, 1)
                .define('y', Tags.Items.NUGGETS_IRON)
                .define('x', Tags.Items.NUGGETS_GOLD)
                .pattern(" y ")
                .pattern("xxx")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Items.IRON_INGOT))
                .save(this.output);
        //  dye
        this.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE)
                .requires(ModItemRegister.HOP_LUPULIN)
                .unlockedBy("has_houblon_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output, "yellow_dye_from_lupulin");
        //  beer container
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE, 7)
                .define('x', Tags.Items.GLASS_BLOCKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .pattern(" x ")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_glass", this.has(Tags.Items.GLASS_BLOCKS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_GLASS, 6)
                .define('x', Tags.Items.GLASS_BLOCKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .pattern("x x")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_glass", this.has(Tags.Items.GLASS_BLOCKS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_MUG, 5)
                .define('x', ItemTags.PLANKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .define('z', Tags.Items.INGOTS_IRON)
                .pattern("x x")
                .pattern("zyz")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
        //  beer glass
        shapelessRecipe(ModItemRegister.EMERALD_CALL_GLASS, ModItemRegister.EMERALD_CALL_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.WORM_HOLE_GLASS, ModItemRegister.WORM_HOLE_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.GROS_GUEULETON_GLASS, ModItemRegister.GROS_GUEULETON_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.PAIN_DIEUX_GLASS, ModItemRegister.PAIN_DIEUX_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.REAL_DWARVE_GLASS, ModItemRegister.REAL_DWARVE_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.PIED_DE_GEANTS_GLASS, ModItemRegister.PIED_DE_GEANTS_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_GLASS, ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
    }

    /**
     *
     * @param ingredient first one used as unlock condition
     */
    private void shapelessRecipe(ItemLike result, ItemLike... ingredient)
    {
        ShapelessRecipeBuilder recipeBuilder = this.shapeless(RecipeCategory.MISC, result);
        for (ItemLike item : ingredient)
        {
            recipeBuilder.requires(item);
        }
        ItemLike firstIngredient = ingredient[0];
        recipeBuilder.unlockedBy("has_" + StringHelper.getSimpleName(firstIngredient.asItem()), this.has(firstIngredient))
                .save(this.output);
    }

    private void brewingBarrelRecipe(ItemLike barrel, ItemLike planks)
    {
        this.shaped(RecipeCategory.MISC, barrel, 1)
                .define('x', planks)
                .define('y', Tags.Items.INGOTS_IRON)
                .pattern("xxx")
                .pattern("y y")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
    }

    public void brewingRecipe()
    {
        this.brewing(72000, ModFluidRegister.EMERALD_CALL.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.POTATO,
                        Items.CARROT,
                        Items.WHEAT,
                        Items.BEETROOT,
                        Items.MELON_SLICE
                )
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(108000, ModFluidRegister.WORM_HOLE.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.ENDER_PEARL,
                        Items.WIND_CHARGE,
                        Items.APPLE
                )
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(144000, ModFluidRegister.GROS_GUEULETON.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.CAKE,
                        Items.MUSHROOM_STEW
                )
                .setIngredients(Tags.Items.FOODS_COOKED_MEAT, Tags.Items.FOODS_COOKED_MEAT)
                .setIngredients(Items.SUGAR)
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(82000, ModFluidRegister.PAIN_DIEUX.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.GUNPOWDER,
                        Items.GUNPOWDER,
                        Items.GUNPOWDER,
                        Items.REDSTONE,
                        Items.WHEAT,
                        Items.GOLDEN_CARROT
                )
                .setIngredients(
                        Tags.Items.NUGGETS_GOLD,
                        Tags.Items.NUGGETS_GOLD
                )
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(72000, ModFluidRegister.REAL_DWARVE.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.POTATO,
                        Items.POTATO,
                        Items.POTATO,
                        Items.FEATHER,
                        Items.EGG
                )
                .setIngredients(Tags.Items.RAW_MATERIALS_GOLD)
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(144000, ModFluidRegister.PIED_DE_GEANTS.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.WHEAT,
                        Items.BLAZE_POWDER,
                        Items.REDSTONE
                )
                .setIngredients(
                        Tags.Items.FOODS_COOKED_MEAT,
                        Tags.Items.FOODS_COOKED_MEAT,
                        Tags.Items.FOODS_COOKED_MEAT,
                        Tags.Items.FOODS_COOKED_MEAT
                )
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

        this.brewing(90000, ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS.get(), 4000)
                .setFluidIngredient(Fluids.WATER, 4000)
                .setIngredients(ModItemRegister.HOP_LUPULIN,
                        Items.BLUE_ICE,
                        Items.WHEAT,
                        Items.SUGAR
                )
                .setIngredients(Tags.Items.MUSHROOMS)
                .unlockedBy("has_hop_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output);

    }

    public BrewingRecipeBuilder brewing(int brewingTime, Fluid fluidResult, int fluidResultAmount)
    {
        return new BrewingRecipeBuilder(
                Houblonneux.MOD_ID,
                this.items,
                this.fluids,
                brewingTime,
                new FluidStackTemplate(fluidResult, fluidResultAmount)
        );
    }

    public void pompRecipe()
    {
        this.pomp(Items.WATER_BUCKET, 1)
                .setFluidIngredient(Fluids.WATER, 1000)
                .setIngredient(Tags.Items.BUCKETS)
                .unlockedBy("has_bucket", this.has(Tags.Items.BUCKETS_WATER))
                .save(this.output);

        this.pomp(ModItemRegister.EMERALD_CALL_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.EMERALD_CALL.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.EMERALD_CALL_MUG, 1)
                .setFluidIngredient(ModFluidRegister.EMERALD_CALL.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.WORM_HOLE_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.WORM_HOLE.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.WORM_HOLE_MUG, 1)
                .setFluidIngredient(ModFluidRegister.WORM_HOLE.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.GROS_GUEULETON_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.GROS_GUEULETON.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.GROS_GUEULETON_MUG, 1)
                .setFluidIngredient(ModFluidRegister.GROS_GUEULETON.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.PAIN_DIEUX_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.PAIN_DIEUX.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.PAIN_DIEUX_MUG, 1)
                .setFluidIngredient(ModFluidRegister.PAIN_DIEUX.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.REAL_DWARVE_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.REAL_DWARVE.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.REAL_DWARVE_MUG, 1)
                .setFluidIngredient(ModFluidRegister.REAL_DWARVE.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.PIED_DE_GEANTS_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.PIED_DE_GEANTS.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.PIED_DE_GEANTS_MUG, 1)
                .setFluidIngredient(ModFluidRegister.PIED_DE_GEANTS.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

        this.pomp(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE, 1)
                .setFluidIngredient(ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)
                .unlockedBy("has_empty_polymorphic_bottle", this.has(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                .save(this.output);
        this.pomp(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_MUG, 1)
                .setFluidIngredient(ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS.get(), 250)
                .setIngredient(ModItemRegister.EMPTY_POLYMORPHIC_MUG)
                .unlockedBy("has_empty_polymorphic_mug", this.has(ModItemRegister.EMPTY_POLYMORPHIC_MUG))
                .save(this.output);

    }

    public PumpingRecipeBuilder pomp(ItemLike item, int amount)
    {
        return new PumpingRecipeBuilder(
                Houblonneux.MOD_ID,
                this.items,
                this.fluids,
                new ItemStackTemplate(item.asItem(), amount)
        );
    }

    public static class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
        {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output)
        {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName()
        {
            return "Houblonneux Recipes";
        }
    }
}