package com.LazyFlesh.variablehorizons.selectionUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

@EventBusSubscriber(side = Side.CLIENT)
public class VariantGuiMain extends GuiScreen {

    private int selectedIndex = -1;
    private final GuiScreen parent;
    private static final int OPENING_BUTTON_ID = 8192;
    private static final int SIDEBAR_WIDTH = 150;
    private static final int PADDING = 6;
    private static final int ICON_SIZE = 32;
    private static final int ICON_TO_DESC_GAP = 40;
    private static final int DESC_TO_FIELD_GAP = 8;

    private static final List<VariantNames> fullVariants = VariantNames.allCompositionVariants;
    private static final List<VariantNames> subVariants = VariantNames.allSubVariants;
    private static final ResourceLocation DEFAULT_ICON = new ResourceLocation(
        "variablehorizons",
        "textures/gui/variants/ohno.png");
    private static final String DIMENSION_LOCKED = VariantNames.DIMLOCKED.id;
    private boolean showingFullVariants = true;
    private VariantList optionList;
    private GuiTextField searchField;
    private GuiTextField dimIdField;
    private final List<VariantNames> filteredVariants = new ArrayList<>();
    private final Map<String, ResourceLocation> iconCache = new HashMap<>();

    public VariantGuiMain(GuiScreen parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiSelectWorld gui) {
            if (event.gui.width / 2 + 248 > event.gui.width) return;
            event.buttonList.add(
                new GuiVariantsButton(
                    6,
                    event.gui.height - 52,
                    82,
                    20,
                    StatCollector.translateToLocal("variantgui.header"),
                    gui));
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

    private void refreshFilteredVariants() {
        String query = searchField.getText()
            .trim()
            .toLowerCase();
        filteredVariants.clear();

        for (VariantNames variant : getActiveVariantList()) {
            if (query.isEmpty()) {
                filteredVariants.add(variant);
                continue;
            }
            String translatedName = VariantNames.getTranslatedVariantName(variant)
                .toLowerCase();
            if (translatedName.contains(query) || variant.id.toLowerCase()
                .contains(query)) {
                filteredVariants.add(variant);
            }
        }

        if (selectedIndex >= filteredVariants.size()) {
            selectedIndex = -1;
        }
        updateBottomButtons();
    }

    private List<String> getWrappedDescriptionLines(VariantNames variant, int wrapWidth) {
        String description = StatCollector.translateToLocal("variants." + variant.id + ".desc");
        return this.fontRendererObj.listFormattedStringToWidth(description, wrapWidth);
    }

    private void syncNumberField() {
        if (selectedIndex < 0 || selectedIndex >= filteredVariants.size()) {
            return;
        }
        VariantNames selected = filteredVariants.get(selectedIndex);
        if (DIMENSION_LOCKED.equals(selected.id)) {
            dimIdField.setText(String.valueOf(GeneralConfig.startingDimID));
            updateNumberFieldPosition(selected);
        }
    }

    private void updateNumberFieldPosition(VariantNames selected) {
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 50;
        int wrapWidth = this.width - panelX - PADDING;

        List<String> lines = getWrappedDescriptionLines(selected, wrapWidth);
        int descBottomY = panelY + ICON_TO_DESC_GAP + lines.size() * (this.fontRendererObj.FONT_HEIGHT + 2);

        dimIdField.xPosition = panelX;
        dimIdField.yPosition = descBottomY + DESC_TO_FIELD_GAP;
    }

    @Override
    public void initGui() {
        this.buttonList.add(
            new GuiButton(
                0,
                this.width / 2 - 100,
                this.height - 27,
                200,
                20,
                StatCollector.translateToLocal("variantgui.done")));
        this.buttonList.add(
            new GuiButton(
                1,
                PADDING,
                this.height - 52,
                SIDEBAR_WIDTH - PADDING,
                20,
                StatCollector.translateToLocal("variantgui.toggle")));
        this.buttonList.add(
            new GuiButton(
                2,
                PADDING,
                this.height - 27,
                SIDEBAR_WIDTH - PADDING,
                20,
                showingFullVariants ? StatCollector.translateToLocal("variantgui.showsub")
                    : StatCollector.translateToLocal("variantgui.showfull")));
        this.searchField = new GuiTextField(this.fontRendererObj, PADDING, 14, SIDEBAR_WIDTH - PADDING - 4, 16);
        this.searchField.setMaxStringLength(64);
        this.searchField.setFocused(true);
        this.dimIdField = new GuiTextField(this.fontRendererObj, SIDEBAR_WIDTH + PADDING * 2, 90, 100, 16);
        this.dimIdField.setMaxStringLength(10);
        this.optionList = new VariantList();

        refreshFilteredVariants();
        syncNumberField();
    }

    private void updateBottomButtons() {
        boolean enableToggle = selectedIndex >= 0 && selectedIndex < filteredVariants.size();
        if (selectedIndex >= 0 && filteredVariants.get(selectedIndex) == VariantNames.NORMAL) {
            enableToggle = false;
        }
        this.buttonList.get(1).enabled = enableToggle;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parent);
        } else if (button.id == 1) {
            VariantNames selectedVariant = filteredVariants.get(selectedIndex);
            boolean variantState = VariantNames.activeContains(selectedVariant.id);
            VariantLoader.toggleVariant(selectedVariant, !variantState);
        } else if (button.id == 2) {
            showingFullVariants = !showingFullVariants;
            selectedIndex = -1;
            button.displayString = showingFullVariants ? StatCollector.translateToLocal("variantgui.showsub")
                : StatCollector.translateToLocal("variantgui.showfull");
            refreshFilteredVariants();
            syncNumberField();
        }
    }

    private boolean isAllowedNumericInput(char typedChar, int keyCode) {
        if (Character.isDigit(typedChar)) return true;
        return keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE
            || keyCode == Keyboard.KEY_LEFT
            || keyCode == Keyboard.KEY_RIGHT
            || keyCode == Keyboard.KEY_HOME
            || keyCode == Keyboard.KEY_END
            || keyCode == Keyboard.KEY_MINUS;
    }

    private boolean isNumberFieldVisible(VariantNames connectedVariant) {
        return selectedIndex >= 0 && selectedIndex < filteredVariants.size()
            && connectedVariant.equals(filteredVariants.get(selectedIndex));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (searchField.textboxKeyTyped(typedChar, keyCode)) {
            refreshFilteredVariants();
            return;
        }

        if (isNumberFieldVisible(VariantNames.DIMLOCKED) && isAllowedNumericInput(typedChar, keyCode)) {
            if (dimIdField.textboxKeyTyped(typedChar, keyCode)) {
                applyNumberFieldValue(dimIdField, VariantNames.DIMLOCKED);
                return;
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void applyNumberFieldValue(GuiTextField field, VariantNames connectedVariant) {
        String text = field.getText();
        if (text.isEmpty()) return;
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(text);
        } catch (NumberFormatException ignored) {}
        if (connectedVariant.equals(VariantNames.DIMLOCKED)) {
            GeneralConfig.startingDimID = parsedInt;
        }
        ConfigurationManager.save(GeneralConfig.class);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        searchField.mouseClicked(mouseX, mouseY, mouseButton);

        if (isNumberFieldVisible(VariantNames.DIMLOCKED)) {
            dimIdField.mouseClicked(mouseX, mouseY, mouseButton);
        } else {
            dimIdField.setFocused(false);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.optionList.drawScreen(mouseX, mouseY, partialTicks);
        searchField.drawTextBox();
        drawDetailsPanel();

        if (isNumberFieldVisible(VariantNames.DIMLOCKED)) {
            dimIdField.drawTextBox();
        }

        this.drawCenteredString(
            this.fontRendererObj,
            StatCollector.translateToLocal("variantgui.header"),
            this.width / 2,
            20,
            0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private ResourceLocation getVariantIcon(String variantId) {
        return iconCache.computeIfAbsent(variantId, id -> {
            ResourceLocation candidate = new ResourceLocation(
                "variablehorizons",
                "textures/gui/variants/" + id + ".png");
            try {
                this.mc.getResourceManager()
                    .getResource(candidate);
                return candidate;
            } catch (IOException e) {
                return DEFAULT_ICON;
            }
        });
    }

    private void drawDetailsPanel() {
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 50;

        if (selectedIndex < 0 || selectedIndex >= filteredVariants.size()) {
            return;
        }

        VariantNames selectedVariant = filteredVariants.get(selectedIndex);

        ResourceLocation icon = getVariantIcon(selectedVariant.id);
        this.mc.getTextureManager()
            .bindTexture(icon);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // is actually drawScaledCustomSizeModalRect I think
        func_152125_a(panelX, panelY, 0, 0, 256, 256, ICON_SIZE, ICON_SIZE, 256, 256);

        int textX = panelX + ICON_SIZE + PADDING;
        this.drawString(
            this.fontRendererObj,
            VariantNames.getTranslatedVariantName(selectedVariant),
            textX,
            panelY + (ICON_SIZE - fontRendererObj.FONT_HEIGHT) / 2,
            0xFFFFFF);

        int wrapWidth = this.width - panelX - PADDING;
        List<String> lines = getWrappedDescriptionLines(selectedVariant, wrapWidth);
        int lineY = panelY + ICON_TO_DESC_GAP;
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
            return filteredVariants.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
            selectedIndex = index;
            syncNumberField();
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
            String selectedVariantID = filteredVariants.get(index).id;
            String label = VariantNames.getTranslatedVariantName(selectedVariantID);
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
