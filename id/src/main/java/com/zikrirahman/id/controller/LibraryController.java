package com.zikrirahman.id.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zikrirahman.id.model.Book;
import com.zikrirahman.id.repository.BookRepository;

@RestController
@RequestMapping("/library")
public class LibraryController {
    
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/getSubstring/{id}")
    public String getSubstring(
                @PathVariable Long id,
                @RequestParam int startIndex,
                @RequestParam int endIndex){

        Optional<Book> bookOptional = bookRepository.findById(id);

        Book book = bookOptional.get();
        String bookName = book.getName();
        if (endIndex < 0 || endIndex > bookName.length() || startIndex > endIndex) {
            return null;
        }

        String substringValue = bookName.substring(startIndex, endIndex + 1);
        return "Name: " + bookName + ", Substring: " + substringValue;
    }

    @GetMapping("/getToUpperCase/{id}")
    public  String getToUpperCase(@PathVariable Long id){

        Optional<Book> bookOptional = bookRepository.findById(id);

        Book book = bookOptional.get();
        String bookName = book.getName();
        String upperCase = bookName.toUpperCase();
        return upperCase;
    }

    @GetMapping("/getContaining")
    public List<Book> getContaining(
                @RequestParam String subString){
            
            List<Book> result = bookRepository.findByNameContainingOrderByIdDesc(subString);
            return result;
    }

    @GetMapping("/getThan")
    public List<Book> getThan(
                @RequestParam Long total){
            
            List<Book> result = bookRepository.findByTotalLessThanEqual(total);
            return result;
    }

    @GetMapping("/getOrBook")
    public List<Book> getOrBook(
                @RequestParam String name,
                @RequestParam Long total){

            List<Book> result = bookRepository.findByNameOrTotal(name, total);
            return result;
    }

    @GetMapping("/getStream")
    public  List<String> getStream(){

        List<String> strings = List.of("Hello", "World", "Stream");

        List<String> combinedHash = strings.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return combinedHash;
    }

    @GetMapping("/getStreamHash/{id}")
    public  String getStreamHash(@PathVariable Long id) throws NoSuchAlgorithmException{

        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = bookOptional.get();
        String nameBook = book.getName();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(nameBook.getBytes());
        byte[] bytes = md.digest();

        StringBuilder builder = new StringBuilder();
        for(byte b : bytes){
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    @GetMapping("/getFilterStream")
    public List<String> getFilterStream(){

        List<String> names = List.of("Hello", "World", "Stream", "Hash");

        List<String> filteredNames = names.stream()
                                .filter(name -> name.startsWith("H"))
                                .collect(Collectors.toList());
        return filteredNames;
    }

    @GetMapping("/getSplitString")
    public String getSplitString(){
        String name = "Apple, Orange, Banana";
        String[] split = name.split(",");
        String result = String.join("|", split).replace(" ", "");
        return result;
    }

    @GetMapping("/getDateFormatter")
    public String getDateFormatter(){
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
        String result = formatDate.format(date);
        return result;
    }

    @GetMapping("/getPaddingZero/{id}")
    public List<String> getPaddingZero(@PathVariable Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> null);
        Long totalBook = book.getTotal();

        List<String> resultList = new ArrayList<>();
        resultList.add(String.format("%15s", totalBook).replace(" ", "0"));
        resultList.add(String.format("%-15s", totalBook).replace(" ", "0"));
        return resultList;
    }

    @GetMapping("/getPaddingSpace/{id}")
    public List<String> getPaddingSpace(@PathVariable Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> null);
        String nameBook = book.getName();

        List<String> resultList = new ArrayList<>();
        resultList.add("LPZ = " + String.format("%15s", nameBook));
        resultList.add("RPS = " + String.format("%-15s", nameBook));
        return resultList;
    }

    @GetMapping("/getResult/{id}")
    public String getResult(@PathVariable Long id){
        Book bookOptional = bookRepository.findById(id).orElseThrow(() -> null);
        String randomString = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 2);
        String resultString = bookOptional.getName() + " " + bookOptional.getTotal().toString() + randomString;
        return "content : " + resultString;
    }

    @GetMapping("/getLuhn/{cardNumber}")
    public boolean validateCardNumber(@PathVariable String cardNumber) {
        String normalizedNumber = cardNumber.replaceAll("\\D", "");
        StringBuilder reversedNumber = new StringBuilder(normalizedNumber).reverse();

        int sum = 0;
        boolean alternate = false;

        for (int i = 0; i < reversedNumber.length(); i++) {
            int digit = Character.getNumericValue(reversedNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }
}
