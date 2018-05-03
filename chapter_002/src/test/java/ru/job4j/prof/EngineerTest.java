package ru.job4j.prof;
/**
 * тестируем класс инженер и класс утилиты -  там будут статичные методы
 * они будут тестировать сможет ли инженер взяться за работу, т.е. если это компьютер
 * то инженер должен будет IT  направления
 * если это так то инженер проверяет истекла гарантия или нет,
 * если гарантия не истекла  то надо будет ремонтировать 100%
 * если  случай уже не гарантийный инженер ремонтиовать оборудование не будет
 * инженер должен чинить технику я думал
 */
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import ru.job4j.prof.dopclass.*;

public class EngineerTest {

    @Test
    public void testirovanieEngenierclassMECHANIC() {
        Engineer vasilii = new Engineer("Василий", Sex.М, 9, Education.БАКАЛАВРИАТ, Direction.MECHANIC);
        Auto volga = new Auto("Волга", 2, 1, Rabsost.СЛОМАНО);
        assertThat(Utilits.oborudovaneiDiagnostika(volga, vasilii), is("Здравствуйте, меня зовут Василий, диагностика показала что оборудование нуждается в ремонте, начинаю ремонт. Ремонт окончен"));
        assertThat(volga.getRabsost(), is(Rabsost.РАБОЧЕЕ));
    }
    @Test
    public void testirovanieEngenierclassPC() {
        Engineer evgenii = new Engineer("Евгений", Sex.М, 9, Education.СРЕДНЕЕ_ПРОФЕССИОНАЛЬНОЕ, Direction.ELEVATOR_ENGINEER);
        Engineer yulia = new Engineer("Юлия", Sex.Ж, 3, Education.МАГИСТРАТУРА, Direction.IT);
        Pc mac = new Pc("Apple MacBook Air 13 Mid 2017", 2, 1, Rabsost.СЛОМАНО);
        Pc mac2016 = new Pc("Apple MacBook Air 13 Mid 2016", 1, 2, Rabsost.СЛОМАНО);
        assertThat(Utilits.oborudovaneiDiagnostika(mac, evgenii), is("Здравствуйте, меня зовут Евгений, я специалист по профилю ELEVATOR_ENGINEER. Вам нужен инженер другого профиля"));
        assertThat(mac.getRabsost(), is(Rabsost.СЛОМАНО));
        assertThat(Utilits.oborudovaneiDiagnostika(mac, yulia), is("Здравствуйте, меня зовут Юлия, диагностика показала что оборудование нуждается в ремонте, начинаю ремонт. Ремонт окончен"));
        assertThat(mac.getRabsost(), is(Rabsost.РАБОЧЕЕ));
        assertThat(Utilits.oborudovaneiDiagnostika(mac2016, yulia), is("Здравствуйте, меня зовут Юлия, Сожалею, но срок службы вашего оборудования превысил гарантийный срок, в ремонте отказано."));
        assertThat(mac2016.getRabsost(), is(Rabsost.СЛОМАНО));
    }
    @Test
    public void testirovanieEngenierclassLift() {
        Engineer karlich = new Engineer("Карлыч", Sex.М, 20, Education.БАКАЛАВРИАТ, Direction.ELEVATOR_ENGINEER);
        Lift liftgruzovoi = new Lift("G1120", 5, 3, Rabsost.РАБОЧЕЕ);
        assertThat(Utilits.oborudovaneiDiagnostika(liftgruzovoi, karlich), is("Здравствуйте, меня зовут Карлыч, диагностика показала что в ремонте нет необходимости."));
    }
}