package farore.tech.common.block;

import com.mojang.realmsclient.gui.ChatFormatting;
import farore.tech.Tech;
import farore.tech.machine.IBlockConductor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class BlockBase extends Block {
    protected String informationName;
    protected int informationRow;


    public BlockBase(Material p_i45394_1_) {
        super(p_i45394_1_);
        setCreativeTab(Tech.tabMain);
    }

    @Override
    public Block setUnlocalizedName(String p_setUnlocalizedName_1_) {
        return super.setUnlocalizedName("tbr."+p_setUnlocalizedName_1_);
    }

    public Block setInformation(String name, int row){
        informationName=name;
        informationRow=row;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        if(informationName!=null && informationName.length()>0 && informationRow>0){
            for(int i=0;i<informationRow;i++) list.add(I18n.format("info.tbr." + informationName + "." + i));
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te=world.getTileEntity(pos);
        if(te instanceof IInventory){
            IInventory inv=(IInventory)te;

            for (int j1 = 0; j1 < inv.getSizeInventory(); j1++){
                ItemStack itemstack = inv.getStackInSlot(j1);

                if (itemstack != null){
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0){
                        int k1 = world.rand.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize){
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(world, (double)((float)pos.getX() + f), (double)((float)pos.getY() + f1), (double)((float)pos.getZ() + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()){
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }

        super.breakBlock(world, pos, state);
    }
}
