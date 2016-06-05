package farore.tech.common.item;

import farore.tech.Tech;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by plusplus_F on 2016/03/18.
 */
public class ItemManager {
    public static void register(){
        GameRegistry.registerItem(new ItemGem(), "gem");
        GameRegistry.registerItem(new ItemCrystalUnit(16, 16, 600).setUnlocalizedName("crystalUnitQuartz"), "crystalUnitQuartz");

        GameRegistry.registerItem(new ItemMultimeter(), "multimeter");
    }

    public static void json(){
        Item tmp;

        tmp=get("gem");
        ModelBakery.registerItemVariants(tmp, new ResourceLocation(Tech.MODID, "gemRockCrystal"), new ResourceLocation(Tech.MODID, "gemRuby"), new ResourceLocation(Tech.MODID, "gemSapphire"), new ResourceLocation(Tech.MODID, "gemAmethyst"));
        ModelLoader.setCustomModelResourceLocation(tmp, 0, new ModelResourceLocation(Tech.MODID + ":gemRockCrystal", "inventory"));
        ModelLoader.setCustomModelResourceLocation(tmp, 1, new ModelResourceLocation(Tech.MODID + ":gemRuby", "inventory"));
        ModelLoader.setCustomModelResourceLocation(tmp, 2, new ModelResourceLocation(Tech.MODID + ":gemSapphire", "inventory"));
        ModelLoader.setCustomModelResourceLocation(tmp, 3, new ModelResourceLocation(Tech.MODID + ":gemAmethyst", "inventory"));

        ModelLoader.setCustomModelResourceLocation(ItemManager.get("crystalUnitQuartz"), 0, new ModelResourceLocation(Tech.MODID + ":crystalUnitQuartz", "inventory"));

        ModelLoader.setCustomModelResourceLocation(ItemManager.get("multimeter"), 0, new ModelResourceLocation(Tech.MODID + ":multimeter", "inventory"));
    }

    public static Item get(String name){
        return Item.getByNameOrId(Tech.MODID+":"+name);
    }
}
