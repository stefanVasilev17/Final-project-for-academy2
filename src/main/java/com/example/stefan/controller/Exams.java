package com.example.stefan.controller;

import com.example.stefan.model.ContractInfo;
import com.example.stefan.model.ParticipantsInfo;
import com.example.stefan.service.ContractService;
import com.example.stefan.service.ParticipantsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class Exams
{
  private final ContractService     contractService;
  private final ParticipantsService participantsService;

  public Exams(ContractService contractService, ParticipantsService participantsService)
  {
    this.contractService = contractService;
    this.participantsService = participantsService;
  }

  //Напишете селект , който да вади информация кои са клиентите и кои са дебиторите от таблицата participants. EXAM 1
  @GetMapping("/api/v1/user/findTypeOfParticipants/{orderBy}/{start}/{fetchNext}")
  public ResponseEntity<List<ParticipantsInfo>> findTypeOfParticipants(@PathVariable String orderBy,
                                                                       @PathVariable int start,
                                                                       @PathVariable int fetchNext)
  {
    List<ParticipantsInfo> participantsList = participantsService.findTypeOfParticipants(orderBy, start, fetchNext);
    return ResponseEntity.ok(participantsList);
  }

  //Напишете селект , който да вади по колко договора участва клиент BANK1 и BANK CC .	EXAM 2
  @GetMapping("/api/v1/user/getCountOfContractsByName/{name}")
  public ResponseEntity<Optional<Long>> getCountOfContractsByName(@PathVariable String name)
      throws SQLException
  {
    Optional<Long> count = contractService.findCountOfContractsByName(name);
    return ResponseEntity.ok(count);
  }

  //Напишете селект , който да вади информация за даден договор – кой е клиент и кой е дебитора. EXAM 3
  @GetMapping("/api/v1/user/getContractInfo/{contract_reference}/{orderBy}/{start}/{fetchNext}")
  public ResponseEntity<List<ContractInfo>> getContractInfo(@PathVariable String contract_reference,
                                                            @PathVariable String orderBy,
                                                            @PathVariable int start,
                                                            @PathVariable int fetchNext)
  {
    List<ContractInfo> contractInfo = contractService.getContractInfo(contract_reference, orderBy, start, fetchNext);
    return ResponseEntity.ok(contractInfo);
  }
}

