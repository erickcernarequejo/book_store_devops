package com.ecernare.libros.service;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.mapper.IAuthorMapper;
import com.ecernare.libros.repository.IAuthorRepository;
import com.ecernare.libros.service.impl.AuthorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AuthorServiceImplTest {

    @MockBean
    private IAuthorRepository authorRepository;

    @MockBean
    private IAuthorMapper authorMapper;

    private AuthorServiceImpl authorService;

    @Before
    public void setup() {
        authorService = new AuthorServiceImpl(authorRepository, authorMapper);
    }

    @Test
    public void testGetAuthorById() {
        Author author = new Author(1L, "Alicia Tom", "Anthology", 38, List.of());
        AuthorDTO authorDTO = new AuthorDTO(1L, "Alicia Tom","Anthology", 38);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.authorToAuthorDTO(author)).thenReturn(authorDTO);

        Optional<AuthorDTO> optionalAuthorDTO = authorService.getAuthorById(1L);

        verify(authorRepository, times(1)).findById(1L);
        assertNotNull(optionalAuthorDTO);
        assertEquals(author.getId(), optionalAuthorDTO.get().getId());
        assertEquals(author.getName(), optionalAuthorDTO.get().getName());

    }

    @Test
    public void testInsertAuthor() {
        Author author = new Author(1L, "Alicia Tom", "Anthology", 38, List.of());
        AuthorDTO authorDTO = new AuthorDTO(1L, "Alicia Tom","Anthology", 38);
        when(authorMapper.authorDTOToAuthor(authorDTO)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.authorToAuthorDTO(author)).thenReturn(authorDTO);

        AuthorDTO authorAttachedDTO = authorService.insert(authorDTO);

        verify(authorRepository, times(1)).save(author);
        assertNotNull(authorAttachedDTO);
        assertEquals(author.getId(), authorAttachedDTO.getId());
        assertEquals(author.getName(), authorAttachedDTO.getName());

    }

}
