package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.data.trading.ModDispenserTradeProvider;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModDispenserTradeTagProvider extends TagsProvider<DispenserTrade>
{

    public ModDispenserTradeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, ModDataPackRegistriesRegister.DISPENSER_TRADE, lookupProvider, Houblonneux.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        this.getOrCreateRawBuilder(ModDispenserTradeTags.BEER_TRADE)
                .addElement(ModDispenserTradeProvider.EMERALD_CALL.identifier());
    }
}