package com.unpainperdu.houblonneux.datagen.asset;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.AbstractBeerConsumeEffect;
import com.unpainperdu.houblonneux.register.ModSoundRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider
{
    public ModSoundDefinitionsProvider(PackOutput output)
    {
        super(output, Houblonneux.MOD_ID);
    }

    @Override
    public void registerSounds()
    {
        add(ModSoundRegister.BEER_DRINK, SoundDefinition.definition()
                .with(
                        sound("houblonneux:beer_drink", SoundDefinition.SoundType.SOUND)
                                .volume(0.8f)
                                .pitch(1.2f)
                                .attenuationDistance(8)
                )
                .subtitle(AbstractBeerConsumeEffect.getBeerDrinkSubtitle())
                .replace(true)
        );

        add(ModSoundRegister.BEER_DRINK_BURP, SoundDefinition.definition()
                .with(
                        sound("houblonneux:beer_drink_burp", SoundDefinition.SoundType.SOUND)
                                .volume(0.8f)
                                .pitch(1.2f)
                                .attenuationDistance(8)
                )
                .subtitle(AbstractBeerConsumeEffect.getBeerDrinkBurpSubtitle())
                .replace(true)
        );
    }
}