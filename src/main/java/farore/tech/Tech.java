package farore.tech;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import farore.tech.common.block.BlockManager;
import farore.tech.common.item.ItemManager;
import farore.tech.common.Tab;
import farore.tech.proxy.ProxyServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by plusplus_F on 2016/03/18.
 */
@Mod(modid = Tech.MODID, version = Tech.VERSION)
public class Tech {
    public static final String MODID = "tech-by-redstone";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "farore.tech.proxy.ProxyClient", serverSide = "farore.tech.proxy.ProxyServer")
    public static ProxyServer proxy;

    public static Tab tabMain;

    public static Logger logger= LogManager.getLogger("ToR");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        tabMain=new Tab("main", "crystalUnitQuartz", 0, null);

        BlockManager.register();
        ItemManager.register();

        if (event.getSide().isClient()) {
            BlockManager.json();
            ItemManager.json();
        }

        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerTileEntity();
    }
}
