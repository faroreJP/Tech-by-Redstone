package farore.tech.machine.te;

import farore.tech.common.TileEntityBase;
import farore.tech.machine.IBlockConductor;
import farore.tech.machine.block.BlockGeneratorInfinity;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;

/**
 * Created by plusplus_F on 2016/03/19.
 * 稼働できる状態であれば、無限にパルスを出力し続ける
 */
public class TileEntityGeneratorInfinity extends TileEntityBase implements IConductor {
    public TileEntityGeneratorInfinity(){

    }

    public IBlockConductor getBlock(){
        Block b=getBlockType();
        return b instanceof IBlockConductor?(IBlockConductor)b:null;
    }

    @Override
    public boolean canConnect(EnumFacing to) {
        return to!=EnumFacing.UP && to!=EnumFacing.DOWN;
    }

    @Override
    public short getOutputAmplitude(EnumFacing to) {
        return !worldObj.getBlockState(getPos()).getValue(BlockGeneratorInfinity.POWERED).booleanValue()?getBlock().getMaxAmplitude():0;
    }

    @Override
    public short getOutputFrequency(EnumFacing to) {
        return !worldObj.getBlockState(getPos()).getValue(BlockGeneratorInfinity.POWERED).booleanValue()?getBlock().getMaxAmplitude():0;
    }
}
