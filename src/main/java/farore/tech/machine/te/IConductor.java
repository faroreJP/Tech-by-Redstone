package farore.tech.machine.te;

import net.minecraft.util.EnumFacing;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public interface IConductor {
    /**
     * 接続できるかを返す
     * @param to 接続する方向
     * @return true:接続可能
     */
    public boolean canConnect(EnumFacing to);
    public short getOutputAmplitude(EnumFacing to);
    public short getOutputFrequency(EnumFacing to);

}
