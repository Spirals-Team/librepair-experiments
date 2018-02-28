package com.kh013j;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import java.io.IOException;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V10;

@MockBean(JavaMailSender.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Configuration
@EnableJpaRepositories("com.kh013j.model.repository")
public class CaffinnoApplicationTestConfiguration {
    @Bean(destroyMethod = "stop")
    public EmbeddedPostgres startPostgres() throws IOException {
        final EmbeddedPostgres postgres = new EmbeddedPostgres(V10);
        final String url = postgres.start("localhost", 5433, "test", "my", "1234");
        return postgres;
    }
}
