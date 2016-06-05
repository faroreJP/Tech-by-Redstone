package farore.tech.machine.model;
// Date: 2015/06/26 22:35:50
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCableToMachineOnly extends ModelBase implements IColored {
    //fields
    ModelRenderer con;
    ModelRenderer con2;
    ModelRenderer body;

    public ModelCableToMachineOnly(int i) {
        textureWidth = 64;
        textureHeight = 32;

        con = new ModelRenderer(this, 8*(i%8), 13*(i/8));
        con.addBox(-1F, -1F, 6F, 2, 2, 2);
        con.setRotationPoint(0F, 0F, 0F);
        con.setTextureSize(64, 32);
        con.mirror = true;
        setRotation(con, 0F, 0F, 0F);

        con2 = new ModelRenderer(this, 6*(i%8), 2*(i/8)+28);
        con2.addBox(-1F, -1F, 5F, 2, 1, 1);
        con2.setRotationPoint(0F, 0F, 0F);
        con2.setTextureSize(64, 32);
        con2.mirror = true;
        setRotation(con2, 0F, 0F, 0F);

        body = new ModelRenderer(this, 8*(i%8), 13*(i/8)+4);
        body.addBox(-1F, 0F, 5F, 2, 7, 2);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        con.render(f5);
        con2.render(f5);
        body.render(f5);
    }
    public void render2(float f5){

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }

    @Override
    public void setColor(int meta) {
        con.setTextureOffset(8*(meta&7), 14*(meta>>3));
        body.setTextureOffset(8*(meta&7), 14*(meta>>3)+4);
        con2.setTextureOffset(6*(meta&7), 2*(meta>>3)+28);
    }
}