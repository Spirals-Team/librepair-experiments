/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experimentdesigner.util;

/**
 * @since July 27, 2017 14:06 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HROnlinePretest extends HRPretest {

    public HROnlinePretest() {
        showRatingAfterStimuliResponse = true;
    }

    @Override
    protected String getExperimentName() {
        return "onlinepretest";
    }

    @Override
    protected String informationScreenText2() {
        return "Dit online experiment is een luisterexperiment. Je krijgt telkens een woord te horen dat ofwel een <b>i-klinker</b> bevat (bijv. dier) ofwel een <b>uu-klinker</b> bevat (bijv. duur). Jouw taak is om aan te geven welk woord je hoort.<br/>"
                + "<br/>"
                + "Bijvoorbeeld:<br/>"
                + "Je hoort het woord [dier] en daarna verschijnen er twee namen op het scherm:<br/>"
                + "links staat “dier” en rechts staat “duur”.<br/>"
                + "Jouw taak is dan om links op “dier” te klikken.<br/>"
                + "<br/>"
                + "Er zijn ongeveer 500 woorden in dit experiment. Een normale sessie duurt daarom ongeveer 20 minuten. Bovenaan elk scherm staat aangegeven hoe ver je in het experiment bent.<br/>"
                + "<br/>"
                + "Let op: je kunt het experiment NIET pauzeren, onderbreken, of later weer hervatten. Doe dit experiment daarom ALLEEN als je ook echt de tijd hebt ervoor. Voer het experiment volledig en serieus uit.<br/>"
                + "<br/>"
                + "Als het experiment helder is en je klaar bent om te beginnen, druk dan op VOLGENDE.<br/>"
                + "Het experiment start dan METEEN!";
    }

    @Override
    protected int repeatCount() {
        return 2;
    }

    @Override
    protected String getStimulusResponseOptions() {
        return "1,2,3,4,5,6,7";
    }

    @Override
    protected String[] getStimuliString() {
        return new String[]{
            ":termites_ບຸນບັ້ງໄຟ_0.wav:rabbit,cow",
            ":termites_ບຸນບັ້ງໄຟ_1.wav:moon,star"
        };
    }
}
