package poi.giftiacoder.civil_mod.entity.renderer;

import java.io.File;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelHuman extends ModelBiped
{
	static
	{
		System.load(new File("RenderTest.dll").getAbsolutePath());
	}
	
	static class CustomModelRenderer extends ModelRenderer
	{
		private long modelId;
		
		public CustomModelRenderer(ModelBase model, String path) 
		{
			super(model);
			modelId = loadCustomModel(path);
		}
		
		@SideOnly(Side.CLIENT)
	    public void render(float scale)
	    {
			renderWithRotation(scale);
	    }
		
	    @SideOnly(Side.CLIENT)
	    public void renderWithRotation(float scale)
	    {
	        if (!this.isHidden)
	        {
	            if (this.showModel)
	            {
	            	renderCustomModel(rotationPointX * scale, rotationPointY * scale + 1.65F, rotationPointZ * scale, 
	            			rotateAngleX, rotateAngleY, rotateAngleZ, modelId);
	            }
	        }
	    }
	    
	    private native static void renderCustomModel(
	    		float rotationPointX, float rotationPointY, float rotationPointZ, 
	    		float rotateAngleX, float rotateAngleY, float rotateAngleZ, 
	    		long modelId);
	    
	    private native static long loadCustomModel(String path);
	}
	
	public ModelHuman()
	{
		bipedBody = new CustomModelRenderer(this, "spe_body0.model.model");
		
		bipedHead.showModel = false;
		bipedBody.showModel = true;
		
		bipedHeadwear.showModel = false;
		bipedRightArm.showModel = false;
		bipedLeftArm.showModel = false;
		bipedRightLeg.showModel = false;
		bipedLeftLeg.showModel = false;
	}
}
