package farore.tech.proxy;

import farore.tech.Tech;
import farore.tech.machine.te.TileEntityCable;
import farore.tech.machine.te.TileEntityGeneratorInfinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class ProxyServer {
    public EntityPlayer getEntityPlayer(){
        return null;
    }
    public World getClientWorld() {
        return null;
    }
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityCable.class, Tech.MODID+":cable");

        GameRegistry.registerTileEntity(TileEntityGeneratorInfinity.class, Tech.MODID+":generatorInfinity");
    }
}
