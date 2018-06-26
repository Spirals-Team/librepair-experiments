package com.viadee.sonarQuest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.externalRessources.SonarQubeApiResponse;
import com.viadee.sonarQuest.repositories.SonarConfigRepository;

@Service
public class SonarConfigService {

    private static final Logger log = LoggerFactory.getLogger(SonarConfigService.class);

    @Autowired
    private SonarConfigRepository sonarConfigRepository;

    @Autowired
    private RestTemplateService restTemplateService;

    public SonarConfig getConfig() {
        return sonarConfigRepository.findFirstBy();
    }

    public SonarConfig saveConfig(final SonarConfig config) {
        final SonarConfig currentConfig = getConfig();
        return currentConfig == null ? sonarConfigRepository.save(config) : updateCurrentConfig(config, currentConfig);
    }

    private SonarConfig updateCurrentConfig(final SonarConfig config, final SonarConfig currentConfig) {
        currentConfig.setName(config.getName());
        currentConfig.setSonarServerUrl(config.getSonarServerUrl());
        return sonarConfigRepository.save(currentConfig);
    }

    public boolean checkSonarQubeURL(final SonarConfig sonarConfig) {
        boolean result = false;

        final String apiAddress = sonarConfig.getSonarServerUrl() + "/api";
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);

        try {
            final ResponseEntity<SonarQubeApiResponse> response = restTemplate.getForEntity(apiAddress,
                    SonarQubeApiResponse.class);

            if (response.hasBody()) {
                result = true;
            }
        } catch (final Exception e) {
            log.error(e.toString());
        }
        return result;
    }

}
