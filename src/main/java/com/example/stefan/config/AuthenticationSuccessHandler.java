package com.example.stefan.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
  private final ObjectMapper objectMapper;

  public AuthenticationSuccessHandler()
  {
    this.objectMapper = new ObjectMapper()
        .configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
        .configure(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM, false);
    //this is to not serialize licenseWarning if empty
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

  }

  @Override
  public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException
  {

    final HttpSession session = ((HttpServletRequest) request).getSession(false);

    clearAuthenticationAttributes(request);

    ServletOutputStream outputStream = response.getOutputStream();
    objectMapper.writeValue(outputStream, new LoginResult("OK"));
  }

}
