package com.unpainperdu.houblonneux.datagen.asset;

import com.unpainperdu.houblonneux.Houblonneux;
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
        add(ModSoundRegister.BURP, SoundDefinition.definition()
                .with(
                        sound("houblonneux:burp_01", SoundDefinition.SoundType.SOUND)
                                .volume(0.8f)
                                .pitch(1.2f)
                                .attenuationDistance(8),
                        sound("houblonneux:burp_02", SoundDefinition.SoundType.SOUND)
                                .volume(0.8f)
                                .pitch(1.2f)
                                .attenuationDistance(8),
                        sound("houblonneux:burp_03", SoundDefinition.SoundType.SOUND)
                                .volume(0.8f)
                                .pitch(1.2f)
                                .attenuationDistance(8)
                )
                .subtitle(ModSoundRegister.BURP_SUBTITLE)
                .replace(true)
        );
    }
}