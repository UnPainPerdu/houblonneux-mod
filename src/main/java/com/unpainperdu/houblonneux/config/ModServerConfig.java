package com.unpainperdu.houblonneux.config;

import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModServerConfig
{
    public static final ModServerConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static final String BREWING_RECIPE_CATEGORY = "brewing_recipes";

    public static final String BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR_NAME = "brewingRecipeTimeMultiplicationFactor";
    public static final String BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR_TRANSLATION_KEY = createTranslationKey(BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR_NAME);
    public final ModConfigSpec.IntValue BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR;

    public static final String BREWING_RECIPE_TIME_DIVISION_FACTOR_NAME = "brewingRecipeTimeDivisionFactor";
    public static final String BREWING_RECIPE_TIME_DIVISION_FACTOR_TRANSLATION_KEY = createTranslationKey(BREWING_RECIPE_TIME_DIVISION_FACTOR_NAME);
    public final ModConfigSpec.IntValue BREWING_RECIPE_TIME_DIVISION_FACTOR;

    public static final String BEER_EFFECTS = "beer_effects";

    public static final String PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME = "painDieuxCanDestroyBlocks";
    public static final String PAIN_DIEUX_CAN_DESTROY_BLOCKS_TRANSLATION_KEY = createTranslationKey(PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME);
    public final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_DESTROY_BLOCKS;

    public static final String PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME = "painDieuxCanHurtOtherPlayer";
    public static final String PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_TRANSLATION_KEY = createTranslationKey(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME);
    public final ModConfigSpec.BooleanValue PAIN_DIEUX_CAN_HURT_OTHER_PLAYER;

    private ModServerConfig(ModConfigSpec.Builder builder)
    {
        //------------------------------------------
        builder.push(BREWING_RECIPE_CATEGORY);
        BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR = builder.comment("Multiplication factor for brewing recipes")
                .comment("You can combine this config value and the division one to create a fraction (multiplication factor / division factor)")
                .translation(BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR_TRANSLATION_KEY)
                .defineInRange(BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR_NAME, 1 ,1, Integer.MAX_VALUE);
        BREWING_RECIPE_TIME_DIVISION_FACTOR = builder.comment("Division factor for brewing recipes")
                .comment("You can combine this config value and the multiplication one to a create fraction (multiplication factor / division factor)")
                .translation(BREWING_RECIPE_TIME_DIVISION_FACTOR_TRANSLATION_KEY)
                .defineInRange(BREWING_RECIPE_TIME_DIVISION_FACTOR_NAME, 1 ,1, Integer.MAX_VALUE);
        builder.pop();
        //------------------------------------------
        builder.push(BEER_EFFECTS);
        PAIN_DIEUX_CAN_DESTROY_BLOCKS = builder.comment("If Pain Dieux mob effect explosion can destroy blocks")
                .translation(PAIN_DIEUX_CAN_DESTROY_BLOCKS_TRANSLATION_KEY)
                .define(PAIN_DIEUX_CAN_DESTROY_BLOCKS_NAME, false);

        PAIN_DIEUX_CAN_HURT_OTHER_PLAYER = builder.comment("If Pain Dieux mob effect explosion can hurt other player")
                .translation(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_TRANSLATION_KEY)
                .define(PAIN_DIEUX_CAN_HURT_OTHER_PLAYER_NAME, false);
        builder.pop();
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
