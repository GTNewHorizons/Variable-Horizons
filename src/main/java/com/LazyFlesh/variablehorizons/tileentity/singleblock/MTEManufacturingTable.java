package com.LazyFlesh.variablehorizons.tileentity.singleblock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StatCollector;

import com.LazyFlesh.variablehorizons.recipes.VHRecipeMaps;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widgets.layout.Grid;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.recipe.BasicUIProperties;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.common.gui.modularui.singleblock.base.MTEBasicMachineBaseGui;
import gregtech.common.gui.modularui.util.MachineModularSlot;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import it.unimi.dsi.fastutil.chars.CharList;

public class MTEManufacturingTable extends MTEBasicMachine {

    public MTEManufacturingTable(int aID, String aName, String aNameRegional, int aTier) {
        super(
            aID,
            aName,
            aNameRegional,
            aTier,
            1,
            "Basically a big crafting table",
            25,
            1,
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER_ACTIVE_GLOW)
                    .glow()
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER_GLOW)
                    .glow()
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_LOCKER)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_LOCKER)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.Casing_Adv_Workbench_Crafting_Overlay)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.Casing_Adv_Workbench_Crafting_Overlay)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER_ACTIVE_GLOW)
                    .glow()
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER_GLOW)
                    .glow()
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_TOP),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()),
            TextureFactory.of(
                TextureFactory.of(Textures.BlockIcons.PRIMITIVE_WOODEN_CASING_SIDE),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                    .build()));
    }

    public MTEManufacturingTable(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 1, aDescription, aTextures, 25, 1);
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEManufacturingTable(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return VHRecipeMaps.manufacturingTableRecipes;
    }

    @Override
    public int checkRecipe() {
        return super.checkRecipe(true);
    }

    @Override
    public String[] getDescription() {
        List<String> description = new ArrayList<>();
        description.add(StatCollector.translateToLocal("variants.singleblocks.desc.manufacturingtable.0"));
        description.add(StatCollector.translateToLocal("variants.singleblocks.desc.manufacturingtable.1"));
        return description.toArray(new String[0]);
    }

    @Override
    public boolean isElectric() {
        return false;
    }

    @Override
    public long maxEUStore() {
        return 0;
    }

    @Override
    public long getMinimumStoredEU() {
        return 0;
    }

    @Override
    public long maxAmperesIn() {
        return 0;
    }

    @Override
    public long maxAmperesOut() {
        return 0;
    }

    @Override
    protected BasicUIProperties getUIProperties() {
        return super.getUIProperties().toBuilder()
            .progressBarTextureMUI2(GTGuiTextures.PROGRESSBAR_ASSEMBLE)
            .build();
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEManufacturingTableGui(this, this.getUIProperties()).useGregTechLogo(true)
            .build(data, syncManager, uiSettings);
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    public static class MTEManufacturingTableGui extends MTEBasicMachineBaseGui<MTEManufacturingTable> {

        public MTEManufacturingTableGui(MTEManufacturingTable machine, BasicUIProperties properties) {
            super(machine, properties);
        }

        @Override
        protected List<CharList> mapInSlotsToMatrix() {
            List<CharList> rows = new ArrayList<>();
            for (int r = 0; r < 5; r++) {
                rows.add(CharList.of("ccccc".toCharArray()));
            }
            return rows;
        }

        @Override
        protected ParentWidget<?> createItemInputSlots(ModularPanel panel, PanelSyncManager syncManager) {
            return new ParentWidget<>().size(90, 90)
                .child(
                    new Grid().coverChildren()
                        .gridOfElements(
                            this.mapInSlotsToMatrix(),
                            (x, y, i,
                                key) -> key == 'c'
                                    ? new ItemSlot()
                                        .backgroundOverlay(this.slotOverlayFunction.apply(i, false, false, false))
                                        .slot(
                                            new MachineModularSlot(
                                                this.machine.inventoryHandler,
                                                this.machine.getInputSlot() + i,
                                                this.baseMetaTileEntity).slotGroup("item_inv"))
                                    : null)
                        .verticalCenter()
                        .rightRel(0.0F));
        }

        @Override
        protected int getBasePanelHeight() {
            return super.getBasePanelHeight() + 18 * 2;
        }

        @Override
        protected int getBasePanelWidth() {
            return super.getBasePanelWidth() + 18;
        }
    }
}
