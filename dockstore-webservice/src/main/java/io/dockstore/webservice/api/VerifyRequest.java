/*
 *    Copyright 2017 OICR
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.dockstore.webservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Created by aduncan on 07/11/16.
 */
@ApiModel("VerifyRequest")
public class VerifyRequest {
    private boolean verify;
    private String verifiedSource;

    public VerifyRequest() {
    }

    public VerifyRequest(boolean verify, String verifiedSource) {
        this.verify = verify;
        this.verifiedSource = verifiedSource;
    }

    @JsonProperty
    public boolean getVerify() {
        return verify;
    }

    @JsonProperty
    public String getVerifiedSource() {
        return verifiedSource;
    }

}
