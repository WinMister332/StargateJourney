package net.povstalec.sgjourney.client.render.level;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.povstalec.sgjourney.common.config.ClientSkyConfig;
import net.povstalec.stellarview.api.StellarViewSpecialEffects;
import net.povstalec.stellarview.api.celestials.CelestialObject;
import net.povstalec.stellarview.api.celestials.Galaxy;
import net.povstalec.stellarview.api.celestials.Galaxy.SpiralGalaxy;
import net.povstalec.stellarview.api.celestials.Supernova;
import net.povstalec.stellarview.api.celestials.orbiting.BlackHole;
import net.povstalec.stellarview.api.celestials.orbiting.Moon;
import net.povstalec.stellarview.api.celestials.orbiting.Planet;
import net.povstalec.stellarview.api.celestials.orbiting.Sun;
import net.povstalec.stellarview.client.render.level.StellarViewSky;

public class StellarViewRendering
{
	public static class StellarViewAbydosEffects extends StellarViewSpecialEffects
	{
		private static final Moon EDFU = new Moon.DefaultMoon(15F);
		private static final Moon DJA_NET = new Moon.DefaultMoon(20F);
		private static final Moon NUTURO = new Moon.DefaultMoon(30F);
		
		private static final Planet ABYDOS = (Planet) new Planet(Planet.EARTH_TEXTURE, 30, Planet.EARTH_DAY_LENGTH)
				//TODO Add these
				//.addAtmosphere(new Planet.Atmosphere(
				//				(ShootingStar) new ShootingStar().setDefaultRarity(10),
				//				(MeteorShower) new MeteorShower().setDefaultRarity(10)))
				.addOrbitingObject(EDFU, 384400F, 360F / 8, (float) Math.toRadians(45), 0, 0)//TODO Add rotations
				.addOrbitingObject(DJA_NET, 384400F, 360F / 8, (float) Math.toRadians(35), (float) Math.toRadians(75), 0)
				.addOrbitingObject(NUTURO, 384400F, 360F / 8, (float) Math.toRadians(40), (float) Math.toRadians(-55), 0);
		
		private static final Sun ABYDOS_SUN = (Sun) new Sun.DefaultSun(50F)
				.addOrbitingObject(ABYDOS, 147280000, 360F / 96, 0, 0, 0);
		
		private static final SpiralGalaxy MILKY_WAY = (SpiralGalaxy) new Galaxy.SpiralGalaxy(100, 10842L, (byte) 4, (short) 1500)
				.addGalacticObject(new Supernova(10.0F, 15 * CelestialObject.TICKS_PER_DAY + 18000, 5 * CelestialObject.TICKS_PER_DAY), 10, -3, 2)
				.addGalacticObject(ABYDOS_SUN, 1, 8, 17, 0, 0, 0);
		
		public StellarViewAbydosEffects()
		{
			super(new StellarViewSky(ABYDOS),
					192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
			MILKY_WAY.setStarBuffer(1, 8, 17, 0, 0, 0);
		}
		
		@Override
		public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	    {
			if(ClientSkyConfig.custom_abydos_sky.get())
				super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
			
	        return ClientSkyConfig.custom_abydos_sky.get();
	    }
	}
	
	public static class StellarViewChulakEffects extends StellarViewSpecialEffects
	{
		private static final Moon JERBAAS = new Moon.DefaultMoon(25F);
		
		private static final Planet CHULAK = (Planet) new Planet(Planet.EARTH_TEXTURE, 30, Planet.EARTH_DAY_LENGTH)
				//TODO Add these
				//.addAtmosphere(new Planet.Atmosphere(
				//				(ShootingStar) new ShootingStar().setDefaultRarity(10),
				//				(MeteorShower) new MeteorShower().setDefaultRarity(10)))
				.addOrbitingObject(JERBAAS, 384400F, 360F / 8, (float) Math.toRadians(45), (float) Math.toRadians(63), 0);//TODO Add rotation
		
		private static final Sun CHULAK_SUN = (Sun) new Sun.VanillaSun()
				.addOrbitingObject(CHULAK, 147280000, 360F / 96, 0, 0, 0);
		
		private static final SpiralGalaxy MILKY_WAY = (SpiralGalaxy) new Galaxy.SpiralGalaxy(100, 10842L, (byte) 4, (short) 1500)
				.addGalacticObject(new Supernova(10.0F, 15 * CelestialObject.TICKS_PER_DAY + 18000, 5 * CelestialObject.TICKS_PER_DAY), 10, -3, 2)
				.addGalacticObject(CHULAK_SUN, 6, -2, 8, 0, (float) (0.2 * Math.PI), (float) (0.6 * Math.PI));
		
		public StellarViewChulakEffects()
		{
			super(new StellarViewSky(CHULAK),
					192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
			MILKY_WAY.setStarBuffer(6, -2, 8, 0, (float) (0.2 * Math.PI), (float) (0.6 * Math.PI));
		}
		
		@Override
		public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	    {
			if(ClientSkyConfig.custom_chulak_sky.get())
				super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
			
	        return ClientSkyConfig.custom_chulak_sky.get();
	    }
	}
	
	public static class StellarViewCavumTenebraeEffects extends StellarViewSpecialEffects
	{
		private static final Planet CAVUM_TENBRAE = (Planet) new Planet(Planet.EARTH_TEXTURE, 30, Planet.EARTH_DAY_LENGTH);
				//TODO Add these
				//.addAtmosphere(new Planet.Atmosphere(
				//				(ShootingStar) new ShootingStar().setDefaultRarity(10),
				//				(MeteorShower) new MeteorShower().setDefaultRarity(10)))

		private static final BlackHole P3W_451_BLACK_HOLE = (BlackHole) new BlackHole.DefaultBlackHole(70F)
				.addOrbitingObject(CAVUM_TENBRAE, 147280000, 360F / 96, 0, 0, 0);
		
		private static final SpiralGalaxy MILKY_WAY = (SpiralGalaxy) new Galaxy.SpiralGalaxy(100, 10842L, (byte) 4, (short) 1500)
				.addGalacticObject(new Supernova(10.0F, 15 * CelestialObject.TICKS_PER_DAY + 18000, 5 * CelestialObject.TICKS_PER_DAY), 10, -3, 2)
				.addGalacticObject(P3W_451_BLACK_HOLE, 3, 3, 3, 0, (float) (0.3 * Math.PI), (float) (0.8 * Math.PI));
		
		public StellarViewCavumTenebraeEffects()
		{
			super(new StellarViewSky(CAVUM_TENBRAE),
					192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
			MILKY_WAY.setStarBuffer(3, 3, 3, 0, (float) (0.3 * Math.PI), (float) (0.8 * Math.PI));
		}
		
		@Override
		public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	    {
			if(ClientSkyConfig.custom_cavum_tenebrae_sky.get())
				super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
			
	        return ClientSkyConfig.custom_cavum_tenebrae_sky.get();
	    }
	}
	
	public static class StellarViewLanteaEffects extends StellarViewSpecialEffects
	{
		private static final Moon LANTEAN_MOON = new Moon.DefaultMoon(25F);
		
		private static final Planet LANTEA = (Planet) new Planet(Planet.EARTH_TEXTURE, 30, Planet.EARTH_DAY_LENGTH)
				//TODO Add these
				//.addAtmosphere(new Planet.Atmosphere(
				//				(ShootingStar) new ShootingStar().setDefaultRarity(10),
				//				(MeteorShower) new MeteorShower().setDefaultRarity(10)))
				.addOrbitingObject(LANTEAN_MOON, 384400F, 360F / 8, (float) Math.toRadians(45), (float) Math.toRadians(12), 0);//TODO Add rotation
		
		private static final Sun LANTEAN_SUN = (Sun) new Sun.VanillaSun()
				.addOrbitingObject(LANTEA, 147280000, 360F / 96, 0, 0, 0);
		
		private static final SpiralGalaxy PEGASUS = (SpiralGalaxy) new Galaxy.SpiralGalaxy(100, 17892L, (byte) 2, (short) 2250)
				.addGalacticObject(new Supernova(10.0F, 15 * CelestialObject.TICKS_PER_DAY + 18000, 5 * CelestialObject.TICKS_PER_DAY), 10, -3, 2)
				.addGalacticObject(LANTEAN_SUN, 8, 0, 16, (float) (0.35 * Math.PI), (float) (0.35 * Math.PI), (float) (0.15 * Math.PI));
		
		public StellarViewLanteaEffects()
		{
			super(new StellarViewSky(LANTEA),
					192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
			PEGASUS.setStarBuffer(8, 0, 16, 0, (float) (0.2 * Math.PI), (float) (0.6 * Math.PI));
		}
		
		@Override
		public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	    {
			if(ClientSkyConfig.custom_lantea_sky.get())
				super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
			
	        return ClientSkyConfig.custom_lantea_sky.get();
	    }
	}
	
	public static class StellarViewAthosEffects extends StellarViewSpecialEffects
	{
		private static final Moon ATHOS_MOON = new Moon.DefaultMoon(25F);
		
		private static final Planet ATHOS = (Planet) new Planet(Planet.EARTH_TEXTURE, 30, Planet.EARTH_DAY_LENGTH)
				//TODO Add these
				//.addAtmosphere(new Planet.Atmosphere(
				//				(ShootingStar) new ShootingStar().setDefaultRarity(10),
				//				(MeteorShower) new MeteorShower().setDefaultRarity(10)))
				.addOrbitingObject(ATHOS_MOON, 384400F, 360F / 8, (float) Math.toRadians(45), (float) Math.toRadians(12), 0);//TODO Add rotation
		
		private static final Sun ATHOS_SUN = (Sun) new Sun.VanillaSun()
				.addOrbitingObject(ATHOS, 147280000, 360F / 96, 0, 0, 0);
		
		private static final SpiralGalaxy PEGASUS = (SpiralGalaxy) new Galaxy.SpiralGalaxy(100, 17892L, (byte) 2, (short) 2250)
				.addGalacticObject(new Supernova(10.0F, 15 * CelestialObject.TICKS_PER_DAY + 18000, 5 * CelestialObject.TICKS_PER_DAY), 10, -3, 2)
				.addGalacticObject(ATHOS_SUN, 7, 5, 14, (float) (0.35 * Math.PI), (float) (0.35 * Math.PI), (float) (0.15 * Math.PI));
		
		public StellarViewAthosEffects()
		{
			super(new StellarViewSky(ATHOS),
					192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
			PEGASUS.setStarBuffer(7, 5, 14, (float) (0.5 * Math.PI), (float) (0.14 * Math.PI), (float) (1.2 * Math.PI));
		}
		
		@Override
		public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	    {
			if(ClientSkyConfig.custom_athos_sky.get())
				super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
			
	        return ClientSkyConfig.custom_athos_sky.get();
	    }
	}
	
	public static void registerStellarViewEffects(RegisterDimensionSpecialEffectsEvent event)
	{
		event.register(SGJourneyDimensionSpecialEffects.ABYDOS_EFFECTS, new StellarViewAbydosEffects());
		event.register(SGJourneyDimensionSpecialEffects.CHULAK_EFFECTS, new StellarViewChulakEffects());
		event.register(SGJourneyDimensionSpecialEffects.CAVUM_TENEBRAE_EFFECTS, new StellarViewCavumTenebraeEffects());
		event.register(SGJourneyDimensionSpecialEffects.LANTEA_EFFECTS, new StellarViewLanteaEffects());
		event.register(SGJourneyDimensionSpecialEffects.ATHOS_EFFECTS, new StellarViewAthosEffects());
	}
}
