package farore.tech.common.block;

import farore.tech.Tech;
import farore.tech.common.item.ItemBlockBase;
import farore.tech.machine.block.BlockCable;
import farore.tech.machine.block.BlockGeneratorInfinity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class BlockManager {
    public static void register(){
        GameRegistry.registerBlock(new BlockBase(Material.rock).setUnlocalizedName("casingStone").setHardness(3.5f).setResistance(17.5f), ItemBlockBase.class, "casingStone");
        GameRegistry.registerBlock(new BlockBase(Material.rock).setUnlocalizedName("casingObsidian").setHardness(50.0f).setResistance(6000.0f), ItemBlockBase.class, "casingObsidian");

        GameRegistry.registerBlock(new BlockCable(16, 16).setUnlocalizedName("cableTin"), ItemBlockBase.class, "cableTin");

        GameRegistry.registerBlock(new BlockGeneratorInfinity(16, 4, Material.rock).setUnlocalizedName("generatorVLF").setHardness(3.5f).setResistance(17.5f), ItemBlockBase.class, "generatorVLF");
    }

    public static void json(){
        Item tmp;

        ModelLoader.setCustomModelResourceLocation(getItem("casingStone"), 0, new ModelResourceLocation(Tech.MODID + ":casingStone", "inventory"));
        ModelLoader.setCustomModelResourceLocation(getItem("casingObsidian"), 0, new ModelResourceLocation(Tech.MODID + ":casingObsidian", "inventory"));

        tmp=getItem("cableTin");
        ModelLoader.setCustomModelResourceLocation(tmp, 0, new ModelResourceLocation(Tech.MODID + ":cableTin", "inventory"));
        //for(int i=0;i<16;i++) ModelLoader.setCustomModelResourceLocation(tmp, i, new ModelResourceLocation(Tech.MODID + ":" + "cableTin", "inventory"));

        tmp=getItem("generatorVLF");
        ModelLoader.setCustomModelResourceLocation(tmp, 0, new ModelResourceLocation(Tech.MODID + ":generatorVLF", "inventory"));
        //ModelLoader.setCustomModelResourceLocation(tmp, 1, new ModelResourceLocation(Tech.MODID + ":" + "generatorVLF", "inventory"));
    }

    public static Block get(String name){
        return Block.getBlockFromName(Tech.MODID+":"+name);
    }
    public static Item getItem(String name){
        return Item.getItemFromBlock(get(name));
    }
}
