/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Commercial License available at http://wso2.com/licenses. For specific
 * language governing the permissions and limitations under this license,
 * please see the license as well as any agreement you’ve entered into with
 * WSO2 governing the purchase of this software and any associated services.
 *
 */

package com.wso2.finance.open.banking.au.products.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoftwareDetails {

    private String softwareProductId = null;
    private String softwareProductStatus = null;

    @JsonProperty("softwareProductId")
    public String getSoftwareProductId() {
        return softwareProductId;
    }
    @JsonProperty("softwareProductId")
    public void setSoftwareProductId(String softwareProductId) {
        this.softwareProductId = softwareProductId;
    }

    @JsonProperty("softwareProductStatus")
    public String getSoftwareProductStatus() {
        return softwareProductStatus;
    }
    @JsonProperty("softwareProductStatus")
    public void setSoftwareProductStatus(String softwareProductStatus) {
        this.softwareProductStatus = softwareProductStatus;
    }

    @Override
    public String toString() {
        return "SoftwareDetails{" +
                "softwareProductId='" + softwareProductId + '\'' +
                ", softwareProductStatus='" + softwareProductStatus + '\'' +
                '}';
    }
}
