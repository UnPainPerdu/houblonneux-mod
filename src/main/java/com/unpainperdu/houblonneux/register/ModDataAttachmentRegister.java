package com.unpainperdu.houblonneux.register;

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

    public static void register(IEventBus event)
    {
        ATTACHMENT_TYPE.register(event);
    }
}