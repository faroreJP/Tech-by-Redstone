package farore.tech.common.item;

import farore.tech.common.item.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class ItemGem extends ItemBase {
    public static final String[] NAMES={
      "RockCrystal", "Ruby", "Sapphire", "Amethyst"
    };

    public ItemGem(){
        setUnlocalizedName("gem");
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_getSubItems_1_, CreativeTabs p_getSubItems_2_, List<ItemStack> p_getSubItems_3_) {
        for(int i=0;i<NAMES.length;i++)  p_getSubItems_3_.add(new ItemStack(p_getSubItems_1_, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName(itemStack)+NAMES[itemStack.getItemDamage()];
    }
}
