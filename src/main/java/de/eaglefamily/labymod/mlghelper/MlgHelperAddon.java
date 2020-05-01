package de.eaglefamily.labymod.mlghelper;

import de.eaglefamily.labymod.mlghelper.module.MlgHelperModule;
import de.eaglefamily.labymod.mlghelper.util.MlgCalculator;
import de.eaglefamily.labymod.mlghelper.util.MlgServer;
import de.eaglefamily.labymod.mlghelper.util.Settings;
import java.util.List;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

public class MlgHelperAddon extends LabyModAddon {

  private Settings settings;

  @Override
  public void onEnable() {
    settings = new Settings(this);
    MlgServer mlgServer = new MlgServer(this);
    MlgCalculator mlgCalculator = new MlgCalculator(mlgServer, settings);
    getApi().registerModule(new MlgHelperModule(settings, mlgCalculator));
  }

  @Override
  public void loadConfig() {
    settings.loadConfig();
  }

  @Override
  protected void fillSettings(List<SettingsElement> settingsElements) {
    settings.fillSettings(settingsElements);
  }
}
