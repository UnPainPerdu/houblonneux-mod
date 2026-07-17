package com.unpainperdu.houblonneux.level.world.attachments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

public class OriginalBlockPosAttach implements ValueIOSerializable
{
    private BlockPos originalBlockPos;

    public OriginalBlockPosAttach(BlockPos originalPos)
    {
        this.originalBlockPos = originalPos;
    }

    @Override
    public void serialize(ValueOutput output)
    {
        output.store("original_block_pos", BlockPos.CODEC, this.getOriginalBlockPos());
    }

    @Override
    public void deserialize(ValueInput input)
    {
        input.read("original_block_pos", BlockPos.CODEC).ifPresent(this::setOriginalBlockPos);
    }

    public void setOriginalBlockPos(BlockPos originalBlockPos)
    {
        this.originalBlockPos = originalBlockPos;
    }

    public BlockPos getOriginalBlockPos()
    {
        return originalBlockPos;
    }
}