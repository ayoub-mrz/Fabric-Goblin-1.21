package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProjectileUtils {
    public static void cleanupLightAfterDelay(World world, BlockPos pos, int ticks) {
        if (world.isClient()) return;

        ServerWorld serverWorld = (ServerWorld) world;

        new Thread(() -> {
            try {
                Thread.sleep(ticks * 50);
                if (serverWorld.getBlockState(pos).getBlock() == Blocks.LIGHT) {
                    serverWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
                    System.out.println(pos + " deleted");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
