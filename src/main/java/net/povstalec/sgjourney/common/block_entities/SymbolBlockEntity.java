package net.povstalec.sgjourney.common.block_entities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import net.povstalec.sgjourney.common.data.Universe;
import net.povstalec.sgjourney.common.init.BlockEntityInit;
import net.povstalec.sgjourney.common.init.PacketHandlerInit;
import net.povstalec.sgjourney.common.packets.ClientboundSymbolUpdatePacket;

public abstract class SymbolBlockEntity extends BlockEntity
{
	private static final String SYMBOL = "Symbol";
	private static final String EMPTY = "sgjourney:empty";
	
	public String symbol = EMPTY;
	
	public SymbolBlockEntity(BlockEntityType<?> entity, BlockPos pos, BlockState state) 
	{
		super(entity, pos, state);
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		if(level.isClientSide())
			return;
		
		if(symbol.equals(EMPTY))
			setSymbol(level);
	}
	
	@Override
    public void load(CompoundTag tag)
    {
    	super.load(tag);
    	
    	if(tag.contains(SYMBOL))
    		symbol = tag.getString(SYMBOL);
	}
	
	@Override
    protected void saveAdditional(@NotNull CompoundTag tag)
	{
		if(symbol != null)
			tag.putString(SYMBOL, symbol);
		
		super.saveAdditional(tag);
	}
	
	public void setSymbol(Level level)
	{
		if(level.isClientSide())
			return;
		
		symbol = Universe.get(level).getPointOfOrigin(level.dimension().location().toString());
	}
	
	public void tick(Level level, BlockPos pos, BlockState state)
	{
		if(level.isClientSide())
			return;
		PacketHandlerInit.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(this.worldPosition)), new ClientboundSymbolUpdatePacket(worldPosition, symbol));
	}
	
	public static class Stone extends SymbolBlockEntity
	{
		public Stone(BlockPos pos, BlockState state)
		{
			super(BlockEntityInit.STONE_SYMBOL.get(), pos, state);
		}
		
	}
	
	public static class Sandstone extends SymbolBlockEntity
	{
		public Sandstone(BlockPos pos, BlockState state)
		{
			super(BlockEntityInit.SANDSTONE_SYMBOL.get(), pos, state);
		}
		
	}

}
