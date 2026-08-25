package com.LazyFlesh.variablehorizons.selectionUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import cpw.mods.fml.client.config.GuiCheckBox;
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
    private static final List<VariantNames> inputFieldVariants = Arrays.asList(
        VariantNames.DIMLOCKED,
        VariantNames.CUSTOM_DIM_START,
        VariantNames.ALTERED_EFFICIENCY,
        VariantNames.ALTERED_RECIPE_TIME);
    private static final List<VariantNames> checkboxVariants = Arrays.asList(VariantNames.SUPERFLAT);
    private static final ResourceLocation DEFAULT_ICON = new ResourceLocation(
        "variablehorizons",
        "textures/gui/variants/ohno.png");
    private boolean showingFullVariants = true;
    private VariantList optionList;
    private GuiTextField searchField;
    private GuiTextField numberInputField;
    private GuiCheckBox checkbox;
    private Set<String> activeVariantsCache = new HashSet<>();
    private final List<VariantNames> filteredVariants = new ArrayList<>();
    private final Map<String, ResourceLocation> iconCache = new HashMap<>();

    private final Set<String> initialActiveVariants;
    private final int initialStartingDimID;
    private final float initialEfficiencyMultiplier;
    private final float initialRecipeTimeMultiplier;
    private final boolean initialSuperflatPopulation;

    public VariantGuiMain(GuiScreen parent) {
        this.parent = parent;
        this.initialActiveVariants = new HashSet<>(VariantNames.getActiveVariantNames());
        this.initialStartingDimID = GeneralConfig.startingDimID;
        this.initialEfficiencyMultiplier = GeneralConfig.efficiencyMultiplier;
        this.initialRecipeTimeMultiplier = GeneralConfig.recipeTimeMultiplier;
        this.initialSuperflatPopulation = GeneralConfig.allowSuperflatPopulation;
    }

    private boolean hasUnsavedChanges() {
        if (!initialActiveVariants.equals(VariantNames.getActiveVariantNames())) return true;
        if (initialStartingDimID != GeneralConfig.startingDimID) return true;
        if (initialEfficiencyMultiplier != GeneralConfig.efficiencyMultiplier) return true;
        if (initialRecipeTimeMultiplier != GeneralConfig.recipeTimeMultiplier) return true;
        if (initialSuperflatPopulation != GeneralConfig.allowSuperflatPopulation) return true;
        return false;
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
        subVariants.remove(VariantNames.NO_RECIPE_ADDITIONS);
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

    private void syncNumberField(VariantNames connectedVariant) {
        if (selectedIndex < 0 || selectedIndex >= filteredVariants.size()) {
            return;
        }
        if (!inputFieldVariants.contains(connectedVariant)) {
            return;
        }

        VariantNames selected = filteredVariants.get(selectedIndex);
        if (selected.equals(VariantNames.DIMLOCKED) || selected.equals(VariantNames.CUSTOM_DIM_START)) {
            numberInputField.setText(String.valueOf(GeneralConfig.startingDimID));
        } else if (selected.equals(VariantNames.ALTERED_EFFICIENCY)) {
            numberInputField.setText(String.valueOf(GeneralConfig.efficiencyMultiplier));
        } else if (selected.equals(VariantNames.ALTERED_RECIPE_TIME)) {
            numberInputField.setText(String.valueOf(GeneralConfig.recipeTimeMultiplier));
        }
        updateNumberFieldPosition(selected);
    }

    private void syncCheckbox(VariantNames connectedVariant) {
        if (selectedIndex < 0 || selectedIndex >= filteredVariants.size()) {
            return;
        }
        if (!checkboxVariants.contains(connectedVariant)) {
            return;
        }

        VariantNames selected = filteredVariants.get(selectedIndex);
        if (selected.equals(VariantNames.SUPERFLAT)) {
            checkbox.setIsChecked(GeneralConfig.allowSuperflatPopulation);
        }
        updateCheckboxPosition(selected);
    }

    private void updateNumberFieldPosition(VariantNames selected) {
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 50;
        int wrapWidth = this.width - panelX - PADDING;

        List<String> lines = getWrappedDescriptionLines(selected, wrapWidth);
        int descBottomY = panelY + ICON_TO_DESC_GAP + lines.size() * (this.fontRendererObj.FONT_HEIGHT + 2);

        numberInputField.xPosition = panelX;
        numberInputField.yPosition = descBottomY + DESC_TO_FIELD_GAP;
    }

    private void updateCheckboxPosition(VariantNames selected) {
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 50;
        int wrapWidth = this.width - panelX - PADDING;

        List<String> lines = getWrappedDescriptionLines(selected, wrapWidth);
        int descBottomY = panelY + ICON_TO_DESC_GAP + lines.size() * (this.fontRendererObj.FONT_HEIGHT + 2);

        checkbox.xPosition = panelX;
        checkbox.yPosition = descBottomY + DESC_TO_FIELD_GAP;
    }

    private void refreshActiveVariantsCache() {
        activeVariantsCache = VariantNames.getActiveVariantNames();
    }

    private boolean isIncompatibleWithActive(VariantNames variant) {
        for (String activeId : activeVariantsCache) {
            if (activeId.equals(variant.id)) continue;
            VariantNames active = VariantNames.getVariantFromID(activeId);
            if (VariantNames.checkIncompatibility(variant, active)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initGui() {
        this.buttonList.add(
            new GuiButton(
                0,
                this.width / 2 - 75,
                this.height - 27,
                150,
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
        this.checkbox = new GuiCheckBox(
            3,
            SIDEBAR_WIDTH + PADDING * 2,
            90,
            StatCollector.translateToLocal("variantgui.superflatpopulation"),
            GeneralConfig.allowSuperflatPopulation);
        this.buttonList.add(checkbox);
        this.searchField = new GuiTextField(this.fontRendererObj, PADDING, 14, SIDEBAR_WIDTH - PADDING - 4, 16);
        this.searchField.setMaxStringLength(64);
        this.searchField.setFocused(true);
        this.numberInputField = new GuiTextField(this.fontRendererObj, SIDEBAR_WIDTH + PADDING * 2, 90, 100, 16);
        this.numberInputField.setMaxStringLength(10);
        this.optionList = new VariantList();

        refreshFilteredVariants();
        syncNumberField(VariantNames.NORMAL);
        refreshActiveVariantsCache();
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
            if (hasUnsavedChanges()) {
                this.mc.displayGuiScreen(new GuiRestartRequired(parent));
            } else {
                this.mc.displayGuiScreen(parent);
            }
        } else if (button.id == 1) {
            VariantNames selectedVariant = filteredVariants.get(selectedIndex);
            boolean variantState = VariantNames.activeContains(selectedVariant.id);
            VariantLoader.toggleVariant(selectedVariant, !variantState);
            refreshActiveVariantsCache();
        } else if (button.id == 2) {
            showingFullVariants = !showingFullVariants;
            selectedIndex = -1;
            button.displayString = showingFullVariants ? StatCollector.translateToLocal("variantgui.showsub")
                : StatCollector.translateToLocal("variantgui.showfull");
            refreshFilteredVariants();
            syncNumberField(VariantNames.NORMAL);
        } else if (button.id == 3) {
            GeneralConfig.allowSuperflatPopulation = checkbox.isChecked();
            ConfigurationManager.save(GeneralConfig.class);
        }
    }

    private boolean isAllowedNumericInput(char typedChar, int keyCode) {
        if (Character.isDigit(typedChar)) return true;
        return keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE
            || keyCode == Keyboard.KEY_LEFT
            || keyCode == Keyboard.KEY_RIGHT
            || keyCode == Keyboard.KEY_HOME
            || keyCode == Keyboard.KEY_END
            || keyCode == Keyboard.KEY_MINUS
            || keyCode == Keyboard.KEY_DECIMAL;
    }

    private boolean isNumberFieldVisible() {
        return selectedIndex >= 0 && selectedIndex < filteredVariants.size()
            && inputFieldVariants.contains(filteredVariants.get(selectedIndex));
    }

    private boolean isCheckboxVisible() {
        return selectedIndex >= 0 && selectedIndex < filteredVariants.size()
            && checkboxVariants.contains(filteredVariants.get(selectedIndex));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (searchField.textboxKeyTyped(typedChar, keyCode)) {
            refreshFilteredVariants();
            return;
        }

        if (isNumberFieldVisible() && isAllowedNumericInput(typedChar, keyCode)) {
            if (numberInputField.textboxKeyTyped(typedChar, keyCode)) {
                applyNumberFieldValue(numberInputField);
                return;
            }
        }

        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (hasUnsavedChanges()) {
                this.mc.displayGuiScreen(new GuiRestartRequired(parent));
            } else {
                this.mc.displayGuiScreen(parent);
            }
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void applyNumberFieldValue(GuiTextField field) {
        String text = field.getText();
        if (text.isEmpty()) return;
        int parsedInt = 0;
        float parsedFloat = 1f;
        VariantNames selectedVariant = filteredVariants.get(selectedIndex);
        boolean dimLock = selectedVariant.equals(VariantNames.DIMLOCKED)
            || selectedVariant.equals(VariantNames.CUSTOM_DIM_START);
        try {
            if (dimLock) {
                parsedInt = Integer.parseInt(text);
            } else {
                parsedFloat = Float.parseFloat(text);
            }
        } catch (NumberFormatException ignored) {}
        if (dimLock) {
            GeneralConfig.startingDimID = parsedInt;
        } else if (selectedVariant.equals(VariantNames.ALTERED_EFFICIENCY)) {
            GeneralConfig.efficiencyMultiplier = parsedFloat;
        } else if (selectedVariant.equals(VariantNames.ALTERED_RECIPE_TIME)) {
            GeneralConfig.recipeTimeMultiplier = parsedFloat;
        }
        ConfigurationManager.save(GeneralConfig.class);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        searchField.mouseClicked(mouseX, mouseY, mouseButton);

        if (isNumberFieldVisible()) {
            numberInputField.mouseClicked(mouseX, mouseY, mouseButton);
        } else {
            numberInputField.setFocused(false);
        }
    }

    private boolean isMouseOverTextField(GuiTextField field, int mouseX, int mouseY) {
        return mouseX >= field.xPosition && mouseX < field.xPosition + field.width
            && mouseY >= field.yPosition
            && mouseY < field.yPosition + field.height;
    }

    private String getTranslatedTextfieldTooltip() {
        String tooltip = "";
        VariantNames selectedVariant = filteredVariants.get(selectedIndex);
        if (selectedVariant.equals(VariantNames.DIMLOCKED) || selectedVariant.equals(VariantNames.CUSTOM_DIM_START)) {
            tooltip = StatCollector.translateToLocal("variantgui.dimidfield.tooltip");
        } else if (selectedVariant.equals(VariantNames.ALTERED_EFFICIENCY)) {
            tooltip = StatCollector.translateToLocal("variantgui.efficiencyfield.tooltip");
        } else if (selectedVariant.equals(VariantNames.ALTERED_RECIPE_TIME)) {
            tooltip = StatCollector.translateToLocal("variantgui.recipetimefield.tooltip");
        }
        return tooltip;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.optionList.drawScreen(mouseX, mouseY, partialTicks);
        searchField.drawTextBox();
        drawDetailsPanel();

        if (isNumberFieldVisible()) {
            numberInputField.drawTextBox();
        }

        boolean checkboxVisible = isCheckboxVisible();
        checkbox.visible = checkboxVisible;
        checkbox.enabled = checkboxVisible;
        if (checkboxVisible) {
            updateCheckboxPosition(filteredVariants.get(selectedIndex));
        }

        this.drawCenteredString(
            this.fontRendererObj,
            StatCollector.translateToLocal("variantgui.header"),
            this.width / 2,
            20,
            0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (isNumberFieldVisible() && isMouseOverTextField(numberInputField, mouseX, mouseY)) {
            List<String> tooltip = this.fontRendererObj
                .listFormattedStringToWidth(getTranslatedTextfieldTooltip(), 200);
            this.drawHoveringText(tooltip, mouseX, mouseY, this.fontRendererObj);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
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
            syncNumberField(filteredVariants.get(selectedIndex));
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
            VariantNames selectedVariant = filteredVariants.get(index);
            String selectedVariantID = selectedVariant.id;
            String label = VariantNames.getTranslatedVariantName(selectedVariantID);
            int xOffset = x + (getListWidth() - 4) / 2;
            int yOffset = y + (slotHeight - VariantGuiMain.this.fontRendererObj.FONT_HEIGHT) / 2;

            int color;
            if (activeVariantsCache.contains(selectedVariantID)) {
                color = 0x45f542;
            } else if (isIncompatibleWithActive(selectedVariant)) {
                color = 0xFF5555;
            } else {
                color = 0xFFFFFF;
            }

            VariantGuiMain.this.drawCenteredString(VariantGuiMain.this.fontRendererObj, label, xOffset, yOffset, color);
        }
    }

    private static class GuiRestartRequired extends GuiScreen {

        private final GuiScreen target;
        private static final int UNDERSTAND_BUTTON_ID = 0;

        GuiRestartRequired(GuiScreen target) {
            this.target = target;
        }

        @Override
        public void initGui() {
            this.buttonList.add(
                new GuiButton(
                    UNDERSTAND_BUTTON_ID,
                    this.width / 2 - 100,
                    this.height / 2 + 36,
                    200,
                    20,
                    StatCollector.translateToLocal("fml.configgui.confirmRestartMessage")));
        }

        @Override
        protected void actionPerformed(GuiButton button) {
            if (button.id == UNDERSTAND_BUTTON_ID) {
                this.mc.displayGuiScreen(target);
            }
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            this.drawDefaultBackground();

            this.drawCenteredString(
                this.fontRendererObj,
                StatCollector.translateToLocal("fml.configgui.gameRestartTitle"),
                this.width / 2,
                this.height / 2 - 40,
                0xCCCCCC);

            List<String> lines = this.fontRendererObj.listFormattedStringToWidth(
                StatCollector.translateToLocal("fml.configgui.gameRestartRequired"),
                this.width - 50);
            int lineY = this.height / 2;
            for (String line : lines) {
                this.drawCenteredString(this.fontRendererObj, line, this.width / 2, lineY, 0xFFFFFF);
                lineY += this.fontRendererObj.FONT_HEIGHT + 2;
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }
    }
}
