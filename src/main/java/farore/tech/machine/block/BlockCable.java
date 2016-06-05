package farore.tech.machine.block;

import com.mojang.realmsclient.gui.ChatFormatting;
import farore.tech.PacketHandler;
import farore.tech.common.block.BlockBase;
import farore.tech.machine.IBlockConductor;
import farore.tech.machine.packet.MessageUpdateConnect;
import farore.tech.machine.te.TileEntityCable;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class BlockCable extends BlockBase implements ITileEntityProvider, IBlockConductor{
    public static final PropertyEnum<EnumDyeColor> COLOR = BlockColored.COLOR;
    public short amplitude;
    public short frequency;

    public BlockCable(int a, int f) {
        super(Material.circuits);
        amplitude=(short)a;
        frequency=(short)f;

        setInformation("cable", 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityCable();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean custom) {
        super.addInformation(itemStack, player, list, custom);
        list.add(ChatFormatting.RED + "" + getMaxAmplitude() + "A, " + getMaxFrequency() + "Hz MAX");

        String col=COLOR.getName((EnumDyeColor.byDyeDamage(itemStack.getItemDamage())));
        col=Character.toUpperCase(col.charAt(0))+col.substring(1);

        list.add("Color : "+col);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List<ItemStack> list) {
        EnumDyeColor[] enumDyeColors = EnumDyeColor.values();
        int length = enumDyeColors.length;

        for (int i = 0; i < length; ++i) {
            EnumDyeColor dyeColor = enumDyeColors[i];
            list.add(new ItemStack(item, 1, dyeColor.getMetadata()));
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // BlockState
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int damageDropped(IBlockState p_damageDropped_1_) {
        return ((EnumDyeColor)p_damageDropped_1_.getValue(COLOR)).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_) {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(p_getStateFromMeta_1_));
    }

    @Override
    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        return ((EnumDyeColor)p_getMetaFromState_1_.getValue(COLOR)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{COLOR});
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

        if(!world.isRemote){
            world.notifyBlockOfStateChange(pos, this);
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
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCable) {
            if(((TileEntityCable) te).updateConnectAndInput()){ // 自身の出力が変化した場合、周囲も変化させる

                world.notifyBlockOfStateChange(pos.down(), this);
                world.notifyBlockOfStateChange(pos.up(), this);
                world.notifyBlockOfStateChange(pos.north(), this);
                world.notifyBlockOfStateChange(pos.south(), this);
                world.notifyBlockOfStateChange(pos.west(), this);
                world.notifyBlockOfStateChange(pos.east(), this);

                world.markBlockRangeForRenderUpdate(pos, pos);
                world.setBlockState(pos, state);
                te.markDirty();

                if(!world.isRemote){
                    PacketHandler.INSTANCE.sendToDimension(new MessageUpdateConnect(pos), world.provider.getDimension());
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // 当たり判定
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isNormalCube(IBlockState p_isNormalCube_1_) {
        return false;
    }

    @Override
    public boolean isFullyOpaque(IBlockState p_isFullyOpaque_1_) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState p_isFullBlock_1_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState p_getRenderType_1_) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing facing) {
        return true;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> list, Entity entity) {
        //addCollisionBoxToList(pos, aabb, p_addCollisionBoxToList_5_, state.getSelectedBoundingBox(world, pos));
        double d = 0.0D;
        double w = (2.0/16.0) / 2.0D;

        int x=pos.getX();
        int y=pos.getY();
        int z=pos.getZ();

        //center
        AxisAlignedBB b = new AxisAlignedBB(x + 0.5D - w, y, z + 0.5D - w, x + 0.5D + w, y + w, z + 0.5D + w);
        if (b != null && aabb.intersectsWith(b)) {
            list.add(b);
        }


        TileEntity t = world.getTileEntity(pos);
        if (t instanceof TileEntityCable) {
            int con = ((TileEntityCable) t).getConnectState();
            if ((con & 32) != 0) {
                b = new AxisAlignedBB(x + 0.5D + w, y, z + 0.5D - w, x + 1.0D, y+w, z + 0.5D + w);
                if (b != null && aabb.intersectsWith(b)) {
                    list.add(b);
                }
            }
            if ((con & 8) != 0) {
                b = new AxisAlignedBB(x + 0.5D - w, y, z + 0.5D + w, x + 0.5D + w, y + w, z + 1.0D);
                if (b != null && aabb.intersectsWith(b)) {
                    list.add(b);
                }
            }
            if ((con & 16) != 0) {
                b = new AxisAlignedBB(x, y, z + 0.5D - w, x + 0.5D - w, y + w, z + 0.5D + w);
                if (b != null && aabb.intersectsWith(b)) {
                    list.add(b);
                }
            }
            if ((con & 4) != 0) {
                b = new AxisAlignedBB(x + 0.5D - w, y, z, x + 0.5D + w, y+w, z + 0.5D - w);
                if (b != null && aabb.intersectsWith(b)) {
                    list.add(b);
                }
            }
            /*
            if ((con & 1) != 0) {
                b = AxisAlignedBB.getBoundingBox(x + 0.5D - w, y, z + 1.0D - w, x + 0.5D + w, y+1, z + 0.5D + w);
                if (b != null && par5AxisAlignedBB.intersectsWith(b)) {
                    par6List.add(b);
                }
            }
            */
            if ((con & 2) != 0) {
                b = new AxisAlignedBB(x + 0.5D - w, y+w, z + 0.5D - w, x + 0.5D + w, y+1, z + 0.5D + w);
                if (b != null && aabb.intersectsWith(b)) {
                    list.add(b);
                }
            }
        }
        //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        double d = -0.0625D;
        double w = (6.0 / 16.0) / 2.0;
        double x=pos.getX(), y=pos.getY(), z=pos.getZ();
        return new AxisAlignedBB(x + 0.5 - w + d, y, z + 0.5 - w + d, x + 0.5 + w - d, y + w - d, z + 0.5 + w - d);
    }

    //------------------------------------------------------------------------------------------------------------------
    // IConductor
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public short getMinAmplitude() {
        return 1;
    }

    @Override
    public short getMaxAmplitude() {
        return amplitude;
    }

    @Override
    public short getMinFrequency() {
        return 1;
    }

    @Override
    public short getMaxFrequency() {
        return frequency;
    }
}
