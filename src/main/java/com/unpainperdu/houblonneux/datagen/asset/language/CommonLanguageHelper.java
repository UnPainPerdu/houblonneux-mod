package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CommonLanguageHelper
{
    private final LanguageProvider languageProvider;

    public CommonLanguageHelper(LanguageProvider languageProvider)
    {
        this.languageProvider = languageProvider;
    }

    public void addConfigCategory(String name, String translation)
    {
        languageProvider.add(Houblonneux.MOD_ID + ".configuration." + name, translation);
    }
}