package com.example.stefan.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContractInfo
{
  @NotNull(message = "contractType cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contractType;
  @NotNull(message = "contractorName cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contractorName;
  @NotNull(message = "contractorLastName cannot be NULL!")
  @Size(min = 2, max = 30)
  private String contractorLastName;
  private Long count;
  public String getContractorLastName()
  {
    return contractorLastName;
  }

  public void setContractorLastName(String contractorLastName)
  {
    this.contractorLastName = contractorLastName;
  }

  public String getContractType()
  {
    return contractType;
  }

  public void setContractType(String contractType)
  {
    this.contractType = contractType;
  }

  public String getContractorName()
  {
    return contractorName;
  }

  public void setContractorName(String contractorName)
  {
    this.contractorName = contractorName;
  }
}
