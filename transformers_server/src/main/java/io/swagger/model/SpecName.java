package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets SpecName
 */
public enum SpecName {
  STRENGTH("Strength"),
    INTELLIGENCE("Intelligence"),
    SPEED("Speed"),
    ENDURANCE("Endurance"),
    RANK("Rank"),
    COURAGE("Courage"),
    FIREPOWER("Firepower"),
    SKILL("Skill");

  private String value;

  SpecName(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SpecName fromValue(String text) {
    for (SpecName b : SpecName.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
