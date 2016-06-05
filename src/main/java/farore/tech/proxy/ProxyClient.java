package farore.tech.proxy;

import farore.tech.Tech;
import farore.tech.machine.render.RenderCable;
import farore.tech.machine.te.TileEntityCable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class ProxyClient extends ProxyServer {
    public EntityPlayer getEntityPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
    public void registerTileEntity() {
        super.registerTileEntity();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
    }
}
