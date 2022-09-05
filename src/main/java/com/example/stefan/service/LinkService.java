package com.example.stefan.service;

import com.example.stefan.model.Link;
import com.example.stefan.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class LinkService
{
  private final LinkRepository linkRepository;

  @Autowired
  public LinkService(LinkRepository linkRepository)
  {
    this.linkRepository = linkRepository;
  }

  public void createLink(Link link)
  {
    linkRepository.createLink(link);
  }

  public void deleteLink(String contract_reference)
  {
    linkRepository.deleteLink(contract_reference);
  }
}
