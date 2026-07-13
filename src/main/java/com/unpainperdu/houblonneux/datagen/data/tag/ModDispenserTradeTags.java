package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

public class ModDispenserTradeTags
{
    public static final TagKey<DispenserTrade> BEER_TRADE = create("beer_trade");

    public static TagKey<DispenserTrade> create(String name)
    {
        return TagKey.create(ModDataPackRegistriesRegister.DISPENSER_TRADE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}