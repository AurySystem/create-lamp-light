package gay.aurum.creategaslamp.renderers;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileInstance;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileRenderer;

import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

import gay.aurum.creategaslamp.BlockRegistry;
import gay.aurum.creategaslamp.CreateGasLamp;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BrassKineticeTileOveride {

	public static class BrassKineticeTileRenderer extends BracketedKineticTileRenderer{
		public BrassKineticeTileRenderer(BlockEntityRendererProvider.Context context) {
			super(context);
		}

		@Override
		protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer,
								  int light, int overlay) {

			if (Backend.canUseInstancing(te.getLevel()))
				return;

			if (!BlockRegistry.LARGE_COGWHEEL.has(te.getBlockState())) {
				super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
				return;
			}

			// apply the same code as before but this time change the shaftless model to one with the texture overridden

			Direction.Axis axis = getRotationAxisOf(te);
			BlockPos pos = te.getBlockPos();

			Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
			renderRotatingBuffer(te,
					CachedBufferer.partialFacingVertical(CreateGasLamp.SHAFTLESS_LARGE_COGWHEEL, te.getBlockState(), facing),
					ms, buffer.getBuffer(RenderType.solid()), light);

			float offset = getShaftAngleOffset(axis, pos);
			float time = AnimationTickHolder.getRenderTime(te.getLevel());
			float angle = ((time * te.getSpeed() * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;

			SuperByteBuffer shaft =
					CachedBufferer.partialFacingVertical(AllBlockPartials.COGWHEEL_SHAFT, te.getBlockState(), facing);
			kineticRotationTransform(shaft, te, axis, angle, light);
			shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));

		}
	}
	public static class BrassKineticeTileInstance extends BracketedKineticTileInstance{

		public BrassKineticeTileInstance(MaterialManager modelManager, KineticTileEntity tile) {
			super(modelManager, tile);
		}

		@Override
		protected Instancer<RotatingData> getModel() {
			if (!ICogWheel.isLargeCog(blockEntity.getBlockState()))
				return super.getModel();

			Direction.Axis axis = KineticTileEntityRenderer.getRotationAxisOf(blockEntity);
			Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
			return getRotatingMaterial().getModel(CreateGasLamp.SHAFTLESS_LARGE_COGWHEEL, facing,
					() -> this.rotateToAxis(axis));
		}

		private PoseStack rotateToAxis(Direction.Axis axis) {
			Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
			PoseStack poseStack = new PoseStack();
			TransformStack.cast(poseStack)
					.centre()
					.rotateToFace(facing)
					.multiply(Vector3f.XN.rotationDegrees(-90))
					.unCentre();
			return poseStack;
		}
	}
}
