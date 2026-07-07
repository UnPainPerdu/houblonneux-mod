package com.unpainperdu.houblonneux.datagen.data.trading;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ModDispenserTradeProvider
{
    //for custom dispenser model, must be register in ModStandaloneModelRegister and mapped in BeerDispenserBlockEntityRenderer
    public static final ResourceKey<DispenserTrade> EMERALD_CALL = createKey("emerald_call");

    public static void bootstrap(BootstrapContext<DispenserTrade> context)
    {
        registerTrade(context, Items.EMERALD, 5, ModItemRegister.EMERALD_CALL_BOTTLE, 1);
    }

    private static void registerTrade(BootstrapContext<DispenserTrade> context, ItemLike costItem, int costNumber, ItemLike resultItem, int resultNumber)
    {
        context.register(EMERALD_CALL, new DispenserTrade(new ItemStackTemplate(costItem.asItem(), costNumber), new ItemStackTemplate(resultItem.asItem(), resultNumber)));
    }

    public static ResourceKey<DispenserTrade> createKey(String name)
    {
        return ResourceKey.create(ModDataPackRegistriesRegister.DISPENSER_TRADE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}