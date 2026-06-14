package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.AbstractBeerConsumeEffect;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CommonLanguageHelper
{
    private final LanguageProvider languageProvider;

    public CommonLanguageHelper(LanguageProvider languageProvider)
    {
        this.languageProvider = languageProvider;
    }

    public void beerToolTipTranslation(AbstractBeerConsumeEffect beerConsumeEffect, String translation)
    {
        this.languageProvider.add(beerConsumeEffect.getTranslationKey(), translation);
    }
}