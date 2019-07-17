package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.TeamName;
import io.swagger.model.Transformers;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * members of the same team
 */
@ApiModel(description = "members of the same team")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-11T12:42:06.333Z[GMT]")
public class Team   {
  @JsonProperty("name")
  private TeamName name = null;

  @JsonProperty("members")
  private Transformers members = null;

  public Team name(TeamName name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public TeamName getName() {
    return name;
  }

  public void setName(TeamName name) {
    this.name = name;
  }

  public Team members(Transformers members) {
    this.members = members;
    return this;
  }

  /**
   * Get members
   * @return members
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  public Transformers getMembers() {
    return members;
  }

  public void setMembers(Transformers members) {
    this.members = members;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Team team = (Team) o;
    return Objects.equals(this.name, team.name) &&
        Objects.equals(this.members, team.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Team {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
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
