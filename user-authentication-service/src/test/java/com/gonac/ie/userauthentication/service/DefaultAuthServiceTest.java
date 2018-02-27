/*
  Created by h.odunsi on 05/01/2018.
 */
package com.gonac.ie.userauthentication.service;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DefaultAuthServiceTest
{

   @Rule
   public MockitoRule mockitoRule = MockitoJUnit.rule();

   @InjectMocks
   DefaultAuthService defaultAuthService;


   @BeforeClass
   public static void setUp()
   {

   }


   @Test
   public void test1()
   {
      assertThat(defaultAuthService.hello(), is( ("Hello world")));
   }

}
