package com.example.stefan.ServiceTests;

import com.example.stefan.model.Participants;
import com.example.stefan.model.User;
import com.example.stefan.service.ParticipantsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ParticipantsMockTests
{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper        objectMapper;
  @Autowired
  private ParticipantsService participantsService;

  @Test
  void contextLoads()
  {
  }

  @Test
  void testCreateParticipantOK200() throws Exception //ne e nujna authorizaciq = .permitAll()
  {
    User c = new User();
    c.setUsername("admin");
    c.setPassword("pass");

    final String token = mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(c)))
        .andExpect(header().exists("X-AUTH-TOKEN"))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getHeader("X-AUTH-TOKEN");

    Participants participants = new Participants("ivan", "georgiev",
        "bulgaria", "burgas", "prif324");


    mockMvc.perform(post("/api/v1/user/createParticipant")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(participants)))
        .andExpect(status().isOk())
        .andDo(print());
    participantsService.deleteParticipants(participants.getParticipantsReference());
  }

  @Test
  void testPostNotFoundURL() throws Exception
  {
    User c = new User();
    c.setUsername("admin");
    c.setPassword("pass");

    final String token = mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(c)))
        .andExpect(header().exists("X-AUTH-TOKEN"))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getHeader("X-AUTH-TOKEN");

    Participants participants = new Participants("ivan", "georgiev", "bulgaria", "burgas", "prima22222ry4ff");

    mockMvc.perform(post("/api/v1/user/createParticipantE")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(participants)))
        .andExpect(status().isNotFound())
        .andDo(print());
    participantsService.deleteParticipants(participants.getParticipantsReference());
  }

  @Test
  void testShowType() throws Exception
  {
    User c = new User();
    c.setUsername("admin");
    c.setPassword("pass");

    final String token = mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(c)))
        .andExpect(header().exists("X-AUTH-TOKEN"))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getHeader("X-AUTH-TOKEN");

    Participants participants = new Participants("ivan", "georgiev", "bulgaria", "burgas", "p2");
    participantsService.createParticipant(participants);

    mockMvc.perform(get("/api/v1/user/showType/{type}", "client")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(participants)))
        .andExpect(status().isOk())
        .andDo(print());
    participantsService.deleteParticipants(participants.getParticipantsReference());
  }

//  @Test
//  void testShowTypeOfParticipants() throws Exception
//  {
//    User c = new User();
//    c.setUsername("admin");
//    c.setPassword("pass");
//
//    final String token = mockMvc.perform(post("/login")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(c)))
//        .andExpect(header().exists("X-AUTH-TOKEN"))
//        .andDo(print())
//        .andReturn()
//        .getResponse()
//        .getHeader("X-AUTH-TOKEN");
//
//    mockMvc.perform(get("/api/v1/user/findTypeOfParticipants/{orderBy}/{start}/{fetchNext}", "names", 0, 2)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("X-AUTH-TOKEN", token)
//        .content(objectMapper.writeValueAsString(c)))
//        .andExpect(status().isOk())
//        .andDo(print());
//  }

  @Test
  void deleteParticipants() throws Exception
  {
    User c = new User();
    c.setUsername("admin");
    c.setPassword("pass");

    final String token = mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(c)))
        .andExpect(header().exists("X-AUTH-TOKEN"))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getHeader("X-AUTH-TOKEN");

    Participants participants = new Participants("ivan", "georgiev", "bulgaria", "burgas", "prif6");

    mockMvc.perform(post("/api/v1/user/createParticipant")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(participants)))
        .andExpect(status().isOk())
        .andDo(print());

    mockMvc.perform(delete("/api/v1/user/deleteParticipant/{participantsReference}", participants.getParticipantsReference())
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(participants)))
        .andExpect(status().isOk())
        .andDo(print());
  }
}

