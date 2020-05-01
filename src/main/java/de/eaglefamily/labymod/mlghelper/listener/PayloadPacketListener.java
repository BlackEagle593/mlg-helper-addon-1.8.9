package de.eaglefamily.labymod.mlghelper.listener;

import net.labymod.utils.Consumer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class PayloadPacketListener implements Consumer<Object> {

  private static final String GOMME_PLUGIN_CHANNEL = "GoMod";

  private boolean onlineOnGomme;

  @Override
  public void accept(Object rawPacket) {
    if (!(rawPacket instanceof S3FPacketCustomPayload)) {
      return;
    }

    S3FPacketCustomPayload payloadPacket = (S3FPacketCustomPayload) rawPacket;
    if (payloadPacket.getChannelName().equals(GOMME_PLUGIN_CHANNEL)) {
      onlineOnGomme = true;
    }
  }

  public void reset() {
    onlineOnGomme = false;
  }

  public boolean isOnAllowedServer() {
    return onlineOnGomme;
  }

  public boolean isOnGomme() {
    return onlineOnGomme;
  }
}
