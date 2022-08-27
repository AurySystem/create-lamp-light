package gay.aurum.creategaslamp.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.CasingBlock;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.schematics.ItemRequirement;
import com.tterrag.registrate.util.entry.BlockEntry;

import gay.aurum.creategaslamp.BlockEntityRegistry;
import gay.aurum.creategaslamp.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import org.jetbrains.annotations.Nullable;

public class EncasedBrassCogWheel extends EncasedCogwheelBlock {
	boolean isLarge;

	public static EncasedBrassCogWheel andesite(boolean large, Properties properties) {
		return new EncasedBrassCogWheel(large, properties, AllBlocks.ANDESITE_CASING);
	}

	public static EncasedBrassCogWheel brass(boolean large, Properties properties) {
		return new EncasedBrassCogWheel(large, properties, AllBlocks.BRASS_CASING);
	}
	public EncasedBrassCogWheel(boolean large, Properties properties, BlockEntry<CasingBlock> casing) {
		super(large, properties, casing);
		isLarge = large;
	}

	@Override
	public ItemStack getPickedStack(BlockState state, BlockGetter view, BlockPos pos, @Nullable Player player, @Nullable HitResult target) {
		if (target instanceof BlockHitResult)
			return ((BlockHitResult) target).getDirection()
					.getAxis() != getRotationAxis(state)
					? isLarge ? BlockRegistry.LARGE_COGWHEEL.asStack() : BlockRegistry.COGWHEEL.asStack()
					: getCasing().asStack();
		return ItemStack.EMPTY;
	}

	@Override
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		if (context.getLevel().isClientSide)
			return InteractionResult.SUCCESS;
		context.getLevel()
				.levelEvent(2001, context.getClickedPos(), Block.getId(state));
		KineticTileEntity.switchToBlockState(context.getLevel(), context.getClickedPos(),
				(isLarge ? BlockRegistry.LARGE_COGWHEEL : BlockRegistry.COGWHEEL).getDefaultState()
						.setValue(AXIS, state.getValue(AXIS)));
		return InteractionResult.SUCCESS;
	}

	@Override
	public ItemRequirement getRequiredItems(BlockState state, BlockEntity te) {
		return ItemRequirement
				.of(isLarge ? BlockRegistry.LARGE_COGWHEEL.getDefaultState() : BlockRegistry.COGWHEEL.getDefaultState(), te);
	}

	@Override
	public BlockEntityType<? extends SimpleKineticTileEntity> getTileEntityType() {
		return this.isLarge ? BlockEntityRegistry.ENCASED_LARGE_BRASS_COGWHEEL.get() : BlockEntityRegistry.ENCASED_BRASS_COGWHEEL.get();
	}
}
