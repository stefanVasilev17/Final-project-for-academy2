package com.example.stefan.ServiceTests;

import com.example.stefan.model.User;
import com.example.stefan.service.ContractService;
import com.example.stefan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserMockTests
{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserService  userService;

  @Autowired
  private ContractService contractService;

  @Test
  void contextLoads()
  {
  }

  @Test
  void testPostOK200() throws Exception
  {
    User user = new User();
    user.setUsername("admin");
    user.setPassword("pass");

    mockMvc.perform(post("/api/v1/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk())
        .andDo(print());
  }
}

