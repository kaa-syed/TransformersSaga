package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Team;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Result
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-11T12:42:06.333Z[GMT]")
public class Result   {
  @JsonProperty("battle")
  private Integer battle = null;

  @JsonProperty("winner")
  private Team winner = null;

  @JsonProperty("survivors")
  private Team survivors = null;

  public Result battle(Integer battle) {
    this.battle = battle;
    return this;
  }

  /**
   * Get battle
   * minimum: 0
   * @return battle
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Min(0)  public Integer getBattle() {
    return battle;
  }

  public void setBattle(Integer battle) {
    this.battle = battle;
  }

  public Result winner(Team winner) {
    this.winner = winner;
    return this;
  }

  /**
   * Get winner
   * @return winner
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public Team getWinner() {
    return winner;
  }

  public void setWinner(Team winner) {
    this.winner = winner;
  }

  public Result survivors(Team survivors) {
    this.survivors = survivors;
    return this;
  }

  /**
   * Get survivors
   * @return survivors
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public Team getSurvivors() {
    return survivors;
  }

  public void setSurvivors(Team survivors) {
    this.survivors = survivors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Result result = (Result) o;
    return Objects.equals(this.battle, result.battle) &&
        Objects.equals(this.winner, result.winner) &&
        Objects.equals(this.survivors, result.survivors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(battle, winner, survivors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Result {\n");
    
    sb.append("    battle: ").append(toIndentedString(battle)).append("\n");
    sb.append("    winner: ").append(toIndentedString(winner)).append("\n");
    sb.append("    survivors: ").append(toIndentedString(survivors)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
