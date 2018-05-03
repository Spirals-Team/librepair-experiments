package ru.job4j.prof.dopclass;
/**
 * зесь будет осуществляться диагностика оборудования, скжем это будет некий диагностический центр
 * и выноситься вердикт, позволяет ли квалификация инженера починить оборудование? ,
 * не привысил ли срок эксплуатации сраока гарантии?
 * и если позволяет то инженер ремонтирует 100%
 */

import ru.job4j.prof.Engineer;

public class Utilits {

    private static final String HELLO = "Здравствуйте, меня зовут ";
    private static final String NONREMONT = ", диагностика показала что в ремонте нет необходимости.";
    private static final String REMONT = ", диагностика показала что оборудование нуждается в ремонте, начинаю ремонт.";
    private static final String REMONTFINAL = " Ремонт окончен";
    private static final String DIRECTPROFIL = ", я специалист по профилю ";
    private static final String DIRECTPROFILLINK = ". Вам нужен инженер другого профиля";
    private static final String REMONTOTKAZ = ", Сожалею, но срок службы вашего оборудования превысил гарантийный срок, в ремонте отказано.";
    private static String result = "";
    private static boolean remont;

    private static boolean refactorDuplicateCOD(Engineer engineer, Rabsost rabsost, int garantia, int lifetime, Direction direction) {
        if (engineer.getDirection().equals(direction)) {
            if (garantia >= lifetime) {
                if (rabsost.equals(Rabsost.РАБОЧЕЕ)) {
                    result = HELLO + engineer.getName() + NONREMONT;
                    remont = false;
                } else if (rabsost.equals(Rabsost.СЛОМАНО)) {
                    result = HELLO + engineer.getName() + REMONT;
                    remont = true;
                    rabsost = Rabsost.РАБОЧЕЕ;
                }
            } else {
                result = HELLO + engineer.getName() + REMONTOTKAZ;
                remont = false;
            }
        }   else {
            result = HELLO + engineer.getName() + DIRECTPROFIL + engineer.getDirection() + DIRECTPROFILLINK;
            remont = false;
        }

        return remont;
    }

    public static String oborudovaneiDiagnostika(Auto auto, Engineer engineer) {
        if (refactorDuplicateCOD(engineer, auto.getRabsost(), auto.getGarantia(), auto.getLifetime(), Direction.MECHANIC)) {
            engineer.mends(auto);
            result = result + REMONTFINAL;
        }
        System.out.println(result + " " + auto.getModel() + " " + auto.getRabsost());
        return result;
    }

    public static String oborudovaneiDiagnostika(Pc pc, Engineer engineer) {
        if (refactorDuplicateCOD(engineer, pc.getRabsost(), pc.getGarantia(), pc.getLifetime(), Direction.IT)) {
            engineer.mends(pc);
            result = result + REMONTFINAL;
        }
        System.out.println(result + " " + pc.getModel() + " " + pc.getRabsost());
        return result;
    }

    public static String oborudovaneiDiagnostika(Lift lift, Engineer engineer) {
        if (refactorDuplicateCOD(engineer, lift.getRabsost(), lift.getGarantia(), lift.getLifetime(), Direction.ELEVATOR_ENGINEER)) {
            engineer.mends(lift);
            result = result + REMONTFINAL;
        }
        System.out.println(result + " " + lift.getModel() + " " + lift.getRabsost());
        return result;
    }

}
