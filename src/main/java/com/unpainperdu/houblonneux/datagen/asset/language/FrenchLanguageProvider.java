package com.unpainperdu.houblonneux.datagen.asset.language;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.ModCreativeTabRegister;
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
        //creative tabs
        this.add(ModCreativeTabRegister.MAIN_TAB_TRANSLATION_KEY, "Houblonneux");
        //items
        this.add(ModItemRegister.HOP_FLOWER.get(), "Fleur de Houblon");
        this.add(ModItemRegister.HOP_LUPULIN.get(), "Lupuline de Houblon");
    }
}