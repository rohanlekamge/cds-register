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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Definition for Software Status Update request body")
public class SoftwareDetails {

    @NotNull
    private String softwareProductId = null;

    @NotNull
    private String softwareProductStatus = null;

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("softwareProductId")
    public String getSoftwareProductId() {
        return softwareProductId;
    }
    public void setSoftwareProductId(String softwareProductId) {
        this.softwareProductId = softwareProductId;
    }

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("softwareProductStatus")
    public String getSoftwareProductStatus() {
        return softwareProductStatus;
    }
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
