package forestryofmiddleearth;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraftforge.common.MinecraftForge;
import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;

@Mod(
        modid = ForestryOfMiddleEarth.MODID,
        name = ForestryOfMiddleEarth.NAME,
        version = ForestryOfMiddleEarth.VERSION,
        dependencies = "required-after:Forge@[10.13.4.1558,);Forestry;required-after:lotr"
)
public class ForestryOfMiddleEarth
{
    public static final String MODID = "forestrylotr";
    public static final String NAME = "Forestry of Middle Earth";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static ForestryOfMiddleEarth instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
