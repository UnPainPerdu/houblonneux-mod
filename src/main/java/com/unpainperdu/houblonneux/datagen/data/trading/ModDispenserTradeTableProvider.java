package com.unpainperdu.houblonneux.datagen.data.trading;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.data.tag.ModDispenserTradeTags;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTable;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class ModDispenserTradeTableProvider
{
    public static final ResourceKey<DispenserTradeTable> VILLAGE_DISPENSER = createKey("village_dispenser");

    public static void bootstrap(BootstrapContext<DispenserTradeTable> context)
    {
        context.register(
                VILLAGE_DISPENSER,
                DispenserTradeTable.getBuilder()
                        .addDispenserTradeTagEntry(ModDispenserTradeTags.BEER_TRADE)
                        .build()
        );
    }

    public static ResourceKey<DispenserTradeTable> createKey(String name)
    {
        return ResourceKey.create(ModDataPackRegistriesRegister.DISPENSER_TRADE_TABLE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}