package ru.job4j.pseudo;

/**
 * @author Alex Rumyantsev
 */

    public class Triangle implements Shape {
        @Override
        // Обязательное определение метода Рисовать для треуголника
        public String draw() {
            StringBuilder pic = new StringBuilder();
            pic.append("   +   ");
            pic.append("  +++  ");
            pic.append(" +++++ ");
            pic.append("+++++++");
            return pic.toString();
        }
    }
