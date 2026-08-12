package com.LazyFlesh.variablehorizons.selectionUI;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

@EventBusSubscriber(side = Side.CLIENT)
public class VariantGuiMain extends GuiScreen {

    private int selectedIndex = -1;
    private final GuiScreen parent;
    private static final int OPENING_BUTTON_ID = 8192;
    private static final int SIDEBAR_WIDTH = 200;
    private static final int PADDING = 6;
    private static final List<VariantNames> fullVariants = VariantNames.allCompositionVariants;
    private static final List<VariantNames> subVariants = VariantNames.allSubVariants;
    private boolean showingFullVariants = true;
    private VariantList optionList;

    public VariantGuiMain(GuiScreen parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiSelectWorld gui) {
            if (event.gui.width / 2 + 248 > event.gui.width) return;
            event.buttonList.add(new GuiVariantsButton(6, event.gui.height - 52, 82, 20, "Variants", gui));
        }
    }

    private static class GuiVariantsButton extends GuiButton {

        private final GuiSelectWorld gui;

        public GuiVariantsButton(int x, int y, int widthIn, int heightIn, String buttonText, GuiSelectWorld gui) {
            super(OPENING_BUTTON_ID, x, y, widthIn, heightIn, buttonText);
            this.gui = gui;
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            if (!super.mousePressed(mc, mouseX, mouseY)) {
                return false;
            }

            mc.displayGuiScreen(new VariantGuiMain(gui));

            return true;
        }
    }

    private List<VariantNames> getActiveVariantList() {
        return showingFullVariants ? fullVariants : subVariants;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 27, 200, 20, "Done"));
        this.buttonList.add(new GuiButton(1, PADDING, this.height - 52, SIDEBAR_WIDTH - PADDING, 20, "Toggle"));
        this.buttonList.add(
            new GuiButton(
                2,
                PADDING,
                this.height - 27,
                SIDEBAR_WIDTH - PADDING,
                20,
                showingFullVariants ? "Show Sub-Variants" : "Show Full Variants"));
        this.optionList = new VariantList();

        updateBottomButtons();
    }

    private void updateBottomButtons() {
        int indexMin = showingFullVariants ? 1 : 0;
        this.buttonList.get(1).enabled = selectedIndex >= indexMin && selectedIndex < getActiveVariantList().size();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parent);
        } else if (button.id == 1) {
            VariantNames selectedVariant = getActiveVariantList().get(selectedIndex);
            boolean variantState = VariantNames.activeContains(selectedVariant.id);
            VariantLoader.toggleVariant(selectedVariant, !variantState);
        } else if (button.id == 2) {
            showingFullVariants = !showingFullVariants;
            selectedIndex = -1;
            button.displayString = showingFullVariants ? "Show Sub-Variants" : "Show Full Variants";
            updateBottomButtons();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.optionList.drawScreen(mouseX, mouseY, partialTicks);
        drawDetailsPanel();
        this.drawCenteredString(this.fontRendererObj, "Variants", this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawDetailsPanel() {
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 32;

        List<VariantNames> active = getActiveVariantList();
        if (selectedIndex < 0 || selectedIndex >= active.size()) {
            return;
        }

        String entry = active.get(selectedIndex).id;
        this.drawString(this.fontRendererObj, entry, panelX, panelY, 0xFFFFFF);

        int wrapWidth = this.width - panelX - PADDING;
        List<String> lines = this.fontRendererObj.listFormattedStringToWidth("blablabla", wrapWidth);
        int lineY = panelY + 20;
        for (String line : lines) {
            this.drawString(this.fontRendererObj, line, panelX, lineY, 0xCCCCCC);
            lineY += this.fontRendererObj.FONT_HEIGHT + 2;
        }
    }

    class VariantList extends GuiSlot {

        VariantList() {
            super(
                VariantGuiMain.this.mc,
                SIDEBAR_WIDTH,
                VariantGuiMain.this.height,
                32,
                VariantGuiMain.this.height - 60,
                20);
            this.setSlotXBoundsFromLeft(0);
        }

        @Override
        public int getListWidth() {
            return SIDEBAR_WIDTH - PADDING;
        }

        @Override
        protected int getScrollBarX() {
            return SIDEBAR_WIDTH - 6;
        }

        @Override
        protected int getSize() {
            return getActiveVariantList().size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
            selectedIndex = index;
            updateBottomButtons();
        }

        @Override
        protected boolean isSelected(int index) {
            return index == selectedIndex;
        }

        @Override
        protected void drawBackground() {
            VariantGuiMain.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int index, int x, int y, int slotHeight, Tessellator tessellator, int mouseX,
            int mouseY) {
            String selectedVariantID = getActiveVariantList().get(index).id;
            String label = StatCollector.translateToLocal("variants." + selectedVariantID + ".name");
            int xOffset = x + (getListWidth() - 4) / 2;
            int yOffset = y + (slotHeight - VariantGuiMain.this.fontRendererObj.FONT_HEIGHT) / 2;
            VariantGuiMain.this.drawCenteredString(
                VariantGuiMain.this.fontRendererObj,
                label,
                xOffset,
                yOffset,
                VariantNames.activeContains(selectedVariantID) ? 0x45f542 : 0xFFFFFF);
        }
    }
}
