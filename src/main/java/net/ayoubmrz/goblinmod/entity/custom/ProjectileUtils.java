package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProjectileUtils {

    private static final ConcurrentLinkedQueue<CleanupTask> pendingCleanupTasks = new ConcurrentLinkedQueue<>();

    private static final ConcurrentHashMap<BlockPos, Long> scheduledCleanups = new ConcurrentHashMap<>();

    private static class CleanupTask {
        final World world;
        final BlockPos pos;
        final long executeAfterTick;

        CleanupTask(World world, BlockPos pos, long executeAfterTick) {
            this.world = world;
            this.pos = pos.toImmutable();
            this.executeAfterTick = executeAfterTick;
        }
    }

    /**
     * Schedule a light block cleanup after a delay (thread-safe)
     */
    public static void cleanupLightAfterDelay(World world, BlockPos pos, int delayTicks) {
        if (world.isClient) return;

        // Prevent duplicate cleanup tasks for the same position
        Long existingCleanup = scheduledCleanups.get(pos);
        if (existingCleanup != null && world.getTime() < existingCleanup) {
            return;
        }

        long executeTime = world.getTime() + delayTicks;
        scheduledCleanups.put(pos.toImmutable(), executeTime);

        // Add to pending tasks queue
        pendingCleanupTasks.offer(new CleanupTask(world, pos, executeTime));
    }

    public static void processPendingCleanups() {
        if (pendingCleanupTasks.isEmpty()) return;

        long currentTime = System.currentTimeMillis();
        CleanupTask task;

        // Process up to 10 cleanup tasks per tick to avoid lag
        int processed = 0;
        while ((task = pendingCleanupTasks.peek()) != null && processed < 10) {
            if (task.world.getTime() >= task.executeAfterTick) {
                // Remove from queue
                pendingCleanupTasks.poll();

                // Execute cleanup on main thread
                executeCleanup(task);

                // Remove from scheduled map
                scheduledCleanups.remove(task.pos);

                processed++;
            } else {
                break;
            }
        }
    }

    private static void executeCleanup(CleanupTask task) {
        try {
            if (task.world instanceof ServerWorld serverWorld) {
                if (serverWorld.getServer().isOnThread()) {
                    serverWorld.setBlockState(task.pos, Blocks.AIR.getDefaultState());
                } else {
                    serverWorld.getServer().execute(() -> {
                        serverWorld.setBlockState(task.pos, Blocks.AIR.getDefaultState());
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error cleaning up light block at " + task.pos + ": " + e.getMessage());
        }
    }

}