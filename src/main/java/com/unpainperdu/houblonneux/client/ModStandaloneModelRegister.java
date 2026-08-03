package com.unpainperdu.houblonneux.client;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

@EventBusSubscriber(modid = Houblonneux.MOD_ID, value = {Dist.CLIENT})
public class ModStandaloneModelRegister
{
    //TODO find a way to not have to register model, when done -> get model "automatically" by using trade resource key (change BE to hold key and not the trade itself)
    public static final StandaloneModelKey<BlockStateModelPart> DEFAULT_BEER_DISPENSER_MODEL = createKey("default_beer_dispenser");
    public static final StandaloneModelKey<BlockStateModelPart> EMERALD_CALL_BEER_DISPENSER_MODEL = createKey("emerald_call_beer_dispenser");
    public static final StandaloneModelKey<BlockStateModelPart> WORM_HOLE_BEER_DISPENSER_MODEL = createKey("worm_hole_beer_dispenser");
    public static final StandaloneModelKey<BlockStateModelPart> GROS_GUEULETON_BEER_DISPENSER_MODEL = createKey("gros_gueuleton_beer_dispenser");

    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterStandalone event)
    {
        event.register(
                DEFAULT_BEER_DISPENSER_MODEL,
                SimpleUnbakedStandaloneModel.simpleModelWrapper(
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "block/entity/default_beer_dispenser")
                )
        );
        event.register(
                EMERALD_CALL_BEER_DISPENSER_MODEL,
                SimpleUnbakedStandaloneModel.simpleModelWrapper(
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "block/entity/emerald_call_beer_dispenser")
                )
        );
        event.register(
                WORM_HOLE_BEER_DISPENSER_MODEL,
                SimpleUnbakedStandaloneModel.simpleModelWrapper(
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "block/entity/worm_hole_beer_dispenser")
                )
        );
        event.register(
                GROS_GUEULETON_BEER_DISPENSER_MODEL,
                SimpleUnbakedStandaloneModel.simpleModelWrapper(
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "block/entity/gros_gueuleton_beer_dispenser")
                )
        );
    }

    private static StandaloneModelKey<BlockStateModelPart> createKey(String id)
    {
        return new StandaloneModelKey<>(
                () -> Houblonneux.MOD_ID + ":" + id
        );
    }
}
