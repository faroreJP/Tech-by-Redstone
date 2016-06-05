package farore.tech.machine.te;

import farore.tech.common.item.ItemCrystalUnit;
import farore.tech.common.TileEntityBase;
import farore.tech.machine.block.BlockMachineBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public abstract class TileEntityMachineBase extends TileEntityBase implements IConductorAcceptable, ITickable {
    private BlockMachineBase block;
    protected short amplitude;
    protected short frequency;

    protected double workAmount;
    public double progress;

    public TileEntityMachineBase(){
        workAmount=32*ItemCrystalUnit.EXPANSION;
    }

    /**
     *
     * @param workAmount 1回あたりの作業に必要な仕事量 ... (適性周波数)*(秒数)
     */
    public TileEntityMachineBase(int workAmount){
        this.workAmount=workAmount* ItemCrystalUnit.EXPANSION;
    }

    /**
     * 機械のブロックを得る
     * @return
     */
    public BlockMachineBase getMachine(){
        if(block==null){
            block=(BlockMachineBase)getBlockType();
        }
        return block;
    }

    /**
     * 機械が稼働できるかを返す
     * @return
     */
    public boolean canWork(){
        BlockMachineBase bmb=getMachine();
        return bmb.getMinAmplitude()<=amplitude && amplitude<=bmb.getMaxAmplitude() &&
                bmb.getMinFrequency()<=frequency && frequency<=bmb.getMaxFrequency();
    }

    public abstract void work();

    @Override
    public void update(){
        if(canWork()){
            boolean worked=false;
            progress+=frequency*ItemCrystalUnit.EXPANSION;
            while(progress>workAmount && canWork()){
                worked=true;
                work();
                progress-=workAmount;
            }

            if(worked){
                markDirty();
            }
        }
    }

    public void onExplode(){

    }

    public void updateStateAndInput(){
        short a=0, f=0;
        for(int i=0;i<6;i++){
            EnumFacing facing=EnumFacing.getFront(i);
            EnumFacing opp=facing.getOpposite();
            if(!canConnect(facing)) continue;

            //隣接するteを得る
            TileEntity te=worldObj.getTileEntity(getPos().add(facing.getDirectionVec()));
            if(te instanceof IConductor){
                IConductor c=(IConductor)te;
                if(!c.canConnect(opp)) continue;

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
        if(a>getMaxAmplitude()) onExplode(); // 入力振幅が定格より大きい場合、爆発する

        amplitude=a;
        frequency=f;
    }


    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);

        progress=par1NBTTagCompound.getDouble("Progress");
        workAmount=par1NBTTagCompound.getDouble("WorkAmount");
        amplitude=par1NBTTagCompound.getShort("Amplitude");
        frequency=par1NBTTagCompound.getShort("Frequency");
    }
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound){
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setDouble("Progress", progress);
        par1NBTTagCompound.setDouble("WorkAmount", workAmount);
        par1NBTTagCompound.setShort("Amplitude", amplitude);
        par1NBTTagCompound.setShort("Frequency", frequency);
    }

    //------------------------------------------------------------------------------------------------------------------
    // IConductor
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean canConnect(EnumFacing to) {
        return to!=getMachine().getFacing(worldObj.getBlockState(getPos()));
    }

    @Override
    public short getMinAmplitude() {
        return getMachine().getMinAmplitude();
    }

    @Override
    public short getMaxAmplitude() {
        return getMachine().getMaxAmplitude();
    }

    @Override
    public short getOutputAmplitude(EnumFacing to) {
        return 0;
    }

    @Override
    public short getMinFrequency() {
        return getMachine().getMinFrequency();
    }

    @Override
    public short getMaxFrequency() {
        return getMachine().getMaxFrequency();
    }

    @Override
    public short getOutputFrequency(EnumFacing to) {
        return 0;
    }
}
