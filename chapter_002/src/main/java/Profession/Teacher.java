package profession;

/**
 * Метод teachesStudents возвращает строку "Преподаватель Ольга Ивановна учит студента Андреева двум дисциплинам",
 * где "Ольга Ивановна" это поле "name" из класса Profession, а "Сергей" и "двум" - аргументы класса Students.
 *
 * @author Alexandar Vysotskiy
 * @version 1.0
 */

public class Teacher extends Profession {
    public String teachesStudents() {
        Profession teacher = new Profession();
        Students student = new Students();
        teacher.setProfession("Преподаватель");
        teacher.setName("Ольга Ивановна");
        student.nameStudents = "Андреева";
        student.items = "двум";
        return teacher.getProfession() + " " + teacher.getName() + " учит студента " + student.nameStudents + " " + student.items + " дисциплинам";
    }
}
