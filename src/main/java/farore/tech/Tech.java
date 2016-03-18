package farore.tech;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by plusplus_F on 2016/03/18.
 */
@Mod(modid = Tech.MODID, version = Tech.VERSION)
public class Tech {
    public static final String MODID = "Tech-by-Redstone";
    public static final String VERSION = "0.0.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        System.out.println("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
    }
}
