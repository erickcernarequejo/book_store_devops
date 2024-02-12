package com.ecernare.libros.service.impl;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.mapper.IAuthorMapper;
import com.ecernare.libros.repository.IAuthorRepository;
import com.ecernare.libros.service.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorRepository authorRepository;
    private final IAuthorMapper authorMapper;

    @Override
    public Optional<AuthorDTO> getAuthorById(long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return Optional.ofNullable(authorMapper.authorToAuthorDTO(authorOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorDTO insert(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        return authorMapper.authorToAuthorDTO(authorRepository.save(author));
    }

    @Override
    public Author update(Author author) {
        Optional<Author> authorOptional = authorRepository.findById(author.getId());
        if (authorOptional.isPresent()) {
            authorOptional.map(authorUpdate -> {
                authorUpdate.setName(author.getName());
                authorUpdate.setBooks(author.getBooks());
                return authorUpdate;
            });
        }
        return authorRepository.save(author);
    }

    @Override
    public void delete(long id) {

    }
}
