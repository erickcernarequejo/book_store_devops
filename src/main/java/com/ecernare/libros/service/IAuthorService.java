package com.ecernare.libros.service;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    Optional<AuthorDTO> getAuthorById(Long id);

    List<AuthorDTO> getAuthors();

    AuthorDTO insert(AuthorDTO authorDTO);

    Optional<AuthorDTO> update(Long id, AuthorDTO authorDTO);

    void delete(long id);

}
