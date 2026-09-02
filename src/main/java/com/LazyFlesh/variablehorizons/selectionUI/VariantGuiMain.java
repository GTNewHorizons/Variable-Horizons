package com.LazyFlesh.variablehorizons.selectionUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    private static final Set<VariantNames> fullVariants = VariantNames.allCompositionVariants;
    private static final Set<VariantNames> subVariants = VariantNames.allSubVariants;
    private static final List<VariantNames> inputFieldVariants = Arrays.asList(
        VariantNames.DIMLOCKED,
        VariantNames.CUSTOM_DIM_START,
        VariantNames.ALTERED_EFFICIENCY,
        VariantNames.ALTERED_RECIPE_TIME);
    private static final ResourceLocation DEFAULT_ICON = new ResourceLocation(
        "variablehorizons",
        "textures/gui/variants/ohno.png");
    private boolean showingFullVariants = true;
    private VariantList optionList;
    private GuiTextField searchField;
    private Set<String> activeVariantsCache = new HashSet<>();
    private final List<VariantNames> filteredVariants = new ArrayList<>();
    private final Map<String, ResourceLocation> iconCache = new HashMap<>();
    private final List<CheckboxEntry> checkboxEntries = new ArrayList<>();
    private final List<TextFieldEntry> textFieldEntries = new ArrayList<>();

    private final Set<String> initialActiveVariants;
    private final int initialStartingDimID;
    private final float initialEfficiencyMultiplier;
    private final boolean initialSuperflatPopulation;
    private final boolean initialSuperflatBiomes;

    public VariantGuiMain(GuiScreen parent) {
        this.parent = parent;
        this.initialActiveVariants = new HashSet<>(VariantNames.getActiveVariantNames());
        this.initialStartingDimID = GeneralConfig.startingDimID;
        this.initialEfficiencyMultiplier = GeneralConfig.efficiencyMultiplier;
        this.initialSuperflatPopulation = GeneralConfig.allowSuperflatPopulation;
        this.initialSuperflatBiomes = GeneralConfig.allowSuperflatBiomes;
    }

    private boolean hasUnsavedChanges() {
        if (!initialActiveVariants.equals(VariantNames.getActiveVariantNames())) return true;
        if (initialStartingDimID != GeneralConfig.startingDimID) return true;
        if (initialEfficiencyMultiplier != GeneralConfig.efficiencyMultiplier) return true;
        if (initialSuperflatPopulation != GeneralConfig.allowSuperflatPopulation) return true;
        if (initialSuperflatBiomes != GeneralConfig.allowSuperflatBiomes) return true;
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

    private static class CheckboxEntry {

        final GuiCheckBox checkbox;
        final VariantNames variant;
        final BooleanSupplier configGetter;
        final Consumer<Boolean> configSetter;

        CheckboxEntry(GuiCheckBox checkbox, VariantNames variant, BooleanSupplier configGetter,
            Consumer<Boolean> configSetter) {
            this.checkbox = checkbox;
            this.variant = variant;
            this.configGetter = configGetter;
            this.configSetter = configSetter;
        }
    }

    private CheckboxEntry makeCheckbox(int idOffset, VariantNames variant, String labelKey, BooleanSupplier getter,
        Consumer<Boolean> setter) {
        GuiCheckBox box = new GuiCheckBox(
            idOffset,
            SIDEBAR_WIDTH + PADDING * 2,
            90,
            StatCollector.translateToLocal(labelKey),
            getter.getAsBoolean());
        return new CheckboxEntry(box, variant, getter, setter);
    }

    private static class TextFieldEntry {

        final GuiTextField field;
        final List<VariantNames> variants;
        final Predicate<Character> charFilter;
        final Supplier<String> configGetter;
        final Consumer<String> configSetter;

        TextFieldEntry(GuiTextField field, List<VariantNames> variants, Predicate<Character> charFilter,
            Supplier<String> configGetter, Consumer<String> configSetter) {
            this.field = field;
            this.variants = variants;
            this.charFilter = charFilter;
            this.configGetter = configGetter;
            this.configSetter = configSetter;
        }
    }

    private TextFieldEntry makeTextField(List<VariantNames> variants, Predicate<Character> charFilter,
        Supplier<String> getter, Consumer<String> setter) {
        int fieldLength = charFilter == null ? 200 : 50;
        GuiTextField field = new GuiTextField(this.fontRendererObj, SIDEBAR_WIDTH + PADDING * 2, 90, fieldLength, 16);
        field.setMaxStringLength(charFilter == null ? 64 : 10);
        field.setText(getter.get());
        return new TextFieldEntry(field, variants, charFilter, getter, setter);
    }

    private int calculateBottomY(VariantNames selected) {
        if (selected == null) return 50;
        int panelX = SIDEBAR_WIDTH + PADDING * 2;
        int panelY = 50;
        int wrapWidth = this.width - panelX - PADDING;
        List<String> lines = getWrappedDescriptionLines(selected, wrapWidth);
        return panelY + ICON_TO_DESC_GAP + lines.size() * (this.fontRendererObj.FONT_HEIGHT + 2) + DESC_TO_FIELD_GAP;
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

    private Set<VariantNames> getActiveVariantList() {
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

        // Variants are ordered based on their order in the VariantNames enum
        filteredVariants.sort(Comparator.comparingInt(Enum::ordinal));

        if (selectedIndex >= filteredVariants.size()) {
            selectedIndex = -1;
        }
        updateBottomButtons();
    }

    private List<String> getWrappedDescriptionLines(VariantNames variant, int wrapWidth) {
        String description = StatCollector.translateToLocal("variants." + variant.id + ".desc");
        return this.fontRendererObj.listFormattedStringToWidth(description, wrapWidth);
    }

    private void syncTextFields(VariantNames connectedVariant) {
        for (TextFieldEntry entry : textFieldEntries) {
            if (entry.variants.contains(connectedVariant)) {
                entry.field.setText(entry.configGetter.get());
            }
        }
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

        checkboxEntries.clear();
        checkboxEntries.add(
            makeCheckbox(
                3,
                VariantNames.SUPERFLAT,
                "variantgui.superflat.population",
                () -> GeneralConfig.allowSuperflatPopulation,
                value -> GeneralConfig.allowSuperflatPopulation = value));
        checkboxEntries.add(
            makeCheckbox(
                4,
                VariantNames.SUPERFLAT,
                "variantgui.superflat.biomes",
                () -> GeneralConfig.allowSuperflatBiomes,
                value -> GeneralConfig.allowSuperflatBiomes = value));
        checkboxEntries.add(
            makeCheckbox(
                5,
                VariantNames.VOID_ISLAND,
                "variantgui.voidisland.tree",
                () -> GeneralConfig.allowVoidIslandTree,
                value -> GeneralConfig.allowVoidIslandTree = value));
        checkboxEntries.add(
            makeCheckbox(
                6,
                VariantNames.VOID_ISLAND,
                "variantgui.voidisland.chest",
                () -> GeneralConfig.allowVoidIslandChest,
                value -> GeneralConfig.allowVoidIslandChest = value));

        textFieldEntries.clear();
        Predicate<Character> dimIdFilter = c -> Character.isDigit(c) || c == '-';
        Predicate<Character> decimalFilter = c -> Character.isDigit(c) || c == '.';
        textFieldEntries.add(
            makeTextField(
                Arrays.asList(VariantNames.DIMLOCKED, VariantNames.CUSTOM_DIM_START),
                dimIdFilter,
                () -> String.valueOf(GeneralConfig.startingDimID),
                text -> {
                    try {
                        GeneralConfig.startingDimID = Integer.parseInt(text);
                    } catch (NumberFormatException ignored) {}
                }));
        textFieldEntries.add(
            makeTextField(
                Collections.singletonList(VariantNames.ALTERED_RECIPE_TIME),
                decimalFilter,
                () -> String.valueOf(GeneralConfig.recipeTimeMultiplier),
                text -> {
                    try {
                        GeneralConfig.recipeTimeMultiplier = Float.parseFloat(text);
                    } catch (NumberFormatException ignored) {}
                }));
        textFieldEntries.add(
            makeTextField(
                Collections.singletonList(VariantNames.ALTERED_EFFICIENCY),
                decimalFilter,
                () -> String.valueOf(GeneralConfig.efficiencyMultiplier),
                text -> {
                    try {
                        GeneralConfig.efficiencyMultiplier = Float.parseFloat(text);
                    } catch (NumberFormatException ignored) {}
                }));
        textFieldEntries.add(
            makeTextField(
                Collections.singletonList(VariantNames.MONOBLOCK),
                null,
                () -> GeneralConfig.replacementBlock,
                text -> GeneralConfig.replacementBlock = text));

        for (CheckboxEntry entry : checkboxEntries) {
            this.buttonList.add(entry.checkbox);
        }
        this.searchField = new GuiTextField(this.fontRendererObj, PADDING, 14, SIDEBAR_WIDTH - PADDING - 4, 16);
        this.searchField.setMaxStringLength(64);
        this.searchField.setFocused(true);
        this.optionList = new VariantList();

        refreshFilteredVariants();
        syncTextFields(VariantNames.NORMAL);
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
            syncTextFields(VariantNames.NORMAL);
        } else {
            for (CheckboxEntry entry : checkboxEntries) {
                if (button.id == entry.checkbox.id) {
                    entry.configSetter.accept(entry.checkbox.isChecked());
                    ConfigurationManager.save(GeneralConfig.class);
                    return;
                }
            }
        }
    }

    private boolean isNavigationKey(int keyCode) {
        return keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE
            || keyCode == Keyboard.KEY_LEFT
            || keyCode == Keyboard.KEY_RIGHT
            || keyCode == Keyboard.KEY_HOME
            || keyCode == Keyboard.KEY_END;
    }

    private VariantNames getSelectedVariant() {
        return (selectedIndex >= 0 && selectedIndex < filteredVariants.size()) ? filteredVariants.get(selectedIndex)
            : null;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (hasUnsavedChanges()) {
                this.mc.displayGuiScreen(new GuiRestartRequired(parent));
            } else {
                this.mc.displayGuiScreen(parent);
            }
            return;
        }

        if (searchField.textboxKeyTyped(typedChar, keyCode)) {
            refreshFilteredVariants();
            return;
        }

        VariantNames selected = getSelectedVariant();
        for (TextFieldEntry entry : textFieldEntries) {
            if (!entry.variants.contains(selected)) continue;

            boolean allowed = entry.charFilter == null || isNavigationKey(keyCode) || entry.charFilter.test(typedChar);

            if (allowed && entry.field.textboxKeyTyped(typedChar, keyCode)) {
                entry.configSetter.accept(entry.field.getText());
                ConfigurationManager.save(GeneralConfig.class);
                return;
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        searchField.mouseClicked(mouseX, mouseY, mouseButton);

        VariantNames selected = getSelectedVariant();
        for (TextFieldEntry entry : textFieldEntries) {
            if (entry.variants.contains(selected)) {
                entry.field.mouseClicked(mouseX, mouseY, mouseButton);
            } else {
                entry.field.setFocused(false);
            }
        }
    }

    private boolean isMouseOverTextField(GuiTextField field, int mouseX, int mouseY) {
        return mouseX >= field.xPosition && mouseX < field.xPosition + field.width
            && mouseY >= field.yPosition
            && mouseY < field.yPosition + field.height;
    }

    private String getTranslatedTextfieldTooltip(VariantNames variant) {
        if (variant == null) {
            return "";
        }
        switch (variant) {
            case DIMLOCKED, CUSTOM_DIM_START -> {
                return StatCollector.translateToLocal("variantgui.dimidfield.tooltip");
            }
            case ALTERED_EFFICIENCY -> {
                return StatCollector.translateToLocal("variantgui.efficiencyfield.tooltip");
            }
            case ALTERED_RECIPE_TIME -> {
                return StatCollector.translateToLocal("variantgui.recipetimefield.tooltip");
            }
            case MONOBLOCK -> {
                return StatCollector.translateToLocal("variantgui.monoblockfield.tooltip");
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.optionList.drawScreen(mouseX, mouseY, partialTicks);
        searchField.drawTextBox();
        drawDetailsPanel();

        VariantNames selectedVariant = getSelectedVariant();
        int nextY = calculateBottomY(selectedVariant);
        for (CheckboxEntry entry : checkboxEntries) {
            boolean visible = selectedVariant != null && selectedVariant.equals(entry.variant);
            entry.checkbox.visible = visible;
            entry.checkbox.enabled = visible;
            if (visible) {
                entry.checkbox.xPosition = SIDEBAR_WIDTH + PADDING * 2;
                entry.checkbox.yPosition = nextY;
                nextY += 15;
            }
        }

        this.drawCenteredString(
            this.fontRendererObj,
            StatCollector.translateToLocal("variantgui.header"),
            this.width / 2,
            20,
            0xFFFFFF);

        for (TextFieldEntry entry : textFieldEntries) {
            if (entry.variants.contains(selectedVariant)) {
                entry.field.xPosition = SIDEBAR_WIDTH + PADDING * 2;
                entry.field.yPosition = nextY;
                entry.field.drawTextBox();
                nextY += 20;
            }
        }

        for (TextFieldEntry entry : textFieldEntries) {
            if (entry.variants.contains(selectedVariant) && isMouseOverTextField(entry.field, mouseX, mouseY)) {
                List<String> tooltip = this.fontRendererObj
                    .listFormattedStringToWidth(getTranslatedTextfieldTooltip(selectedVariant), 200);
                this.drawHoveringText(tooltip, mouseX, mouseY, this.fontRendererObj);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                break;
            }
        }

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
            syncTextFields(getSelectedVariant());
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
