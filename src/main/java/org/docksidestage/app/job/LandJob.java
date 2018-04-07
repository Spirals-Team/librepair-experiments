/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.app.job;

import javax.annotation.Resource;

import org.docksidestage.app.logic.DanceSongLogic;
import org.docksidestage.dbflute.exbhv.MemberBhv;
import org.docksidestage.dbflute.exentity.Member;
import org.lastaflute.db.jta.stage.TransactionStage;
import org.lastaflute.job.LaJob;
import org.lastaflute.job.LaJobRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jflute
 */
public class LandJob implements LaJob {

    private static final Logger logger = LoggerFactory.getLogger(LandJob.class);

    @Resource
    private TransactionStage stage;
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private DanceSongLogic danceSongLogic; // logic call example
    @Resource
    private ItsMyPartyAssist itsMyPartyAssist; // assist call example

    @Override
    public void run(LaJobRuntime runtime) {
        waitFirstIfNeeds(runtime);
        stage.required(tx -> {
            Member before = memberBhv.selectByPK(3).get();
            updateMember(before.getMemberId());
            restoreMember(before.getMemberId(), before.getMemberName()); // for test
        });
        danceSongLogic.letsDance();
        itsMyPartyAssist.beHappy();
    }

    private void waitFirstIfNeeds(LaJobRuntime runtime) {
        Long waitFirst = (Long) runtime.getParameterMap().get("waitFirst");
        if (waitFirst != null) {
            logger.debug("...Waiting ({}): job={}", waitFirst, getClass().getSimpleName());
            try {
                Thread.sleep(waitFirst);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Failed to sleep.", e);
            }
        }
    }

    private void updateMember(Integer memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberName("byJob");
        memberBhv.updateNonstrict(member);
    }

    private void restoreMember(Integer memberId, String memberName) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberName(memberName);
        memberBhv.updateNonstrict(member);
    }
}
