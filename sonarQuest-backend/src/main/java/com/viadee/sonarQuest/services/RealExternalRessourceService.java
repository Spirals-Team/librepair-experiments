package com.viadee.sonarQuest.services;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubePaging;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.externalRessources.SonarQubeProjectRessource;

@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "false", matchIfMissing = true)
public class RealExternalRessourceService extends ExternalRessourceService {

    private static final Logger log = LoggerFactory.getLogger(RealExternalRessourceService.class);

    private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the sonar server or start this server with --simulateSonarServer=true";

    @Override
    public List<SonarQubeProject> getSonarQubeProjects() {
        try {

            final List<SonarQubeProject> sonarQubeProjects = new ArrayList<>();
            final SonarQubeProjectRessource sonarQubeProjectRessource = getSonarQubeProjecRessourceForPageIndex(1);
            sonarQubeProjects.addAll(sonarQubeProjectRessource.getSonarQubeProjects());
            final Integer pagesOfExternalProjects = determinePagesOfExternalRessourcesToBeRequested(
                    sonarQubeProjectRessource.getPaging());
            for (int i = 2; i <= pagesOfExternalProjects; i++) {
                sonarQubeProjects.addAll(getSonarQubeProjecRessourceForPageIndex(i).getSonarQubeProjects());
            }
            return sonarQubeProjects;
        } catch (final ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException) {
                log.error(ERROR_NO_CONNECTION);
            }
            throw e;
        }
    }

    @Override
    public List<SonarQubeIssue> getIssuesForSonarQubeProject(final String projectKey) {
        try {

            final List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();

            final SonarQubeIssueRessource sonarQubeIssueRessource = getSonarQubeIssueResourceForProjectAndPageIndex(
                    projectKey, 1);

            sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());

            final Integer pagesOfExternalIssues = determinePagesOfExternalRessourcesToBeRequested(
                    sonarQubeIssueRessource.getPaging());

            for (int i = 2; i <= pagesOfExternalIssues; i++) {
                sonarQubeIssueList.addAll(getSonarQubeIssueResourceForProjectAndPageIndex(projectKey, i).getIssues());
            }
            return sonarQubeIssueList;

        } catch (final ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException) {
                log.error(ERROR_NO_CONNECTION);
            }
            throw e;
        }
    }

    public int determinePagesOfExternalRessourcesToBeRequested(final SonarQubePaging sonarQubePaging) {
        return sonarQubePaging.getTotal() / sonarQubePaging.getPageSize() + 1;
    }

    public SonarQubeIssueRessource getSonarQubeIssueResourceForProjectAndPageIndex(final String projectKey,
            final int pageIndex) {
        final Client client = ClientBuilder.newClient();

        final WebTarget webTarget = client.target(RessourceEndpoints.DEV_ENDPOINT + "/issues/search?componentRoots="
                + projectKey + "&pageSize=500&pageIndex=" + pageIndex);

        final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        final SonarQubeIssueRessource sonarQubeIssueRessource = invocationBuilder.get(SonarQubeIssueRessource.class);

        return sonarQubeIssueRessource;
    }

    public SonarQubeProjectRessource getSonarQubeProjecRessourceForPageIndex(final int pageIndex) {
        final Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(RessourceEndpoints.DEV_ENDPOINT
                + "/components/search?qualifiers=TRK&pageSize=500&pageIndex=" + pageIndex);
        final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        final SonarQubeProjectRessource sonarQubeProjectRessource = invocationBuilder
                .get(SonarQubeProjectRessource.class);
        return sonarQubeProjectRessource;
    }
}
