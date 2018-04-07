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
package org.docksidestage.app.web.signout;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.FortressBaseAction;
import org.docksidestage.app.web.base.login.FortressLoginAssist;
import org.docksidestage.app.web.signin.SigninAction;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author toshiaki.arai
 * @author jflute
 */
public class SignoutAction extends FortressBaseAction {

    @Resource
    private FortressLoginAssist fortressLoginAssist;

    @Execute
    public HtmlResponse index() {
        fortressLoginAssist.logout();
        return redirect(SigninAction.class);
    }
}
