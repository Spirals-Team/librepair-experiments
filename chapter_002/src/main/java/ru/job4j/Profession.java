package ru.job4j;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Profession {
    String name;
    float salary;
    int age;

    public String getName() {
        return this.name;
    }

    class Teacher extends Profession {
        public int learn(Student student) {
            student.knowledge = 100;
            return student.knowledge;
        }
    }

    class Engineer extends Profession {
        public boolean designVerification(Plan plan) {
            plan.tested = true;
            return plan.tested;
        }
    }

    class Doctor extends Profession {
        public int treat(Patient patient) {
            patient.treatment = 100;
            return patient.treatment;
        }
        public String diagnoseHeal(Patient patient) {
            return "Doctor " + getName() + " treats " + patient.name;
        }
    }

    class Student {
        String name;
        int knowledge;
    }

    class Plan {
        String name;
        boolean tested;
    }

    class Patient {
        String name;
        String ill;
        int treatment;
    }
}
