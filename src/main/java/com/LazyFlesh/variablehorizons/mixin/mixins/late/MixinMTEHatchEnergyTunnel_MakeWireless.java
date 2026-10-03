package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import static gregtech.api.enums.GTValues.V;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.strongCheckOrAddUser;
import static gregtech.common.misc.WirelessNetworkManager.totalStorage;

import java.math.BigInteger;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.math.LongMath;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.misc.WirelessNetworkManager;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyMulti;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

@Mixin(MTEHatchEnergyTunnel.class)
public class MixinMTEHatchEnergyTunnel_MakeWireless extends MTEHatchEnergyMulti {

    @Unique
    private UUID variablehorizons$owner;
    @Unique
    private double variablehorizons$overflowDivisor;
    @Unique
    private long variablehorizons$ticksBetween;
    @Unique
    private long variablehorizons$euPerOp;
    @Unique
    private int variablehorizons$cachedAmperes = -1;
    @Unique
    public final long variablehorizons$precisionMultiplier = LongMath.pow(10, 15);

    protected MixinMTEHatchEnergyTunnel_MakeWireless() {
        super(null, 0, 0, null, null);
    }

    @Unique
    private void variablehorizons$ensureInit() {
        if (Amperes <= 0 || variablehorizons$cachedAmperes == Amperes) return;
        variablehorizons$cachedAmperes = Amperes;

        BigInteger perOp = BigInteger.valueOf(Amperes * V[mTier])
            .multiply(BigInteger.valueOf(WirelessNetworkManager.ticks_between_energy_addition));

        variablehorizons$overflowDivisor = perOp.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0
            ? perOp.doubleValue() / Long.MAX_VALUE
            : 1d;

        variablehorizons$ticksBetween = variablehorizons$overflowDivisor > 1
            ? (long) (WirelessNetworkManager.ticks_between_energy_addition / (variablehorizons$overflowDivisor * 2))
            : WirelessNetworkManager.ticks_between_energy_addition;

        variablehorizons$euPerOp = variablehorizons$overflowDivisor > 1
            ? perOp
                .divide(
                    BigInteger
                        .valueOf((long) (variablehorizons$overflowDivisor * variablehorizons$precisionMultiplier * 2)))
                .multiply(BigInteger.valueOf(variablehorizons$precisionMultiplier))
                .longValue()
            : perOp.longValue();
    }

    @Unique
    private void variablehorizons$tryFetchingEnergy() {
        variablehorizons$ensureInit();
        long current = getBaseMetaTileEntity().getStoredEU();
        long toTransfer = Math.min(maxEUStore() - current, variablehorizons$euPerOp);
        if (toTransfer <= 0) return;
        if (!addEUToGlobalEnergyMap(variablehorizons$owner, -toTransfer)) return;
        setEUVar(current + toTransfer);
    }

    @Inject(method = "onFirstTick", at = @At("TAIL"), remap = false)
    private void variablehorizons$onFirstTick(IGregTechTileEntity base, CallbackInfo ci) {
        if (!base.isServerSide()) return;
        variablehorizons$ensureInit();
        variablehorizons$owner = base.getOwnerUuid();
        strongCheckOrAddUser(variablehorizons$owner);
        variablehorizons$tryFetchingEnergy();
    }

    @Inject(method = "onPostTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$onPostTick(IGregTechTileEntity base, long tick, CallbackInfo ci) {
        if (base.isServerSide()) {
            variablehorizons$ensureInit();
            if (tick % variablehorizons$ticksBetween == 0L) variablehorizons$tryFetchingEnergy();
        }
        ci.cancel(); // skips the laser's own EU drain
    }

    @Inject(method = "maxEUStore", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$maxEUStore(CallbackInfoReturnable<Long> cir) {
        variablehorizons$ensureInit();
        cir.setReturnValue((long) (totalStorage(V[mTier]) / (2 * variablehorizons$overflowDivisor) * Amperes));
    }

    @Inject(method = "getMinimumStoredEU", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$minEU(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(Amperes * V[mTier]);
    }

    @Inject(method = "getTexturesActive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$texturesActive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[0] });
    }

    @Inject(method = "getTexturesInactive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$texturesInactive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[0] });
    }

    @Inject(method = "getConnectionType", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$connectionType(CallbackInfoReturnable<ConnectionType> cir) {
        cir.setReturnValue(ConnectionType.WIRELESS);
    }

}
