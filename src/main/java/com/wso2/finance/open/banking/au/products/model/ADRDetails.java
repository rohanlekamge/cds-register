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

@ApiModel(description = "Definition for ADR Status Update request body")
public class ADRDetails {

    @NotNull
    private String dataRecipientId = null;

    @NotNull
    private String dataRecipientStatus = null;

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("dataRecipientId")
    public String getDataRecipientId() {
        return dataRecipientId;
    }
    public void setDataRecipientId(String dataRecipientId) {
        this.dataRecipientId = dataRecipientId;
    }

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("dataRecipientStatus")
    public String getDataRecipientStatus() {
        return dataRecipientStatus;
    }
    public void setDataRecipientStatus(String dataRecipientStatus) {
        this.dataRecipientStatus = dataRecipientStatus;
    }

    @Override
    public String toString() {
        return "ADRDetails{" +
                "dataRecipientId='" + dataRecipientId + '\'' +
                ", dataRecipientStatus='" + dataRecipientStatus + '\'' +
                '}';
    }
}
