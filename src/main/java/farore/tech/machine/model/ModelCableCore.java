package farore.tech.machine.model;

// Date: 2015/06/26 21:34:58
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCableCore extends ModelBase implements IColored {
    //fields
    ModelRenderer core;

    public ModelCableCore(int ofX, int ofY) {
        textureWidth = 64;
        textureHeight = 32;

        core = new ModelRenderer(this, ofX, ofY);
        core.addBox(-1F, 6F, -1F, 2, 2, 2);
        core.setRotationPoint(0F, 0F, 0F);
        core.setTextureSize(64, 32);
        core.mirror = true;
        setRotation(core, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        core.render(f5);
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
        core.setTextureOffset(0, 4 * meta);
    }
}
