package com.ecernare.libros.controller;

import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final IAuthorService authorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {

        Optional<AuthorDTO> author = authorService.getAuthorById(id);

        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author with id " + id + " not found.");
        } else {
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createUser(@RequestBody AuthorDTO authorDTO) {

        return new ResponseEntity<>(authorService.insert(authorDTO), HttpStatus.CREATED);
    }

}
