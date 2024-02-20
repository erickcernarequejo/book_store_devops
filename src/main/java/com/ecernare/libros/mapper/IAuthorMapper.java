package com.ecernare.libros.mapper;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAuthorMapper {

    AuthorDTO authorToAuthorDTO(Author author);

    List<AuthorDTO> authorToAuthorDTO(List<Author> author);

    Author authorDTOToAuthor(AuthorDTO author);

    List<Author> authorDTOToAuthor(List<AuthorDTO> author);

    void updateAuthorFromAuthorDTO(AuthorDTO authorDTO, @MappingTarget Author author);

}
