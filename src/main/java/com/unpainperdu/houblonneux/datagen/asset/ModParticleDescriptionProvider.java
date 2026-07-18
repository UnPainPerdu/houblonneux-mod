package com.unpainperdu.houblonneux.datagen.asset;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.ModParticleTypeRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider
{
    public ModParticleDescriptionProvider(PackOutput output)
    {
        super(output);
    }

    @Override
    protected void addDescriptions()
    {
        spriteSet(ModParticleTypeRegister.WORM_HOLE_PORTAL.get(),
                Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "worm_hole_portal"),
                6,
                false
        );
    }
}
