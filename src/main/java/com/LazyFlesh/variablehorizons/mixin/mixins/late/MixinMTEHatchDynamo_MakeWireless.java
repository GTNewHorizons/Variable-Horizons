package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import static gregtech.api.enums.GTValues.V;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.strongCheckOrAddUser;

import java.math.BigInteger;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.math.LongMath;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchDynamo;
import gregtech.common.misc.WirelessNetworkManager;

@Mixin(MTEHatchDynamo.class)
public abstract class MixinMTEHatchDynamo_MakeWireless extends MTEHatch {

    @Unique
    private UUID variablehorizons$owner;
    @Unique
    private long variablehorizons$ticksBetween;
    @Unique
    private long variablehorizons$capacity = 0;
    @Unique
    private long variablehorizons$cachedAmperes = -1;
    @Unique
    public final long variablehorizons$precisionMultiplier = LongMath.pow(10, 15);

    protected MixinMTEHatchDynamo_MakeWireless() {
        super(null, 0, 0, null, null);
    }

    @Unique
    private void variablehorizons$ensureInit() {
        long amps = maxAmperesOut();
        if (amps <= 0 || variablehorizons$cachedAmperes == amps) return;
        variablehorizons$cachedAmperes = amps;

        BigInteger perOp = BigInteger.valueOf(amps * V[mTier])
            .multiply(BigInteger.valueOf(WirelessNetworkManager.ticks_between_energy_addition));

        double div = perOp.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? perOp.doubleValue() / Long.MAX_VALUE
            : 1d;

        variablehorizons$ticksBetween = div > 1 ? (long) (WirelessNetworkManager.ticks_between_energy_addition / div)
            : WirelessNetworkManager.ticks_between_energy_addition;

        variablehorizons$capacity = div
            > 1 ? perOp.divide(BigInteger.valueOf((long) (div * variablehorizons$precisionMultiplier)))
                .multiply(BigInteger.valueOf(variablehorizons$precisionMultiplier))
                .longValue() : perOp.longValue();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity baseMetaTileEntity) {
        super.onFirstTick(baseMetaTileEntity);
        if (!baseMetaTileEntity.isServerSide()) return;
        variablehorizons$owner = baseMetaTileEntity.getOwnerUuid();
        strongCheckOrAddUser(variablehorizons$owner);
    }

    @Override
    public void onPostTick(IGregTechTileEntity baseMetaTileEntity, long tick) {
        super.onPostTick(baseMetaTileEntity, tick);
        if (baseMetaTileEntity.isServerSide() && variablehorizons$owner != null) {
            variablehorizons$ensureInit();
            if (tick % variablehorizons$ticksBetween == 0L) {
                long stored = baseMetaTileEntity.getStoredEU();
                if (stored > 0 && addEUToGlobalEnergyMap(variablehorizons$owner, stored)) {
                    setEUVar(0);
                }
            }
        }
    }

    @Inject(method = "getTexturesActive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$texturesActive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS[0] });
    }

    @Inject(method = "getTexturesInactive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$texturesInactive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS[0] });
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.WIRELESS;
    }

    @Inject(method = "isEnetOutput", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$isEnetOutput(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "maxEUStore", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$maxEUStore(CallbackInfoReturnable<Long> cir) {
        variablehorizons$ensureInit();
        if (variablehorizons$capacity > 0) {
            cir.setReturnValue(variablehorizons$capacity);
        }
    }

}
