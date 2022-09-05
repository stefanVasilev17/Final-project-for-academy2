package com.example.stefan.controller;

import com.example.stefan.model.Contract;
import com.example.stefan.model.ContractInfo;
import com.example.stefan.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.SQLException;


@Validated
@RestController
public class ContractController
{
  private final ContractService contractService;

  @Autowired
  public ContractController(ContractService contractService)
  {
    this.contractService = contractService;
  }

  @PostMapping("/api/v1/user/createContract")
  @ResponseStatus(HttpStatus.CREATED)
  public void createContract(@Valid @RequestBody Contract contract) throws SQLException
  {
    contractService.createContract(contract);
  }

//  @GetMapping("/api/v1/user/getCountOfContractsByName/{name}")
//  public ResponseEntity<Optional<Long>> getCountOfContractsByName(@PathVariable String name)
//      throws SQLException
//  {
//    Optional<Long> count = contractService.findCountOfContractsByKey(name);
//    return ResponseEntity.ok(count);
//  }

//  @GetMapping("/api/v1/user/getContractInfo/{contract_reference}")
//  public ResponseEntity<List<ContractInfo>> getContractInfo(@PathVariable String contract_reference)
//  {
//    List<ContractInfo> contractInfo = contractService.getContractInfo(contract_reference);
//    return ResponseEntity.ok(contractInfo);
//  }

  @DeleteMapping("/api/v1/user/deleteContract/{contract_reference}")
  public ResponseEntity<Void> deleteContract(@PathVariable String contract_reference)
  {
    contractService.deleteContract(contract_reference);
    return ResponseEntity.ok().build();
  }
}
