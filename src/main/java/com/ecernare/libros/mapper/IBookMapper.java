package com.ecernare.libros.mapper;

import com.ecernare.libros.domain.Book;
import com.ecernare.libros.dto.BookDTO;
import org.mapstruct.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Named("IBookMapper")
@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface IBookMapper {

    BookDTO bookToBookDTO(Book book);

    List<BookDTO> bookToBookDTO(List<Book> book);

    Book bookDTOToBook(BookDTO bookDTO);

    List<Book> bookDTOToBook(List<BookDTO> bookDTO);

    void updateBookFromBookDTO(Book book, @MappingTarget BookDTO bookDTO);

    @Transactional(readOnly = true)
    @Query("SELECT b FROM Book b WHERE b.author.id = :id")
    Book fetchPageBooksOfAuthorById(Long id);

}
