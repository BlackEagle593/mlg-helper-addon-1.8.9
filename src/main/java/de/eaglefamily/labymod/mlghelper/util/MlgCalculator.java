package de.eaglefamily.labymod.mlghelper.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import net.labymod.core.LabyModCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class MlgCalculator {

  private static final int MIN_HEIGHT_DIFFERENCE = 10;
  private static final int MAX_RANGE = 32;

  private final List<Integer> walk = Lists.newArrayList(16, 19, 22, 24, 27, 29, 34, 36, 38, 40, 42,
      49, 51, 53, 55, 58, 60, 62, 65, 67, 69, 72, 74, 77, 79, 82, 85, 87, 90, 93, 95, 98, 101, 104,
      106, 109, 112, 115, 118, 119, 121, 127, 130, 133, 135, 136);
  private final List<Integer> jump = Lists.newArrayList(17, 18, 20, 23, 28, 30, 31, 35, 37, 39, 41,
      43, 45, 50, 52, 54, 56, 59, 61, 63, 66, 68, 71, 73, 76, 78, 81, 83, 86, 89, 91, 94, 97, 100,
      103, 105, 107, 108, 111, 114, 117, 122, 125, 128);
  private final List<Integer> both = Lists.newArrayList(10, 11, 13, 14, 15, 22, 26, 33, 47);

  private final MlgServer mlgServer;
  private final Settings settings;

  public MlgCalculator(MlgServer mlgServer, Settings settings) {
    this.mlgServer = mlgServer;
    this.settings = settings;
  }

  /**
   * Calculates the current mlg action to do in the current state.
   *
   * @return the mlg action for the current state
   */
  public MlgAction calculateCurrentAction() {
    if (!mlgServer.isOnMlgServer()) {
      return MlgAction.NOTHING;
    }

    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    if (Objects.isNull(player)) {
      return MlgAction.NOTHING;
    }

    if (hasHeightChanged()) {
      return MlgAction.NOTHING;
    }

    if (!hasMlgItemInHand() && (!settings.isEnabledAsSpectator() || !mlgServer.isSpectating())) {
      return MlgAction.NOTHING;
    }

    return getLookingAtMlgAction();
  }

  private boolean hasHeightChanged() {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    return player.prevPosY != player.posY;
  }

  private boolean hasMlgItemInHand() {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    ItemStack currentEquippedItem = player.getCurrentEquippedItem();
    if (Objects.isNull(currentEquippedItem)) {
      return false;
    }

    return currentEquippedItem.getUnlocalizedName().equals("tile.web");
  }

  private MlgAction getLookingAtMlgAction() {
    MovingObjectPosition target = playerLookingAt();
    if (Objects.isNull(target)) {
      return MlgAction.NOTHING;
    }

    if (!isTargetInRange(target)) {
      return MlgAction.NOTHING;
    }

    if (!isTargetValid(target)) {
      return MlgAction.NOTHING;
    }

    int heightDifference = getHeightDifference(target) - 1;
    return getActionFromDifference(heightDifference);
  }

  private MovingObjectPosition playerLookingAt() {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    return player.rayTrace(256, 1);
  }

  private boolean isTargetInRange(MovingObjectPosition target) {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    BlockPos playerPosition = player.getPosition();
    BlockPos targetPosition = target.getBlockPos();
    int differenceX = Math.abs(targetPosition.getX() - playerPosition.getX());
    int differenceZ = Math.abs(targetPosition.getZ() - playerPosition.getZ());
    return differenceX <= MAX_RANGE && differenceZ <= MAX_RANGE;
  }

  private boolean isTargetValid(MovingObjectPosition target) {
    BlockPos blockAbove = target.getBlockPos().north();
    IBlockState blockState = LabyModCore.getMinecraft().getWorld().getBlockState(blockAbove);
    Material material = blockState.getBlock().getMaterial();
    return material == Material.air;
  }

  private int getHeightDifference(MovingObjectPosition target) {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    int targetY = target.getBlockPos().getY();
    int playerY = player.getPosition().getY();
    return playerY - targetY;
  }

  private MlgAction getActionFromDifference(int heightDifference) {
    if (heightDifference <= MIN_HEIGHT_DIFFERENCE) {
      return MlgAction.NOTHING;
    }

    if (walk.contains(heightDifference)) {
      return MlgAction.WALK;
    }

    if (jump.contains(heightDifference)) {
      return MlgAction.JUMP;
    }

    if (both.contains(heightDifference)) {
      return MlgAction.BOTH;
    }

    return MlgAction.IMPOSSIBLE;
  }
}
