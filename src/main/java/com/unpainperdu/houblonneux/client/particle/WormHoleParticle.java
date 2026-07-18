package com.unpainperdu.houblonneux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public class WormHoleParticle extends SingleQuadParticle
{
    private final SpriteSet spriteSet;

    public WormHoleParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet)
    {
        super(level, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.quadSize = 0.8F;
        this.lifetime = 20;
    }

    @Override
    public void tick()
    {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    protected Layer getLayer()
    {
        return Layer.OPAQUE;
    }

    public static class WormHolePortalProvider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public WormHolePortalProvider(SpriteSet spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random)
        {
            return new WormHoleParticle(level, x, y, z, this.spriteSet);
        }
    }
}