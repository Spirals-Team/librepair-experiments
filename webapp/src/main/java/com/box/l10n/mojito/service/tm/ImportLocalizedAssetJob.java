package com.box.l10n.mojito.service.tm;

import com.box.l10n.mojito.entity.Asset;
import com.box.l10n.mojito.entity.RepositoryLocale;
import com.box.l10n.mojito.okapi.CheckForDoNotTranslateStep;
import com.box.l10n.mojito.okapi.CopyFormsOnImport;
import com.box.l10n.mojito.okapi.FilterEventsToInMemoryRawDocumentStep;
import com.box.l10n.mojito.okapi.ImportTranslationsFromLocalizedAssetStep;
import com.box.l10n.mojito.okapi.RawDocument;
import com.box.l10n.mojito.quartz.QuartzPollableJob;
import com.box.l10n.mojito.service.asset.AssetRepository;
import com.box.l10n.mojito.service.assetExtraction.extractor.AssetExtractor;
import com.box.l10n.mojito.service.pollableTask.PollableFuture;
import com.box.l10n.mojito.service.pollableTask.PollableFutureTaskResult;
import com.box.l10n.mojito.service.repository.RepositoryLocaleRepository;
import net.sf.okapi.common.LocaleId;
import net.sf.okapi.common.pipelinedriver.IPipelineDriver;
import net.sf.okapi.common.pipelinedriver.PipelineDriver;
import net.sf.okapi.steps.common.RawDocumentToFilterEventsStep;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ImportLocalizedAssetJob extends QuartzPollableJob<ImportLocalizedAssetJobInput, Void> {

    /**
     * logger
     */
    static Logger logger = LoggerFactory.getLogger(ImportLocalizedAssetJob.class);

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    RepositoryLocaleRepository repositoryLocaleRepository;

    @Autowired
    AssetExtractor assetExtractor;

    @Override
    public Void call(ImportLocalizedAssetJobInput input) throws JobExecutionException {

        Asset asset = assetRepository.findOne(input.getAssetId());
        RepositoryLocale repositoryLocale = repositoryLocaleRepository.findByRepositoryIdAndLocaleId(asset.getRepository().getId(), input.getLocaleId());

        String bcp47Tag = repositoryLocale.getLocale().getBcp47Tag();

        logger.debug("Configuring pipeline to import localized file");

        IPipelineDriver driver = new PipelineDriver();

        driver.addStep(new RawDocumentToFilterEventsStep());
        driver.addStep(new CheckForDoNotTranslateStep());
        driver.addStep(new ImportTranslationsFromLocalizedAssetStep(asset, repositoryLocale, input.getStatusForEqualtarget()));

        logger.debug("Adding all supported filters to the pipeline driver");
        driver.setFilterConfigurationMapper(assetExtractor.getConfiguredFilterConfigurationMapper());

        FilterEventsToInMemoryRawDocumentStep filterEventsToInMemoryRawDocumentStep = new FilterEventsToInMemoryRawDocumentStep();
        driver.addStep(filterEventsToInMemoryRawDocumentStep);

        LocaleId targetLocaleId = LocaleId.fromBCP47(bcp47Tag);
        RawDocument rawDocument = new RawDocument(input.getContent(), LocaleId.ENGLISH, targetLocaleId);
        rawDocument.setAnnotation(new CopyFormsOnImport());

        String filterConfigId;

        if (input.getFilterConfigIdOverride() != null) {
            filterConfigId = input.getFilterConfigIdOverride().getOkapiFilterId();
        } else {
            filterConfigId = assetExtractor.getFilterConfigIdForAsset(asset);
        }

        rawDocument.setFilterConfigId(filterConfigId);
        logger.debug("Set filter config {} for asset {}", filterConfigId, asset.getPath());

        driver.addBatchItem(rawDocument);

        logger.debug("Start processing batch");
        processBatchInTransaction(driver);

        return null;
    }

    @Transactional
    void processBatchInTransaction(IPipelineDriver driver) {
        driver.processBatch();
    }

}
