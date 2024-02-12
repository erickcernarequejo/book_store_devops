package com.ecernare.libros.service.impl;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.domain.Book;
import com.ecernare.libros.repository.IAuthorRepository;
import com.ecernare.libros.repository.IBookRepository;
import com.ecernare.libros.service.IBookStoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookStoreServiceImpl implements IBookStoreService {

    private final IAuthorRepository authorRepository;
    private final IBookRepository bookRepository;

    @Override
    public void insertAuthorWithBooks() {
        Author author = new Author();
        author.setName("Alicia Tom");

        Book book = new Book();
        book.setIsbn("001-AT");
        book.setTitle("The book of swords");

        author.addBook(book); // use addBook() helper

        authorRepository.save(author);
    }
}
