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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.math.LongMath;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.misc.WirelessNetworkManager;
import tectech.thing.metaTileEntity.hatch.MTEHatchDynamoMulti;
import tectech.thing.metaTileEntity.hatch.MTEHatchDynamoTunnel;

@Mixin(MTEHatchDynamoTunnel.class)
public class MixinMTEHatchDynamoTunnel_MakeWireless extends MTEHatchDynamoMulti {

    @Unique
    private UUID variablehorizons$owner;
    @Unique
    private long variablehorizons$ticksBetween;
    @Unique
    private long variablehorizons$capacity = 0;
    @Unique
    private int variablehorizons$cachedAmperes = -1;
    @Unique
    public final long variablehorizons$precisionMultiplier = LongMath.pow(10, 15);

    protected MixinMTEHatchDynamoTunnel_MakeWireless() {
        super(null, 0, 0, null, null);
    }

    @Unique
    private void variablehorizons$ensureInit() {
        if (Amperes <= 0 || variablehorizons$cachedAmperes == Amperes) return;
        variablehorizons$cachedAmperes = Amperes;

        BigInteger perOp = BigInteger.valueOf(Amperes * V[mTier])
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

    @Inject(method = "onFirstTick", at = @At("TAIL"), remap = false)
    private void variablehorizons$onFirstTick(IGregTechTileEntity base, CallbackInfo ci) {
        if (!base.isServerSide()) return;
        variablehorizons$owner = base.getOwnerUuid();
        strongCheckOrAddUser(variablehorizons$owner);
    }

    @Inject(method = "onPostTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$onPostTick(IGregTechTileEntity base, long tick, CallbackInfo ci) {
        if (base.isServerSide() && variablehorizons$owner != null) {
            variablehorizons$ensureInit();
            if (tick % variablehorizons$ticksBetween == 0L) {
                long stored = base.getStoredEU();
                if (stored > 0 && addEUToGlobalEnergyMap(variablehorizons$owner, stored)) {
                    setEUVar(0);
                }
            }
        }
        ci.cancel(); // skips the laser's power transfer
    }

    @Inject(method = "maxEUStore", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$maxEUStore(CallbackInfoReturnable<Long> cir) {
        variablehorizons$ensureInit();
        if (variablehorizons$capacity > 0) {
            cir.setReturnValue(variablehorizons$capacity);
        }
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
