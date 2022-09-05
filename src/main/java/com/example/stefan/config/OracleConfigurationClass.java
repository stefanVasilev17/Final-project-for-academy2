package com.example.stefan.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.dialect.AnsiDialect;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class OracleConfigurationClass extends AbstractJdbcConfiguration
{
  final String URL = "jdbc:oracle:thin:@83.228.124.173:6223/stefan_vasilev";

  @Bean
  DataSource dataSource() throws SQLException
  {
    OracleDataSource dataSource = new OracleDataSource();
    dataSource.setUser("stefan_vasilev");
    dataSource.setPassword("dbpass");
    dataSource.setURL(URL);
    dataSource.setImplicitCachingEnabled(true);
    dataSource.setFastConnectionFailoverEnabled(true);
    return dataSource;
  }

  @Bean
  public Dialect jdbcDialect(NamedParameterJdbcOperations namedTemplate)
  {
    return AnsiDialect.INSTANCE;
  }

  @Bean(name = {"txManager", "transactionManager"})
  public PlatformTransactionManager txManager() throws SQLException
  {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean(name = "namedTemplate")
  @Primary
  public NamedParameterJdbcOperations namedTemplate() throws SQLException
  {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
    jdbcTemplate.setFetchSize(30);

    jdbcTemplate.setMaxRows(50000);

    NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
    npjt.setCacheLimit(384);
    return npjt;
  }


}
