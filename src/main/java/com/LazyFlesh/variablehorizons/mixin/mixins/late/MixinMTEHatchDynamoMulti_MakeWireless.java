package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import static gregtech.api.enums.GTValues.V;

import java.math.BigInteger;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.math.LongMath;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.implementations.MTEHatchDynamo;
import gregtech.common.misc.WirelessNetworkManager;
import tectech.thing.metaTileEntity.hatch.MTEHatchDynamoMulti;

@Mixin(MTEHatchDynamoMulti.class)
public class MixinMTEHatchDynamoMulti_MakeWireless extends MTEHatchDynamo {

    @Shadow
    public int Amperes;

    @Unique
    private long variablehorizonsMultiamp$capacity = 0;
    @Unique
    private long variablehorizonsMultiamp$cachedAmperes = -1;
    @Unique
    public final long variablehorizonsMultiamp$precisionMultiplier = LongMath.pow(10, 15);

    protected MixinMTEHatchDynamoMulti_MakeWireless() {
        super(0, null, null, 0, null);
    }

    @Unique
    private void variablehorizonsMultiamp$ensureInit() {
        long amps = maxAmperesOut();
        if (amps <= 0 || variablehorizonsMultiamp$cachedAmperes == amps) return;
        variablehorizonsMultiamp$cachedAmperes = amps;

        BigInteger perOp = BigInteger.valueOf(amps * V[mTier])
            .multiply(BigInteger.valueOf(WirelessNetworkManager.ticks_between_energy_addition));

        double div = perOp.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? perOp.doubleValue() / Long.MAX_VALUE
            : 1d;

        variablehorizonsMultiamp$capacity = div
            > 1 ? perOp.divide(BigInteger.valueOf((long) (div * variablehorizonsMultiamp$precisionMultiplier)))
                .multiply(BigInteger.valueOf(variablehorizonsMultiamp$precisionMultiplier))
                .longValue() : perOp.longValue();
    }

    @Inject(method = "maxEUStore", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsMultiamp$maxEUStore(CallbackInfoReturnable<Long> cir) {
        variablehorizonsMultiamp$ensureInit();
        if (variablehorizonsMultiamp$capacity > 0) {
            cir.setReturnValue(variablehorizonsMultiamp$capacity);
        }
    }

    @Inject(method = "getTexturesActive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsMultiamp$texturesActive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        if (Amperes > 64) {
            cir.setReturnValue(
                new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[mTier + 1] });
        } else if (Amperes > 16) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_64A[mTier + 1] });
        } else if (Amperes > 4) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_16A[mTier + 1] });
        } else if (Amperes > 2) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_4A[mTier + 1] });
        } else {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS[mTier + 1] });
        }
    }

    @Inject(method = "getTexturesInactive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsMultiamp$texturesInactive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        if (Amperes > 64) {
            cir.setReturnValue(
                new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[mTier + 1] });
        } else if (Amperes > 16) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_64A[mTier + 1] });
        } else if (Amperes > 4) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_16A[mTier + 1] });
        } else if (Amperes > 2) {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_4A[mTier + 1] });
        } else {
            cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS[mTier + 1] });
        }
    }
}
