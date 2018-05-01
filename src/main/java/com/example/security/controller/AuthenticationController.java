package com.example.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author VIVEK KUMAR SINGH
 * @since (2018-04-30 15:41:27)
 */
@RestController
@RequestMapping("/api")
public class AuthenticationController {

  @GetMapping("/hello")
  public String hello(@AuthenticationPrincipal final UserDetails userDetails) {

    if (userDetails != null) {
      StringBuffer sb = new StringBuffer("Hello ");
      sb.append(userDetails.getUsername()).append(" !!!\n");

      userDetails.getAuthorities().forEach(role -> sb.append("your role is : ").append(role));

      return sb.toString();
    }

    return "Hello World !!!";
  }
}
