package com.ecernare.libros.mapper;

import com.ecernare.libros.domain.Author;
import com.ecernare.libros.domain.Book;
import com.ecernare.libros.dto.AuthorDTO;
import com.ecernare.libros.dto.BookDTO;
import org.mapstruct.*;

import java.util.List;

@Named("IAuthorMapper")
@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface IAuthorMapper {

    @Mapping(source = "books", target = "booksDTO")
    AuthorDTO authorToAuthorDTO(Author author);

    List<AuthorDTO> authorToAuthorDTOList(List<Author> author);

    @Mapping(source = "booksDTO", target = "books")
    Author authorDTOToAuthor(AuthorDTO authorDTO);

    List<Author> authorDTOToAuthorList(List<AuthorDTO> author);

    @Mapping(source = "booksDTO", target = "books")
    void updateAuthorFromAuthorDTO(AuthorDTO authorDTO, @MappingTarget Author author);

    BookDTO bookToBookDTO(Book book);

    List<BookDTO> bookToBookDTO(List<Book> book);

    Book bookDTOToBook(BookDTO bookDTO);

    List<Book> bookDTOToBook(List<BookDTO> bookDTO);

    void updateBookFromBookDTO(Book book, @MappingTarget BookDTO bookDTO);

}
