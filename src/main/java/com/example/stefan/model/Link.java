package com.example.stefan.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Link
{
  @NotNull(message = "contract_reference cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contract_reference;
  @NotNull(message = "participants_reference cannot be NULL!")
  @Size(min = 2, max = 30)
  private String participants_reference;
  @NotNull(message = "type cannot be NULL!")
  @Size(min = 2, max = 30)
  private String type;

  public Link()
  {
  }

  public Link(String contract_reference,String participants_reference,String type)
  {
    this.contract_reference = contract_reference;
    this.participants_reference = participants_reference;
    this.type = type;
  }

  public String getContract_reference()
  {
    return contract_reference;
  }

  public void setContract_reference(String contract_reference)
  {
    this.contract_reference = contract_reference;
  }

  public String getParticipants_reference()
  {
    return participants_reference;
  }

  public void setParticipants_reference(String participants_reference)
  {
    this.participants_reference = participants_reference;
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
