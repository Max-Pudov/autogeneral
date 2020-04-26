package au.com.autogeneral.config;

import static com.opentable.db.postgres.embedded.EmbeddedPostgres.builder;
import static java.util.Map.of;
import static org.flywaydb.core.Flyway.configure;

import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author iklas.alzhanov
 * @since 2/26/20
 */
@TestConfiguration
public class RepositoryTestConfig {

  @Bean
  public DataSource dataSource() throws IOException {
    DataSource dataSource = builder()
        .start()
        .getPostgresDatabase(of("currentSchema", "autogeneral"));
    createDbSchema(dataSource);
    return dataSource;
  }

  private void createDbSchema(DataSource dataSource) {
    configure()
        .schemas("autogeneral")
        .dataSource(dataSource)
        .locations("db/migration")
        .load()
        .migrate();
  }
}