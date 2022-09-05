package com.example.stefan.controller;

import com.example.stefan.model.Participants;
import com.example.stefan.model.ParticipantsInfo;
import com.example.stefan.service.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
public class ParticipantsController
{
  private final ParticipantsService participantsService;

  @Autowired
  public ParticipantsController(ParticipantsService participantsService)
  {
    this.participantsService = participantsService;
  }


  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/api/v1/user/createParticipant", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Participants> createParticipant(@Valid @RequestBody Participants participants)
  {
    participantsService.createParticipant(participants);
    return ResponseEntity.ok(participants);
  }


  @GetMapping("/api/v1/user/showType/{type}")
  public ResponseEntity<List<Participants>> showType(@PathVariable String type)
  {

    List<Participants> participantsList = participantsService.findParticipantsByType(type.toUpperCase());
    return ResponseEntity.ok(participantsList);
  }
//
//  @GetMapping("/api/v1/user/findTypeOfParticipants/{orderBy}/{start}/{fetchNext}")
//  public ResponseEntity<List<ParticipantsInfo>> findTypeOfParticipants(@PathVariable String orderBy,
//                                                                       @PathVariable int start,
//                                                                       @PathVariable int fetchNext)
//  {
//    List<ParticipantsInfo> participantsList = participantsService.findTypeOfParticipants(orderBy,start,fetchNext);
//    return ResponseEntity.ok(participantsList);
//  }

  // for TESTS
  @DeleteMapping("/api/v1/user/deleteParticipant/{participantsReference}")
  public ResponseEntity<Void> deleteParticipants(@PathVariable String participantsReference)
  {
    participantsService.deleteParticipants(participantsReference);
    return ResponseEntity.ok().build();
  }
}
