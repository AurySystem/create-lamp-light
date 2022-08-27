package gay.aurum.creategaslamp.renderers;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;

import gay.aurum.creategaslamp.CreateGasLamp;
import gay.aurum.creategaslamp.blocks.SpringBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SpringTileInstance extends SingleRotatingInstance {

	private final SpringBlockEntity blockEntity = (SpringBlockEntity) super.blockEntity;

	public SpringTileInstance(MaterialManager modelManager, SpringBlockEntity tile) {
		super(modelManager, tile);
	}

	@Override
	public void init() {
		super.init();

	}

	@Override
	protected Instancer<RotatingData> getModel() {
		BlockState referenceState = blockEntity.getBlockState();
		Direction facing = referenceState.getValue(BlockStateProperties.FACING);
		return getRotatingMaterial().getModel(AllBlockPartials.MECHANICAL_PUMP_COG, facing);
	}

	@Override
	public void remove() {
		super.remove();

	}
}
