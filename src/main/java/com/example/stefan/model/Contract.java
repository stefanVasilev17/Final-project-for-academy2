package com.example.stefan.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Contract
{
  @NotNull(message = "contract_reference cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contract_reference;
  @NotNull(message = "contract_info cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contract_info;

  public String getContract_reference()
  {
    return contract_reference;
  }

  public Contract(String contract_reference,String contract_info)
  {
    this.contract_reference = contract_reference;
    this.contract_info = contract_info;
  }

  public Contract()
  {
  }

  public void setContract_reference(String contract_reference)
  {
    this.contract_reference = contract_reference;
  }

  public String getContract_info()
  {
    return contract_info;
  }

  public void setContract_info(String contract_info)
  {
    this.contract_info = contract_info;
  }
}
