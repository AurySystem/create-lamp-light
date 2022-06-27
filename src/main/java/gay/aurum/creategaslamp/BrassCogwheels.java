package gay.aurum.creategaslamp;

import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.relays.elementary.CogWheelBlock;

import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
		return super.use(state, world, pos, player, hand, ray);
	}


	@Override
	public BlockEntityType<? extends SimpleKineticTileEntity> getTileEntityType() {
		return CreateGasLamp.BRACKETED_KINETIC.get();
	}
}
