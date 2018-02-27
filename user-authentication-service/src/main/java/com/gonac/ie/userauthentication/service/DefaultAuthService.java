/*
  Created by h.odunsi on 05/01/2018.
 */
package com.gonac.ie.userauthentication.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultAuthService implements AuthService
{
   @Override
   public String hello()
   {
      return "Hello world";
   }
}
