package farore.tech.machine.packet;

import farore.tech.Tech;
import farore.tech.machine.te.TileEntityCable;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by plusplus_F on 2016/03/19.
 */
public class MessageUpdateConnect implements IMessage {
    public BlockPos pos;

    public MessageUpdateConnect(){}
    public MessageUpdateConnect(BlockPos pos){
        this.pos=pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        int x=byteBuf.readInt();
        int y=byteBuf.readInt();
        int z=byteBuf.readInt();

        pos=new BlockPos(x,y,z);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(pos.getX());
        byteBuf.writeInt(pos.getY());
        byteBuf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<MessageUpdateConnect, IMessage>{
        @Override
        public IMessage onMessage(MessageUpdateConnect messageUpdateConnect, MessageContext messageContext) {
            World w=Tech.proxy.getClientWorld();
            if(w!=null){
                TileEntity te=w.getTileEntity(messageUpdateConnect.pos);
                if(te instanceof TileEntityCable){
                    ((TileEntityCable) te).updateConnectAndInput();
                }
            }
            return null;
        }
    }
}
