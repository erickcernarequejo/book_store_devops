package com.ecernare.libros.service;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    Optional<AuthorDTO> getAuthorById(long id);

    List<Author> getAuthors();

    AuthorDTO insert(AuthorDTO authorDTO);

    Author update(Author author);

    void delete(long id);

}
