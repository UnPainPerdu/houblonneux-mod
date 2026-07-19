package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.data.trading.ModDispenserTradeProvider;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;

import java.util.concurrent.CompletableFuture;

public class ModDispenserTradeTagProvider extends KeyTagProvider<DispenserTrade>
{
    public ModDispenserTradeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, ModDataPackRegistriesRegister.DISPENSER_TRADE, lookupProvider, Houblonneux.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        this.tag(ModDispenserTradeTags.BEER_TRADE)
                .add(ModDispenserTradeProvider.EMERALD_CALL)
                .add(ModDispenserTradeProvider.WORM_HOLE)
                .add(ModDispenserTradeProvider.GROS_GUEULETON)
        ;
    }
}