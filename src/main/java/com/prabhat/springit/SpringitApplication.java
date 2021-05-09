package com.prabhat.springit;

import com.prabhat.springit.domain.Comment;
import com.prabhat.springit.domain.Link;
import com.prabhat.springit.repository.CommentRepository;
import com.prabhat.springit.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringitApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
	}

//	@Bean
	CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
		return args -> {
			Link link = new Link("Getting Started with Spring boot 2", "www.google.com");
			linkRepository.save(link);

			Comment comment = new Comment("This is awesome", link);
			commentRepository.save(comment);
			link.addComment(comment);

			System.out.println("=======================");
			System.out.println("Data Inserted in database");
			System.out.println("=======================");
		};
	};
}
