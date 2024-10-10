package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.Utils.Utils;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.KeyInputHandler;

@Mixin(WaterFluid.class)
public class WaterBreakBlockEventImp  {
    @Inject(at= @At(value="HEAD"), method = "beforeBreakingBlock(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V")
    private void guh(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci)
    {
        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return ;

        if (!(state.getBlock() instanceof CropBlock)) return;
        if (!(ConfigFile.getValue("replantCropBrokenByWater").getAsBoolean())) return;
        MinecraftClient client = MinecraftClient.getInstance();
        BlockHitResult res = new BlockHitResult(Utils.BlockPosToVector3d(pos), Direction.UP, pos, false);
        int savedSlotValue = client.player.getInventory().selectedSlot;
        ItemStack pickStack = null;
        Block block = state.getBlock();
        if (block instanceof CropBlock crop)
        {
            ItemConvertible fuckthisshit = ((CropBlockMixin) crop).invokeGetSeedsitem();
            pickStack = new ItemStack(fuckthisshit);
        } else if (block instanceof CocoaBlock)
        {
            pickStack = new ItemStack(Items.COCOA_BEANS);
        } else if (block instanceof NetherWartBlock)
        {
            pickStack = new ItemStack(Items.NETHER_WART);
        }
        Utils.queuePlacement(client, res, savedSlotValue, pickStack, false,false);
    }


}
