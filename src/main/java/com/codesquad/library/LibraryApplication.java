package com.codesquad.library;

import com.codesquad.library.domain.Article;
import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.domain.repositories.ArticleRepository;
import com.codesquad.library.domain.repositories.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties
@ComponentScan
public class LibraryApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:application-local.yml,"
			+ "classpath:application-prod.yml,"
			+ "classpath:application-remotetest.yml,"
			+ "/home/ubuntu/app/nonstop/config/library-config.yml";

	public static void main(String[] args) {
        new SpringApplicationBuilder(LibraryApplication.class)
                .sources(LibraryApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .build()
                .run(args);
	}

	@Bean
    public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder, MemberRepository memberRepository, ArticleRepository articleRepository) {
	    return args -> {
	        String password = passwordEncoder.encode("1234");

	        Member member = Member.builder()
                    .name("정휘준")
                    .loginEmail("shit@fuck.com")
                    .password(password)
                    .roles(Member.MemberRoles.USER)
                    .build();

	        memberRepository.save(member);

	        Article article = Article.builder()
                    .content("fuck you!")
                    .title("fuck")
                    .urgent(true)
                    .build();

	        articleRepository.save(article);
        };
    }
}
