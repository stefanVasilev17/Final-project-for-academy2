package com.example.stefan.ServiceTests;

import com.example.stefan.model.Link;
import com.example.stefan.model.User;
import com.example.stefan.service.LinkService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LinkTestsMock
{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private LinkService  linkService;

  @Test
  void contextLoads()
  {
  }

  @Test
  void testPostOK200() throws Exception
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

    Link link = new Link("ehooo","3nenene","DEBTOR");

    mockMvc.perform(post("/api/v1/user/createLink")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(link)))
        .andExpect(status().isOk())
        .andDo(print());
    linkService.deleteLink(link.getContract_reference());
  }

  @Test
  void testPostLinkUnAuthorized401() throws Exception
  {

    Link link = new Link("shshs","ehooo","DEBTOR");
    mockMvc.perform(post("/api/v1/user/createLink")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(link)))
        .andExpect(status().isUnauthorized())
        .andDo(print());
    linkService.deleteLink(link.getContract_reference());
  }

  @Test
  void testDeleteLinkIsOk200() throws Exception
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

    Link link = new Link("ehoo223o","hohoh","DEBTOR");
    linkService.createLink(link);

    mockMvc.perform(delete("/api/v1/user/deleteLink/{contract_reference}", link.getContract_reference())
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token)
        .content(objectMapper.writeValueAsString(link)))
        .andExpect(status().isOk())
        .andDo(print());

  }

}

