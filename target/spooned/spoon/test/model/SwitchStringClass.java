package spoon.test.model;


public class SwitchStringClass {
    public java.lang.String getTypeOfDayWithSwitchStatement(java.lang.String dayOfWeekArg) {
        java.lang.String typeOfDay;
        switch (dayOfWeekArg) {
            case "Monday" :
                typeOfDay = "Start of work week";
                break;
            case "Tuesday" :
            case "Wednesday" :
            case "Thursday" :
                typeOfDay = "Midweek";
                break;
            case "Friday" :
                typeOfDay = "End of work week";
                break;
            case "Saturday" :
            case "Sunday" :
                typeOfDay = "Weekend";
                break;
            default :
                throw new java.lang.IllegalArgumentException(("Invalid day of the week: " + dayOfWeekArg));
        }
        return typeOfDay;
    }
}

