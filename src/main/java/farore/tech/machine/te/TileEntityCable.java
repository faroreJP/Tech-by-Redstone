package farore.tech.machine.te;

import farore.tech.common.TileEntityBase;
import farore.tech.machine.block.BlockCable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class TileEntityCable extends TileEntityBase implements IConductorAcceptable {
    protected short amplitude;
    protected short frequency;

    protected byte connectState;
    protected byte connectStateToCable;
    protected boolean connectMachineOne;

    protected int metal;
    protected BlockCable block;

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        connectState = par1NBTTagCompound.getByte("ConnectState");
        connectStateToCable = par1NBTTagCompound.getByte("ConnectStateToCable");
        connectMachineOne=par1NBTTagCompound.getBoolean("ConnectMachineOne");
        amplitude = par1NBTTagCompound.getShort("Amplitude");
        frequency = par1NBTTagCompound.getShort("Frequency");
        metal=par1NBTTagCompound.getInteger("Metal");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setByte("ConnectState", connectState);
        par1NBTTagCompound.setByte("ConnectStateToCable", connectStateToCable);
        par1NBTTagCompound.setBoolean("ConnectMachineOne", connectMachineOne);
        par1NBTTagCompound.setShort("Amplitude", amplitude);
        par1NBTTagCompound.setShort("Frequency", frequency);
        par1NBTTagCompound.setInteger("Metal", metal);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        super.onDataPacket(net, pkt);
        //updateConnectAndInput();
        worldObj.markBlockRangeForRenderUpdate(pos, pos);
    }

    /**
     * ケーブルのブロックを得る
     * @return
     */
    public BlockCable getCable(){
        if(block==null){
            block=(BlockCable)getBlockType();
        }
        return block;
    }

    public boolean updateConnectAndInput(){
        short a=0, f=0;
        int conn=0, conn2=0;
        int cCount=0;

        //Tech.logger.info("update connect state : "+getPos());

        for(int i=0;i<6;i++){
            EnumFacing facing=EnumFacing.getFront(i);
            EnumFacing opp=facing.getOpposite();
            if(!canConnect(facing)) continue;

            //隣接するteを得る
            TileEntity te=worldObj.getTileEntity(getPos().add(facing.getDirectionVec()));
            if(te instanceof IConductor){
                IConductor c=(IConductor)te;
                if(!c.canConnect(opp)) continue;

                conn = (conn | (1 << i));
                if(c.getClass()==TileEntityCable.class) conn2 = (conn2 | (1 << i));
                cCount++;

                short ta=c.getOutputAmplitude(opp);
                short tf=c.getOutputFrequency(opp);

                if(ta>0){ //新しい入力が振幅1以上で
                    if(a==0){ // 現在の入力が無い場合、無条件に書き換える
                        a=ta;
                        f=tf;
                    }
                    else{
                        if(a<ta) a=ta; // 現在の入力があり、振幅が大きい場合、振幅は大きいほうが優先される
                        if(f>tf) f=tf; // 現在の入力があり、周波数が小さい場合、周波数は小さいほうが優先される
                    }
                }
            }
        }

        //例外処理
        if(f<0 || f>getMaxFrequency()) f=0; // 入力周波数が0未満または定格より大きい場合、0になる
        if(a>getMaxAmplitude()){
            a=getMaxAmplitude(); // 入力振幅が定格より大きい場合、最大値になる
            f=0;
        }

        boolean changed=(connectState !=conn || connectStateToCable!=conn2 || amplitude!=a || frequency!=f);

        //
        connectState = (byte) conn;
        connectStateToCable=(byte)conn2;
        connectMachineOne=(cCount==1 && conn2==0);

        amplitude=a;
        frequency=f;
        return changed;
    }

    //------------------------------------------------------------------------------------------------------------------
    // 描画関係
    //------------------------------------------------------------------------------------------------------------------
    public int getMetal(){
        return 0;
    }
    public byte getConnectState(){
        return connectState;
    }
    public byte getConnectStateToCable(){
        return connectStateToCable;
    }
    public boolean isConnectMachineOne(){
        return connectMachineOne;
    }

    //------------------------------------------------------------------------------------------------------------------
    // IConductor
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean canConnect(EnumFacing to) {
        return true;
    }

    @Override
    public short getMinAmplitude() {
        return getCable().getMinAmplitude();
    }

    @Override
    public short getMaxAmplitude() {
        return getCable().getMaxAmplitude();
    }

    @Override
    public short getOutputAmplitude(EnumFacing to) {
        return (short)(amplitude>1?amplitude-1:0);
    }

    @Override
    public short getMinFrequency() {
        return getCable().getMinFrequency();
    }

    @Override
    public short getMaxFrequency() {
        return getCable().getMaxFrequency();
    }

    @Override
    public short getOutputFrequency(EnumFacing to) {
        return amplitude>1?frequency:0;
    }
}
