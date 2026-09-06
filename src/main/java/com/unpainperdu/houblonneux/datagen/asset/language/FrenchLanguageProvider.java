package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.screen.BrewingBarrelScreen;
import com.unpainperdu.houblonneux.integration.jei.category.BrewingCategory;
import com.unpainperdu.houblonneux.integration.jei.category.PumpingCategory;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.*;
import com.unpainperdu.houblonneux.neoevent.ModItemToolTipEvent;
import com.unpainperdu.houblonneux.register.ModCreativeTabRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class FrenchLanguageProvider extends LanguageProvider
{
    public FrenchLanguageProvider(PackOutput output)
    {
        super(output, Houblonneux.MOD_ID, "fr_fr");
    }

    @Override
    protected void addTranslations()
    {
        CommonLanguageHelper clp = new CommonLanguageHelper(this);
        //integration
        //  jei
        this.add(BrewingCategory.CATEGORY_KEY, "Brassage");
        this.add(PumpingCategory.CATEGORY_KEY, "Tirage");
        this.add(BrewingCategory.BREWING_TIME_KEY, "Temps de brassage : %sh %sm %ss");
        //menu
        this.add(BeerDispenserBlockEntity.TITLE_KEY, "Stockage de disque");
        this.add(BrewingBarrelBlockEntity.TITLE_KEY, "Tonneau de fermentation");
        this.add(BrewingBarrelScreen.BREWING_TIME, "Temps restant :\n%sh %sm %ss\nSur %sh %sm %ss");
        this.add(BrewingBarrelScreen.NO_BREWING, "Rien ne fermante");
        //tooltip
        this.add(ModItemToolTipEvent.ITEM_FLUIDSTACK_TOOLTIP, "%s mB de %s");
        //  menu
        this.add(BrewingBarrelScreen.TRASH, "Supprimer le liquide contenu?");
        //creative tabs
        this.add(ModCreativeTabRegister.MAIN_TAB_TRANSLATION_KEY, "Houblonneux");
        // mob effect
        this.addEffect(ModMobEffectRegister.WORM_HOLE, "Worm Hole");
        // block
        this.add(ModBlockRegister.BEER_DISPENSER.get().asItem(), "Distributeur de bière");
        this.add(ModBlockRegister.COASTER.get().asItem(), "Dessous de verre");
        //items
        this.add(ModItemRegister.HOP_FLOWER.get(), "Fleur de Houblon");
        this.add(ModItemRegister.HOP_LUPULIN.get(), "Lupuline de Houblon");
        this.add(ModItemRegister.LOCKER.get(), "Verrouilleur");
        //beer
        //  empty
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE.get(), "Bouteille polymorphe vide");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_GLASS.get(), "Verre polymorphe vide");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_MUG.get(), "Chope polymorphe vide");
        //  emerald_call
        this.add(ModItemRegister.EMERALD_CALL_BOTTLE.get(), "Bouteille d'Emerald Call");
        this.add(ModItemRegister.EMERALD_CALL_GLASS.get(), "Verre d'Emerald Call");
        this.add(ModItemRegister.EMERALD_CALL_MUG.get(), "Chope d'Emerald Call");
        this.add(EmeraldCallBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "Bouteille et bière verte!");
        this.add(EmeraldCallBeerConsumeEffect.GLASS_TOOLTIP_KEY, "Ca sent... le commerce.");
        this.add(EmeraldCallBeerConsumeEffect.MUG_TOOLTIP_KEY, "De bonnes affaires en perspectives.");
        //  worm_hole
        this.add(ModItemRegister.WORM_HOLE_BOTTLE.get(), "Bouteille de Worm Hole");
        this.add(ModItemRegister.WORM_HOLE_GLASS.get(), "Verre de Worm Hole");
        this.add(ModItemRegister.WORM_HOLE_MUG.get(), "Chope de Worm Hole");
        this.add(WormHoleBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "Disparu et réaparu de si tôt");
        this.add(WormHoleBeerConsumeEffect.GLASS_TOOLTIP_KEY, "Où bordel de merde je suis?");
        this.add(WormHoleBeerConsumeEffect.MUG_TOOLTIP_KEY, "La meilleure manière d'utiliser cette technologie!");
        //  gros_gueuleton
        this.add(ModItemRegister.GROS_GUEULETON_BOTTLE.get(), "Bouteille de Gros Gueuleton");
        this.add(ModItemRegister.GROS_GUEULETON_GLASS.get(), "Verre de Gros Gueuleton");
        this.add(ModItemRegister.GROS_GUEULETON_MUG.get(), "Chope de Gros Gueuleton");
        this.add(GrosGueuletonBeerConsumeEffect.BOTTLE_TOOLTIP_KEY, "Une bière, c'est l'équivalent d'une tartine");
        this.add(GrosGueuletonBeerConsumeEffect.GLASS_TOOLTIP_KEY, "Tellement rassasié");
        this.add(GrosGueuletonBeerConsumeEffect.MUG_TOOLTIP_KEY, "Plus besoin de rien avec ça!");
        //  pain_dieux
        this.add(ModItemRegister.PAIN_DIEUX_BOTTLE.get(), "Bouteille de Pain Dieux");
        this.add(ModItemRegister.PAIN_DIEUX_GLASS.get(), "Verre de Pain Dieux");
        this.add(ModItemRegister.PAIN_DIEUX_MUG.get(), "Chope de Pain Dieux");
        this.add(PainDieuxConsumeEffect.BOTTLE_TOOLTIP_KEY, "un vrai travail de moine");
        this.add(PainDieuxConsumeEffect.GLASS_TOOLTIP_KEY, "Quand tu as besoin de voir Sa lumière");
        this.add(PainDieuxConsumeEffect.MUG_TOOLTIP_KEY, "Bon Dieux, ce goût!");
        //  real_dwarve
        this.add(ModItemRegister.REAL_DWARVE_BOTTLE.get(), "Bouteille de Real Dwarve");
        this.add(ModItemRegister.REAL_DWARVE_GLASS.get(), "Verre de Real Dwarve");
        this.add(ModItemRegister.REAL_DWARVE_MUG.get(), "Chope de Real Dwarve");
        this.add(RealDwarveConsumeEffect.BOTTLE_TOOLTIP_KEY, "La mini voix non incluse");
        this.add(RealDwarveConsumeEffect.GLASS_TOOLTIP_KEY, "Parfois, il faut foire les choses en plus petits pour avancer");
        this.add(RealDwarveConsumeEffect.MUG_TOOLTIP_KEY, "Karl aurrait approuvé");
        //  pied_de_geants
        this.add(ModItemRegister.PIED_DE_GEANTS_BOTTLE.get(), "Bouteille de Pied De Géants");
        this.add(ModItemRegister.PIED_DE_GEANTS_GLASS.get(), "Verre de Pied De Géants");
        this.add(ModItemRegister.PIED_DE_GEANTS_MUG.get(), "Chope de Pied De Géants");
        this.add(PiedDeGeantsConsumeEffect.BOTTLE_TOOLTIP_KEY, "Tu as un complexe?");
        this.add(PiedDeGeantsConsumeEffect.GLASS_TOOLTIP_KEY, "La force brute est parfois la solution");
        this.add(PiedDeGeantsConsumeEffect.MUG_TOOLTIP_KEY, "Le plus gros des gros");
        //  la_blanche_de_chez_nous
        this.add(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE.get(), "Bouteille de La Blanche De Chez Nous");
        this.add(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_GLASS.get(), "Verre de La Blanche De Chez Nous");
        this.add(ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_MUG.get(), "Chope de La Blanche De Chez Nous");
        this.add(LaBlancheDeChezNousConsumeEffect.BOTTLE_TOOLTIP_KEY, "Bien frais");
        this.add(LaBlancheDeChezNousConsumeEffect.GLASS_TOOLTIP_KEY, "Je ne sens plus rien");
        this.add(LaBlancheDeChezNousConsumeEffect.MUG_TOOLTIP_KEY, "Je ne fais qu'un avec la neige et la neige ne fait qu'un avec moi");
        // brewing_barrel
        // brewing_barrel
        this.add(ModBlockRegister.OAK_BREWING_BARREL.asItem(), "Tonneau de fermentation en chêne");
        this.add(ModBlockRegister.SPRUCE_BREWING_BARREL.asItem(), "Tonneau de fermentation en sapin");
        this.add(ModBlockRegister.BIRCH_BREWING_BARREL.asItem(), "Tonneau de fermentation en bouleau");
        this.add(ModBlockRegister.JUNGLE_BREWING_BARREL.asItem(), "Tonneau de fermentation en acajou");
        this.add(ModBlockRegister.ACACIA_BREWING_BARREL.asItem(), "Tonneau de fermentation en acacia");
        this.add(ModBlockRegister.DARK_OAK_BREWING_BARREL.asItem(), "Tonneau de fermentation en chêne noir");
        this.add(ModBlockRegister.MANGROVE_BREWING_BARREL.asItem(), "Tonneau de fermentation en palétuvier");
        this.add(ModBlockRegister.CHERRY_BREWING_BARREL.asItem(), "Tonneau de fermentation en cerisier");
        this.add(ModBlockRegister.PALE_OAK_BREWING_BARREL.asItem(), "Tonneau de fermentation en chêne pâle");
        this.add(ModBlockRegister.BAMBOO_BREWING_BARREL.asItem(), "Tonneau de fermentation en bambou");
        this.add(ModBlockRegister.CRIMSON_BREWING_BARREL.asItem(), "Tonneau de fermentation carmin");
        this.add(ModBlockRegister.WARPED_BREWING_BARREL.asItem(), "Tonneau de fermentation birscornue");
    }
}