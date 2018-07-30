package com.psg.glms.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.psg.glms.model.BookRequest;
import com.psg.glms.repository.BookRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.*;

@RestController
@RequestMapping("api/Bookrequest")
public class BookRequestController {

    @Autowired
  private BookRequestRepository bookRequestRepository;

//   @GetMapping("/get")
//   public @ResponseBody ResponseEntity<List<BookRequest>> all() {
//     Stream<BookRequest> bookRequeststream = bookRequestRepository.findAllCustomers();
    
//     Map<BookRequest, Long> counted = bookRequeststream
//             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

//         System.out.println(counted);  

//       return new ResponseEntity<>(bookRequestRepository.findAll(), HttpStatus.OK);
//   }

  @RequestMapping("/findall")
  public String findAllByStream() {
      List<String> mapstream = Collections.emptyList();

      try (Stream<BookRequest> stream = bookRequestRepository.findAllCustomers()) {
          mapstream = stream.map(customer -> customer.toString()).collect(Collectors.toList());
      }

      return mapstream.toString();
  }
    
}