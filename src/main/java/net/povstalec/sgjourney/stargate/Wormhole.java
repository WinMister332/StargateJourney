package net.povstalec.sgjourney.stargate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.povstalec.sgjourney.block_entities.stargate.AbstractStargateEntity;
import net.povstalec.sgjourney.config.CommonStargateConfig;
import net.povstalec.sgjourney.init.SoundInit;
import net.povstalec.sgjourney.misc.MatrixHelper;
import net.povstalec.sgjourney.misc.Orientation;

public class Wormhole implements ITeleporter
{
	
	Map<Integer, Vec3> entityLocations = new HashMap<Integer, Vec3>();
	List<Entity> localEntities = new ArrayList<Entity>();
	
	public Wormhole()
    {
    }
	
	public boolean hasCandidates()
	{
		return localEntities.isEmpty();
	}
	
	public boolean findCandidates(Level level, BlockPos centerPos, Direction direction)
	{
		AABB localBox = new AABB(
			(centerPos.getX() - 2), (centerPos.getY() - 2), (centerPos.getZ() - 2), 
			(centerPos.getX() + 3), (centerPos.getY() + 3), (centerPos.getZ() + 3));
		
		localEntities = level.getEntitiesOfClass(Entity.class, localBox);
		
		return !localEntities.isEmpty();
	}
	
	public void wormholeEntities(AbstractStargateEntity initialStargate, AbstractStargateEntity targetStargate, boolean canWormhole)
	{
		Direction direction = initialStargate.getDirection();
		Direction orientationDirection = Orientation.getEffectiveDirection(direction, initialStargate.getOrientation());
		Map<Integer, Vec3> entityLocations = new HashMap<Integer, Vec3>();
		localEntities.stream().forEach((traveler) ->
		{
			if(this.entityLocations.containsKey(traveler.getId()))
			{
				double previousX = this.entityLocations.get(traveler.getId()).x();
				double previousY = this.entityLocations.get(traveler.getId()).y();
				double previousZ = this.entityLocations.get(traveler.getId()).z();
				
				Vec3 momentum = new Vec3(traveler.getX() - previousX, traveler.getY() - previousY, traveler.getZ() - previousZ);

				int unitDistance;
				double previousTravelerPos;
				double travelerPos;
				double axisMomentum;
				
				if(orientationDirection.getAxis() == Direction.Axis.X)
				{
					unitDistance = initialStargate.getCenterPos().getX() - initialStargate.getCenterPos().relative(orientationDirection).getX();
					previousTravelerPos = initialStargate.getCenterPos().getX() + 0.5 - previousX;
					travelerPos = initialStargate.getCenterPos().getX() + 0.5 - traveler.getX();
					axisMomentum = momentum.x();
					//momentum = new Vec3(reverseIfNeeded(unitDistance > 0, axisMomentum), momentum.y(), momentum.z());
				}
				else if(orientationDirection.getAxis() == Direction.Axis.Z)
				{
					unitDistance = initialStargate.getCenterPos().getZ() - initialStargate.getCenterPos().relative(orientationDirection).getZ();
					previousTravelerPos = initialStargate.getCenterPos().getZ() + 0.5 - previousZ;
					travelerPos = initialStargate.getCenterPos().getZ() + 0.5 - traveler.getZ();
					axisMomentum = momentum.z();
					//momentum = new Vec3(momentum.x(), momentum.y(), reverseIfNeeded(unitDistance > 0, axisMomentum));
				}
				else
				{
					unitDistance = initialStargate.getCenterPos().getY() - initialStargate.getCenterPos().relative(orientationDirection).getY();
					previousTravelerPos = initialStargate.getCenterPos().getY() + 0.28125 - previousY;
					travelerPos = initialStargate.getCenterPos().getY() + 0.28125 - traveler.getY();
					axisMomentum = momentum.y();
					//momentum = new Vec3(momentum.x(), reverseIfNeeded(unitDistance > 0, axisMomentum), momentum.z());
				}
				
				if(shouldWormhole(unitDistance, previousTravelerPos, travelerPos, axisMomentum))
				{
					doWormhole(initialStargate, targetStargate, traveler, momentum, canWormhole);
				}
				else
					entityLocations.put(traveler.getId(), new Vec3(traveler.getX(), traveler.getY(), traveler.getZ()));
				
			}
			else
				entityLocations.put(traveler.getId(), new Vec3(traveler.getX(), traveler.getY(), traveler.getZ()));
		});
		
		this.entityLocations = entityLocations;
	}
	
	public boolean shouldWormhole(int unitDistance, double previousTravelerPos, double travelerPos, double axisMomentum)
	{
		previousTravelerPos = reverseIfNeeded(unitDistance > 0, previousTravelerPos);
		travelerPos = reverseIfNeeded(unitDistance > 0, travelerPos);
		axisMomentum = reverseIfNeeded(unitDistance > 0, axisMomentum);
		
		if(previousTravelerPos < 0 && travelerPos >= 0 && axisMomentum < 0)
			return true;
		
		return false;
	}
	
	public double reverseIfNeeded(boolean shouldReverse, double number)
	{
		return shouldReverse ? -number : number;
	}
    
    public void doWormhole(AbstractStargateEntity initialStargate, AbstractStargateEntity targetStargate, Entity traveler, Vec3 momentum, boolean canWormhole)
    {
		Level level = traveler.getLevel();
		playWormholeSound(level, traveler);
		
		if(level.isClientSide())
			return;
		
		if(canWormhole)
		{
			ServerLevel destinationlevel = (ServerLevel) targetStargate.getLevel();
	        
	        if (destinationlevel == null)
	        {
	        	System.out.println("Dimension is null");
	            return;
	        }
	        
	        if(targetStargate != null)
	        {
		        Direction initialDirection = initialStargate.getDirection();
		        Orientation initialOrientation = initialStargate.getOrientation();
	        	Direction destinationDirection = targetStargate.getDirection();
		        Orientation destinationOrientation = targetStargate.getOrientation();
	    		Vec3 position = preserveRelative(initialDirection, initialOrientation, destinationDirection, destinationOrientation, new Vec3(traveler.getX() - (initialStargate.getCenterPos().getX() + 0.5), traveler.getY() - initialStargate.getCenterPos().getY(), traveler.getZ() - (initialStargate.getCenterPos().getZ() + 0.5)));
	    		
	    		if(traveler instanceof ServerPlayer player)
		    	{
		        	player.teleportTo(destinationlevel, targetStargate.getCenterPos().getX() + 0.5 + position.x(), targetStargate.getCenterPos().getY() + 0.5 + position.y(), targetStargate.getCenterPos().getZ() + 0.5 + position.z(), preserveYRot(initialDirection, destinationDirection, player.getYRot()), player.getXRot());
		        	player.setDeltaMovement(preserveRelative(initialDirection, initialOrientation, destinationDirection, destinationOrientation, momentum));
		        	player.connection.send(new ClientboundSetEntityMotionPacket(traveler));
		    		playWormholeSound(level, player);
		    	}
		    	else
		    	{
		    		Entity newTraveler = traveler;
		    		if((ServerLevel) level != destinationlevel)
		    			newTraveler = traveler.changeDimension(destinationlevel, this);
		    		
		    		newTraveler.moveTo(targetStargate.getCenterPos().getX() + 0.5 + position.x(), targetStargate.getCenterPos().getY() + 0.5 + position.y(), targetStargate.getCenterPos().getZ() + 0.5 + position.z(), preserveYRot(initialDirection, destinationDirection, traveler.getYRot()), traveler.getXRot());
		    		newTraveler.setDeltaMovement(preserveRelative(initialDirection, initialOrientation, destinationDirection, destinationOrientation, momentum));
		    		playWormholeSound(level, newTraveler);
		    	}
	        }
		}
		else if(CommonStargateConfig.reverse_wormhole_kills.get())
		{
			if(traveler instanceof Player player && player.isCreative())
				player.displayClientMessage(Component.translatable("block.sgjourney.stargate.one_way_wormhole"), true);
			else
				traveler.kill();
		}
    }
    
    private static Vec3 preserveRelative(Direction initialDirection, Orientation initialOrientation, Direction destinationDirection, Orientation destinationOrientation, Vec3 initial)
    {
    	return MatrixHelper.rotateVector(initial, initialDirection, initialOrientation, destinationDirection, destinationOrientation);
    }
	
	private static float preserveYRot(Direction initialDirection, Direction destinationDirection, float yRot)
	{
		float initialStargateDirection = Mth.wrapDegrees(initialDirection.toYRot());
    	float destinationStargateDirection = Mth.wrapDegrees(destinationDirection.toYRot());
    	
    	float relativeRot = destinationStargateDirection - initialStargateDirection;
    	
    	yRot = yRot + relativeRot + 180;
    	
    	return yRot;
	}
	
	private static void playWormholeSound(Level level, Entity traveler)
	{
		level.playSound((Player)null, traveler.blockPosition(), SoundInit.WORMHOLE_ENTER.get(), SoundSource.BLOCKS, 0.25F, 1F);
	}
}