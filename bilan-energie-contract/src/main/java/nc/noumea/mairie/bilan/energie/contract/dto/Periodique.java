package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

/**
 * Interface pour les classes periodique et Compteur avec dateDébut dateFin
 */
public interface Periodique {

    /**
     * @return the dateDebut
     */
    public Date getDateDebut();

    /**
     * @return the dateFin
     */
    public Date getDateFin();

    /**
     * Vérifie si deux periodiques ou deux compteurs ont des périodes (dateDebut-> dateFin) qui se chevauchent
     * @param periodique1
     * @param periodique2
     * @return true si les périodes se chevauchent
     */
    public static boolean periodeChevauche(Periodique periodique1, Periodique periodique2){
        if (periodique1.getDateDebut().equals(periodique2.getDateDebut()))
            return true;

        if (periodique1.getDateFin() == null && periodique2.getDateFin() == null)
            return true;

        if (periodique1.getDateFin() != null && periodique2.getDateFin() == null){
            return periodique2.getDateDebut().before(periodique1.getDateFin());
        }

        if (periodique1.getDateFin() == null && periodique2.getDateFin() != null){
            return periodique1.getDateDebut().before(periodique2.getDateFin());
        }

        if (periodique1.getDateFin() != null && periodique2.getDateFin() != null){
            if (periodique1.getDateDebut().before(periodique2.getDateDebut())){
                return periodique1.getDateFin().after(periodique2.getDateDebut());
            }
            if (periodique2.getDateDebut().before(periodique1.getDateDebut())){
                return periodique2.getDateFin().after(periodique1.getDateDebut());
            }
        }
        return false;
    }

    /**
     * Vérifie si le periodique 1 est bien contenu dans les dates du periodique parent
     * @param periodiqueEnfant
     * @param periodiqueParent
     * @return true si le periodique 1 est contenu dans les dates du periodique parent
     */
    public static boolean isInPeriode(Periodique periodiqueEnfant, Periodique periodiqueParent){


        if (periodiqueEnfant.getDateDebut().before(periodiqueParent.getDateDebut()))
            return false;

        if (periodiqueEnfant.getDateFin() == null && periodiqueParent.getDateFin() != null)
            return false;

        if (periodiqueEnfant.getDateFin() != null ) {
            return (periodiqueEnfant.getDateDebut().after(periodiqueParent.getDateDebut()) || periodiqueEnfant.getDateDebut().equals(periodiqueParent.getDateDebut()))
                    && ( periodiqueParent.getDateFin() == null || periodiqueEnfant.getDateFin().before(periodiqueParent.getDateFin()) || periodiqueEnfant.getDateFin().equals(periodiqueParent.getDateFin()));
        }
        return true;
    }
}
