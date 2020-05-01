package de.eaglefamily.labymod.mlghelper.module;

import de.eaglefamily.labymod.mlghelper.util.MlgAction;
import de.eaglefamily.labymod.mlghelper.util.MlgCalculator;
import de.eaglefamily.labymod.mlghelper.util.Settings;
import java.util.Objects;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * Displays the mlg helper module on the screen.
 * This class has 6 parents which is greater than 5 authorized... LabyMod is trash.
 */
public class MlgHelperModule extends SimpleModule {

  private final Settings settings;
  private final MlgCalculator mlgCalculator;

  public MlgHelperModule(Settings settings, MlgCalculator mlgCalculator) {
    this.settings = settings;
    this.mlgCalculator = mlgCalculator;
  }

  @Override
  public String getDisplayName() {
    return "MLG";
  }

  @Override
  public String getDisplayValue() {
    MlgAction mlgAction = mlgCalculator.calculateCurrentAction();
    return mlgAction.getDisplayName();
  }

  @Override
  public String getDefaultValue() {
    return MlgAction.WALK.getDisplayName();
  }

  @Override
  public String getControlName() {
    return "MLG Helper";
  }

  @Override
  public ControlElement.IconData getIconData() {
    return new ControlElement.IconData(Material.WEB);
  }

  @Override
  public boolean isShown() {
    return settings.isEnabled() && Objects.nonNull(getDisplayValue());
  }

  @Override
  public void loadSettings() {
    // nothing to load
  }

  @Override
  public String getSettingName() {
    return "mlghelper";
  }

  @Override
  public String getDescription() {
    return "";
  }

  @Override
  public int getSortingId() {
    return 0;
  }
}
