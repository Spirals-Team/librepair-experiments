package com.box.l10n.mojito.service.assetExtraction;

import com.box.l10n.mojito.entity.PollableTask;
import com.box.l10n.mojito.quartz.QuartzPollableJob;
import com.box.l10n.mojito.service.pollableTask.PollableTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessAssetJob extends QuartzPollableJob<ProcessAssetJobInput, Void> {

    @Autowired
    AssetExtractionService assetExtractionService;

    @Autowired
    PollableTaskService pollableTaskService;

    @Override
    public Void call(ProcessAssetJobInput input) throws Exception {

        PollableTask currentPollableTask = null;

        if (input.getCurrentTaskId() != null) {
            currentPollableTask = pollableTaskService.getPollableTask(input.getCurrentTaskId());
        }

        assetExtractionService.processAsset(
                input.getAssetId(),
                input.getFilterConfigIdOverride(),
                currentPollableTask);

        return null;
    }
}
