package gay.aurum.creategaslamp.renderers;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogInstance;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogRenderer;

import com.simibubi.create.content.contraptions.relays.encased.EncasedCogwheelBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import gay.aurum.creategaslamp.CreateGasLamp;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BrassEncassedCogsOveride {
	public static class EncasedBrassCogRenderer extends EncasedCogRenderer {

		private final boolean large;

		public static EncasedBrassCogRenderer small(BlockEntityRendererProvider.Context context) {
			return new EncasedBrassCogRenderer(context, false);
		}

		public static EncasedBrassCogRenderer large(BlockEntityRendererProvider.Context context) {
			return new EncasedBrassCogRenderer(context, true);
		}

		public EncasedBrassCogRenderer(BlockEntityRendererProvider.Context context, boolean large) {
			super(context, large);
			this.large = large;
		}

		@Override
		protected SuperByteBuffer getRotatedModel(KineticTileEntity te, BlockState state) {
			return CachedBufferer.partialFacingVertical(
					large ? CreateGasLamp.SHAFTLESS_LARGE_COGWHEEL : CreateGasLamp.SHAFTLESS_COGWHEEL, state,
					Direction.fromAxisAndDirection(state.getValue(EncasedCogwheelBlock.AXIS), Direction.AxisDirection.POSITIVE));
		}
	}

	public static class EncasedBrassCogInstance extends EncasedCogInstance {

		private final boolean large;
		public static EncasedBrassCogInstance small(MaterialManager modelManager, KineticTileEntity tile) {
			return new EncasedBrassCogInstance(modelManager, tile, false);
		}

		public static EncasedBrassCogInstance large(MaterialManager modelManager, KineticTileEntity tile) {
			return new EncasedBrassCogInstance(modelManager, tile, true);
		}
		public EncasedBrassCogInstance(MaterialManager modelManager, KineticTileEntity tile, boolean large) {
			super(modelManager, tile, large);
			this.large = large;
		}

		protected Instancer<RotatingData> getCogModel() {
			BlockState referenceState = blockEntity.getBlockState();
			Direction facing =
					Direction.fromAxisAndDirection(referenceState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
			PartialModel partial = large ? CreateGasLamp.SHAFTLESS_LARGE_COGWHEEL : CreateGasLamp.SHAFTLESS_COGWHEEL;

			return getRotatingMaterial().getModel(partial, facing, () -> {
				PoseStack poseStack = new PoseStack();
				TransformStack.cast(poseStack)
						.centre()
						.rotateToFace(facing)
						.multiply(Vector3f.XN.rotationDegrees(90))
						.unCentre();
				return poseStack;
			});
		}
	}
}
