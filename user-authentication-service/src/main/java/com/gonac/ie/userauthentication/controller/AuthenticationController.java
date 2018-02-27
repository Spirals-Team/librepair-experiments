/*
  Created by h.odunsi on 05/01/2018.
 */
package com.gonac.ie.userauthentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController
{

   @RequestMapping(value = "/hello", method = RequestMethod.GET)
   public String hello()
   {
      return "Hello world";
   }


}
