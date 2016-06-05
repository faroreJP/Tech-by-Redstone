package farore.tech.common.item;

import farore.tech.Tech;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/18.
 */
public class ItemBase extends Item{
    protected String informationName;
    protected int informationRow;

    public ItemBase() {
        setCreativeTab(Tech.tabMain);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        return super.setUnlocalizedName("tbr."+ name);
    }

    public Item setInformation(String name, int row){
        informationName=name;
        informationRow=row;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        if(informationName!=null && informationName.length()>0 && informationRow>0){
            for(int i=0;i<informationRow;i++) list.add(I18n.format("info.tbr."+informationName+"."+i));
        }
    }
}
