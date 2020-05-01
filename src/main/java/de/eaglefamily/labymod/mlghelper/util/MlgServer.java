package de.eaglefamily.labymod.mlghelper.util;

import de.eaglefamily.labymod.mlghelper.listener.PayloadPacketListener;
import java.util.Objects;
import net.labymod.api.LabyModAddon;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.labymod.utils.ServerData;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.Team;

public class MlgServer {

  private final PayloadPacketListener payloadPacketListener;

  /**
   * Represents a server the player is connected to.
   *
   * @param addon the addon
   */
  public MlgServer(LabyModAddon addon) {
    addon.getApi().getEventManager().registerOnJoin(serverData -> resetServer());
    payloadPacketListener = new PayloadPacketListener();
    addon.getApi().getEventManager().registerOnIncomingPacket(payloadPacketListener);
  }

  private void resetServer() {
    payloadPacketListener.reset();
  }

  /**
   * Checks if the player is on an allowed mlg server.
   *
   * @return is on allowed mlg server
   */
  public boolean isOnMlgServer() {
    if (isInSingleplayer()) {
      return true;
    }

    return payloadPacketListener.isOnAllowedServer();
  }

  private boolean isInSingleplayer() {
    ServerData currentServerData = LabyMod.getInstance().getCurrentServerData();
    return Objects.isNull(currentServerData);
  }

  /**
   * Checks if the player is currently a spectator.
   *
   * @return is spectating
   */
  public boolean isSpectating() {
    if (payloadPacketListener.isOnGomme()) {
      return isGommeSpectator();
    }

    return false;
  }

  private boolean isGommeSpectator() {
    EntityPlayerSP player = LabyModCore.getMinecraft().getPlayer();
    Team team = player.getTeam();
    if (Objects.isNull(team)) {
      return false;
    }

    String teamName = team.getRegisteredName();
    if (Objects.isNull(teamName)) {
      return false;
    }

    return teamName.startsWith("ZZZ");
  }
}
