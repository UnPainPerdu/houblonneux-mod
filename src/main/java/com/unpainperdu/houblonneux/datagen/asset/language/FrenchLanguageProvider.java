package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.BeerType;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.EmeraldCallBeerConsumeEffect;
import com.unpainperdu.houblonneux.register.ModCreativeTabRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
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
        //menu
        this.add(BeerDispenserBlockEntity.TITLE_KEY, "Stockage de disque");
        //creative tabs
        this.add(ModCreativeTabRegister.MAIN_TAB_TRANSLATION_KEY, "Houblonneux");
        // block
        this.add(ModBlockRegister.BEER_DISPENSER.get(), "Distributeur de bière");
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
        //      tool_tip
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.BOTTLE), "Bouteille et bière verte!");
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.GLASS), "Ca sent... le commerce.");
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.MUG), "De bonnes affaires en perspectives.");
    }
}