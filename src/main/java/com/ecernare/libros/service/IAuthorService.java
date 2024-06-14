package com.ecernare.libros.service;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    AuthorDTO getAuthorById(Long id);

    List<AuthorDTO> getAuthors();

    AuthorDTO insert(AuthorDTO authorDTO);

    Optional<AuthorDTO> insertNewBook(Long id, List<BookDTO> booksDTO);

    Optional<AuthorDTO> update(AuthorDTO authorDTO);

    void delete(long id);

}
