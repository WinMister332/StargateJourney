package net.povstalec.sgjourney.common.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.povstalec.sgjourney.StargateJourney;
import net.povstalec.sgjourney.common.blocks.SGJourneyBaseEntityBlock;
import net.povstalec.sgjourney.common.blocks.dhd.MilkyWayDHDBlock;
import net.povstalec.sgjourney.common.blocks.dhd.PegasusDHDBlock;
import net.povstalec.sgjourney.common.config.CommonStargateConfig;
import net.povstalec.sgjourney.common.items.VialItem;
import net.povstalec.sgjourney.common.items.SyringeItem;
import net.povstalec.sgjourney.common.items.armor.PersonalShieldItem;

@Mod.EventBusSubscriber(modid = StargateJourney.MODID)
public class TabInit
{
	@SubscribeEvent
	public static void onRegisterModTabs(final CreativeModeTabEvent.Register event)
	{
		event.registerCreativeModeTab(new ResourceLocation(StargateJourney.MODID, "stargate_items"), (builder) ->
		{
			builder.title(Component.translatable("itemGroup.stargate_items"))
			.icon(() -> new ItemStack(ItemInit.NAQUADAH.get()))
			.displayItems((flag, items, hasPermisions) ->
			{
				items.accept(ItemInit.RAW_NAQUADAH.get());
				items.accept(ItemInit.NAQUADAH_ALLOY.get());
				items.accept(ItemInit.PURE_NAQUADAH.get());
				items.accept(ItemInit.NAQUADAH.get());
				items.accept(ItemInit.LIQUID_NAQUADAH_BUCKET.get());
				//items.accept(ItemInit.LIQUID_NAQUADAH_BOTTLE.get());
				items.accept(VialItem.liquidNaquadahSetup());

				items.accept(ItemInit.NAQUADAH_ROD.get());
				items.accept(ItemInit.REACTION_CHAMBER.get());
				items.accept(ItemInit.PLASMA_CONVERTER.get());
				
				items.accept(ItemInit.PDA.get());
				
				items.accept(ItemInit.NAQUADAH_SWORD.get());
				items.accept(ItemInit.NAQUADAH_PICKAXE.get());
				items.accept(ItemInit.NAQUADAH_AXE.get());
				items.accept(ItemInit.NAQUADAH_SHOVEL.get());
				items.accept(ItemInit.NAQUADAH_HOE.get());
				
				items.accept(ItemInit.NAQUADAH_HELMET.get());
				items.accept(ItemInit.NAQUADAH_CHESTPLATE.get());
				items.accept(ItemInit.NAQUADAH_LEGGINGS.get());
				items.accept(ItemInit.NAQUADAH_BOOTS.get());
				
				items.accept(ItemInit.KARA_KESH.get());
				items.accept(ItemInit.RING_REMOTE.get());
				
				items.accept(ItemInit.MATOK.get());
				
				items.accept(ItemInit.JACKAL_HELMET.get());
				//items.accept(ItemInit.HORUS_HELMET.get());
				items.accept(ItemInit.JAFFA_HELMET.get());
				items.accept(ItemInit.JAFFA_CHESTPLATE.get());
				items.accept(ItemInit.JAFFA_LEGGINGS.get());
				items.accept(ItemInit.JAFFA_BOOTS.get());

				items.accept(ItemInit.ZPM.get());
				//items.accept(ItemInit.PERSONAL_SHIELD_EMITTER.get());
				items.accept(PersonalShieldItem.personalShieldSetup());

				items.accept(ItemInit.SYRINGE.get());
				items.accept(SyringeItem.addContents(SyringeItem.Contents.PROTOTYPE_ATA));
				items.accept(SyringeItem.addContents(SyringeItem.Contents.ATA));

				items.accept(ItemInit.CRYSTAL_BASE.get());
				
				items.accept(ItemInit.LARGE_CONTROL_CRYSTAL.get());
				items.accept(ItemInit.CONTROL_CRYSTAL.get());
				items.accept(ItemInit.ADVANCED_CONTROL_CRYSTAL.get());
				items.accept(ItemInit.MEMORY_CRYSTAL.get());
				items.accept(ItemInit.ADVANCED_MEMORY_CRYSTAL.get());
				//items.accept(MemoryCrystalItem.atlantisAddress());
				//items.accept(MemoryCrystalItem.abydosAddress());
				items.accept(ItemInit.MATERIALIZATION_CRYSTAL.get());
				items.accept(ItemInit.ADVANCED_MATERIALIZATION_CRYSTAL.get());
				items.accept(ItemInit.ENERGY_CRYSTAL.get());
				items.accept(ItemInit.ADVANCED_ENERGY_CRYSTAL.get());
				items.accept(ItemInit.COMMUNICATION_CRYSTAL.get());
				items.accept(ItemInit.ADVANCED_COMMUNICATION_CRYSTAL.get());
				
				if(CommonStargateConfig.enable_classic_stargate_upgrades.get())
				{
					items.accept(ItemInit.UNIVERSE_UPGRADE_CRYSTAL.get());
					items.accept(ItemInit.MILKY_WAY_UPGRADE_CRYSTAL.get());
					items.accept(ItemInit.PEGASUS_UPGRADE_CRYSTAL.get());
					items.accept(ItemInit.TOLLAN_UPGRADE_CRYSTAL.get());
				}
			});
		});
		
		event.registerCreativeModeTab(new ResourceLocation(StargateJourney.MODID, "stargate_blocks"), (builder) ->
		{
			builder.title(Component.translatable("itemGroup.stargate_blocks"))
			.icon(() -> new ItemStack(BlockInit.NAQUADAH_BLOCK.get()))
			.displayItems((flag, items, hasPermisions) ->
			{
				items.accept(BlockInit.UNIVERSE_STARGATE.get());
				items.accept(BlockInit.MILKY_WAY_STARGATE.get());
				//items.accept(BlockInit.MILKY_WAY_DHD.get());
				items.accept(MilkyWayDHDBlock.milkyWayCrystalSetup());
				items.accept(BlockInit.PEGASUS_STARGATE.get());
				//items.accept(BlockInit.PEGASUS_DHD.get());
				items.accept(PegasusDHDBlock.pegasusCrystalSetup());
				items.accept(BlockInit.CLASSIC_STARGATE.get());
				items.accept(BlockInit.CLASSIC_STARGATE_BASE_BLOCK.get());
				items.accept(BlockInit.CLASSIC_STARGATE_CHEVRON_BLOCK.get());
				items.accept(BlockInit.CLASSIC_STARGATE_RING_BLOCK.get());
				items.accept(BlockInit.CLASSIC_DHD.get());
				items.accept(BlockInit.TOLLAN_STARGATE.get());

				items.accept(BlockInit.TRANSPORT_RINGS.get());
				items.accept(BlockInit.RING_PANEL.get());
				
				items.accept(BlockInit.ARCHEOLOGY_TABLE.get());
				items.accept(BlockInit.GOLDEN_IDOL.get());

				items.accept(BlockInit.NAQUADAH_ORE.get());
				items.accept(BlockInit.DEEPSLATE_NAQUADAH_ORE.get());
				items.accept(BlockInit.NETHER_NAQUADAH_ORE.get());
				items.accept(BlockInit.RAW_NAQUADAH_BLOCK.get());
				
				items.accept(BlockInit.NAQUADAH_BLOCK.get());
				items.accept(BlockInit.NAQUADAH_STAIRS.get());
				items.accept(BlockInit.NAQUADAH_SLAB.get());
				items.accept(BlockInit.CUT_NAQUADAH_BLOCK.get());
				items.accept(BlockInit.CUT_NAQUADAH_STAIRS.get());
				items.accept(BlockInit.CUT_NAQUADAH_SLAB.get());
				
				items.accept(BlockInit.SANDSTONE_HIEROGLYPHS.get());
				items.accept(BlockInit.SANDSTONE_SWITCH.get());
				items.accept(BlockInit.SANDSTONE_WITH_LAPIS.get());
				items.accept(BlockInit.SANDSTONE_SYMBOL.get());
				items.accept(BlockInit.STONE_SYMBOL.get());
				
				items.accept(BlockInit.SANDSTONE_CARTOUCHE.get());
				items.accept(BlockInit.STONE_CARTOUCHE.get());
				
				items.accept(BlockInit.FIRE_PIT.get());

				items.accept(BlockInit.LIQUIDIZER.get());
				items.accept(BlockInit.CRYSTALLIZER.get());

				items.accept(BlockInit.NAQUADAH_GENERATOR_MARK_I.get());
				items.accept(BlockInit.NAQUADAH_GENERATOR_MARK_II.get());
				
				items.accept(BlockInit.BASIC_INTERFACE.get());
				items.accept(BlockInit.CRYSTAL_INTERFACE.get());

				items.accept(BlockInit.ANCIENT_GENE_DETECTOR.get());
				
				items.accept(BlockInit.ZPM_HUB.get());
			});
		});
	}
	
	@SubscribeEvent
	public static void addCreative(final CreativeModeTabEvent.BuildContents event)
	{
		if(event.getTab() == CreativeModeTabs.OP_BLOCKS && event.hasPermissions())
		{
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.UNIVERSE_STARGATE.get())));
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.MILKY_WAY_STARGATE.get())));
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.PEGASUS_STARGATE.get())));
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.CLASSIC_STARGATE.get())));
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.TOLLAN_STARGATE.get())));
			event.accept(SGJourneyBaseEntityBlock.excludeFromNetwork(new ItemStack(BlockInit.TRANSPORT_RINGS.get())));
		}
	}
}
