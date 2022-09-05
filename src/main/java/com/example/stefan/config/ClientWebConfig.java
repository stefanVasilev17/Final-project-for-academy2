package com.example.stefan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Property of CODIX SA
 * Date: 6/4/2020 Time: 5:29 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author lparvov
 */
@Configuration
public class ClientWebConfig implements WebMvcConfigurer
{

  private final WebApplicationContext wac;


  @Autowired
  public ClientWebConfig(WebApplicationContext wac)
  {
    Assert.notNull(wac, "WebApplicationContext mus be init.");
    this.wac = wac;
  }

  @Bean(name = {"validator", "mvcValidator"})
  public LocalValidatorFactoryBean validator()
  {

    return new LocalValidatorFactoryBean();
  }

  /**
   * @return LocalValidatorFactoryBean
   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
   * {@link WebMvcConfigurationSupport#mvcValidator()}
   */
  @Override
  public org.springframework.validation.Validator getValidator()
  {

    LocalValidatorFactoryBean validator = wac.getAutowireCapableBeanFactory().getBean(LocalValidatorFactoryBean.class);
    Assert.notNull(validator, "Validator must be init.");
    return validator;
  }

  /**
   * This beans must be static, to be instantiated before the other MethodValidationPostProcessors.
   * Otherwise, some are not instantiated and it breaks the transactional manager
   *
   * @see <a href="https://stackoverflow.com/questions/39533215/methodvalidationpostprocessor-breaks-transactional">
   * MethodValidationPostProcessor breaks transactional</a>
   * @see <a href="https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/context/annotation/Bean.html"></a>
   * @see <a href="https://docs.spring.io/spring/docs/5.0.x/javadoc-api/org/springframework/context/annotation/Bean.html"></a>
   * <p>
   * Special consideration must be taken for @Bean methods that return Spring BeanFactoryPostProcessor (BFPP) types.
   * Because BFPP objects must be instantiated very early in the container lifecycle,
   * they can interfere with processing of annotations such as @Autowired, @Value, and @PostConstruct within @Configuration classes.
   * To avoid these lifecycle issues, mark BFPP-returning @Bean methods as static. For example:
   * <p>
   * Bean
   * public static PropertyPlaceholderConfigurer ppc() {
   * // instantiate, configure and return ppc ...
   * }
   * <p>
   * By marking this method as static, it can be invoked without causing instantiation of its declaring @Configuration class, thus avoiding the above-mentioned lifecycle conflicts.
   * Note however that static @Bean methods will not be enhanced for scoping and AOP semantics as mentioned above.
   * This works out in BFPP cases, as they are not typically referenced by other @Bean methods.
   * As a reminder, a WARN-level log message will be issued for any non-static @Bean methods having a return type assignable to BeanFactoryPostProcessor.
   */
  @Bean(name = "methodValidationPostProcessor")
  public static MethodValidationPostProcessor methodValidationPostProcessor
  (
      @Qualifier("validator") LocalValidatorFactoryBean validator
  )
  {
    MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();

    methodValidationPostProcessor.setValidatorFactory(validator); //localValidatorFactoryBean.getValidator() should be return javax.validation.Validator

    return methodValidationPostProcessor;
  }
}
