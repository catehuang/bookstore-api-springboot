package com;

import com.model.Book;
import com.model.Role;
import com.model.User;
import com.repository.BookRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner roleData(RoleRepository roleRepo) {
		return args -> {
			List<String> roles = Arrays.asList("admin", "user");
			roles.forEach(r -> roleRepo.save(new Role(r)));
		};
	}

	@Bean
	public CommandLineRunner userData(UserRepository userRepo) {
		return args -> {
			userRepo.save(new User("test", "test@exmaple.com", "password"));
			userRepo.save(new User("admin", "admin@exmaple.com", "password"));
		};
	}

	@Bean
	public CommandLineRunner bookData(BookRepository bookRepo) {
		return args -> {
			bookRepo.save(new Book("It Ends with Us: A Novel (Volume 1)",
					"Colleen Hoover",
					"https://images-na.ssl-images-amazon.com/images/I/71EwoNQypZL._AC._SR360,460.jpg",
					3.2,
					7221,
					"In this \"brave and heartbreaking novel that digs its claws into you and doesn't let go, " +
							"long after you've finished it\" (Anna Todd, New York Times bestselling author) " +
							"from the #1 New York Times bestselling author of All Your Perfects, a workaholic with " +
							"a too-good-to-be-true romance can't stop thinking about her first love. " +
							"Lily hasn't always had it easy, but that's never stopped her from working hard for" +
							" the life she wants. She's come a long way from the small town where she grew up—she " +
							"graduated from college, moved to Boston, and started her own business. And when she " +
							"feels a spark with a gorgeous neurosurgeon named Ryle Kincaid, everything in Lily's " +
							"life seems too good to be true. Ryle is assertive, stubborn, maybe even a " +
							"little arrogant. He's also sensitive, brilliant, and has a total soft spot for " +
							"Lily. And the way he looks in scrubs certainly doesn't hurt. Lily can't get him " +
							"out of her head. But Ryle's complete aversion to relationships is disturbing. " +
							"Even as Lily finds herself becoming the exception to his \"no dating\" rule, she " +
							"can't help but wonder what made him that way in the first place. As questions about " +
							"her new relationship overwhelm her, so do thoughts of Atlas Corrigan—her first love " +
							"and a link to the past she left behind. He was her kindred spirit, her protector." +
							" When Atlas suddenly reappears, everything Lily has built with Ryle is threatened. " +
							"An honest, evocative, and tender novel, It Ends with Us is \"a glorious and touching " +
							"read, a forever keeper. The kind of book that gets handed down\" (USA TODAY).",
					13.51,
					15
					));
			bookRepo.save(new Book("Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
					"James Clear",
					"https://images-na.ssl-images-amazon.com/images/I/81wgcld4wxL._AC._SR360,460.jpg",
					4.5,
					70221,
					"In this \"brave and heartbreaking novel that digs its claws into you and doesn't let go, " +
							"long after you've finished it\" (Anna Todd, New York Times bestselling author) " +
							"from the #1 New York Times bestselling author of All Your Perfects, a workaholic with " +
							"a too-good-to-be-true romance can't stop thinking about her first love. " +
							"Lily hasn't always had it easy, but that's never stopped her from working hard for" +
							" the life she wants. She's come a long way from the small town where she grew up—she " +
							"graduated from college, moved to Boston, and started her own business. And when she " +
							"feels a spark with a gorgeous neurosurgeon named Ryle Kincaid, everything in Lily's " +
							"life seems too good to be true. Ryle is assertive, stubborn, maybe even a " +
							"little arrogant. He's also sensitive, brilliant, and has a total soft spot for " +
							"Lily. And the way he looks in scrubs certainly doesn't hurt. Lily can't get him " +
							"out of her head. But Ryle's complete aversion to relationships is disturbing. " +
							"Even as Lily finds herself becoming the exception to his \"no dating\" rule, she " +
							"can't help but wonder what made him that way in the first place. As questions about " +
							"her new relationship overwhelm her, so do thoughts of Atlas Corrigan—her first love " +
							"and a link to the past she left behind. He was her kindred spirit, her protector." +
							" When Atlas suddenly reappears, everything Lily has built with Ryle is threatened. " +
							"An honest, evocative, and tender novel, It Ends with Us is \"a glorious and touching " +
							"read, a forever keeper. The kind of book that gets handed down\" (USA TODAY).",
					15.61,
					9
			));
		};
	}
}
