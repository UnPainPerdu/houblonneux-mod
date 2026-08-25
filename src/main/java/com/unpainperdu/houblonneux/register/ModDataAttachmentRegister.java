package com.unpainperdu.houblonneux.register;

import com.mojang.serialization.Codec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.attachments.OriginalBlockPosAttach;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDataAttachmentRegister
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Houblonneux.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<OriginalBlockPosAttach>> ORIGINAL_BLOCK_POS = ATTACHMENT_TYPE.register(
            "original_block_pos", () -> AttachmentType.serializable(() -> new OriginalBlockPosAttach(new BlockPos(0, 0, 0))).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> ORIGINAL_SIZE_FACTOR = ATTACHMENT_TYPE.register(
            "original_size_factor", () -> AttachmentType.builder(() -> 1.0D).serialize(Codec.DOUBLE.fieldOf("original_size_factor")).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> ORIGINAL_SPEED_FACTOR = ATTACHMENT_TYPE.register(
            "original_speed_factor", () -> AttachmentType.builder(() -> 0.1D).serialize(Codec.DOUBLE.fieldOf("original_speed_factor")).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> ORIGINAL_JUMP_FACTOR = ATTACHMENT_TYPE.register(
            "original_jump_factor", () -> AttachmentType.builder(() -> 0.45D).serialize(Codec.DOUBLE.fieldOf("original_jump_factor")).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> ORIGINAL_REACH_FACTOR = ATTACHMENT_TYPE.register(
            "original_reach_factor", () -> AttachmentType.builder(() -> 4.5D).serialize(Codec.DOUBLE.fieldOf("original_reach_factor")).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> ORIGINAL_SAFE_FALL_DISTANCE_FACTOR = ATTACHMENT_TYPE.register(
            "original_safe_fall_distance_factor", () -> AttachmentType.builder(() -> 3D).serialize(Codec.DOUBLE.fieldOf("original_safe_fall_distance_factor")).build()
    );

    public static void register(IEventBus event)
    {
        ATTACHMENT_TYPE.register(event);
    }
}