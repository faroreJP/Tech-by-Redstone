package farore.tech.machine.block;

import com.mojang.realmsclient.gui.ChatFormatting;
import farore.tech.common.block.BlockBase;
import farore.tech.machine.IBlockConductor;
import farore.tech.machine.te.TileEntityMachineBase;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public abstract class BlockMachineBase extends BlockBase implements ITileEntityProvider, IBlockConductor {
    public static final Material[] MATERIALS={
            Material.rock, Material.iron, Material.rock
    };
    public static final float[] HARDNESS={
            3.5f, 5.0f, 50.0f
    };
    public static final float[] RESISTANCE={
            17.5f, 50.f, 6000.f
    };
    public static final PropertyDirection FACING=BlockHorizontal.FACING;

    protected short minA, minF, maxA, maxF;

    public BlockMachineBase(int tier) {
        super(MATERIALS[tier]);
        setHardness(HARDNESS[tier]);
        setResistance(RESISTANCE[tier]);

        switch (tier){
            case 0: minA=1; minF=1; maxA=32; maxF=32; break;
            case 1: minA=32; minF=32; maxA=128; maxF=128; break;
            case 2: minA=64; minF=64; maxA=256; maxF=256; break;
        }
    }
    public BlockMachineBase(int tier, int minA, int maxA, int minF, int maxF){
        super(MATERIALS[tier]);
        setHardness(HARDNESS[tier]);
        setResistance(RESISTANCE[tier]);

        this.minA=(short)minA;
        this.maxA=(short)maxA;
        this.minF=(short)minF;
        this.maxF=(short)maxF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        super.addInformation(itemStack, player, list, custom);
        list.add(ChatFormatting.RED + "Input : " + getMinAmplitude() + "A, " + getMinFrequency() + "Hz MIN / " + getMaxAmplitude() + "A, " + getMaxFrequency() + "Hz MAX");
    }

    public TileEntityMachineBase getTE(World world, BlockPos pos){
        TileEntity te=world.getTileEntity(pos);
        return te instanceof TileEntityMachineBase?(TileEntityMachineBase)te:null;
    }

    //------------------------------------------------------------------------------------------------------------------
    // 更新関係
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block) {
        TileEntityMachineBase te=getTE(world, pos);
        if(te!=null){
            te.updateStateAndInput();
            te.markDirty();
        }
        super.onNeighborBlockChange(world, pos, state, block);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);

        if (!world.isRemote) {
            world.notifyNeighborsOfStateChange(pos, this);
            /*
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
            world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
            world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
            */
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // BlockState関係
    //------------------------------------------------------------------------------------------------------------------
    public EnumFacing getFacing(IBlockState state){
        return ((EnumFacing)state.getValue(FACING));
    }

    @Override
    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        return ((EnumFacing)p_getMetaFromState_1_.getValue(FACING)).getIndex();
    }
    @Override
    public IBlockState withRotation(IBlockState p_withRotation_1_, Rotation p_withRotation_2_) {
        return p_withRotation_1_.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)p_withRotation_1_.getValue(FACING)));
    }
    @Override
    public IBlockState withMirror(IBlockState p_withMirror_1_, Mirror p_withMirror_2_) {
        return p_withMirror_1_.withRotation(p_withMirror_2_.toRotation((EnumFacing)p_withMirror_1_.getValue(FACING)));
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    //------------------------------------------------------------------------------------------------------------------
    // IConductor
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public short getMinAmplitude(){
        return minA;
    }
    @Override
    public short getMaxAmplitude(){
        return maxA;
    }
    @Override
    public short getMinFrequency(){
        return minF;
    }
    @Override
    public short getMaxFrequency(){
        return maxF;
    }
}
