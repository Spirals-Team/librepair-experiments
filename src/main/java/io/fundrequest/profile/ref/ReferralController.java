package io.fundrequest.profile.ref;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ReferralController {

    private ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("/referrals")
    public ModelAndView showReferrals(Principal principal) {
        ModelAndView mav = new ModelAndView("fragments/referrals");
        mav.addObject("referrals", referralService.getReferrals(principal));
        return mav;
    }

    @GetMapping("/referrals/total")
    @ResponseBody
    public String showTotal(Principal principal) {
        return referralService.getTotalVerifiedReferrals(principal) + " FND";
    }
}
