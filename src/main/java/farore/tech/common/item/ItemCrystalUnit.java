package farore.tech.common.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class ItemCrystalUnit extends ItemBase {
    public static final int EXPANSION =10000;
    public static final String DAMAGE="tbr.damage";

    protected short amplitude;
    protected short frequency;
    /**
     * 出力frequencyで稼働させた場合の振動子の稼働時間(秒)
     */
    protected short till;

    public ItemCrystalUnit(int amp, int freq, int till){
        amplitude=(short)amp;
        frequency=(short)freq;
        this.till=(short)till;
        setInformation("crystalUnit", 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        super.addInformation(itemStack, player, list, custom);
        list.add(ChatFormatting.RED+"Output : "+amplitude+"A, "+frequency+"Hz MAX");
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return getDamageNBT(itemStack)>0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack) {
        return getDamageNBT(itemStack)/getMaxDamageNBT(itemStack);
    }

    public short getAmplitude(ItemStack itemStack){
        return amplitude;
    }
    public short getFrequency(ItemStack itemStack){
        return frequency;
    }
    public short getTill(ItemStack itemStack){
        return till;
    }
    public double getMaxDamageNBT(ItemStack itemStack){
        return (double)frequency*20*till;
    }

    public NBTTagCompound getNBT(ItemStack itemStack){
        if(!itemStack.hasTagCompound()){
            NBTTagCompound nbt=new NBTTagCompound();
            nbt.setDouble(DAMAGE, 0);
            itemStack.setTagCompound(nbt);
        }
        return itemStack.getTagCompound();
    }

    /**
     * @param unit
     * @return 振動子のダメージ
     */
    public static double getDamageNBT(ItemStack unit){
        if(!(unit.getItem() instanceof ItemCrystalUnit)) return 0;
        ItemCrystalUnit icu=(ItemCrystalUnit)unit.getItem();
        return icu.getNBT(unit).getDouble(DAMAGE);
    }

    /**
     * 振動子にダメージを与える
     * @param unit
     * @param damage 与えるダメージ量。事前にDAMAGE_EXPANTIONをかける必要は無い
     * @return ダメージが最大ダメージ以上になったか否か
     */
    public static boolean addDamage(ItemStack unit, double damage){
        if(!(unit.getItem() instanceof ItemCrystalUnit)) return false;
        ItemCrystalUnit icu=(ItemCrystalUnit)unit.getItem();
        NBTTagCompound nbt=icu.getNBT(unit);

        double d=nbt.getDouble(DAMAGE)+damage* EXPANSION;
        boolean flag=(d>=icu.getMaxDamageNBT(unit));

        return flag;

    }
}
