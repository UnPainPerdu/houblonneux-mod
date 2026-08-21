package com.unpainperdu.houblonneux;

import com.mojang.logging.LogUtils;
import com.unpainperdu.houblonneux.datagen.DataGatherer;
import com.unpainperdu.houblonneux.register.RegisterHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

@Mod(Houblonneux.MOD_ID)
public class Houblonneux
{
    public static final String MOD_ID = "houblonneux";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Houblonneux(IEventBus modEventBus, ModContainer modContainer)
    {
        RegisterHandler.register(modEventBus);
        modEventBus.addListener(DataGatherer::gatherData);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.CONFIG);
    }

    public static class Config
    {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        public static final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_DESTROY_BLOCKS = BUILDER
                .comment("If Pain Dieux mob effect explosion can destroy blocks")
                .define("painDieuxCanDestroyBlocks", false);

        public static final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_HURT_OTHER_PLAYER = BUILDER
                .comment("If Pain Dieux mob effect explosion can hurt other player")
                .define("painDieuxCanHurtOtherPlayer", false);

        static final ModConfigSpec CONFIG = BUILDER.build();
    }
}