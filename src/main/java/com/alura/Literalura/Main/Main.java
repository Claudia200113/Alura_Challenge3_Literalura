package com.alura.Literalura.Main;

import com.alura.Literalura.Service.APIConsumer;
import com.alura.Literalura.Service.AuthorService;
import com.alura.Literalura.Service.BookService;
import com.alura.Literalura.Service.JsonConversor;
import com.alura.Literalura.dto.BookDTO;
import com.alura.Literalura.dto.SearchDTO;
import com.alura.Literalura.models.Author;
import com.alura.Literalura.models.Book;

import javax.sound.midi.Soundbank;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private String userInput ="";
    private Scanner scanner = new Scanner(System.in);
    private final String MAIN_URL = "https://gutendex.com/books/";


    private APIConsumer apiConsumption = new APIConsumer();
    private JsonConversor jsonConversor = new JsonConversor();
    private BookService bookService;
    private AuthorService authorService;

    public Main(BookService bookService, AuthorService authorService){
        this.bookService = bookService;
        this.authorService = authorService;
    }

    static void menu() {
        System.out.println("""
                    Please choose an option
                    1) Look by title
                    2) Show searched books
                    3) Show searched authors
                    4) Look authors (from your search) alive in a specific year 
                    5) Look books by language
                    6) Show most downloaded books
                    7) Exit
                    """);
    }

    public void ShowMenu() {
        int userOption = 0;

        //menu();

        do {
            try {
                menu();
                System.out.print("Enter an option please \n");
                String input = scanner.nextLine();

                if (!input.matches("\\d+")) {
                    throw new InputMismatchException("Only enter a valid number please");
                }

                userOption = Integer.parseInt(input);

                if (userOption < 0 || userOption > 7) {
                    System.out.println("The number is not valid, please enter an option from the menu. \n");
                } else if (userOption != 7) {
                    ConsultApi(userOption);
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter only a valid number.\n");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
            }
        } while (userOption != 7);

        System.out.println("Thanks for using our service \n");
        scanner.close();
    }

    private void ConsultApi(int option) {
        switch(option){
            case 1 -> LookByTitle();
			case 2 -> SearchedBooks();
			case 3 -> SearchedAuthors();
			case 4 -> LookByYear();
			case 5 -> LookByLanguages();
            case 6 ->TopDownloadedBooks();
        }
    }

    ////////////////////    METHODS   ///////////////////////////

    private void LookByTitle(){
        System.out.println("Enter the title please");
        String title =  scanner.nextLine();

        var FormatedTitle = title.replace(" ", "%20");

        if(!bookService.isExist(title)){
            String url=MAIN_URL +"?search="+ URLEncoder.encode(FormatedTitle);

            var json = apiConsumption.getDataApi(url);

            SearchDTO response=jsonConversor.getData(json,SearchDTO.class);
            List<BookDTO> bookDto = response.results();
            Optional<Book> book = Optional.ofNullable(bookDto)
                    .flatMap(list -> list.stream().findFirst())
                    .map(Book::new);
            if(book.isPresent()){
                bookService.saveBook(book.get());
                System.out.println("----------Book Data---------- \n" + book.get());
                System.out.println("\n Book saved");
            }else{
                System.out.println("Book wasn't found");
            }

        }else{
            System.out.println("Book has been already searched");
        }

    }

    private void SearchedBooks(){
        List<Book> books=bookService.getAll();
        books.forEach(System.out::println);
    }

    private void SearchedAuthors(){
        List<Author> authors =authorService.getAllAuthors();
        authors.forEach(System.out::println);
    }

    private void LookByYear(){
        System.out.println("Enter the year you want to search:");

        int year = scanner.nextInt();

       List<Author> authorsList = authorService.getAuthorsByYear(year);

        if(!authorsList.isEmpty()){
            System.out.println("The authors that were alive are:");
            authorsList.forEach(System.out::println);
        }else{
            System.out.println("No authors were alive");
        }
    }

    private void LookByLanguages(){
        System.out.println("Enter the language you want to look for:\n" +
                "Consider \n" +
                "English -> en\n" +
                "Spanish -> es\n" +
                "French -> fr\n");

        String languageToLook =  scanner.nextLine();

        if (languageToLook.matches("[a-zA-Z]{2}")){
            Optional <List<Book>> booksLangauges = bookService.getBooksInLanguage(languageToLook);
            if(booksLangauges.isPresent()){
                DoubleSummaryStatistics statistics = booksLangauges.get().stream()
                        .collect(Collectors.summarizingDouble(Book::getDownloads));

                System.out.println("Total books in that language: " + statistics.getCount());
                System.out.println("Total downloads are: " + statistics.getSum());
                System.out.println("Average downloads in that language are: " + statistics.getAverage());
            }else{
                System.out.println("No books were found in that language");
            }

        }else{
            throw new IllegalArgumentException("Please enter only the valid codes");
        }
        var json = apiConsumption.getDataApi(
                MAIN_URL + "languages=" +
                        languageToLook.replace("",","));

    }

    private void TopDownloadedBooks(){

        List<Book> books= bookService.topFiveBooks();
        System.out.println("The most downloaded books are: \n");
        books.forEach(System.out::println);
    }

}

