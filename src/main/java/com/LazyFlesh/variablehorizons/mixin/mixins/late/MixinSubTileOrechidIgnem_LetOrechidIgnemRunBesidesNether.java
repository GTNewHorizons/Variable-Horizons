package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import vazkii.botania.common.block.subtile.functional.SubTileOrechidIgnem;

@Mixin(SubTileOrechidIgnem.class)
public class MixinSubTileOrechidIgnem_LetOrechidIgnemRunBesidesNether {

    @Inject(method = "canOperate", at = @At("HEAD"), cancellable = true, remap = false)
    private void onCanOperate(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
