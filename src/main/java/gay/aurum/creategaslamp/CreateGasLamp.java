package gay.aurum.creategaslamp;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;

import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticBlockModel;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileInstance;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileRenderer;
import com.simibubi.create.content.contraptions.relays.elementary.CogwheelBlockItem;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.Registrate;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.SoundType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.simibubi.create.AllTags.axeOrPickaxe;

public class CreateGasLamp implements ModInitializer {
	public static final String ID = "creategaslamp";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final CreativeModeTab TAB = FabricItemGroupBuilder.create(id("createlamp")).icon(AllBlocks.BRASS_ENCASED_LARGE_COGWHEEL::asStack).build();

	private static final NonNullSupplier<CreateRegistrate> REGISTRATE = CreateRegistrate.lazy(ID);

	public static final BlockEntry<BrassCogwheels> COGWHEEL = registrer().creativeModeTab(()->TAB).block("brass_cogwheel", BrassCogwheels::small)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.sound(SoundType.METAL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.axisBlockProvider(false))
			.onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
			.item(CogwheelBlockItem::new)
			.build()
			.register();

	public static final BlockEntry<BrassCogwheels> LARGE_COGWHEEL =
			registrer().block("large_brass_cogwheel", BrassCogwheels::large)
					.initialProperties(SharedProperties::softMetal)
					.properties(p -> p.sound(SoundType.METAL))
					.transform(axeOrPickaxe())
					.transform(BlockStressDefaults.setNoImpact())
					.blockstate(BlockStateGen.axisBlockProvider(false))
					.onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
					.item(CogwheelBlockItem::new)
					.build()
					.register();

	public static final BlockEntityEntry<BracketedKineticTileEntity> BRACKETED_KINETIC = registrer()
			.tileEntity("brass_kinetic", BracketedKineticTileEntity::new)
			.instance(() -> BracketedKineticTileInstance::new, false)
			.validBlocks(COGWHEEL, LARGE_COGWHEEL)
			.renderer(() -> BracketedKineticTileRenderer::new)
			.register();

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] loading alongside Create [{}]!", ID, Create.VERSION);
		registrer().register();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static CreateRegistrate registrer() {
		return REGISTRATE.get();
	}
}
