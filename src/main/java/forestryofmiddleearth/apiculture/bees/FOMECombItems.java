package forestryofmiddleearth.apiculture.bees;

import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import java.util.HashMap;
import java.util.Map;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FOMECombItems {
    public static Item combMithril;
    public static Item shardMithril;

    public static void init() {
        // Register Mithril Comb
        combMithril = new Item()
                .setUnlocalizedName("combMithril")
                .setTextureName("forestrylotr:combMithril")
                .setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(combMithril, "combMithril");

        // Register Mithril Shard
        shardMithril = new Item()
                .setUnlocalizedName("shardMithril")
                .setTextureName("forestrylotr:shardMithril")
                .setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(shardMithril, "shardMithril");

        // Crafting: 9 Mithril Shards -> 1 LOTRMod Mithril Nugget
        if (LOTRMod.mithrilNugget != null) {
            GameRegistry.addRecipe(new ShapedOreRecipe(
                    new ItemStack(LOTRMod.mithrilNugget, 1),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', shardMithril
            ));
        }

        // Centrifuge: Mithril Comb -> Mithril Shard (35% chance) and Beeswax (50% chance)
        // Forestry 1.7.10 uses item stack with damage wildcard or specific metadata
        try {
            Item beeswax = GameRegistry.findItem("Forestry", "beeswax");
            Map<ItemStack, Float> products = new HashMap<ItemStack, Float>();

            // 0.35f represents a 35% chance
            products.put(new ItemStack(shardMithril), 0.35f);

            if (beeswax != null) {
                // 0.5f represents a 50% chance
                products.put(new ItemStack(beeswax, 1), 0.5f);
            }

            // Register with Forestry's Centrifuge Recipe Manager
            RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(combMithril), products);
        } catch (Exception e) {
            System.err.println("[Forestry LOTR] Failed to register centrifuge recipes: " + e.getMessage());
        }
    }
}