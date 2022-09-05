package com.example.stefan.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParticipantsInfo
{
  @NotNull(message = "name cannot be NULL!")
  @Size(min = 2, max = 30)
  private String name;
  @NotNull(message = "last_name cannot be NULL!")
  @Size(min = 2, max = 30)
  private String last_name;
  @NotNull(message = "type cannot be NULL!")
  private String type;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getLast_name()
  {
    return last_name;
  }

  public void setLast_name(String last_name)
  {
    this.last_name = last_name;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }
}
