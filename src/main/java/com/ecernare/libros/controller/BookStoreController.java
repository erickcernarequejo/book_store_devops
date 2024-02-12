package com.ecernare.libros.controller;

import com.ecernare.libros.domain.Book;
import com.ecernare.libros.service.IBookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookStoreController {

//    private final IBookStoreService bookstoreService;
//
//    @GetMapping(value = "/user", headers = "Accept=application/json")
//    public ResponseEntity<List<Book>> listAllUsers() {
//        List<Book> users = bookstoreService.insertAuthorWithBooks();
//
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            // You many decide to return HttpStatus.NOT_FOUND
//        }
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

}
