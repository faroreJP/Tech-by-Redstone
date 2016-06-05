package farore.tech.common.item;

import farore.tech.Tech;
import farore.tech.machine.te.IConductor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class ItemMultimeter extends ItemBase {
    public ItemMultimeter(){
        setUnlocalizedName("multimeter");
        setMaxStackSize(1);
        setNoRepair();
        setInformation("multimeter", 1);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack item, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float disX, float disY, float disZ) {
        if(!world.isRemote && !world.isAirBlock(pos)){
            TileEntity te=world.getTileEntity(pos);
            if(te instanceof IConductor){
                EnumFacing opp=facing;
                IConductor c=(IConductor)te;
                player.addChatComponentMessage(new TextComponentString("[Multimeter]"+opp.getName()+" : "+c.getOutputAmplitude(opp)+"A, "+c.getOutputFrequency(opp)+"Hz"));
            }
        }

        return EnumActionResult.SUCCESS;
    }
}
