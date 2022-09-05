package com.example.stefan.service;

import com.example.stefan.methodValidations.Validations;
import com.example.stefan.model.Contract;
import com.example.stefan.model.ContractInfo;
import com.example.stefan.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService
{
  private final ContractRepository contractRepository;
  private final Validations        validations;

  @Autowired
  public ContractService(ContractRepository contractRepository, Validations validations)
  {
    this.contractRepository = contractRepository;
    this.validations = validations;
  }

  public void createContract(Contract contract) throws SQLException
  {
    contractRepository.createContract(contract);
  }

  public Optional<Long> findCountOfContractsByName(String name) throws SQLException
  {
    return contractRepository.findCountOfContractsByName(name);
  }

  public List<ContractInfo> getContractInfo(String contract_reference, String orderBy, int start, int fetchNext)
  {
    validations.validatePaginationStringInputsForContracts(orderBy);
    validations.validatePaginationNumberInputs(start, fetchNext);
    return contractRepository.getContractInfo(contract_reference, orderBy, start, fetchNext);
  }

  public void deleteContract(String contract_reference)
  {
    contractRepository.deleteContract(contract_reference);
  }
}

