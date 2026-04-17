package com.example.onlinebookstore;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookService bookService) {
        return args -> {
            Book firstBook = new Book();
            firstBook.setTitle("Clean Code");
            firstBook.setAuthor("Robert Martin");
            firstBook.setIsbn("9780132350884");
            firstBook.setPrice(BigDecimal.valueOf(35.99));
            firstBook.setDescription("A handbook of agile software craftsmanship");
            firstBook.setCoverImage("clean-code.jpg");

            Book secondBook = new Book();
            secondBook.setTitle("Effective Java");
            secondBook.setAuthor("Joshua Bloch");
            secondBook.setIsbn("9780134685991");
            secondBook.setPrice(BigDecimal.valueOf(45.50));
            secondBook.setDescription("Best practices for the Java platform");
            secondBook.setCoverImage("effective-java.jpg");

            bookService.save(firstBook);
            bookService.save(secondBook);

            bookService.findAll().forEach(book ->
                    System.out.println(book.getTitle() + " by " + book.getAuthor()));
        };
    }
}
