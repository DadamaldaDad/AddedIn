package net.dadamalda.added_in.compat.jade;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(ModBlockComponentProvider.INSTANCE, Block.class);
        registration.registerEntityComponent(ModEntityComponentProvider.INSTANCE, Entity.class);
    }
}
