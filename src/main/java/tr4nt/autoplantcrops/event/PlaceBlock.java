package tr4nt.autoplantcrops.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import tr4nt.autoplantcrops.config.ConfigFile;

public class PlaceBlock {

    public static void placeSeed(MinecraftClient client, BlockHitResult hit)
    {

        BlockHitResult temp = hit;
        BlockHitResult tempThe = new BlockHitResult(hit.getPos(), Direction.UP, hit.getBlockPos(), hit.isInsideBlock());
        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),tempThe);

        if (ConfigFile.getValue("plantMultiple").getAsBoolean())
        {
            BlockHitResult res1 = new BlockHitResult(temp.getPos().add(0,0,1), Direction.UP, temp.getBlockPos().add(new Vec3i(0,0,1)), temp.isInsideBlock());
            BlockHitResult res2 = new BlockHitResult(temp.getPos().subtract(0,0,1), Direction.UP, temp.getBlockPos().subtract(new Vec3i(0,0,1)), temp.isInsideBlock());
            BlockHitResult res3 = new BlockHitResult(temp.getPos().add(1,0,0), Direction.UP, temp.getBlockPos().add(new Vec3i(1,0,0)), temp.isInsideBlock());
            BlockHitResult res4 = new BlockHitResult(temp.getPos().subtract(1,0,0), Direction.UP, temp.getBlockPos().subtract(new Vec3i(1,0,0)), temp.isInsideBlock());
            client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res1);
            client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res2);
            client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res3);
            client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res4);

        }



    }

}
