package com.ecernare.libros.service.impl;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.mapper.IAuthorMapper;
import com.ecernare.libros.repository.IAuthorRepository;
import com.ecernare.libros.service.IAuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Log4j2
public class AuthorServiceImpl implements IAuthorService {

    private static final String MESSAGE_AUTHOR_CODE_NULL = "Author id must not be null!";

    private final IAuthorRepository authorRepository;
    private final IAuthorMapper authorMapper;

    @Override
    public Optional<AuthorDTO> getAuthorById(Long id) {
        log.debug("Entrada en getAuthorById {}", id);
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(authorMapper.authorToAuthorDTO(authorOptional.get()));
        }
    }

    @Override
    public List<AuthorDTO> getAuthors() {
        return authorMapper.authorToAuthorDTOList(authorRepository.findAll());
    }

    @Transactional
    @Override
    public AuthorDTO insert(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        Long id = author.getId();

        log.debug("Entrada en insert {}", id);

        if (id != null) {
            if (authorRepository.existsById(id)) {
                return null;
            } else {
                return authorMapper.authorToAuthorDTO(authorRepository.save(author));
            }
        } else {
            return authorMapper.authorToAuthorDTO(authorRepository.save(author));
        }

    }

    @Override
    public Optional<AuthorDTO> update(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        Long id = author.getId();

        log.debug("Entrada en update {}", id);

        Assert.notNull(id, MESSAGE_AUTHOR_CODE_NULL);

        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            return Optional.empty();
        } else {
            Author attachedAuthor = authorRepository.save(author);
            return Optional.ofNullable(authorMapper.authorToAuthorDTO(attachedAuthor));
        }
    }

    @Override
    public void delete(long id) {
        log.debug("Entrada en delete {}", id);

        Assert.notNull(id, MESSAGE_AUTHOR_CODE_NULL);

        Optional<Author> authorOptional = authorRepository.findById(id);

        authorRepository.deleteById(authorOptional.get().getId());
    }
}
