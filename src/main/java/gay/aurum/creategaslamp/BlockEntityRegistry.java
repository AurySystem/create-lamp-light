package gay.aurum.creategaslamp;

import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import gay.aurum.creategaslamp.blocks.SpringBlockEntity;
import gay.aurum.creategaslamp.renderers.BrassEncassedCogsOveride;
import gay.aurum.creategaslamp.renderers.BrassKineticeTileOveride;
import gay.aurum.creategaslamp.renderers.SpringTileInstance;
import gay.aurum.creategaslamp.renderers.SpringTileRenderer;

public class BlockEntityRegistry {

	public static final BlockEntityEntry<BracketedKineticTileEntity> BRACKETED_KINETIC = CreateGasLamp.registerer()
			.tileEntity("brass_kinetic", BracketedKineticTileEntity::new)
			.instance(() -> BrassKineticeTileOveride.BrassKineticeTileInstance::new, false)
			.validBlocks(BlockRegistry.COGWHEEL, BlockRegistry.LARGE_COGWHEEL)
			.renderer(() -> BrassKineticeTileOveride.BrassKineticeTileRenderer::new)
			.register();
	public static final BlockEntityEntry<SimpleKineticTileEntity> ENCASED_BRASS_COGWHEEL = CreateGasLamp.registerer()
			.tileEntity("encased_brass_cogwheel", SimpleKineticTileEntity::new)
			.instance(() -> BrassEncassedCogsOveride.EncasedBrassCogInstance::small, false)
			.validBlocks(BlockRegistry.ANDESITE_ENCASED_BRASS_COGWHEEL, BlockRegistry.BRASS_ENCASED_BRASS_COGWHEEL)
			.renderer(() -> BrassEncassedCogsOveride.EncasedBrassCogRenderer::small)
			.register();
	public static final BlockEntityEntry<SimpleKineticTileEntity> ENCASED_LARGE_BRASS_COGWHEEL = CreateGasLamp.registerer()
			.tileEntity("encased_large_brass_cogwheel", SimpleKineticTileEntity::new)
			.instance(() -> BrassEncassedCogsOveride.EncasedBrassCogInstance::large, false)
			.validBlocks(BlockRegistry.ANDESITE_ENCASED_LARGE_BRASS_COGWHEEL, BlockRegistry.BRASS_ENCASED_LARGE_BRASS_COGWHEEL)
			.renderer(() -> BrassEncassedCogsOveride.EncasedBrassCogRenderer::large)
			.register();

	public static final BlockEntityEntry<SpringBlockEntity> WINDING_SPRING = CreateGasLamp.registerer()
			.tileEntity("winding_spring", SpringBlockEntity::new)
			.instance(() -> SpringTileInstance::new)
			.validBlocks(BlockRegistry.WINDING_SPRING)
			.renderer(() -> SpringTileRenderer::new)
			.register();

	public static void init(){

	}
}
