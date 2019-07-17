package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets TeamName
 */
public enum TeamName {
  AUTOBOT("Autobot"),
    DECEPTICON("Decepticon");

  private String value;

  TeamName(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static TeamName fromValue(String text) {
    for (TeamName b : TeamName.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
