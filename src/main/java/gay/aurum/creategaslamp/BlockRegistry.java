package gay.aurum.creategaslamp;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticBlockModel;
import com.simibubi.create.content.contraptions.relays.elementary.CogwheelBlockItem;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.util.entry.BlockEntry;

import gay.aurum.creategaslamp.blocks.BrassCogwheels;
import gay.aurum.creategaslamp.blocks.EncasedBrassCogWheel;
import gay.aurum.creategaslamp.blocks.SpringBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;

import static com.simibubi.create.AllTags.axeOrPickaxe;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class BlockRegistry {
	private static final CreateRegistrate registrate = CreateGasLamp.registerer().creativeModeTab(()->CreateGasLamp.TAB);

	public static final BlockEntry<BrassCogwheels> COGWHEEL = registrate.block("brass_cogwheel", BrassCogwheels::small)
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
			registrate.block("large_brass_cogwheel", BrassCogwheels::large)
					.initialProperties(SharedProperties::softMetal)
					.properties(p -> p.sound(SoundType.METAL))
					.transform(axeOrPickaxe())
					.transform(BlockStressDefaults.setNoImpact())
					.blockstate(BlockStateGen.axisBlockProvider(false))
					.onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
					.item(CogwheelBlockItem::new)
					.build()
					.register();

	public static final BlockEntry<EncasedBrassCogWheel> ANDESITE_ENCASED_BRASS_COGWHEEL =
			registrate.block("andesite_encased_brass_cogwheel", p -> EncasedBrassCogWheel.andesite(false, p))
					.transform(BuilderTransformers.encasedCogwheel("andesite", () -> AllSpriteShifts.ANDESITE_CASING))
					.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
							Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE,
									AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
					.transform(axeOrPickaxe())
					.register();

	public static final BlockEntry<EncasedBrassCogWheel> BRASS_ENCASED_BRASS_COGWHEEL =
			registrate.block("brass_encased_brass_cogwheel", p -> EncasedBrassCogWheel.brass(false, p))
					.transform(BuilderTransformers.encasedCogwheel("brass", () -> AllSpriteShifts.BRASS_CASING))
					.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
							Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE,
									AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
					.transform(axeOrPickaxe())
					.register();

	public static final BlockEntry<EncasedBrassCogWheel> ANDESITE_ENCASED_LARGE_BRASS_COGWHEEL =
			registrate.block("andesite_encased_large_brass_cogwheel", p -> EncasedBrassCogWheel.andesite(true, p))
					.transform(BuilderTransformers.encasedLargeCogwheel("andesite", () -> AllSpriteShifts.ANDESITE_CASING))
					.transform(axeOrPickaxe())
					.register();

	public static final BlockEntry<EncasedBrassCogWheel> BRASS_ENCASED_LARGE_BRASS_COGWHEEL =
			registrate.block("brass_encased_large_brass_cogwheel", p -> EncasedBrassCogWheel.brass(true, p))
					.transform(BuilderTransformers.encasedLargeCogwheel("brass", () -> AllSpriteShifts.BRASS_CASING))
					.transform(axeOrPickaxe())
					.register();

	public static final BlockEntry<SpringBlock> WINDING_SPRING = registrate.block("winding_spring", SpringBlock::new)
			.initialProperties(SharedProperties::stone)
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.transform(axeOrPickaxe())
			.transform(BlockStressDefaults.setCapacity(16.0))
			.transform(BlockStressDefaults.setImpact(2.0))
			.item()
			.transform(customItemModel())
			.register();

	public static void init(){

	}
}
