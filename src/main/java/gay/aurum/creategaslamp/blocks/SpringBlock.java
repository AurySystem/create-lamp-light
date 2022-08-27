package gay.aurum.creategaslamp.blocks;


import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.block.ITE;

import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;

import gay.aurum.creategaslamp.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SpringBlock extends DirectionalKineticBlock implements ITE<SpringBlockEntity>, ICogWheel {
	public SpringBlock(Properties properties) {
		super(properties);
	}


	@Override
	public boolean showCapacityWithAnnotation() {
		return true;
	}

	@Override
	public Class<SpringBlockEntity> getTileEntityClass() {
		return SpringBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends SpringBlockEntity> getTileEntityType() {
		return BlockEntityRegistry.WINDING_SPRING.get();
	}

	@Override
	public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
		blockUpdate(context.getLevel(), context.getClickedPos());
		return newState;
	}

	protected void blockUpdate(LevelAccessor worldIn, BlockPos pos) {
		if (worldIn instanceof WrappedWorld)
			return;
		if (worldIn.isClientSide())
			return;
		withTileEntityDo(worldIn, pos, SpringBlockEntity::queueGeneratorUpdate);
	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING).getAxis();
	}
	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.getValue(FACING)
				.getOpposite();
	}

}
