package gay.aurum.creategaslamp;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import com.tterrag.registrate.util.nullness.NonNullSupplier;

import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateGasLamp implements ModInitializer {
	public static final String ID = "creategaslamp";
	public static final String NAME = "Create: Gas Lamp Fantasy";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final PartialModel SHAFTLESS_COGWHEEL = new PartialModel(id("block/brass_cogwheel_shaftless"));
	public static final PartialModel SHAFTLESS_LARGE_COGWHEEL =  new PartialModel(id("block/large_brass_cogwheel_shaftless"));

	public static final CreativeModeTab TAB = new CreativeModeTab(ItemGroupUtil.expandArrayAndGetId(),"createlamp") {
		@Override
		public ItemStack makeIcon() {
			return BlockRegistry.BRASS_ENCASED_LARGE_BRASS_COGWHEEL.asStack();
		}
	};

	private static final NonNullSupplier<CreateRegistrate> REGISTRATE = CreateRegistrate.lazy(ID);


	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] loading alongside Create [{}]!", NAME, Create.VERSION);
		BlockRegistry.init();
		BlockEntityRegistry.init();
		registerer().register();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static CreateRegistrate registerer() {
		return REGISTRATE.get();
	}
}
