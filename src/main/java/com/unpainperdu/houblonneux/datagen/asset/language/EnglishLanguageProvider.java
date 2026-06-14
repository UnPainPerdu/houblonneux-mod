package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.BeerType;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.EmeraldCallBeerConsumeEffect;
import com.unpainperdu.houblonneux.register.ModCreativeTabRegister;
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

        //creative tabs
        this.add(ModCreativeTabRegister.MAIN_TAB_TRANSLATION_KEY, "Houblonneux");
        //items
        this.add(ModItemRegister.HOP_FLOWER.get(), "Hop Flower");
        this.add(ModItemRegister.HOP_LUPULIN.get(), "Hop Lupulin");
        //beer
        //  empty
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE.get(), "Empty Polymorphic Bottle");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_GLASS.get(), "Empty Polymorphic Glass");
        this.add(ModItemRegister.EMPTY_POLYMORPHIC_MUG.get(), "Empty Polymorphic Mug");
        //  emerald_call
        this.add(ModItemRegister.EMERALD_CALL_BOTTLE.get(), "Emerald Call Bottle");
        this.add(ModItemRegister.EMERALD_CALL_GLASS.get(), "Emerald Call Glass");
        this.add(ModItemRegister.EMERALD_CALL_MUG.get(), "Emerald Call Mug");
        //      tool_tip
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.BOTTLE), "Green Bottle and Green Beer!");
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.GLASS), "I smell... Money");
        clp.beerToolTipTranslation(new EmeraldCallBeerConsumeEffect(BeerType.MUG), "Good Deals in sight");
    }
}