package forestryofmiddleearth.apiculture.bees;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.*;
import forestry.api.genetics.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;

public class FOMEBees {
    public static IAlleleBeeSpecies mithrilBee;
    public static IClassification dwarfClassification;

    public static void init() {
        // Retrieve Forestry's Genetic Root for Apiculture
        IBeeRoot beeRoot = (IBeeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
        if (beeRoot == null) return;

        // Register custom dwarven/underground classification branch
        IClassification apidae = AlleleManager.alleleRegistry.getClassification("family.apidae");
        dwarfClassification = AlleleManager.alleleRegistry.createAndRegisterClassification(
                IClassification.EnumClassLevel.GENUS,
                "dwarf",
                "Dwarven",
                apidae
        );

        // Define the Mithril Bee Species Allele
        mithrilBee = new FOMEBeeSpecies(
                "forestry.allele.beeMithril",
                true, // Dominant
                "Mithril",
                "Mithril-producing subterranean bees",
                dwarfClassification,
                0x8A9EA7, // Primary Color (Silver-Blueish Grey)
                0xD3E8EE  // Secondary Outline Color (Shiny Mithril White)
        );

        // Register the allele to the registry
        AlleleManager.alleleRegistry.registerAllele(mithrilBee);

        // Build Genome Template based on standard Forest bee base
        IAllele[] parentTemplate = beeRoot.getTemplate("forestry.allele.beeCommon");
        if (parentTemplate != null) {
            IAllele[] mithrilTemplate = parentTemplate.clone();
            mithrilTemplate[EnumBeeChromosome.SPECIES.ordinal()] = mithrilBee;

            // Register template so breeding, spawning, and item generation work automatically
            beeRoot.registerTemplate("forestrylotr.bee.mithril", mithrilTemplate);
        }
    }

    // Custom Species Implementation for 1.7.10 Forestry Compatibility
    private static class FOMEBeeSpecies implements IAlleleBeeSpecies {
        private final String uid;
        private final boolean dominant;
        private final String name;
        private final String description;
        private final IClassification branch;
        private final int primaryColor;
        private final int secondaryColor;

        public FOMEBeeSpecies(
                String uid,
                boolean dominant,
                String name,
                String description,
                IClassification branch,
                int primaryColor,
                int secondaryColor) {
            this.uid = uid;
            this.dominant = dominant;
            this.name = name;
            this.description = description;
            this.branch = branch;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
        }

        @Override public String getUID() { return uid; }
        @Override public boolean isDominant() { return dominant; }
        @Override public String getName() { return name; }
        @Override public String getDescription() { return description; }
        @Override public IClassification getBranch() { return branch; }
        @Override public int getIconColour(int renderPass) { return renderPass == 0 ? primaryColor : secondaryColor; }
        @Override public int getComplexity() { return 1; }
        @Override public boolean isSecret() { return false; }
        @Override public boolean isCounted() { return true; }

        @Override
        public IBeeRoot getRoot() {
            return (IBeeRoot) AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
        }

        @Override
        public boolean isNocturnal() {
            return true; // Subterranean/Mithril bees can work at night
        }

        @Override
        public Map<ItemStack, Float> getProductChances() {
            Map<ItemStack, Float> products = new HashMap<ItemStack, Float>();
            products.put(new ItemStack(FOMECombItems.combMithril), 0.15f); // 15% production chance
            return products;
        }

        @Override
        public Map<ItemStack, Float> getSpecialtyChances() {
            return new HashMap<ItemStack, Float>();
        }

        @Override
        public boolean isJubilant(IBeeGenome genome, IBeeHousing housing) {
            return true;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(EnumBeeType type, int renderPass) {
            return null; // Delegate icon rendering to default Forestry systems
        }

        @Deprecated
        @Override
        public String getEntityTexture() {
            return null;
        }

        @Override
        public String getBinomial() { return "Apis mithrilis"; }
        @Override
        public String getUnlocalizedName() { return "forestrylotr.species.mithril"; }
        @Override public EnumTemperature getTemperature() { return EnumTemperature.NORMAL; }
        @Override public EnumHumidity getHumidity() { return EnumHumidity.NORMAL; }
        @Override public boolean hasEffect() { return true; }

        @Override
        public String getAuthority() {
            return "Forestry of Middle Earth";
        }

        @Override
        public float getResearchSuitability(ItemStack itemStack) {
            return 0.0f;
        }

        @Override
        public ItemStack[] getResearchBounty(World world, GameProfile gameProfile, IIndividual individual, int i) {
            return new ItemStack[0];
        }

        @SideOnly(Side.CLIENT)
        @Override
        public IIconProvider getIconProvider() {
            return null;
        }
    }
}