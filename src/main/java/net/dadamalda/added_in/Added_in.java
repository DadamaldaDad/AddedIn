package net.dadamalda.added_in;

import net.dadamalda.added_in.data_loading.VersionDataReloadListener;
import net.dadamalda.added_in.providers.DefaultVersionProviders;
import net.dadamalda.added_in.providers.VersionProviders;
import net.dadamalda.added_in.providers.VersionResult;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Added_in.MODID)
public class Added_in {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "added_in";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Added_in(IEventBus modEventBus, ModContainer modContainer) {
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Added_in) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::registerClientReloadListeners);

        DefaultVersionProviders.register();
    }

    public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new VersionDataReloadListener());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public void onItemTooltip(ItemTooltipEvent event) {
        VersionResult result = VersionProviders.getItemVersion(event.getItemStack());
        if(result.state() == TriState.FALSE) return;
        event.getToolTip().add(Component.translatable("tooltip.added_in", result.version()).withStyle(ChatFormatting.BLUE));
    }
}
