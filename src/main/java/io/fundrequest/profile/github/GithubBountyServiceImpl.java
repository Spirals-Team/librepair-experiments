package io.fundrequest.profile.github;

import io.fundrequest.profile.bounty.BountyService;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.github.domain.GithubBounty;
import io.fundrequest.profile.github.dto.GithubUser;
import io.fundrequest.profile.github.infrastructure.GithubBountyRepository;
import io.fundrequest.profile.github.infrastructure.GithubClient;
import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.GithubVerificationDto;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.Month;

@Service
@Slf4j
public class GithubBountyServiceImpl implements GithubBountyService, ApplicationListener<AuthenticationSuccessEvent> {

    private static final LocalDateTime MIN_PROFILE_DATE = LocalDateTime.of(2018, Month.JANUARY.getValue(), 1, 0, 0, 0, 0);
    private ProfileService profileService;
    private GithubBountyRepository githubBountyRepository;
    private BountyService bountyService;
    private GithubClient githubClient;

    public GithubBountyServiceImpl(ProfileService profileService, GithubBountyRepository githubBountyRepository, BountyService bountyService, GithubClient githubClient) {
        this.profileService = profileService;
        this.githubBountyRepository = githubBountyRepository;
        this.bountyService = bountyService;
        this.githubClient = githubClient;
    }

    @EventListener
    @Transactional
    public void onProviderLinked(UserLinkedProviderEvent event) {
        if (event.getProvider() == Provider.GITHUB) {
            UserProfile userProfile = profileService.getUserProfile(null, event.getPrincipal());
            createBountyWhenNecessary(event.getPrincipal(), userProfile);
        }
    }

    @Override
    @Transactional
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication principal = event.getAuthentication();
        UserProfile userProfile = profileService.getUserProfile(null, principal);
        if (userProfile.getGithub() != null && StringUtils.isNotBlank(userProfile.getGithub().getUserId())) {
            createBountyWhenNecessary(principal, userProfile);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GithubVerificationDto getVerification(Principal principal) {
        return githubBountyRepository.findByUserId(principal.getName()).map(b -> GithubVerificationDto.builder().approved(b.getValid()).createdAt(b.getCreatedAt()).build())
                .orElse(GithubVerificationDto.builder()
                        .approved(false).build());
    }

    private void createBountyWhenNecessary(Principal principal, UserProfile userProfile) {
        try {
            if (!githubBountyRepository.existsByUserId(principal.getName())) {
                GithubUser githubUser = githubClient.getUser(userProfile.getGithub().getUsername());
                boolean validForBounty = isValidForBounty(githubUser);
                saveGithubBounty(principal, githubUser, validForBounty);
                if (validForBounty) {
                    saveBounty(principal);
                }
            }
        } catch (Exception e) {
            log.error("Error when creating github bounty", e);
        }

    }

    private boolean isValidForBounty(GithubUser githubUser) {
        return githubUser.getCreatedAt().isBefore(MIN_PROFILE_DATE);
    }

    private void saveBounty(Principal principal) {
        bountyService.createBounty(CreateBountyCommand.builder()
                .userId(principal.getName())
                .type(BountyType.LINK_GITHUB).build());
    }

    private void saveGithubBounty(Principal principal, GithubUser githubUser, boolean validForBounty) {
        GithubBounty githubBounty = GithubBounty.builder()
                .userId(principal.getName())
                .githubId(profileService.getUserProfile(null, principal).getGithub().getUserId())
                .createdAt(githubUser.getCreatedAt())
                .location(githubUser.getLocation())
                .valid(validForBounty)
                .build();
        githubBountyRepository.save(githubBounty);
    }
}
