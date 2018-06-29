package com.viadee.sonarQuest.services;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubePaging;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.externalRessources.SonarQubeProjectRessource;

@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "false", matchIfMissing = true)
public class RealExternalRessourceService extends ExternalRessourceService {

    @Autowired
    private SonarConfigService sonarConfigService;

    @Autowired
    private RestTemplateService restTemplateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RealExternalRessourceService.class);

    private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the sonar server or start this server with --simulateSonarServer=true";

    @Override
    public List<SonarQubeProject> getSonarQubeProjects() {
        try {
            final SonarConfig sonarConfig = sonarConfigService.getConfig();

            final List<SonarQubeProject> sonarQubeProjects = new ArrayList<>();

            final SonarQubeProjectRessource sonarQubeProjectRessource = getSonarQubeProjecRessourceForPageIndex(
                    sonarConfig, 1);

            sonarQubeProjects.addAll(sonarQubeProjectRessource.getSonarQubeProjects());

            final Integer pagesOfExternalProjects = determinePagesOfExternalRessourcesToBeRequested(
                    sonarQubeProjectRessource.getPaging());

            for (int i = 2; i <= pagesOfExternalProjects; i++) {
                sonarQubeProjects
                        .addAll(getSonarQubeProjecRessourceForPageIndex(sonarConfig, i).getSonarQubeProjects());

            }

            return sonarQubeProjects;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SonarQubeIssue> getIssuesForSonarQubeProject(final String projectKey) {
        try {

            final SonarConfig sonarConfig = sonarConfigService.getConfig();

            final List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();

            final SonarQubeIssueRessource sonarQubeIssueRessource = getSonarQubeIssueResourceForProjectAndPageIndex(
                    sonarConfig,
                    projectKey, 1);

            sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());

            final Integer pagesOfExternalIssues = determinePagesOfExternalRessourcesToBeRequested(
                    sonarQubeIssueRessource.getPaging());

            for (int i = 2; i <= pagesOfExternalIssues; i++) {
                sonarQubeIssueList.addAll(
                        getSonarQubeIssueResourceForProjectAndPageIndex(sonarConfig, projectKey, i).getIssues());
            }
            return sonarQubeIssueList;

        } catch (final ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException) {
                LOGGER.error(ERROR_NO_CONNECTION);
            }
            throw e;
        }
    }

    public int determinePagesOfExternalRessourcesToBeRequested(final SonarQubePaging sonarQubePaging) {
        return sonarQubePaging.getTotal() / sonarQubePaging.getPageSize() + 1;
    }

    private SonarQubeIssueRessource getSonarQubeIssueResourceForProjectAndPageIndex(final SonarConfig sonarConfig,
            final String projectKey, final int pageIndex) {
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
        final String fooResourceUrl = sonarConfig.getSonarServerUrl() + "/api/issues/search?componentRoots="
                + projectKey + "&pageSize=500&pageIndex=" + pageIndex;
        final ResponseEntity<SonarQubeIssueRessource> response = restTemplate.getForEntity(fooResourceUrl,
                SonarQubeIssueRessource.class);

        return response.getBody();
    }

    private SonarQubeProjectRessource getSonarQubeProjecRessourceForPageIndex(final SonarConfig sonarConfig,
            final int pageIndex) {
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
        final String fooResourceUrl = sonarConfig.getSonarServerUrl()
                + "/api/components/search?qualifiers=TRK&pageSize=500&pageIndex=" + pageIndex;
        final ResponseEntity<SonarQubeProjectRessource> response = restTemplate.getForEntity(fooResourceUrl,
                SonarQubeProjectRessource.class);

        return response.getBody();
    }

    // HttpHeaders createHeaders(String username, String password){
    // return new HttpHeaders() {{
    // String auth = username + ":" + password;
    // byte[] encodedAuth = Base64.encodeBase64(
    // auth.getBytes(Charset.forName("US-ASCII")) );
    // String authHeader = "Basic " + new String( encodedAuth );
    // set( "Authorization", authHeader );
    // }};
    // }
}
