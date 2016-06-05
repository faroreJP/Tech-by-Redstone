package farore.tech.machine.te;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public interface IConductorAcceptable extends IConductor {
    public short getMinAmplitude();
    public short getMaxAmplitude();

    public short getMinFrequency();
    public short getMaxFrequency();
}
