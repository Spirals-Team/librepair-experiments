package org.jboss.resteasy.test.resource.generic;

public class Student
{
   private String name;

   public Student()
   {
   }

   public Student(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "Student: " + name;
   }
}
