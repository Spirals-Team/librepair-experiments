/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experiment.client.model;

import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

/**
 * @since Dec 3, 2015 11:35:06 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GeneratedStimulus implements Stimulus {

    public static final GeneratedStimulus[] values = new GeneratedStimulus[]{
        new GeneratedStimulus("d1e286", "url", new Tag[]{Tag.tag_number, Tag.tag_interesting}, "One", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e291", "url", new Tag[]{Tag.tag_number, Tag.tag_multiple_words, Tag.tag_interesting}, "Two", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e298", "url", new Tag[]{Tag.tag_FILLER_AUDIO}, "Three", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e301", "url", new Tag[]{Tag.tag_FILLER_AUDIO}, "Four", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e304", "url", new Tag[]{Tag.tag_NOISE_AUDIO}, "Five", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e307", "url", new Tag[]{Tag.tag_NOISE_AUDIO}, "Six", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e310", "url", new Tag[]{Tag.tag_sim}, "sim_rabbit", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e313", "url", new Tag[]{Tag.tag_sim}, "sim_cat", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e316", "url", new Tag[]{Tag.tag_sim}, "sim_muffin", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e319", "url", new Tag[]{Tag.tag_sim}, "sim_you", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e322", "url", new Tag[]{Tag.tag_mid}, "mid_rabbit", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e326", "url", new Tag[]{Tag.tag_mid}, "mid_cat", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e329", "url", new Tag[]{Tag.tag_mid}, "mid_muffin", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e332", "url", new Tag[]{Tag.tag_mid}, "mid_you", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e335", "url", new Tag[]{Tag.tag_diff}, "diff_rabbit", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e338", "url", new Tag[]{Tag.tag_diff}, "diff_cat", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e341", "url", new Tag[]{Tag.tag_diff}, "diff_muffin", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e344", "url", new Tag[]{Tag.tag_diff}, "diff_you", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e347", "url", new Tag[]{Tag.tag_noise}, "noise_rabbit", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e350", "url", new Tag[]{Tag.tag_noise}, "noise_cat", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e353", "url", new Tag[]{Tag.tag_noise}, "noise_muffin", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e356", "url", new Tag[]{Tag.tag_noise}, "noise_you", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e360", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e365", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e370", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e375", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e380", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e385", "url", new Tag[]{Tag.tag_termites, Tag.tag_Rocket}, "termites_Rocket_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e390", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e395", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e400", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e405", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e410", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e416", "url", new Tag[]{Tag.tag_Festival, Tag.tag_termites}, "termites_Festival_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e421", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e426", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e431", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e436", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e441", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e446", "url", new Tag[]{Tag.tag_termites, Tag.tag_Thai}, "termites_Thai_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e451", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e456", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e461", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e466", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e472", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e477", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_termites}, "termites_ประเพณีบุญบั้งไฟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e482", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e487", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e492", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e497", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e502", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e507", "url", new Tag[]{Tag.tag_Lao, Tag.tag_termites}, "termites_Lao_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e512", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e517", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e522", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e528", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e533", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e538", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_termites}, "termites_ບຸນບັ້ງໄຟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e543", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e548", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e553", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e558", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e563", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e568", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Rocket}, "scorpions_Rocket_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e573", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e578", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e584", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e589", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e594", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e599", "url", new Tag[]{Tag.tag_Festival, Tag.tag_scorpions}, "scorpions_Festival_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e604", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e609", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e614", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e619", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e624", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e629", "url", new Tag[]{Tag.tag_scorpions, Tag.tag_Thai}, "scorpions_Thai_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e634", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e640", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e645", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e650", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e655", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e660", "url", new Tag[]{Tag.tag_ประเพณีบุญบั้งไฟ, Tag.tag_scorpions}, "scorpions_ประเพณีบุญบั้งไฟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e665", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e670", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e675", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e680", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e685", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e690", "url", new Tag[]{Tag.tag_Lao, Tag.tag_scorpions}, "scorpions_Lao_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e696", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e701", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e706", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e711", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e716", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e721", "url", new Tag[]{Tag.tag_ບຸນບັ້ງໄຟ, Tag.tag_scorpions}, "scorpions_ບຸນບັ້ງໄຟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e726", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e731", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e736", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e741", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e746", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e752", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Rocket}, "centipedes_Rocket_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e757", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e762", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e767", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e772", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e777", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e782", "url", new Tag[]{Tag.tag_Festival, Tag.tag_centipedes}, "centipedes_Festival_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e787", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e792", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e797", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e802", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e808", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e813", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Thai}, "centipedes_Thai_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e818", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e823", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e828", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e833", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e838", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e843", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ประเพณีบุญบั้งไฟ}, "centipedes_ประเพณีบุญบั้งไฟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e848", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e853", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e858", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e864", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e869", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e874", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_Lao}, "centipedes_Lao_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e879", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_0", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e884", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_1", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e889", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_2", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e894", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_3", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e899", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_4", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e904", "url", new Tag[]{Tag.tag_centipedes, Tag.tag_ບຸນບັ້ງໄຟ}, "centipedes_ບຸນບັ້ງໄຟ_5", "code", 0, true, true, true, true),
        new GeneratedStimulus("d1e909", "url", new Tag[]{Tag.tag_bad, Tag.tag_, Tag.tag__t_n_x0B_f_r_, Tag.tag_bad_chars, Tag.tag_this_add_, Tag.tag__, Tag.tag____, Tag.tag_chars}, "bad chars", "code", 0, true, true, true, true),
        new GeneratedStimulus(":bier_buur_step0.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e51", "code_d1e51", 0, "bier_buur_step0", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step1.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e55", "code_d1e55", 0, "bier_buur_step1", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step2.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e59", "code_d1e59", 0, "bier_buur_step2", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step3.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e63", "code_d1e63", 0, "bier_buur_step3", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step4.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e67", "code_d1e67", 0, "bier_buur_step4", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step5.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e71", "code_d1e71", 0, "bier_buur_step5", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step6.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e75", "code_d1e75", 0, "bier_buur_step6", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step7.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e79", "code_d1e79", 0, "bier_buur_step7", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step8.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e83", "code_d1e83", 0, "bier_buur_step8", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step9.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e87", "code_d1e87", 0, "bier_buur_step9", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step10.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e91", "code_d1e91", 0, "bier_buur_step10", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step11.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e96", "code_d1e96", 0, "bier_buur_step11", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step12.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e100", "code_d1e100", 0, "bier_buur_step12", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step13.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e104", "code_d1e104", 0, "bier_buur_step13", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step14.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e108", "code_d1e108", 0, "bier_buur_step14", null, null, "bier,buur"),
        new GeneratedStimulus(":bier_buur_step15.wav:bier,buur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e112", "code_d1e112", 0, "bier_buur_step15", null, null, "bier,buur"),
        new GeneratedStimulus(":kier_kuur_step0.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e116", "code_d1e116", 0, "kier_kuur_step0", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step1.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e120", "code_d1e120", 0, "kier_kuur_step1", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step2.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e124", "code_d1e124", 0, "kier_kuur_step2", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step3.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e128", "code_d1e128", 0, "kier_kuur_step3", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step4.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e132", "code_d1e132", 0, "kier_kuur_step4", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step5.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e136", "code_d1e136", 0, "kier_kuur_step5", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step6.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e141", "code_d1e141", 0, "kier_kuur_step6", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step7.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e145", "code_d1e145", 0, "kier_kuur_step7", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step8.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e149", "code_d1e149", 0, "kier_kuur_step8", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step9.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e153", "code_d1e153", 0, "kier_kuur_step9", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step10.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e157", "code_d1e157", 0, "kier_kuur_step10", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step11.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e161", "code_d1e161", 0, "kier_kuur_step11", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step12.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e165", "code_d1e165", 0, "kier_kuur_step12", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step13.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e169", "code_d1e169", 0, "kier_kuur_step13", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step14.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e173", "code_d1e173", 0, "kier_kuur_step14", null, null, "kier,kuur"),
        new GeneratedStimulus(":kier_kuur_step15.wav:kier,kuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e177", "code_d1e177", 0, "kier_kuur_step15", null, null, "kier,kuur"),
        new GeneratedStimulus(":mier_muur_step0.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e181", "code_d1e181", 0, "mier_muur_step0", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step1.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e186", "code_d1e186", 0, "mier_muur_step1", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step2.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e190", "code_d1e190", 0, "mier_muur_step2", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step3.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e194", "code_d1e194", 0, "mier_muur_step3", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step4.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e198", "code_d1e198", 0, "mier_muur_step4", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step5.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e202", "code_d1e202", 0, "mier_muur_step5", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step6.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e206", "code_d1e206", 0, "mier_muur_step6", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step7.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e210", "code_d1e210", 0, "mier_muur_step7", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step8.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e214", "code_d1e214", 0, "mier_muur_step8", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step9.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e218", "code_d1e218", 0, "mier_muur_step9", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step10.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e222", "code_d1e222", 0, "mier_muur_step10", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step11.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e226", "code_d1e226", 0, "mier_muur_step11", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step12.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e231", "code_d1e231", 0, "mier_muur_step12", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step13.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e235", "code_d1e235", 0, "mier_muur_step13", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step14.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e239", "code_d1e239", 0, "mier_muur_step14", null, null, "mier,muur"),
        new GeneratedStimulus(":mier_muur_step15.wav:mier,muur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e243", "code_d1e243", 0, "mier_muur_step15", null, null, "mier,muur"),
        new GeneratedStimulus(":stier_stuur_step0.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e247", "code_d1e247", 0, "stier_stuur_step0", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step1.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e251", "code_d1e251", 0, "stier_stuur_step1", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step2.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e255", "code_d1e255", 0, "stier_stuur_step2", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step3.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e259", "code_d1e259", 0, "stier_stuur_step3", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step4.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e263", "code_d1e263", 0, "stier_stuur_step4", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step5.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e267", "code_d1e267", 0, "stier_stuur_step5", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step6.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e271", "code_d1e271", 0, "stier_stuur_step6", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step7.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e276", "code_d1e276", 0, "stier_stuur_step7", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step8.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e280", "code_d1e280", 0, "stier_stuur_step8", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step9.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e284", "code_d1e284", 0, "stier_stuur_step9", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step10.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e288", "code_d1e288", 0, "stier_stuur_step10", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step11.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e292", "code_d1e292", 0, "stier_stuur_step11", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step12.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e296", "code_d1e296", 0, "stier_stuur_step12", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step13.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e300", "code_d1e300", 0, "stier_stuur_step13", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step14.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e304", "code_d1e304", 0, "stier_stuur_step14", null, null, "stier,stuur"),
        new GeneratedStimulus(":stier_stuur_step15.wav:stier,stuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e308", "code_d1e308", 0, "stier_stuur_step15", null, null, "stier,stuur"),
        new GeneratedStimulus(":tier_tuur_step0.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e312", "code_d1e312", 0, "tier_tuur_step0", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step1.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e316", "code_d1e316", 0, "tier_tuur_step1", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step2.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e321", "code_d1e321", 0, "tier_tuur_step2", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step3.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e325", "code_d1e325", 0, "tier_tuur_step3", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step4.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e329", "code_d1e329", 0, "tier_tuur_step4", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step5.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e333", "code_d1e333", 0, "tier_tuur_step5", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step6.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e337", "code_d1e337", 0, "tier_tuur_step6", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step7.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e341", "code_d1e341", 0, "tier_tuur_step7", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step8.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e345", "code_d1e345", 0, "tier_tuur_step8", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step9.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e349", "code_d1e349", 0, "tier_tuur_step9", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step10.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e353", "code_d1e353", 0, "tier_tuur_step10", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step11.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e357", "code_d1e357", 0, "tier_tuur_step11", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step12.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e361", "code_d1e361", 0, "tier_tuur_step12", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step13.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e366", "code_d1e366", 0, "tier_tuur_step13", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step14.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e370", "code_d1e370", 0, "tier_tuur_step14", null, null, "tier,tuur"),
        new GeneratedStimulus(":tier_tuur_step15.wav:tier,tuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e374", "code_d1e374", 0, "tier_tuur_step15", null, null, "tier,tuur"),
        new GeneratedStimulus(":tiert_tuurt_step0.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e378", "code_d1e378", 0, "tiert_tuurt_step0", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step1.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e382", "code_d1e382", 0, "tiert_tuurt_step1", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step2.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e386", "code_d1e386", 0, "tiert_tuurt_step2", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step3.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e390", "code_d1e390", 0, "tiert_tuurt_step3", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step4.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e394", "code_d1e394", 0, "tiert_tuurt_step4", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step5.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e398", "code_d1e398", 0, "tiert_tuurt_step5", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step6.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e402", "code_d1e402", 0, "tiert_tuurt_step6", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step7.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e406", "code_d1e406", 0, "tiert_tuurt_step7", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step8.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e411", "code_d1e411", 0, "tiert_tuurt_step8", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step9.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e415", "code_d1e415", 0, "tiert_tuurt_step9", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step10.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e419", "code_d1e419", 0, "tiert_tuurt_step10", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step11.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e423", "code_d1e423", 0, "tiert_tuurt_step11", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step12.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e427", "code_d1e427", 0, "tiert_tuurt_step12", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step13.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e431", "code_d1e431", 0, "tiert_tuurt_step13", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step14.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e435", "code_d1e435", 0, "tiert_tuurt_step14", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiert_tuurt_step15.wav:tiert,tuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e439", "code_d1e439", 0, "tiert_tuurt_step15", null, null, "tiert,tuurt"),
        new GeneratedStimulus(":tiet_tuut_step0.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e443", "code_d1e443", 0, "tiet_tuut_step0", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step1.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e447", "code_d1e447", 0, "tiet_tuut_step1", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step2.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e451", "code_d1e451", 0, "tiet_tuut_step2", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step3.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e456", "code_d1e456", 0, "tiet_tuut_step3", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step4.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e460", "code_d1e460", 0, "tiet_tuut_step4", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step5.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e464", "code_d1e464", 0, "tiet_tuut_step5", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step6.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e468", "code_d1e468", 0, "tiet_tuut_step6", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step7.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e472", "code_d1e472", 0, "tiet_tuut_step7", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step8.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e476", "code_d1e476", 0, "tiet_tuut_step8", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step9.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e480", "code_d1e480", 0, "tiet_tuut_step9", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step10.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e484", "code_d1e484", 0, "tiet_tuut_step10", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step11.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e488", "code_d1e488", 0, "tiet_tuut_step11", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step12.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e492", "code_d1e492", 0, "tiet_tuut_step12", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step13.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e496", "code_d1e496", 0, "tiet_tuut_step13", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step14.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e501", "code_d1e501", 0, "tiet_tuut_step14", null, null, "tiet,tuut"),
        new GeneratedStimulus(":tiet_tuut_step15.wav:tiet,tuut", new Tag[]{Tag.tag_HRPretest02}, "label_d1e505", "code_d1e505", 0, "tiet_tuut_step15", null, null, "tiet,tuut"),
        new GeneratedStimulus(":vier_vuur_step0.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e509", "code_d1e509", 0, "vier_vuur_step0", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step1.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e513", "code_d1e513", 0, "vier_vuur_step1", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step2.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e517", "code_d1e517", 0, "vier_vuur_step2", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step3.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e521", "code_d1e521", 0, "vier_vuur_step3", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step4.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e525", "code_d1e525", 0, "vier_vuur_step4", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step5.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e529", "code_d1e529", 0, "vier_vuur_step5", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step6.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e533", "code_d1e533", 0, "vier_vuur_step6", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step7.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e537", "code_d1e537", 0, "vier_vuur_step7", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step8.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e541", "code_d1e541", 0, "vier_vuur_step8", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step9.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e546", "code_d1e546", 0, "vier_vuur_step9", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step10.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e550", "code_d1e550", 0, "vier_vuur_step10", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step11.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e554", "code_d1e554", 0, "vier_vuur_step11", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step12.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e558", "code_d1e558", 0, "vier_vuur_step12", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step13.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e562", "code_d1e562", 0, "vier_vuur_step13", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step14.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e566", "code_d1e566", 0, "vier_vuur_step14", null, null, "vier,vuur"),
        new GeneratedStimulus(":vier_vuur_step15.wav:vier,vuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e570", "code_d1e570", 0, "vier_vuur_step15", null, null, "vier,vuur"),
        new GeneratedStimulus(":viert_vuurt_step0.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e574", "code_d1e574", 0, "viert_vuurt_step0", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step1.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e578", "code_d1e578", 0, "viert_vuurt_step1", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step2.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e582", "code_d1e582", 0, "viert_vuurt_step2", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step3.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e586", "code_d1e586", 0, "viert_vuurt_step3", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step4.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e591", "code_d1e591", 0, "viert_vuurt_step4", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step5.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e595", "code_d1e595", 0, "viert_vuurt_step5", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step6.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e599", "code_d1e599", 0, "viert_vuurt_step6", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step7.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e603", "code_d1e603", 0, "viert_vuurt_step7", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step8.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e607", "code_d1e607", 0, "viert_vuurt_step8", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step9.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e611", "code_d1e611", 0, "viert_vuurt_step9", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step10.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e615", "code_d1e615", 0, "viert_vuurt_step10", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step11.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e619", "code_d1e619", 0, "viert_vuurt_step11", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step12.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e623", "code_d1e623", 0, "viert_vuurt_step12", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step13.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e627", "code_d1e627", 0, "viert_vuurt_step13", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step14.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e631", "code_d1e631", 0, "viert_vuurt_step14", null, null, "viert,vuurt"),
        new GeneratedStimulus(":viert_vuurt_step15.wav:viert,vuurt", new Tag[]{Tag.tag_HRPretest02}, "label_d1e636", "code_d1e636", 0, "viert_vuurt_step15", null, null, "viert,vuurt"),
        new GeneratedStimulus(":kiep_kuub_step0.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e640", "code_d1e640", 0, "kiep_kuub_step0", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step1.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e644", "code_d1e644", 0, "kiep_kuub_step1", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step2.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e648", "code_d1e648", 0, "kiep_kuub_step2", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step3.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e652", "code_d1e652", 0, "kiep_kuub_step3", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step4.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e656", "code_d1e656", 0, "kiep_kuub_step4", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step5.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e660", "code_d1e660", 0, "kiep_kuub_step5", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step6.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e664", "code_d1e664", 0, "kiep_kuub_step6", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step7.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e668", "code_d1e668", 0, "kiep_kuub_step7", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step8.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e672", "code_d1e672", 0, "kiep_kuub_step8", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step9.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e676", "code_d1e676", 0, "kiep_kuub_step9", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step10.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e681", "code_d1e681", 0, "kiep_kuub_step10", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step11.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e685", "code_d1e685", 0, "kiep_kuub_step11", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step12.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e689", "code_d1e689", 0, "kiep_kuub_step12", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step13.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e693", "code_d1e693", 0, "kiep_kuub_step13", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step14.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e697", "code_d1e697", 0, "kiep_kuub_step14", null, null, "kiep,kuub"),
        new GeneratedStimulus(":kiep_kuub_step15.wav:kiep,kuub", new Tag[]{Tag.tag_HRPretest02}, "label_d1e701", "code_d1e701", 0, "kiep_kuub_step15", null, null, "kiep,kuub"),
        new GeneratedStimulus(":riet_Ruud_step0.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e705", "code_d1e705", 0, "riet_Ruud_step0", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step1.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e709", "code_d1e709", 0, "riet_Ruud_step1", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step2.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e713", "code_d1e713", 0, "riet_Ruud_step2", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step3.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e717", "code_d1e717", 0, "riet_Ruud_step3", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step4.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e721", "code_d1e721", 0, "riet_Ruud_step4", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step5.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e726", "code_d1e726", 0, "riet_Ruud_step5", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step6.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e730", "code_d1e730", 0, "riet_Ruud_step6", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step7.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e734", "code_d1e734", 0, "riet_Ruud_step7", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step8.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e738", "code_d1e738", 0, "riet_Ruud_step8", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step9.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e742", "code_d1e742", 0, "riet_Ruud_step9", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step10.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e746", "code_d1e746", 0, "riet_Ruud_step10", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step11.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e750", "code_d1e750", 0, "riet_Ruud_step11", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step12.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e754", "code_d1e754", 0, "riet_Ruud_step12", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step13.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e758", "code_d1e758", 0, "riet_Ruud_step13", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step14.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e762", "code_d1e762", 0, "riet_Ruud_step14", null, null, "riet,Ruud"),
        new GeneratedStimulus(":riet_Ruud_step15.wav:riet,Ruud", new Tag[]{Tag.tag_HRPretest02}, "label_d1e766", "code_d1e766", 0, "riet_Ruud_step15", null, null, "riet,Ruud"),
        new GeneratedStimulus(":dier_duur_step0.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e771", "code_d1e771", 0, "dier_duur_step0", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step1.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e775", "code_d1e775", 0, "dier_duur_step1", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step2.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e779", "code_d1e779", 0, "dier_duur_step2", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step3.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e783", "code_d1e783", 0, "dier_duur_step3", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step4.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e787", "code_d1e787", 0, "dier_duur_step4", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step5.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e791", "code_d1e791", 0, "dier_duur_step5", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step6.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e795", "code_d1e795", 0, "dier_duur_step6", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step7.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e799", "code_d1e799", 0, "dier_duur_step7", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step8.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e803", "code_d1e803", 0, "dier_duur_step8", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step9.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e807", "code_d1e807", 0, "dier_duur_step9", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step10.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e811", "code_d1e811", 0, "dier_duur_step10", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step11.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e816", "code_d1e816", 0, "dier_duur_step11", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step12.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e820", "code_d1e820", 0, "dier_duur_step12", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step13.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e824", "code_d1e824", 0, "dier_duur_step13", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step14.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e828", "code_d1e828", 0, "dier_duur_step14", null, null, "dier,duur"),
        new GeneratedStimulus(":dier_duur_step15.wav:dier,duur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e832", "code_d1e832", 0, "dier_duur_step15", null, null, "dier,duur"),
        new GeneratedStimulus(":gier_guur_step0.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e836", "code_d1e836", 0, "gier_guur_step0", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step1.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e840", "code_d1e840", 0, "gier_guur_step1", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step2.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e844", "code_d1e844", 0, "gier_guur_step2", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step3.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e848", "code_d1e848", 0, "gier_guur_step3", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step4.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e852", "code_d1e852", 0, "gier_guur_step4", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step5.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e856", "code_d1e856", 0, "gier_guur_step5", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step6.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e861", "code_d1e861", 0, "gier_guur_step6", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step7.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e865", "code_d1e865", 0, "gier_guur_step7", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step8.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e869", "code_d1e869", 0, "gier_guur_step8", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step9.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e873", "code_d1e873", 0, "gier_guur_step9", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step10.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e877", "code_d1e877", 0, "gier_guur_step10", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step11.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e881", "code_d1e881", 0, "gier_guur_step11", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step12.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e885", "code_d1e885", 0, "gier_guur_step12", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step13.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e889", "code_d1e889", 0, "gier_guur_step13", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step14.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e893", "code_d1e893", 0, "gier_guur_step14", null, null, "gier,guur"),
        new GeneratedStimulus(":gier_guur_step15.wav:gier,guur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e897", "code_d1e897", 0, "gier_guur_step15", null, null, "gier,guur"),
        new GeneratedStimulus(":hier_huur_step0.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e901", "code_d1e901", 0, "hier_huur_step0", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step1.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e906", "code_d1e906", 0, "hier_huur_step1", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step2.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e910", "code_d1e910", 0, "hier_huur_step2", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step3.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e914", "code_d1e914", 0, "hier_huur_step3", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step4.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e918", "code_d1e918", 0, "hier_huur_step4", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step5.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e922", "code_d1e922", 0, "hier_huur_step5", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step6.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e926", "code_d1e926", 0, "hier_huur_step6", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step7.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e930", "code_d1e930", 0, "hier_huur_step7", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step8.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e934", "code_d1e934", 0, "hier_huur_step8", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step9.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e938", "code_d1e938", 0, "hier_huur_step9", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step10.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e942", "code_d1e942", 0, "hier_huur_step10", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step11.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e946", "code_d1e946", 0, "hier_huur_step11", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step12.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e951", "code_d1e951", 0, "hier_huur_step12", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step13.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e955", "code_d1e955", 0, "hier_huur_step13", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step14.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e959", "code_d1e959", 0, "hier_huur_step14", null, null, "hier,huur"),
        new GeneratedStimulus(":hier_huur_step15.wav:hier,huur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e963", "code_d1e963", 0, "hier_huur_step15", null, null, "hier,huur"),
        new GeneratedStimulus(":pier_puur_step0.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e967", "code_d1e967", 0, "pier_puur_step0", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step1.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e971", "code_d1e971", 0, "pier_puur_step1", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step2.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e975", "code_d1e975", 0, "pier_puur_step2", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step3.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e979", "code_d1e979", 0, "pier_puur_step3", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step4.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e983", "code_d1e983", 0, "pier_puur_step4", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step5.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e987", "code_d1e987", 0, "pier_puur_step5", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step6.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e991", "code_d1e991", 0, "pier_puur_step6", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step7.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e996", "code_d1e996", 0, "pier_puur_step7", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step8.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1000", "code_d1e1000", 0, "pier_puur_step8", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step9.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1004", "code_d1e1004", 0, "pier_puur_step9", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step10.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1008", "code_d1e1008", 0, "pier_puur_step10", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step11.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1012", "code_d1e1012", 0, "pier_puur_step11", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step12.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1016", "code_d1e1016", 0, "pier_puur_step12", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step13.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1020", "code_d1e1020", 0, "pier_puur_step13", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step14.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1024", "code_d1e1024", 0, "pier_puur_step14", null, null, "pier,puur"),
        new GeneratedStimulus(":pier_puur_step15.wav:pier,puur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1028", "code_d1e1028", 0, "pier_puur_step15", null, null, "pier,puur"),
        new GeneratedStimulus(":zier_zuur_step0.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1032", "code_d1e1032", 0, "zier_zuur_step0", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step1.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1036", "code_d1e1036", 0, "zier_zuur_step1", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step2.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1041", "code_d1e1041", 0, "zier_zuur_step2", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step3.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1045", "code_d1e1045", 0, "zier_zuur_step3", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step4.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1049", "code_d1e1049", 0, "zier_zuur_step4", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step5.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1053", "code_d1e1053", 0, "zier_zuur_step5", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step6.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1057", "code_d1e1057", 0, "zier_zuur_step6", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step7.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1061", "code_d1e1061", 0, "zier_zuur_step7", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step8.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1065", "code_d1e1065", 0, "zier_zuur_step8", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step9.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1069", "code_d1e1069", 0, "zier_zuur_step9", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step10.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1073", "code_d1e1073", 0, "zier_zuur_step10", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step11.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1077", "code_d1e1077", 0, "zier_zuur_step11", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step12.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1081", "code_d1e1081", 0, "zier_zuur_step12", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step13.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1086", "code_d1e1086", 0, "zier_zuur_step13", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step14.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1090", "code_d1e1090", 0, "zier_zuur_step14", null, null, "zier,zuur"),
        new GeneratedStimulus(":zier_zuur_step15.wav:zier,zuur", new Tag[]{Tag.tag_HRPretest02}, "label_d1e1094", "code_d1e1094", 0, "zier_zuur_step15", null, null, "zier,zuur"),
        new GeneratedStimulus("999:tgt_5_1100Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e51", "code_d1e51", 0, "tgt_5_1100Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e56", "code_d1e56", 0, "tgt_5_1100Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e61", "code_d1e61", 0, "tgt_5_1100Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e66", "code_d1e66", 0, "tgt_5_1100Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e71", "code_d1e71", 0, "tgt_5_1100Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e76", "code_d1e76", 0, "tgt_5_1100Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1100Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e81", "code_d1e81", 0, "tgt_5_1100Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e86", "code_d1e86", 0, "tgt_5_1150Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e91", "code_d1e91", 0, "tgt_5_1150Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e96", "code_d1e96", 0, "tgt_5_1150Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e101", "code_d1e101", 0, "tgt_5_1150Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e107", "code_d1e107", 0, "tgt_5_1150Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e112", "code_d1e112", 0, "tgt_5_1150Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1150Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e117", "code_d1e117", 0, "tgt_5_1150Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e122", "code_d1e122", 0, "tgt_5_1200Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e127", "code_d1e127", 0, "tgt_5_1200Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e132", "code_d1e132", 0, "tgt_5_1200Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e137", "code_d1e137", 0, "tgt_5_1200Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e142", "code_d1e142", 0, "tgt_5_1200Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e147", "code_d1e147", 0, "tgt_5_1200Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1200Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e152", "code_d1e152", 0, "tgt_5_1200Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e157", "code_d1e157", 0, "tgt_5_1250Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e163", "code_d1e163", 0, "tgt_5_1250Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e168", "code_d1e168", 0, "tgt_5_1250Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e173", "code_d1e173", 0, "tgt_5_1250Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e178", "code_d1e178", 0, "tgt_5_1250Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e183", "code_d1e183", 0, "tgt_5_1250Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1250Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e188", "code_d1e188", 0, "tgt_5_1250Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e193", "code_d1e193", 0, "tgt_5_1300Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e198", "code_d1e198", 0, "tgt_5_1300Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e203", "code_d1e203", 0, "tgt_5_1300Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e208", "code_d1e208", 0, "tgt_5_1300Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e213", "code_d1e213", 0, "tgt_5_1300Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e219", "code_d1e219", 0, "tgt_5_1300Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1300Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e224", "code_d1e224", 0, "tgt_5_1300Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e229", "code_d1e229", 0, "tgt_5_1350Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e234", "code_d1e234", 0, "tgt_5_1350Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e239", "code_d1e239", 0, "tgt_5_1350Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e244", "code_d1e244", 0, "tgt_5_1350Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e249", "code_d1e249", 0, "tgt_5_1350Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e254", "code_d1e254", 0, "tgt_5_1350Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1350Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e259", "code_d1e259", 0, "tgt_5_1350Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_100ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e264", "code_d1e264", 0, "tgt_5_1400Hz_100ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_120ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e269", "code_d1e269", 0, "tgt_5_1400Hz_120ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_130ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e275", "code_d1e275", 0, "tgt_5_1400Hz_130ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_140ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e280", "code_d1e280", 0, "tgt_5_1400Hz_140ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_150ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e285", "code_d1e285", 0, "tgt_5_1400Hz_150ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_160ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e290", "code_d1e290", 0, "tgt_5_1400Hz_160ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_5_1400Hz_180ms.wav:bas,baas", new Tag[]{Tag.tag_HRPretest01}, "label_d1e295", "code_d1e295", 0, "tgt_5_1400Hz_180ms", null, null, "bas,baas"),
        new GeneratedStimulus("999:tgt_6_1100Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e300", "code_d1e300", 0, "tgt_6_1100Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e305", "code_d1e305", 0, "tgt_6_1100Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e310", "code_d1e310", 0, "tgt_6_1100Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e315", "code_d1e315", 0, "tgt_6_1100Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e320", "code_d1e320", 0, "tgt_6_1100Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e325", "code_d1e325", 0, "tgt_6_1100Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1100Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e331", "code_d1e331", 0, "tgt_6_1100Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e336", "code_d1e336", 0, "tgt_6_1150Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e341", "code_d1e341", 0, "tgt_6_1150Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e346", "code_d1e346", 0, "tgt_6_1150Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e351", "code_d1e351", 0, "tgt_6_1150Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e356", "code_d1e356", 0, "tgt_6_1150Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e361", "code_d1e361", 0, "tgt_6_1150Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1150Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e366", "code_d1e366", 0, "tgt_6_1150Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e371", "code_d1e371", 0, "tgt_6_1200Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e376", "code_d1e376", 0, "tgt_6_1200Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e381", "code_d1e381", 0, "tgt_6_1200Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e387", "code_d1e387", 0, "tgt_6_1200Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e392", "code_d1e392", 0, "tgt_6_1200Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e397", "code_d1e397", 0, "tgt_6_1200Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1200Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e402", "code_d1e402", 0, "tgt_6_1200Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e407", "code_d1e407", 0, "tgt_6_1250Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e412", "code_d1e412", 0, "tgt_6_1250Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e417", "code_d1e417", 0, "tgt_6_1250Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e422", "code_d1e422", 0, "tgt_6_1250Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e427", "code_d1e427", 0, "tgt_6_1250Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e432", "code_d1e432", 0, "tgt_6_1250Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1250Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e437", "code_d1e437", 0, "tgt_6_1250Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e443", "code_d1e443", 0, "tgt_6_1300Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e448", "code_d1e448", 0, "tgt_6_1300Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e453", "code_d1e453", 0, "tgt_6_1300Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e458", "code_d1e458", 0, "tgt_6_1300Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e463", "code_d1e463", 0, "tgt_6_1300Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e468", "code_d1e468", 0, "tgt_6_1300Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1300Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e473", "code_d1e473", 0, "tgt_6_1300Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e478", "code_d1e478", 0, "tgt_6_1350Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e483", "code_d1e483", 0, "tgt_6_1350Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e488", "code_d1e488", 0, "tgt_6_1350Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e493", "code_d1e493", 0, "tgt_6_1350Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e499", "code_d1e499", 0, "tgt_6_1350Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e504", "code_d1e504", 0, "tgt_6_1350Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1350Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e509", "code_d1e509", 0, "tgt_6_1350Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_100ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e514", "code_d1e514", 0, "tgt_6_1400Hz_100ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_120ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e519", "code_d1e519", 0, "tgt_6_1400Hz_120ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_130ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e524", "code_d1e524", 0, "tgt_6_1400Hz_130ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_140ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e529", "code_d1e529", 0, "tgt_6_1400Hz_140ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_150ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e534", "code_d1e534", 0, "tgt_6_1400Hz_150ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_160ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e539", "code_d1e539", 0, "tgt_6_1400Hz_160ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_6_1400Hz_180ms.wav:ad,aad", new Tag[]{Tag.tag_HRPretest01}, "label_d1e544", "code_d1e544", 0, "tgt_6_1400Hz_180ms", null, null, "ad,aad"),
        new GeneratedStimulus("999:tgt_7_1100Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e549", "code_d1e549", 0, "tgt_7_1100Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e555", "code_d1e555", 0, "tgt_7_1100Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e560", "code_d1e560", 0, "tgt_7_1100Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e565", "code_d1e565", 0, "tgt_7_1100Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e570", "code_d1e570", 0, "tgt_7_1100Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e575", "code_d1e575", 0, "tgt_7_1100Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1100Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e580", "code_d1e580", 0, "tgt_7_1100Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e585", "code_d1e585", 0, "tgt_7_1150Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e590", "code_d1e590", 0, "tgt_7_1150Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e595", "code_d1e595", 0, "tgt_7_1150Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e600", "code_d1e600", 0, "tgt_7_1150Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e605", "code_d1e605", 0, "tgt_7_1150Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e611", "code_d1e611", 0, "tgt_7_1150Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1150Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e616", "code_d1e616", 0, "tgt_7_1150Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e621", "code_d1e621", 0, "tgt_7_1200Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e626", "code_d1e626", 0, "tgt_7_1200Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e631", "code_d1e631", 0, "tgt_7_1200Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e636", "code_d1e636", 0, "tgt_7_1200Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e641", "code_d1e641", 0, "tgt_7_1200Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e646", "code_d1e646", 0, "tgt_7_1200Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1200Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e651", "code_d1e651", 0, "tgt_7_1200Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e656", "code_d1e656", 0, "tgt_7_1250Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e661", "code_d1e661", 0, "tgt_7_1250Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e667", "code_d1e667", 0, "tgt_7_1250Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e672", "code_d1e672", 0, "tgt_7_1250Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e677", "code_d1e677", 0, "tgt_7_1250Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e682", "code_d1e682", 0, "tgt_7_1250Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1250Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e687", "code_d1e687", 0, "tgt_7_1250Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e692", "code_d1e692", 0, "tgt_7_1300Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e697", "code_d1e697", 0, "tgt_7_1300Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e702", "code_d1e702", 0, "tgt_7_1300Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e707", "code_d1e707", 0, "tgt_7_1300Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e712", "code_d1e712", 0, "tgt_7_1300Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e717", "code_d1e717", 0, "tgt_7_1300Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1300Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e723", "code_d1e723", 0, "tgt_7_1300Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e728", "code_d1e728", 0, "tgt_7_1350Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e733", "code_d1e733", 0, "tgt_7_1350Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e738", "code_d1e738", 0, "tgt_7_1350Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e743", "code_d1e743", 0, "tgt_7_1350Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e748", "code_d1e748", 0, "tgt_7_1350Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e753", "code_d1e753", 0, "tgt_7_1350Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1350Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e758", "code_d1e758", 0, "tgt_7_1350Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_100ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e763", "code_d1e763", 0, "tgt_7_1400Hz_100ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_120ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e768", "code_d1e768", 0, "tgt_7_1400Hz_120ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_130ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e773", "code_d1e773", 0, "tgt_7_1400Hz_130ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_140ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e779", "code_d1e779", 0, "tgt_7_1400Hz_140ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_150ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e784", "code_d1e784", 0, "tgt_7_1400Hz_150ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_160ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e789", "code_d1e789", 0, "tgt_7_1400Hz_160ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_7_1400Hz_180ms.wav:mart,maart", new Tag[]{Tag.tag_HRPretest01}, "label_d1e794", "code_d1e794", 0, "tgt_7_1400Hz_180ms", null, null, "mart,maart"),
        new GeneratedStimulus("999:tgt_8_1100Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e799", "code_d1e799", 0, "tgt_8_1100Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e804", "code_d1e804", 0, "tgt_8_1100Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e809", "code_d1e809", 0, "tgt_8_1100Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e814", "code_d1e814", 0, "tgt_8_1100Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e819", "code_d1e819", 0, "tgt_8_1100Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e824", "code_d1e824", 0, "tgt_8_1100Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1100Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e829", "code_d1e829", 0, "tgt_8_1100Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e835", "code_d1e835", 0, "tgt_8_1150Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e840", "code_d1e840", 0, "tgt_8_1150Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e845", "code_d1e845", 0, "tgt_8_1150Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e850", "code_d1e850", 0, "tgt_8_1150Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e855", "code_d1e855", 0, "tgt_8_1150Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e860", "code_d1e860", 0, "tgt_8_1150Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1150Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e865", "code_d1e865", 0, "tgt_8_1150Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e870", "code_d1e870", 0, "tgt_8_1200Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e875", "code_d1e875", 0, "tgt_8_1200Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e880", "code_d1e880", 0, "tgt_8_1200Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e885", "code_d1e885", 0, "tgt_8_1200Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e891", "code_d1e891", 0, "tgt_8_1200Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e896", "code_d1e896", 0, "tgt_8_1200Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1200Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e901", "code_d1e901", 0, "tgt_8_1200Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e906", "code_d1e906", 0, "tgt_8_1250Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e911", "code_d1e911", 0, "tgt_8_1250Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e916", "code_d1e916", 0, "tgt_8_1250Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e921", "code_d1e921", 0, "tgt_8_1250Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e926", "code_d1e926", 0, "tgt_8_1250Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e931", "code_d1e931", 0, "tgt_8_1250Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1250Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e936", "code_d1e936", 0, "tgt_8_1250Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e941", "code_d1e941", 0, "tgt_8_1300Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e947", "code_d1e947", 0, "tgt_8_1300Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e952", "code_d1e952", 0, "tgt_8_1300Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e957", "code_d1e957", 0, "tgt_8_1300Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e962", "code_d1e962", 0, "tgt_8_1300Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e967", "code_d1e967", 0, "tgt_8_1300Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1300Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e972", "code_d1e972", 0, "tgt_8_1300Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e977", "code_d1e977", 0, "tgt_8_1350Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e982", "code_d1e982", 0, "tgt_8_1350Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e987", "code_d1e987", 0, "tgt_8_1350Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e992", "code_d1e992", 0, "tgt_8_1350Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e997", "code_d1e997", 0, "tgt_8_1350Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1003", "code_d1e1003", 0, "tgt_8_1350Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1350Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1008", "code_d1e1008", 0, "tgt_8_1350Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_100ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1013", "code_d1e1013", 0, "tgt_8_1400Hz_100ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_120ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1018", "code_d1e1018", 0, "tgt_8_1400Hz_120ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_130ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1023", "code_d1e1023", 0, "tgt_8_1400Hz_130ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_140ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1028", "code_d1e1028", 0, "tgt_8_1400Hz_140ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_150ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1033", "code_d1e1033", 0, "tgt_8_1400Hz_150ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_160ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1038", "code_d1e1038", 0, "tgt_8_1400Hz_160ms", null, null, "dan,daan"),
        new GeneratedStimulus("999:tgt_8_1400Hz_180ms.wav:dan,daan", new Tag[]{Tag.tag_HRPretest01}, "label_d1e1043", "code_d1e1043", 0, "tgt_8_1400Hz_180ms", null, null, "dan,daan"),
        new GeneratedStimulus("2.png:shape1:version1:quadrant3:moveRotated270", new Tag[]{Tag.tag_2, Tag.tag_moveRotated270, Tag.tag_quadrant3, Tag.tag_shape1, Tag.tag_version1}, "label_d1e1791", "code_d1e1791", 0, null, null, "2.png", null),
        new GeneratedStimulus("6.png:shape1:version1:quadrant4:moveRotated300", new Tag[]{Tag.tag_6, Tag.tag_moveRotated300, Tag.tag_quadrant4, Tag.tag_shape1, Tag.tag_version1}, "label_d1e1802", "code_d1e1802", 0, null, null, "6.png", null),
        new GeneratedStimulus("27.png:shape4:version1:quadrant2:moveRotated120", new Tag[]{Tag.tag_27, Tag.tag_moveRotated120, Tag.tag_quadrant2, Tag.tag_shape4, Tag.tag_version1}, "label_d1e1813", "code_d1e1813", 0, null, null, "27.png", null),
        new GeneratedStimulus("12.png:shape2:version1:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated30", new Tag[]{Tag.tag_12, Tag.tag_moveRotated30, Tag.tag_quadrant1, Tag.tag_shape2, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1824", "code_d1e1824", 0, null, null, "12.png", null),
        new GeneratedStimulus("24.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version4:quadrant2:moveRotated180", new Tag[]{Tag.tag_24, Tag.tag_moveRotated180, Tag.tag_quadrant2, Tag.tag_shape4, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version4}, "label_d1e1843", "code_d1e1843", 0, null, null, "24.png", null),
        new GeneratedStimulus("23.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version5:quadrant4:moveRotated360", new Tag[]{Tag.tag_23, Tag.tag_moveRotated360, Tag.tag_quadrant4, Tag.tag_shape4, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version5}, "label_d1e1864", "code_d1e1864", 0, null, null, "23.png", null),
        new GeneratedStimulus("3.png:shape1:version1:version1round3:version1round4:version1round5:quadrant1:moveRotated90", new Tag[]{Tag.tag_3, Tag.tag_moveRotated90, Tag.tag_quadrant1, Tag.tag_shape1, Tag.tag_version1, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1885", "code_d1e1885", 0, null, null, "3.png", null),
        new GeneratedStimulus("11.png:shape2:version1:version1round3:version1round4:version1round5:quadrant4:moveRotated330", new Tag[]{Tag.tag_11, Tag.tag_moveRotated330, Tag.tag_quadrant4, Tag.tag_shape2, Tag.tag_version1, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1902", "code_d1e1902", 0, null, null, "11.png", null),
        new GeneratedStimulus("20.png:shape3:version1:version1round3:version1round4:version1round5:quadrant3:moveRotated240", new Tag[]{Tag.tag_20, Tag.tag_moveRotated240, Tag.tag_quadrant3, Tag.tag_shape3, Tag.tag_version1, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1919", "code_d1e1919", 0, null, null, "20.png", null),
        new GeneratedStimulus("14.png:shape2:version1:version1round4:version1round5:quadrant2:moveRotated120", new Tag[]{Tag.tag_14, Tag.tag_moveRotated120, Tag.tag_quadrant2, Tag.tag_shape2, Tag.tag_version1, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1936", "code_d1e1936", 0, null, null, "14.png", null),
        new GeneratedStimulus("21.png:shape3:version1:version1round4:version1round5:quadrant4:moveRotated360", new Tag[]{Tag.tag_21, Tag.tag_moveRotated360, Tag.tag_quadrant4, Tag.tag_shape3, Tag.tag_version1, Tag.tag_version1round4, Tag.tag_version1round5}, "label_d1e1951", "code_d1e1951", 0, null, null, "21.png", null),
        new GeneratedStimulus("4.png:shape1:version1:version1round4:version1round5:version5:version5zero:quadrant4:moveRotated330", new Tag[]{Tag.tag_4, Tag.tag_moveRotated330, Tag.tag_quadrant4, Tag.tag_shape1, Tag.tag_version1, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version5, Tag.tag_version5zero}, "label_d1e1967", "code_d1e1967", 0, null, null, "4.png", null),
        new GeneratedStimulus("25.png:shape4:version1:version1round5:quadrant4:moveRotated315", new Tag[]{Tag.tag_25, Tag.tag_moveRotated315, Tag.tag_quadrant4, Tag.tag_shape4, Tag.tag_version1, Tag.tag_version1round5}, "label_d1e1986", "code_d1e1986", 0, null, null, "25.png", null),
        new GeneratedStimulus("16.png:shape3:version1:version1round5:version4:version4zero:quadrant1:moveRotated45", new Tag[]{Tag.tag_16, Tag.tag_moveRotated45, Tag.tag_quadrant1, Tag.tag_shape3, Tag.tag_version1, Tag.tag_version1round5, Tag.tag_version4, Tag.tag_version4zero}, "label_d1e1999", "code_d1e1999", 0, null, null, "16.png", null),
        new GeneratedStimulus("1.png:shape1:version1:version1round5:version5:quadrant3:moveRotated210", new Tag[]{Tag.tag_1, Tag.tag_moveRotated210, Tag.tag_quadrant3, Tag.tag_shape1, Tag.tag_version1, Tag.tag_version1round5, Tag.tag_version5}, "label_d1e2016", "code_d1e2016", 0, null, null, "1.png", null),
        new GeneratedStimulus("7.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135", new Tag[]{Tag.tag_7, Tag.tag_moveRotated135, Tag.tag_quadrant2, Tag.tag_shape1, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2031", "code_d1e2031", 0, null, null, "7.png", null),
        new GeneratedStimulus("9.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated270", new Tag[]{Tag.tag_9, Tag.tag_moveRotated270, Tag.tag_quadrant3, Tag.tag_shape2, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2052", "code_d1e2052", 0, null, null, "9.png", null),
        new GeneratedStimulus("10.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated150", new Tag[]{Tag.tag_10, Tag.tag_moveRotated150, Tag.tag_quadrant2, Tag.tag_shape2, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2073", "code_d1e2073", 0, null, null, "10.png", null),
        new GeneratedStimulus("19.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135", new Tag[]{Tag.tag_19, Tag.tag_moveRotated135, Tag.tag_quadrant2, Tag.tag_shape3, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2094", "code_d1e2094", 0, null, null, "19.png", null),
        new GeneratedStimulus("22.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated60", new Tag[]{Tag.tag_22, Tag.tag_moveRotated60, Tag.tag_quadrant1, Tag.tag_shape4, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2115", "code_d1e2115", 0, null, null, "22.png", null),
        new GeneratedStimulus("28.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated225", new Tag[]{Tag.tag_28, Tag.tag_moveRotated225, Tag.tag_quadrant3, Tag.tag_shape4, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero}, "label_d1e2136", "code_d1e2136", 0, null, null, "28.png", null),
        new GeneratedStimulus("17.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version2:quadrant4:moveRotated315", new Tag[]{Tag.tag_17, Tag.tag_moveRotated315, Tag.tag_quadrant4, Tag.tag_shape3, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero, Tag.tag_version2}, "label_d1e2157", "code_d1e2157", 0, null, null, "17.png", null),
        new GeneratedStimulus("5.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version6:quadrant1:moveRotated30", new Tag[]{Tag.tag_5, Tag.tag_moveRotated30, Tag.tag_quadrant1, Tag.tag_shape1, Tag.tag_version1, Tag.tag_version1round2, Tag.tag_version1round3, Tag.tag_version1round4, Tag.tag_version1round5, Tag.tag_version1zero, Tag.tag_version6}, "label_d1e2181", "code_d1e2181", 0, null, null, "5.png", null)
    };

    public enum Tag implements nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag {
        tag_HRPretest01, tag_HRPretest02, tag_grammar, tag_number, tag_interesting, tag_multiple_words, tag_FILLER_AUDIO, tag_NOISE_AUDIO, tag_sim, tag_mid, tag_diff, tag_noise, tag_termites, tag_Rocket, tag_Festival, tag_Thai, tag_ประเพณีบุญบั้งไฟ, tag_Lao, tag_ບຸນບັ້ງໄຟ, tag_scorpions, tag_centipedes, tag_bad, tag_, tag__t_n_x0B_f_r_, tag_bad_chars, tag_this_add_, tag__, tag____, tag_chars,
        tag_2, tag_moveRotated270, tag_quadrant3, tag_shape1, tag_version1, tag_6, tag_moveRotated300, tag_quadrant4, tag_27, tag_moveRotated120, tag_quadrant2, tag_shape4, tag_12, tag_moveRotated30, tag_quadrant1, tag_shape2, tag_version1round2, tag_version1round3, tag_version1round4, tag_version1round5, tag_24, tag_moveRotated180, tag_version4, tag_23, tag_moveRotated360, tag_version5, tag_3, tag_moveRotated90, tag_11, tag_moveRotated330, tag_20, tag_moveRotated240, tag_shape3, tag_14, tag_21, tag_4, tag_version5zero, tag_25, tag_moveRotated315, tag_16, tag_moveRotated45, tag_version4zero, tag_1, tag_moveRotated210, tag_7, tag_moveRotated135, tag_version1zero, tag_9, tag_10, tag_moveRotated150, tag_19, tag_22, tag_moveRotated60, tag_28, tag_moveRotated225, tag_17, tag_version2, tag_5, tag_version6

    }

    final private String uniqueId;
    final private String urlString;
    final private String imageString;
    final private List<Tag> tags;
    final private String label;
    final private String code;
    final private int pauseMs;
    final private boolean mp3;
    final private boolean mp4;
    final private boolean ogg;
    final private boolean image;

    public GeneratedStimulus(String uniqueId, String urlString, Tag tags[], String label, String code, int pauseMs, boolean mp3, boolean mp4, boolean ogg, boolean image) {
        this.uniqueId = uniqueId;
        this.urlString = urlString;
        this.tags = Arrays.asList(tags);
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.mp3 = mp3;
        this.mp4 = mp4;
        this.ogg = ogg;
        this.image = image;
        this.imageString = null;
    }

    public GeneratedStimulus(String uniqueId, Tag tags[], String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels) {
        this.uniqueId = uniqueId;
        this.urlString = null;
        this.tags = Arrays.asList(tags);
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.mp3 = false;
        this.mp4 = false;
        this.ogg = false;
        this.image = false;
        this.imageString = imagePath;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPauseMs() {
        return pauseMs;
    }

    @Override
    public boolean hasAudio() {
        return mp3;
    }

    @Override
    public boolean hasVideo() {
        return mp4 || ogg;
    }

    @Override
    public boolean hasRatingLabels() {
        return false;
    }

    @Override
    public boolean hasImage() {
        return image;
    }

    @Override
    public String getAudio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getImage() {
        return imageString;
    }

    @Override
    public String getVideo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRatingLabels() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasCorrectResponses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCorrectResponses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Stimulus o) {
        return this.uniqueId.compareTo(o.getUniqueId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.uniqueId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stimulus other = (Stimulus) obj;
        if (!Objects.equals(this.uniqueId, other.getUniqueId())) {
            return false;
        }
        return true;
    }
}
