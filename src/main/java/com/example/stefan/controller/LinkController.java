package com.example.stefan.controller;

import com.example.stefan.model.Link;
import com.example.stefan.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Validated
@RestController
public class LinkController
{
  private final LinkService linkService;


  @Autowired
  public LinkController(LinkService linkService)
  {
    this.linkService = linkService;

  }

  @PostMapping("/api/v1/user/createLink")
  public ResponseEntity<Optional<Void>> createLink(@Valid @RequestBody Link link)
  {
    linkService.createLink(link);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/api/v1/user/deleteLink/{contract_reference}")
  public ResponseEntity<Optional<Void>> deleteLink(@PathVariable String contract_reference)
  {
    linkService.deleteLink(contract_reference);
    return ResponseEntity.ok().build();
  }
}
