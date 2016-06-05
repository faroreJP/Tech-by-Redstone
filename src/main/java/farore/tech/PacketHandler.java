package farore.tech;

import farore.tech.machine.packet.MessageUpdateConnect;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tech.MODID);

    public static void init() {
        INSTANCE.registerMessage(MessageUpdateConnect.Handler.class, MessageUpdateConnect.class, 0, Side.CLIENT);

    }
}
