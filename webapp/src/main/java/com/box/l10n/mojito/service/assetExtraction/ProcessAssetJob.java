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

        PollableTask pollableTask = null;

        if (input.getParentTaskId() != null) {
            pollableTask = pollableTaskService.getPollableTask(input.getParentTaskId());
        }

        assetExtractionService.processAssetSync(
                input.getAssetId(),
                input.getFilterConfigIdOverride(),
                pollableTask,
                PollableTask.INJECT_CURRENT_TASK);

        return null;
    }
}
