package farore.tech.common;

import farore.tech.common.item.ItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class Tab extends CreativeTabs {
    private Item item;

    private String itemName;
    private int damage;
    private NBTTagCompound nbt;

    public Tab(String name, String iconName, int iconDamage, @Nullable NBTTagCompound iconNBT) {
        super("tbr."+name);

        itemName=iconName;
        damage=iconDamage;
        nbt=iconNBT;
    }

    @Override
    public Item getTabIconItem() {
        if(item==null){
            item= ItemManager.get(itemName);
        }
        return item;
    }
}
