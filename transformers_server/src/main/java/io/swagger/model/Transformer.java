package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.TeamName;
import java.util.HashMap;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transformer
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-11T12:42:06.333Z[GMT]")
public class Transformer   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("team")
  private TeamName team = null;

  @JsonProperty("specs")
  @Valid
  private Map<String, Integer> specs = new HashMap<String, Integer>();

  @JsonProperty("creationDate")
  private OffsetDateTime creationDate = null;

  public Transformer id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "d290f1ee", value = "")

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Transformer name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Soundwave", required = true, value = "")
  @NotNull

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Transformer team(TeamName team) {
    this.team = team;
    return this;
  }

  /**
   * Get team
   * @return team
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public TeamName getTeam() {
    return team;
  }

  public void setTeam(TeamName team) {
    this.team = team;
  }

  public Transformer specs(Map<String, Integer> specs) {
    this.specs = specs;
    return this;
  }

  public Transformer putSpecsItem(String key, Integer specsItem) {
    this.specs.put(key, specsItem);
    return this;
  }

  /**
   * Get specs
   * @return specs
  **/
  @ApiModelProperty(example = "{\"Strength\":8,\"Intelligence\":7,\"Speed\":4,\"Endurance\":8,\"Rank\":1,\"Courage\":10,\"Firepower\":3,\"Skill\":8}", required = true, value = "")
  @NotNull

  public Map<String, Integer> getSpecs() {
    return specs;
  }

  public void setSpecs(Map<String, Integer> specs) {
    this.specs = specs;
  }

  public Transformer creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
  **/
  @ApiModelProperty(value = "")

  @Valid
  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transformer transformer = (Transformer) o;
    return Objects.equals(this.id, transformer.id) &&
        Objects.equals(this.name, transformer.name) &&
        Objects.equals(this.team, transformer.team) &&
        Objects.equals(this.specs, transformer.specs) &&
        Objects.equals(this.creationDate, transformer.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, team, specs, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transformer {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
    sb.append("    specs: ").append(toIndentedString(specs)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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
