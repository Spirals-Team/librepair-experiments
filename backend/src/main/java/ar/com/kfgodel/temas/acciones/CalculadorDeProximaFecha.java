package ar.com.kfgodel.temas.acciones;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by fede on 11/09/17.
 */
public class CalculadorDeProximaFecha {

    public LocalDate calcularFechaDeRoots(LocalDate hoy) {
        LocalDate tercerViernesDeEsteMes = hoy.with(tercerViernesDelMes());
        if (tercerViernesDeEsteMes.isBefore(hoy)) {
            return calcularTercerViernesDelProximoMes(hoy);
        }
        return tercerViernesDeEsteMes;
    }

    private LocalDate calcularTercerViernesDelProximoMes(LocalDate hoy) {
        LocalDate tercerViernesDeEsteMes=hoy.with(tercerViernesDelMes());
        if(tercerViernesDeEsteMes.isBefore(hoy)){
            return hoy.with(TemporalAdjusters.firstDayOfNextMonth()).with(tercerViernesDelMes());
        }
        return tercerViernesDeEsteMes;
    }

    private TemporalAdjuster tercerViernesDelMes() {
        return TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.FRIDAY);
    }

}
