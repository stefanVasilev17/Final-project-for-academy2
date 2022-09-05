package com.example.stefan.ServiceTests;

import com.example.stefan.model.Contract;
import com.example.stefan.model.Link;
import com.example.stefan.model.Participants;
import com.example.stefan.model.User;
import com.example.stefan.service.ContractService;
import com.example.stefan.service.LinkService;
import com.example.stefan.service.ParticipantsService;
import com.example.stefan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractMockTests
{
  @Autowired
  private MockMvc             mockMvc;
  @Autowired
  private ObjectMapper        objectMapper;
  @Autowired
  private ContractService     contractService;
  @Autowired
  private LinkService         linkService;
  @Autowired
  private ParticipantsService participantsService;
  @Autowired
  private UserService         userService;

  @Test
  void contextLoads()
  {
  }

  @Test
  public void createContractIsCreated201() throws Exception
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

    Contract contract1 = new Contract("r99", "informaciq");

    mockMvc.perform(post("/api/v1/user/createContract")
        .header("X-AUTH-TOKEN", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contract1)))
        .andExpect(status().isCreated())
        .andDo(print());
    contractService.deleteContract(contract1.getContract_reference());
  }

//  @Test
//  void testGetCountOfContractsByName200() throws Exception
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
//
//    mockMvc.perform(get("/api/v1/user/getCountOfContractsByName/{name}", "BANK1")
//        .header("X-AUTH-TOKEN", token))
//        .andExpect(status().isOk())
//        .andDo(print());
//
//    participantsService.deleteParticipants("BANK1");
//
//  }

//  @Test
//  public void getContractInfoIsOk200() throws Exception
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
//    mockMvc.perform(get("/api/v1/user/getContractInfo/{contract_reference}", "cnt_primery_key2")
//        .header("X-AUTH-TOKEN", token))
//        .andExpect(status().isOk())
//        .andDo(print());
//
//    linkService.deleteLink("cnt_primery_key2");
//
//  }

  @Test
  public void createContractUnauthorized401() throws Exception
  {
    Contract contract1 = new Contract("refer", "informaciq");

    mockMvc.perform(post("/api/v1/user/createContract")
        //.header("X-AUTH-TOKEN", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contract1)))
        .andExpect(status().isUnauthorized())
        .andDo(print());
    contractService.deleteContract(contract1.getContract_reference());
  }

  @Test
  public void deleteContractIsOk200() throws Exception
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

    Contract contract1 = new Contract("cr6", "informaciq");
    contractService.createContract(contract1);

    mockMvc.perform(delete("/api/v1/user/deleteContract/{contract_reference}", contract1.getContract_reference())
        .header("X-AUTH-TOKEN", token))
        .andExpect(status().isOk())
        .andDo(print());

    contractService.deleteContract(contract1.getContract_reference());
  }
}

