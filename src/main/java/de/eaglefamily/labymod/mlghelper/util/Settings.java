package de.eaglefamily.labymod.mlghelper.util;

import com.google.gson.JsonObject;
import java.util.List;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

public class Settings {

  private static final String ENABLED_CONFIG_NAME = "enabled";
  private static final String ENABLED_AS_SPECTATOR_CONFIG_NAME = "enabledAsSpectator";

  private final LabyModAddon addon;
  private boolean enabled;
  private boolean enabledAsSpectator;

  public Settings(LabyModAddon addon) {
    this.addon = addon;
  }

  /**
   * Loads the settings config.
   */
  public void loadConfig() {
    enabled = !getConfig().has(ENABLED_CONFIG_NAME) || getConfig().get(ENABLED_CONFIG_NAME)
        .getAsBoolean();
    enabledAsSpectator = !getConfig().has(ENABLED_AS_SPECTATOR_CONFIG_NAME) || getConfig().get(
        ENABLED_AS_SPECTATOR_CONFIG_NAME).getAsBoolean();
  }

  /**
   * Gets the config from the addon.
   *
   * @return the config
   */
  private JsonObject getConfig() {
    return addon.getConfig();
  }

  /**
   * Fills the settings from the addon.
   *
   * @param list the settings
   */
  public void fillSettings(List<SettingsElement> list) {
    list.add(new HeaderElement("§eMLG Helper"));
    list.add(new HeaderElement("§6General"));
    list.add(createEnabledElement());
    list.add(createEnabledAsSpectatorElement());
  }

  private BooleanElement createEnabledElement() {
    ControlElement.IconData iconData = new ControlElement.IconData(Material.LEVER);
    return new BooleanElement("§6Enabled", iconData, this::onEnable, enabled);
  }

  private BooleanElement createEnabledAsSpectatorElement() {
    ControlElement.IconData iconData = new ControlElement.IconData(Material.COMPASS);
    return new BooleanElement("§6Enabled as spectator", iconData, this::onEnableAsSpectator,
        enabledAsSpectator);
  }

  private void onEnable(Boolean enabled) {
    this.enabled = enabled;
    getConfig().addProperty(ENABLED_CONFIG_NAME, enabled);
    saveConfig();
  }

  private void onEnableAsSpectator(Boolean enabledAsSpectator) {
    this.enabledAsSpectator = enabledAsSpectator;
    getConfig().addProperty(ENABLED_AS_SPECTATOR_CONFIG_NAME, enabledAsSpectator);
    saveConfig();
  }

  private void saveConfig() {
    addon.saveConfig();
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isEnabledAsSpectator() {
    return enabledAsSpectator;
  }
}
