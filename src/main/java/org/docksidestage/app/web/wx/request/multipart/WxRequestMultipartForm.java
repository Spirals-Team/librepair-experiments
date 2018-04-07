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
package org.docksidestage.app.web.wx.request.multipart;

import org.lastaflute.web.ruts.multipart.MultipartFormFile;
import org.lastaflute.web.validation.Required;

/**
 * @author jflute
 */
public class WxRequestMultipartForm {

    public String birthdate;

    @Required
    public MultipartFormFile file;

    @Override
    public String toString() {
        return "{" + birthdate + ", " + file + "}";
    }
}
