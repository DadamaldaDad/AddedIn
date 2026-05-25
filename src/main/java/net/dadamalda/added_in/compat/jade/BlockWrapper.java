package net.dadamalda.added_in.compat.jade;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BlockWrapper {
    private final Supplier<Block> blockSupplier;
    private final Supplier<BlockState> blockStateSupplier;
    private final Supplier<BlockEntity> blockEntitySupplier;

    public BlockWrapper(Supplier<Block> blockSupplier1, Supplier<BlockState> blockStateSupplier1, Supplier<BlockEntity> blockEntitySupplier1) {
        blockSupplier = blockSupplier1;
        blockStateSupplier = blockStateSupplier1;
        blockEntitySupplier = blockEntitySupplier1;
    }

    public Block getBlock() {
        return blockSupplier.get();
    }
    public BlockState getBlockState() {
        return blockStateSupplier.get();
    }
    public BlockEntity getBlockEntity() {
        return blockEntitySupplier.get();
    }
}
