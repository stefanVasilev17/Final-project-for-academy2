package com.example.stefan.methodValidations;

import com.example.stefan.exception.IllegalDataException;
import org.springframework.stereotype.Component;


@Component
public class Validations
{
  public void validateType(String type)
  {
    if (!"DEBITOR".equals(type) && !"CLIENT".equals(type)) {
      throw new IllegalDataException("Invalid type.");
    }
  }

  public void validatePaginationNumberInputs(int start, int fetchNext)
  {
    if (start < 0 || fetchNext <= 0) {
      throw new IllegalDataException("Invalid number input data.");
    }
  }

  public void validatePaginationStringInputs(String orderBy)
  {
    if (!"names".equals(orderBy) && !"last_name".equals(orderBy) &&
        !"country".equals(orderBy) && !"city".equals(orderBy)
        && !"participantsReference.eq".equals(orderBy)) {
      throw new IllegalDataException("Invalid string input data.");
    }
  }

  public void validatePaginationStringInputsForContracts(String orderBy)
  {
    if (!"contract_info".equals(orderBy) && !"contract_reference".equals(orderBy)) {
      throw new IllegalDataException("Invalid string input data.");
    }
  }
}
