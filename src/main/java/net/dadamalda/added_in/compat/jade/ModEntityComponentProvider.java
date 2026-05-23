package net.dadamalda.added_in.compat.jade;

import net.dadamalda.added_in.Added_in;
import net.dadamalda.added_in.providers.VersionProviders;
import net.dadamalda.added_in.providers.VersionResult;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.TriState;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ModEntityComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(
            ITooltip tooltip,
            EntityAccessor accessor,
            IPluginConfig config
    ) {
        VersionResult result = VersionProviders.getEntityVersion(accessor.getEntity());
        if(result.state() == TriState.FALSE) return;
        tooltip.add(Component.translatable("tooltip.added_in", result.version()).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Added_in.MODID, "entity");
    }

    @Override
    public int getDefaultPriority() {
        return 1000;
    }
}
