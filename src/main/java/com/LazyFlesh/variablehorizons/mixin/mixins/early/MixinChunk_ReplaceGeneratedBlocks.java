package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(Chunk.class)
public abstract class MixinChunk_ReplaceGeneratedBlocks {

    @Shadow
    public abstract boolean func_150807_a(int x, int y, int z, Block block, int meta);

    @Inject(method = "func_150807_a", at = @At("HEAD"), cancellable = true)
    private void variablehorizons$redirectBlock(int x, int y, int z, Block block, int metadata,
        CallbackInfoReturnable<Boolean> cir) {
        if (randomUtil.WorldGenFlag.isGenerating() && block != Blocks.air
            && block != randomUtil.REPLACEMENT_BLOCK
            && !block.hasTileEntity(metadata)) {

            boolean result = this.func_150807_a(x, y, z, randomUtil.REPLACEMENT_BLOCK, randomUtil.REPLACEMENT_META);
            cir.setReturnValue(result);
        }
    }
}
