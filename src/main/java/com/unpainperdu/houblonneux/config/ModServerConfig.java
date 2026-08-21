package com.unpainperdu.houblonneux.config;

import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModServerConfig
{
    public static final ModServerConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static final String PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME = "painDieuxCanDestroyBlocks";
    public static final String PAIN_DIEUX_CAN_DESTROY_BLOCKS_TRANSLATION_KEY = createTranslationKey(PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME);
    public final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_DESTROY_BLOCKS;

    public static final String PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME = "painDieuxCanHurtOtherPlayer";
    public static final String PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_TRANSLATION_KEY = createTranslationKey(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME);
    public final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_HURT_OTHER_PLAYER;

    private ModServerConfig(ModConfigSpec.Builder builder)
    {
        PAIN_DIEUX_CAN_DESTROY_BLOCKS = builder.comment("If Pain Dieux mob effect explosion can destroy blocks")
                .translation(PAIN_DIEUX_CAN_DESTROY_BLOCKS_TRANSLATION_KEY)
                .define(PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME, false);

        PAIN_DIEUX_CAN_HURT_OTHER_PLAYER = builder.comment("If Pain Dieux mob effect explosion can hurt other player")
                .translation(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_TRANSLATION_KEY)
                .define(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME, false);
    }

    private static String createTranslationKey(String baseName)
    {
        return Houblonneux.MOD_ID + ".configuration." + baseName;
    }

    static
    {
        Pair<ModServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ModServerConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
