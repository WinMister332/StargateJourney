package net.povstalec.sgjourney.client.render.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.povstalec.sgjourney.client.models.UniverseStargateModel;
import net.povstalec.sgjourney.common.block_entities.stargate.UniverseStargateEntity;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBaseBlock;
import net.povstalec.sgjourney.common.blocks.stargate.UniverseStargateBlock;
import net.povstalec.sgjourney.common.blockstates.Orientation;
import net.povstalec.sgjourney.common.config.ClientStargateConfig;

public class UniverseStargateRenderer extends AbstractStargateRenderer implements BlockEntityRenderer<UniverseStargateEntity>
{
	protected final UniverseStargateModel stargateModel;
	
	public static final int WORMHOLE_R = 200; 
	public static final int WORMHOLE_G = 220;
	public static final int WORMHOLE_B = 255;
	public static final int WORMHOLE_ALPHA = 255;
	
	public UniverseStargateRenderer(BlockEntityRendererProvider.Context context)
	{
		super(context, WORMHOLE_R, WORMHOLE_G, WORMHOLE_B, WORMHOLE_ALPHA, 0.25F);
		this.wormholeModel.setRGBConfigValue(ClientStargateConfig.universe_rgba);
		this.stargateModel = new UniverseStargateModel();
	}
	
	@Override
	public void render(UniverseStargateEntity stargate, float partialTick, PoseStack stack,
			MultiBufferSource source, int combinedLight, int combinedOverlay)
	{
		BlockState blockstate = stargate.getBlockState();
		float facing = blockstate.getValue(UniverseStargateBlock.FACING).toYRot();
		Vec3 center = stargate.getRelativeCenter();
		Orientation orientation = blockstate.getValue(AbstractStargateBaseBlock.ORIENTATION);
		
        stack.pushPose();
		stack.translate(center.x(), center.y(), center.z());
        stack.mulPose(Axis.YP.rotationDegrees(-facing));
        
        if(orientation == Orientation.UPWARD)
            stack.mulPose(Axis.XP.rotationDegrees(-90));
        else if(orientation == Orientation.DOWNWARD)
            stack.mulPose(Axis.XP.rotationDegrees(90));
        
        this.stargateModel.setRotation(stargate.getRotation(partialTick));
        this.stargateModel.renderStargate(stargate, partialTick, stack, source, combinedLight, combinedOverlay);
        
        this.renderWormhole(stargate, stack, source, this.stargateModel, combinedLight, combinedOverlay);
	    stack.popPose();
	    
	}
	
	@Override
	public int getViewDistance()
	{
		return 128;
	}
	
}
