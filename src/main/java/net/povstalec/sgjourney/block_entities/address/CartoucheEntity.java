package net.povstalec.sgjourney.block_entities.address;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.povstalec.sgjourney.data.StargateNetwork;

public abstract class CartoucheEntity extends BlockEntity
{
	private String dimension;
	
	public CartoucheEntity(BlockEntityType<?> cartouche, BlockPos pos, BlockState state) 
	{
		super(cartouche, pos, state);
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		if(level.isClientSide)
			return;
		
		if(dimension == null)
			setDimension(level);
	}
	
	@Override
    public void load(CompoundTag tag)
    {
    	super.load(tag);
    	
    	if(tag.contains("Dimension"))
    		dimension = tag.getString("Dimension");
	}
	
	@Override
    protected void saveAdditional(@NotNull CompoundTag tag)
	{
		if(dimension != null)
			tag.putString("Dimension", dimension);
		
		super.saveAdditional(tag);
	}
	
	public void setDimension(Level level)
	{
		if(level.isClientSide())
			return;
		
		dimension = level.dimension().location().toString();
	}
	
	public String getAddress()
	{
		return StargateNetwork.get(level).getLocalAddress(dimension);
	}

}
