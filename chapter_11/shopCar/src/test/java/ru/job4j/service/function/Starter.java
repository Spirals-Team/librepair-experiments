package ru.job4j.service.function;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 17.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Starter {
    private static final Logger LOG = LogManager.getLogger(Starter.class);

    public static void main(String[] args) {
        Starter s = new Starter();
        System.out.println(s.d());
//        Person p = new Person("aaa", new Date(System.currentTimeMillis()),15D);
//        System.out.println(s.processHospitel(p, 4, (g, d)->0.8* 8* g*d));

    }

    public Double processHospitel(Person p, Integer day, ProcessHospital ph) {
        return ph.process(p.getGrade(), day);
    }

    public int d() {
        try {
            return 5;
        } finally {
            System.out.println("10");
        }
    }
    interface ProcessHospital {
        Double process(Double grade, Integer days);
    }
}
