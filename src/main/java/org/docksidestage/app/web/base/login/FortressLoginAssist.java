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
package org.docksidestage.app.web.base.login;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalThing;
import org.docksidestage.app.web.signin.SigninAction;
import org.docksidestage.dbflute.cbean.MemberCB;
import org.docksidestage.dbflute.exbhv.MemberBhv;
import org.docksidestage.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dbflute.exentity.Member;
import org.docksidestage.dbflute.exentity.MemberLogin;
import org.docksidestage.mylasta.action.FortressUserBean;
import org.docksidestage.mylasta.direction.FortressConfig;
import org.lastaflute.core.time.TimeManager;
import org.lastaflute.web.login.PrimaryLoginManager;
import org.lastaflute.web.login.TypicalLoginAssist;
import org.lastaflute.web.login.credential.UserPasswordCredential;
import org.lastaflute.web.login.option.LoginSpecifiedOption;

/**
 * @author jflute
 */
public class FortressLoginAssist extends TypicalLoginAssist<Integer, FortressUserBean, Member> // #change_it also UserBean
        implements PrimaryLoginManager {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private TimeManager timeManager;
    @Resource
    private FortressConfig config;
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private MemberLoginBhv memberLoginBhv;

    // ===================================================================================
    //                                                                           Find User
    //                                                                           =========
    @Override
    protected void checkCredential(CredentialChecker checker) {
        checker.check(UserPasswordCredential.class, credential -> {
            return memberBhv.selectCount(cb -> arrangeLoginByCredential(cb, credential)) > 0;
        });
    }

    @Override
    protected void resolveCredential(CredentialResolver resolver) {
        resolver.resolve(UserPasswordCredential.class, credential -> {
            return memberBhv.selectEntity(cb -> arrangeLoginByCredential(cb, credential));
        });
    }

    private void arrangeLoginByCredential(MemberCB cb, UserPasswordCredential credential) {
        cb.query().arrangeLogin(credential.getUser(), encryptPassword(credential.getPassword()));
    }

    @Override
    protected OptionalEntity<Member> doFindLoginUser(Integer userId) {
        return memberBhv.selectEntity(cb -> {
            cb.query().arrangeLoginByIdentity(userId);
        });
    }

    // ===================================================================================
    //                                                                       Login Process
    //                                                                       =============
    @Override
    protected FortressUserBean createUserBean(Member userEntity) {
        return new FortressUserBean(userEntity);
    }

    @Override
    protected OptionalThing<String> getCookieRememberMeKey() {
        return OptionalThing.of(config.getCookieRememberMeFortressKey());
    }

    @Override
    protected Integer toTypedUserId(String userKey) {
        return Integer.valueOf(userKey);
    }

    @Override
    protected void saveLoginHistory(Member member, FortressUserBean userBean, LoginSpecifiedOption option) {
        MemberLogin login = new MemberLogin();
        login.setMemberId(member.getMemberId());
        login.setLoginMemberStatusCodeAsMemberStatus(member.getMemberStatusCodeAsMemberStatus());
        login.setLoginDatetime(timeManager.currentDateTime());
        login.setMobileLoginFlg_False(); // mobile unsupported for now
        memberLoginBhv.insert(login);
    }

    // ===================================================================================
    //                                                                      Login Resource
    //                                                                      ==============
    @Override
    protected Class<FortressUserBean> getUserBeanType() {
        return FortressUserBean.class;
    }

    @Override
    protected Class<?> getLoginActionType() {
        return SigninAction.class;
    }
}
