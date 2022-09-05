package com.example.stefan.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Participants
{
  @NotNull(message = "name cannot be NULL!")
  private String name;
  @NotNull(message = "last_name cannot be NULL!")
  @Size(min = 2, max = 30)
  private String last_name;
  @NotNull(message = "country cannot be NULL!")
  @Size(min = 2, max = 30)
  private String country;
  @NotNull(message = "city cannot be NULL!")
  @Size(min = 2, max = 30)
  private String city;
  @NotNull(message = "participantsReference cannot be NULL!")
  @Size(min = 2, max = 30)
  private String participantsReference;

  public Participants()
  {
  }

  public Participants(String name, String last_name, String country, String city, String participantsReference)
  {
    this.name = name;
    this.last_name = last_name;
    this.country = country;
    this.city = city;
    this.participantsReference = participantsReference;
  }

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

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getParticipantsReference()
  {
    return participantsReference;
  }

  public void setParticipantsReference(String participantsReference)
  {
    this.participantsReference = participantsReference;
  }
}
