package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.screen.BrewingBarrelScreen;
import com.unpainperdu.houblonneux.client.screen.util.FluidTankRenderer;
import com.unpainperdu.houblonneux.integration.jei.category.BrewingCategory;
import com.unpainperdu.houblonneux.integration.jei.category.PumpingCategory;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.EmeraldCallBeerConsumeEffect;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.GrosGueuletonBeerConsumeEffect;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.WormHoleBeerConsumeEffect;
import com.unpainperdu.houblonneux.neoevent.ModItemToolTipEvent;
import com.unpainperdu.houblonneux.register.ModCreativeTabRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import com.unpainperdu.houblonneux.register.fluid.ModFluidTypeRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider
{
    public EnglishLanguageProvider(PackOutput output)
    {
        super(output, Houblonneux.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        CommonLanguageHelper clp = new CommonLanguageHelper(this);
        //neoforge
        //  config
        this.add(Houblonneux.Config.PAIN_DIEUX_CAN_DESTROY_BLOCKS_TRANSLATION_KEY, "Pain Dieux explosion destroy blocks");
        this.add(Houblonneux.Config.PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_TRANSLATION_KEY, "Pain Dieux explosion hurt other player");
        //integration
        //  jei
        this.add(BrewingCategory.CATEGORY_KEY, "Brewing");
        this.add(PumpingCategory.CATEGORY_KEY, "Pumping");
        this.add(BrewingCategory.BREWING_TIME_KEY, "Time To Brew : %sh %sm %ss");
        //fluid
        this.add(ModFluidTypeRegister.EMERALD_CALL.get().getDescriptionId(), "Emerald Call");
        this.add(ModFluidTypeRegister.WORM_HOLE.get().getDescriptionId(), "Worm Hole");
        this.add(ModFluidTypeRegister.GROS_GUEULETON.get().getDescriptionId(), "Gros Gueuleton");
        //menu
        this.add(BeerDispenserBlockEntity.TITLE_KEY, "Disk Storage");
        this.add(BrewingBarrelBlockEntity.TITLE_KEY, "Brewing Barrel");
        this.add(BrewingBarrelScreen.BREWING_TIME, "Time left :\n%sh %sm %ss\nOf %sh %sm %ss");
        this.add(BrewingBarrelScreen.NO_BREWING, "Nothing Brew");
        //tooltip
        this.add(FluidTankRenderer.AMOUNT, "%s mB");
        this.add(FluidTankRenderer.AMOUNT_AND_CAPACITY, "%s / %s mB");
        this.add(ModItemToolTipEvent.ITEM_FLUIDSTACK_TOOLTIP, "%s mB of %s");
        //  menu
        this.add(BrewingBarrelScreen.TRASH, "Delete liquid Stored ?");
        //creative tabs
        this.add(ModCreativeTabRegister.MAIN_TAB_TRANSLATION_KEY, "Houblonneux");
        // mob effect
        this.addEffect(ModMobEffectRegister.WORM_HOLE, "Worm Hole");
        // block
        this.add(ModBlockRegister.BEER_DISPENSER.get().asItem(), "Beer Dispenser");
        this.add(ModBlockRegister.COASTER.get().asItem(), "Coaster");
        //items
        this.add(ModItemRegister.HOP_FLOWER.get(), "Hop Flower");
        this.add(ModItemRegister.HOP_LUPULIN.get(), "Hop Lupulin");
        this.add(ModItemRegister.LOCKER.get(), "Locker");
        //beer
        //  empty
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE.get(), "Empty Polymorphic Bottle");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_GLASS.get(), "Empty Polymorphic Glass");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_MUG.get(), "Empty Polymorphic Mug");
        //  emerald_call
        this.add(ModItemRegister.EMERALD_CALL_BOTTLE.get(), "Emerald Call Bottle");
        this.add(ModItemRegister.EMERALD_CALL_GLASS.get(), "Emerald Call Glass");
        this.add(ModItemRegister.EMERALD_CALL_MUG.get(), "Emerald Call Mug");
        this.add(EmeraldCallBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "Green Bottle and Green Beer !");
        this.add(EmeraldCallBeerConsumeEffect.GLASS_TOOLTIP_KEY, "I smell... Money");
        this.add(EmeraldCallBeerConsumeEffect.MUG_TOOLTIP_KEY, "Good Deals in sight");
        //  worm_hole
        this.add(ModItemRegister.WORM_HOLE_BOTTLE.get(), "Worm Hole Bottle");
        this.add(ModItemRegister.WORM_HOLE_GLASS.get(), "Worm Hole Glass");
        this.add(ModItemRegister.WORM_HOLE_MUG.get(), "Worm Hole Mug");
        this.add(WormHoleBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "Zoup in. Zoup out");
        this.add(WormHoleBeerConsumeEffect.GLASS_TOOLTIP_KEY, "Where the fuck Am I now ?");
        this.add(WormHoleBeerConsumeEffect.MUG_TOOLTIP_KEY, "We could not find a better use of this technology !");
        //  gros_gueuleton
        this.add(ModItemRegister.GROS_GUEULETON_BOTTLE.get(), "Gros Gueuleton Bottle");
        this.add(ModItemRegister.GROS_GUEULETON_GLASS.get(), "Gros Gueuleton Glass");
        this.add(ModItemRegister.GROS_GUEULETON_MUG.get(), "Gros Gueuleton Mug");
        this.add(GrosGueuletonBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "One beer is equivalent of one slice of bread");
        this.add(GrosGueuletonBeerConsumeEffect.GLASS_TOOLTIP_KEY, "So much");
        this.add(GrosGueuletonBeerConsumeEffect.MUG_TOOLTIP_KEY, "We does not need anymore anything else !");
        // brewing_barrel
        this.add(ModBlockRegister.OAK_BREWING_BARREL.asItem(), "Oak Brewing Barrel");
        this.add(ModBlockRegister.SPRUCE_BREWING_BARREL.asItem(), "Spruce Brewing Barrel");
        this.add(ModBlockRegister.BIRCH_BREWING_BARREL.asItem(), "Birch Brewing Barrel");
        this.add(ModBlockRegister.JUNGLE_BREWING_BARREL.asItem(), "Jungle Brewing Barrel");
        this.add(ModBlockRegister.ACACIA_BREWING_BARREL.asItem(), "Acacia Brewing Barrel");
        this.add(ModBlockRegister.DARK_OAK_BREWING_BARREL.asItem(), "Dark Oak Brewing Barrel");
        this.add(ModBlockRegister.MANGROVE_BREWING_BARREL.asItem(), "Mangrove Brewing Barrel");
        this.add(ModBlockRegister.CHERRY_BREWING_BARREL.asItem(), "Cherry Brewing Barrel");
        this.add(ModBlockRegister.PALE_OAK_BREWING_BARREL.asItem(), "Pale Oak Brewing Barrel");
        this.add(ModBlockRegister.BAMBOO_BREWING_BARREL.asItem(), "Bamboo Brewing Barrel");
        this.add(ModBlockRegister.CRIMSON_BREWING_BARREL.asItem(), "Crimson Brewing Barrel");
        this.add(ModBlockRegister.WARPED_BREWING_BARREL.asItem(), "Warped Brewing Barrel");
    }
}