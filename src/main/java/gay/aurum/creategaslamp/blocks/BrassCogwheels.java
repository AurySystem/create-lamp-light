package gay.aurum.creategaslamp.blocks;

import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.CogWheelBlock;

import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;

import com.simibubi.create.content.contraptions.relays.encased.EncasedCogwheelBlock;
import com.simibubi.create.foundation.utility.Iterate;

import gay.aurum.creategaslamp.BlockEntityRegistry;
import gay.aurum.creategaslamp.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BrassCogwheels extends CogWheelBlock {
	protected BrassCogwheels(boolean large, Properties properties) {
		super(large, properties);
	}

	public static BrassCogwheels small(Properties properties) {
		return new BrassCogwheels(false, properties);
	}

	public static BrassCogwheels large(Properties properties) {
		return new BrassCogwheels(true, properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
		if (player.isShiftKeyDown() || !player.mayBuild())
			return InteractionResult.PASS;

		ItemStack heldItem = player.getItemInHand(hand);
		EncasedCogwheelBlock[] encasedBlocks = isLargeCog()
				? new EncasedCogwheelBlock[] { BlockRegistry.ANDESITE_ENCASED_LARGE_BRASS_COGWHEEL.get(),
				BlockRegistry.BRASS_ENCASED_LARGE_BRASS_COGWHEEL.get() }
				: new EncasedCogwheelBlock[] { BlockRegistry.ANDESITE_ENCASED_BRASS_COGWHEEL.get(),
				BlockRegistry.BRASS_ENCASED_BRASS_COGWHEEL.get() };

		for (EncasedCogwheelBlock encasedCog : encasedBlocks) {
			if (!encasedCog.getCasing()
					.isIn(heldItem))
				continue;

			if (world.isClientSide)
				return InteractionResult.SUCCESS;

			BlockState encasedState = encasedCog.defaultBlockState()
					.setValue(AXIS, state.getValue(AXIS));

			for (Direction d : Iterate.directionsInAxis(state.getValue(AXIS))) {
				BlockState adjacentState = world.getBlockState(pos.relative(d));
				if (!(adjacentState.getBlock() instanceof IRotate))
					continue;
				IRotate def = (IRotate) adjacentState.getBlock();
				if (!def.hasShaftTowards(world, pos.relative(d), adjacentState, d.getOpposite()))
					continue;
				encasedState =
						encasedState.cycle(d.getAxisDirection() == Direction.AxisDirection.POSITIVE ? EncasedCogwheelBlock.TOP_SHAFT
								: EncasedCogwheelBlock.BOTTOM_SHAFT);
			}

			KineticTileEntity.switchToBlockState(world, pos, encasedState);
			return InteractionResult.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, ray);
	}


	@Override
	public BlockEntityType<? extends SimpleKineticTileEntity> getTileEntityType() {
		return BlockEntityRegistry.BRACKETED_KINETIC.get();
	}
}
