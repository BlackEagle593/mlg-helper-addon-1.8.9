package de.eaglefamily.labymod.mlghelper.util;

public enum MlgAction {

  NOTHING(null),
  IMPOSSIBLE("Impossible"),
  WALK("Walk"),
  JUMP("Jump"),
  BOTH("Both");

  private final String displayName;

  MlgAction(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
