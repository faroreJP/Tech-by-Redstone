package farore.tech.machine.block;

import com.mojang.realmsclient.gui.ChatFormatting;
import farore.tech.common.block.BlockBase;
import farore.tech.machine.IBlockConductor;
import farore.tech.machine.te.TileEntityGeneratorInfinity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 * RS信号が入力されていない場合、無限にパルスを出力し続ける
 */
public class BlockGeneratorInfinity extends BlockBase implements IBlockConductor, ITileEntityProvider {
    public static final AxisAlignedBB BOX=new AxisAlignedBB(0,0,0,1,13.f/16.f,1);
    public static final PropertyBool POWERED = BlockDoor.POWERED;

    protected short amplitude;
    protected short frequency;

    public BlockGeneratorInfinity(int a, int f, Material material) {
        super(material);
        setInformation("generator", 1);

        amplitude=(short)a;
        frequency=(short)f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        super.addInformation(itemStack, player, list, custom);
        list.add(ChatFormatting.RED + "Output " + getMaxAmplitude() + "A, " + getMaxFrequency() + "Hz MAX");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityGeneratorInfinity();
    }


    //------------------------------------------------------------------------------------------------------------------
    // 判定
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        return BOX;
    }

    /*
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        return BOX.addCoord(pos.getX(), pos.getY(), pos.getZ());
    }
    */

    @Override
    public boolean isFullyOpaque(IBlockState p_isFullyOpaque_1_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState p_doesSideBlockRendering_1_, IBlockAccess p_doesSideBlockRendering_2_, BlockPos p_doesSideBlockRendering_3_, EnumFacing p_doesSideBlockRendering_4_) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState p_shouldSideBeRendered_1_, IBlockAccess p_shouldSideBeRendered_2_, BlockPos p_shouldSideBeRendered_3_, EnumFacing p_shouldSideBeRendered_4_) {
        return true;
    }
    //------------------------------------------------------------------------------------------------------------------
    // 更新関係
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);

        if(!world.isRemote){
            world.notifyBlockOfStateChange(pos.down(), this);
            world.notifyBlockOfStateChange(pos.up(), this);
            world.notifyBlockOfStateChange(pos.north(), this);
            world.notifyBlockOfStateChange(pos.south(), this);
            world.notifyBlockOfStateChange(pos.west(), this);
            world.notifyBlockOfStateChange(pos.east(), this);
        }
    }


    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);


        if(!world.isRemote) {
            //初期状態
            if(world.isBlockPowered(pos)) {
                world.setBlockState(pos, getDefaultState().withProperty(POWERED, true), 2);
            } else  {
                world.setBlockState(pos, getDefaultState().withProperty(POWERED, false), 2);
            }

            //周囲の更新
            world.notifyBlockOfStateChange(pos.down(), this);
            world.notifyBlockOfStateChange(pos.up(), this);
            world.notifyBlockOfStateChange(pos.north(), this);
            world.notifyBlockOfStateChange(pos.south(), this);
            world.notifyBlockOfStateChange(pos.west(), this);
            world.notifyBlockOfStateChange(pos.east(), this);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block) {
        if (!world.isRemote) {
            boolean pre=state.getValue(POWERED).booleanValue();

            if(world.isBlockPowered(pos)) {
                world.setBlockState(pos, getDefaultState().withProperty(POWERED, true), 2);
            } else  {
                world.setBlockState(pos, getDefaultState().withProperty(POWERED, false), 2);
            }

            if(pre!=world.getBlockState(pos).getValue(POWERED).booleanValue()){
                world.notifyBlockOfStateChange(pos.down(), this);
                world.notifyBlockOfStateChange(pos.up(), this);
                world.notifyBlockOfStateChange(pos.north(), this);
                world.notifyBlockOfStateChange(pos.south(), this);
                world.notifyBlockOfStateChange(pos.west(), this);
                world.notifyBlockOfStateChange(pos.east(), this);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // BlockState
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int damageDropped(IBlockState p_damageDropped_1_) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_) {
        return this.getDefaultState().withProperty(POWERED, Boolean.valueOf((p_getStateFromMeta_1_ & 1) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        return p_getMetaFromState_1_.getValue(POWERED).booleanValue()?1:0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{POWERED});
    }

    //------------------------------------------------------------------------------------------------------------------
    // IConductor
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public short getMinAmplitude() {
        return 0;
    }

    @Override
    public short getMaxAmplitude() {
        return amplitude;
    }

    @Override
    public short getMinFrequency() {
        return 0;
    }

    @Override
    public short getMaxFrequency() {
        return frequency;
    }
}
