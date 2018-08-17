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
package nl.mpi.tg.eg.frinex.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import nl.mpi.tg.eg.frinex.model.TagPairData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Aug 4, 2015 5:38:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class ParticipantDetailController {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ScreenDataRepository screenDataRepository;
    @Autowired
    private TagPairRepository tagPairRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TimeStampRepository timeStampRepository;

    // the first ibdex is the label report, e.g. screen1 or screen2
    // the first index is the row number
    // the third index if the cell/ column  in the row
    private HashMap<String, String[][]> userSummary;
    private HashMap<String, String[][]> fastTrack;
    private HashMap<String, String[][]> fineTuning;

    private List<String> userSummarySortedKeys;
    private List<String> fastTrackSortedKeys;
    private List<String> fineTuningSortedKeys;

    @RequestMapping("participantdetail")
    public String participantDetail(ServletRequest request, @RequestParam(value = "id", required = true) String id, Model model) {

        List<TagPairData> tagPairData = this.tagPairRepository.findByUserIdOrderByTagDateAsc(id);

        this.fetchCvsTables(tagPairData);

        Map<String, String[]> paramMap = request.getParameterMap();
        boolean showStale = paramMap.containsKey("detailed");
        if (showStale) {
            model.addAttribute("participantData", this.participantRepository.findByUserId(id));
        } else {
            model.addAttribute("participantData", this.participantRepository.findByStaleCopyAndUserId(false, id));
        }
        model.addAttribute("participantScreenData", this.screenDataRepository.findByUserIdOrderByViewDateAsc(id));
        model.addAttribute("countOfBrowserWindowClosed", this.screenDataRepository.countDistinctViewDateByUserIdAndScreenName(id, BROWSER_WINDOW_CLOSED));
        model.addAttribute("userSummary", this.userSummary);
        model.addAttribute("fastTrack", this.fastTrack);
        model.addAttribute("fineTuning", this.fineTuning);
        model.addAttribute("userSummarySortedKeys", this.userSummarySortedKeys);
        model.addAttribute("fastTrackSortedKeys", this.fastTrackSortedKeys);
        model.addAttribute("fineTuningSortedKeys", this.fineTuningSortedKeys);
        model.addAttribute("participantTagPairData", this.tagPairRepository.findByUserIdOrderByTagDateAsc(id));
        model.addAttribute("participantSubsetStimulus", this.tagPairRepository.findByUserIdAndEventTagOrderByTagDateAsc(id, SUBSET_STIMULUS));
        model.addAttribute("participantCompletionCode", this.tagRepository.findByUserIdAndEventTagOrderByTagDateAsc(id, COMPLETION_CODE));
        model.addAttribute("participantAudioTestCount", this.tagRepository.countDistinctTagDateByUserIdAndTagValue(id, CARLY_BLUE_CHAIROGG));
        model.addAttribute("participantNextButtonMsData", this.timeStampRepository.findByUserIdAndEventTagOrderByTagDateAsc(id, STIMULUS1_NEXT));
        model.addAttribute("participantTagData", this.tagRepository.findDistinctUserIdEventTagTagValueEventMsTageDateByUserIdOrderByTagDateAsc(id));
        model.addAttribute("participantTimeStampData", this.timeStampRepository.findByUserIdOrderByTagDateAsc(id));
        return "participantdetail";
    }
    private static final String CARLY_BLUE_CHAIROGG = "carly_blue_chair.ogg";
    private static final String COMPLETION_CODE = "CompletionCode";
    private static final String BROWSER_WINDOW_CLOSED = "BrowserWindowClosed";
    private static final String STIMULUS1_NEXT = "stimulus1Next";
    private static final String SUBSET_STIMULUS = "SubsetStimulus";

    private void fetchCvsTables(List<TagPairData> bulk) {

        HashMap<String, ArrayList<String[]>> userSummaryHelper = new HashMap<String, ArrayList<String[]>>();
        HashMap<String, ArrayList<String[]>> fastTrackHelper = new HashMap<String, ArrayList<String[]>>();
        HashMap<String, ArrayList<String[]>> fineTuningHelper = new HashMap<String, ArrayList<String[]>>();

        this.userSummarySortedKeys = new ArrayList<String>();
        this.fastTrackSortedKeys = new ArrayList<String>();
        this.fineTuningSortedKeys = new ArrayList<String>();

        for (TagPairData tagPairData : bulk) {

            String screen = tagPairData.getScreenName();

            if (!userSummaryHelper.containsKey(screen)) {
                userSummaryHelper.put(screen, new ArrayList<String[]>());
                this.userSummarySortedKeys.add(screen);
            }
            if (!fastTrackHelper.containsKey(screen)) {
                fastTrackHelper.put(screen, new ArrayList<String[]>());
                this.fastTrackSortedKeys.add(screen);
            }
            if (!fineTuningHelper.containsKey(screen)) {
                fineTuningHelper.put(screen, new ArrayList<String[]>());
                this.fineTuningSortedKeys.add(screen);

            }

            if (tagPairData.getEventTag().equals("user_summary")) {
                this.addCsvRow(userSummaryHelper.get(screen), tagPairData);
            } else {
                if (tagPairData.getEventTag().equals("fast_track")) {
                    this.addCsvRow(fastTrackHelper.get(screen), tagPairData);
                } else {
                    if (tagPairData.getEventTag().equals("fine_tuning")) {
                        this.addCsvRow(fineTuningHelper.get(screen), tagPairData);
                    }
                }
            }
        }

        this.userSummary = this.createOrderedTable(userSummaryHelper);
        this.fastTrack = this.createOrderedTable(fastTrackHelper);
        this.fineTuning = this.createOrderedTable(fineTuningHelper);

        Collections.sort(this.userSummarySortedKeys);
        Collections.sort(this.fastTrackSortedKeys);
        Collections.sort(this.fineTuningSortedKeys);
    }

    private void addCsvRow(ArrayList<String[]> table, TagPairData tagPairData) {
        String[] row = tagPairData.getTagValue2().split(";");
        String[] csvRow = new String[row.length + 1];
        csvRow[0] = tagPairData.getTagValue1(); // gets row label
        for (int i = 0; i < row.length; i++) {
            csvRow[i + 1] = row[i];
        }
        table.add(csvRow);
    }

    private String[][] orderByRowTag(ArrayList<String[]> buffer) {
        String[][] retVal = new String[buffer.size()][]; // amount of rows
        for (String[] csvRow : buffer) {
            String strIndex = csvRow[0].substring("row".length());
            int index = Integer.parseInt(strIndex);
            retVal[index] = Arrays.copyOfRange(csvRow, 1, csvRow.length);
        }
        return retVal;
    }

    private HashMap<String, String[][]> createOrderedTable(HashMap<String, ArrayList<String[]>> unorderedTable) {
        HashMap<String, String[][]> retVal = new HashMap<String, String[][]>();
        Set<String> summaryKeys = unorderedTable.keySet();
        for (String key : summaryKeys) {
            String[][] orderedRow = this.orderByRowTag(unorderedTable.get(key));
            retVal.put(key, orderedRow);

        }
        return retVal;
    }

}
