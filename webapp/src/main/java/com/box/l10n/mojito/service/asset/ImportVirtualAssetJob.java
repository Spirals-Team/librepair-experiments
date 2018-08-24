package com.box.l10n.mojito.service.asset;

import com.box.l10n.mojito.entity.Asset;
import com.box.l10n.mojito.quartz.QuartzPollableJob;
import com.box.l10n.mojito.service.pollableTask.PollableTaskService;
import com.box.l10n.mojito.service.tm.importer.TextUnitBatchImporterService;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jaurambault
 */
@Component
public class ImportVirtualAssetJob extends QuartzPollableJob<ImportVirtualAssetJobInput, Void> {

    /**
     * logger
     */
    static Logger logger = LoggerFactory.getLogger(ImportVirtualAssetJob.class);


    @Autowired
    VirtualTextUnitBatchUpdaterService virtualTextUnitBatchUpdaterService;

    @Autowired
    VirtualAssetService virtualAssetService;


    @Override
    public Void call(ImportVirtualAssetJobInput input) throws Exception {
        Asset asset = virtualAssetService.getVirtualAsset(input.getAssetId());
        virtualTextUnitBatchUpdaterService.updateTextUnits(asset, input.getVirtualAssetTextUnits(), input.isReplace());
        return null;
    }
}
