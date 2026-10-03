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
    private UUID variablehorizonsLaser$owner;
    @Unique
    private long variablehorizonsLaser$ticksBetween;
    @Unique
    private long variablehorizonsLaser$capacity = 0;
    @Unique
    private int variablehorizonsLaser$cachedAmperes = -1;
    @Unique
    public final long variablehorizonsLaser$precisionMultiplier = LongMath.pow(10, 15);

    protected MixinMTEHatchDynamoTunnel_MakeWireless() {
        super(null, 0, 0, null, null);
    }

    @Unique
    private void variablehorizonsLaser$ensureInit() {
        if (Amperes <= 0 || variablehorizonsLaser$cachedAmperes == Amperes) return;
        variablehorizonsLaser$cachedAmperes = Amperes;

        BigInteger perOp = BigInteger.valueOf(Amperes * V[mTier])
            .multiply(BigInteger.valueOf(WirelessNetworkManager.ticks_between_energy_addition));

        double div = perOp.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? perOp.doubleValue() / Long.MAX_VALUE
            : 1d;

        variablehorizonsLaser$ticksBetween = div > 1
            ? (long) (WirelessNetworkManager.ticks_between_energy_addition / div)
            : WirelessNetworkManager.ticks_between_energy_addition;

        variablehorizonsLaser$capacity = div
            > 1 ? perOp.divide(BigInteger.valueOf((long) (div * variablehorizonsLaser$precisionMultiplier)))
                .multiply(BigInteger.valueOf(variablehorizonsLaser$precisionMultiplier))
                .longValue() : perOp.longValue();
    }

    @Inject(method = "onFirstTick", at = @At("TAIL"), remap = false)
    private void variablehorizonsLaser$onFirstTick(IGregTechTileEntity base, CallbackInfo ci) {
        if (!base.isServerSide()) return;
        variablehorizonsLaser$owner = base.getOwnerUuid();
        strongCheckOrAddUser(variablehorizonsLaser$owner);
    }

    @Inject(method = "onPostTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsLaser$onPostTick(IGregTechTileEntity base, long tick, CallbackInfo ci) {
        if (base.isServerSide() && variablehorizonsLaser$owner != null) {
            variablehorizonsLaser$ensureInit();
            if (tick % variablehorizonsLaser$ticksBetween == 0L) {
                long stored = base.getStoredEU();
                if (stored > 0 && addEUToGlobalEnergyMap(variablehorizonsLaser$owner, stored)) {
                    setEUVar(0);
                }
            }
        }
        ci.cancel(); // skips the laser's power transfer
    }

    @Inject(method = "maxEUStore", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsLaser$maxEUStore(CallbackInfoReturnable<Long> cir) {
        variablehorizonsLaser$ensureInit();
        if (variablehorizonsLaser$capacity > 0) {
            cir.setReturnValue(variablehorizonsLaser$capacity);
        }
    }

    @Inject(method = "getTexturesActive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsLaser$texturesActive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[0] });
    }

    @Inject(method = "getTexturesInactive", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsLaser$texturesInactive(ITexture base, CallbackInfoReturnable<ITexture[]> cir) {
        cir.setReturnValue(new ITexture[] { base, Textures.BlockIcons.OVERLAYS_ENERGY_ON_WIRELESS_LASER[0] });
    }

    @Inject(method = "getConnectionType", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizonsLaser$connectionType(CallbackInfoReturnable<ConnectionType> cir) {
        cir.setReturnValue(ConnectionType.WIRELESS);
    }

}
