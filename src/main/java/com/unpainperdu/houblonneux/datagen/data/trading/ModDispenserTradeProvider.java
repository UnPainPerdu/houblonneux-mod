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
    public static final ResourceKey<DispenserTrade> WORM_HOLE = createKey("worm_hole");
    public static final ResourceKey<DispenserTrade> GROS_GUEULETON = createKey("gros_gueuleton");
    public static final ResourceKey<DispenserTrade> PAIN_DIEUX = createKey("pain_dieux");
    public static final ResourceKey<DispenserTrade> REAL_DWARVE = createKey("real_dwarve");
    public static final ResourceKey<DispenserTrade> PIED_DE_GEANTS = createKey("pied_de_geants");
    public static final ResourceKey<DispenserTrade> LA_BLANCHE_DE_CHEZ_NOUS = createKey("la_blanche_de_chez_nous");

    public static void bootstrap(BootstrapContext<DispenserTrade> context)
    {
        registerTrade(context, EMERALD_CALL, Items.EMERALD, 5, ModItemRegister.EMERALD_CALL_BOTTLE, 1);
        registerTrade(context, WORM_HOLE, Items.EMERALD, 8, ModItemRegister.WORM_HOLE_BOTTLE, 1);
        registerTrade(context, GROS_GUEULETON, Items.EMERALD, 20, ModItemRegister.GROS_GUEULETON_BOTTLE, 1);
        registerTrade(context, PAIN_DIEUX, Items.EMERALD, 12, ModItemRegister.PAIN_DIEUX_BOTTLE, 1);
        registerTrade(context, REAL_DWARVE, Items.EMERALD, 9, ModItemRegister.REAL_DWARVE_BOTTLE, 1);
        registerTrade(context, PIED_DE_GEANTS, Items.EMERALD, 22, ModItemRegister.PIED_DE_GEANTS_BOTTLE, 1);
        registerTrade(context, LA_BLANCHE_DE_CHEZ_NOUS, Items.EMERALD, 15, ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE, 1);
    }

    private static void registerTrade(BootstrapContext<DispenserTrade> context, ResourceKey<DispenserTrade> resourceKey, ItemLike costItem, int costNumber, ItemLike resultItem, int resultNumber)
    {
        context.register(resourceKey, new DispenserTrade(new ItemStackTemplate(costItem.asItem(), costNumber), new ItemStackTemplate(resultItem.asItem(), resultNumber)));
    }

    public static ResourceKey<DispenserTrade> createKey(String name)
    {
        return ResourceKey.create(ModDataPackRegistriesRegister.DISPENSER_TRADE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}