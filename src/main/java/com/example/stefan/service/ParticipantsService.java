package com.example.stefan.service;

import com.example.stefan.exception.IllegalDataException;
import com.example.stefan.methodValidations.Validations;
import com.example.stefan.model.Participants;
import com.example.stefan.model.ParticipantsInfo;
import com.example.stefan.repository.ParticipantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantsService
{
  private final ParticipantsRepository participantsRepository;
  private final Validations            validations;

  @Autowired
  public ParticipantsService(ParticipantsRepository participantsRepository, Validations validations)
  {
    this.participantsRepository = participantsRepository;
    this.validations = validations;
  }

  public void createParticipant(Participants participant)
  {
    participantsRepository.createParticipant(participant);
  }


  public List<Participants> findParticipantsByType(String type)
  {
    validations.validateType(type);
    return participantsRepository.findParticipantsByType(type);
  }

  public List<ParticipantsInfo> findTypeOfParticipants(String orderBy,
                                                       int start,
                                                       int fetchNext)
  {
    validations.validatePaginationNumberInputs(start, fetchNext);
    validations.validatePaginationStringInputs(orderBy);
    return participantsRepository.findTypeOfParticipants(orderBy, start, fetchNext);
  }

  public void deleteParticipants(String participantsReference)
  {
    participantsRepository.deleteParticipants(participantsReference);
  }

}
