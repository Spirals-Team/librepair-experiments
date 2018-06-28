package ru.job4j.search;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Integer.compare;

public class SortUser {
    /**
     * Метод превращает список юзеров во множество юзеров отсортированный по возрасту.
     * @param - список пользователей
     * @return множество юзеров отсортированных по возрасту
     */
   public Set<UserNew> sort(List<UserNew> users) {
       TreeSet<UserNew> result = new TreeSet<>();
       for (UserNew user : users) {
           result.add(user);
       }
       return result;
   }

    /**
     * Метод сортирует список юзеров по длине имен
     * @param - список пользователей
     */
   public List<UserNew> sortNameLength(List<UserNew> users) {
       users.sort(
               new Comparator<UserNew>() {
                   @Override
                   public int compare(UserNew o1, UserNew o2) {
                       return Integer.compare(o1.getName().length(), o2.getName().length());
                   }

               }
       );
       return users;
   }

    /**
     * Метод сортирует список юзеров сначала по имени в лексикографияеском порядке, потом по возрасту
     * @param - список пользователей
     */
   public List<UserNew> sortByAllFields(List<UserNew> users) {
       users.sort(
               new Comparator<UserNew>() {
                   @Override
                   public int compare(UserNew o1, UserNew o2) {
                       int res = o1.getName().compareTo(o2.getName());
                       return res != 0 ? res : Integer.compare(o1.getAge(), o2.getAge());
                   }

               }
       );
       return users;
   }
}