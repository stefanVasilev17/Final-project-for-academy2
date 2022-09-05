package com.example.stefan.ServiceTests;

import com.example.stefan.model.User;
import com.example.stefan.service.ContractService;
import com.example.stefan.service.LinkService;
import com.example.stefan.service.ParticipantsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExamsTests
{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper        objectMapper;
  @Autowired
  private LinkService         linkService;
  @Autowired
  private ParticipantsService participantsService;
  @Autowired
  private ContractService     contractService;

  @Test
  void contextLoads()
  {
  }

  @Test
  void testGetCountOfContractsByName200() throws Exception
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


    mockMvc.perform(get("/api/v1/user/getCountOfContractsByName/{name}", "BANK1")
        .header("X-AUTH-TOKEN", token))
        .andExpect(status().isOk())
        .andDo(print());

  }

  @Test
  public void getContractInfoIsOk200() throws Exception
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

    mockMvc.perform(get("/api/v1/user/getContractInfo/{contract_reference}/{orderBy}/{start}/{fetchNext}",
        "cnt_primery_key2", "contract_reference", 0, 4)
        .header("X-AUTH-TOKEN", token))
        .andExpect(status().isOk())
        .andDo(print());

  }

  @Test
  void testShowTypeOfParticipants() throws Exception
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

    mockMvc.perform(get("/api/v1/user/findTypeOfParticipants/{orderBy}/{start}/{fetchNext}", "names", 0, 4)
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(c)))
        .andExpect(status().isOk())
        .andDo(print());
  }
}

