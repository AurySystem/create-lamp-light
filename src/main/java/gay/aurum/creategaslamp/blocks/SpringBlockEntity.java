package gay.aurum.creategaslamp.blocks;

import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;

import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.block.BlockStressValues;
import com.simibubi.create.foundation.config.AllConfigs;

import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SpringBlockEntity extends GeneratingKineticTileEntity {

	protected boolean isGenerator;
	protected boolean updateGenerator;
	protected float storedStress;
	protected static float maxStress = 400000; //temporary until we have a config setup

	public SpringBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		isGenerator = false;
		updateGenerator = false;
		storedStress = 0;
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		if (!wasMoved)
			isGenerator = compound.getBoolean("Generating");
			storedStress = compound.getFloat("Stored");
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		compound.putBoolean("Generating", isGenerator);
		compound.putFloat("Stored", storedStress);
		super.write(compound, clientPacket);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);

		tooltip.add(componentSpacing.plainCopy().append(Lang.translate("gui.goggles.generator_stats")));
		//tooltip.add(componentSpacing.plainCopy().append(Lang.translate("tooltip.capacityProvided").withStyle(ChatFormatting.GRAY)));

		tooltip.add(
				componentSpacing.plainCopy()
						.append(new TextComponent(" " + IHaveGoggleInformation.format(storedStress))
								.append(Lang.translate("generic.unit.stress"))
								.withStyle(ChatFormatting.AQUA))
						.append(" stored ")
						.append(Lang.translate("gui.goggles.at_current_speed").withStyle(ChatFormatting.DARK_GRAY)));

		added = true;

		return added;
	}

	@Override
	public float calculateStressApplied() {
		return isGenerator ? 0 : super.calculateStressApplied();
	}

	@Override
	public float calculateAddedStressCapacity() {
		float capacity = calculatePotentialCapacity();
		if(capacity != 0) this.lastCapacityProvided = capacity;
		return capacity;
	}

	@Override
	public float getGeneratedSpeed() {
		return isGenerator && storedStress > 0 ? -50 : 0;
	}

	public void queueGeneratorUpdate() {
		updateGenerator = true;
	}

	public void updateGenerator() {
		boolean shouldGenerate = (this.getTheoreticalSpeed() < 0 && storedStress > 0);

		if (shouldGenerate == isGenerator) return;
		isGenerator = shouldGenerate;
		updateGeneratedRotation();
	}

	public float calculatePotentialCapacity(){
		float capacity = (float) BlockStressValues.getCapacity(getStressConfigKey());
		float genspeed = Math.abs(getGeneratedSpeed());
		if(!isGenerator || genspeed == 0) return 0;
		if (storedStress - capacity < 0){
			return storedStress / genspeed;
		} else return capacity / genspeed;
	}

	@Override
	public void onSpeedChanged(float prevSpeed) {
		updateGenerator = true;
		super.onSpeedChanged(prevSpeed);
	}

	@Override
	public void tick() {
		super.tick();

		boolean server = !level.isClientSide || isVirtual();

		if (server && isGenerator && storedStress >= 0) {
			storedStress -= Math.abs(calculatePotentialCapacity() * getGeneratedSpeed());
			sendData();
			if(storedStress == 0) updateGenerator = true;
		}

		if (updateGenerator) {
			updateGenerator = false;
			updateGenerator();
		}

		if (getSpeed() <= 0) return;
		storedStress += (float) BlockStressValues.getImpact(getStressConfigKey()) * getSpeed();
		sendData();
	}

}
