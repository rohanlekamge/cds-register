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

package com.wso2.finance.open.banking.au.products.services;

import com.wso2.finance.open.banking.au.products.model.ADRDetails;
import com.wso2.finance.open.banking.au.products.model.SoftwareDetails;

import java.util.List;
import java.util.Map;

/**
 *This interface is used to initialize the Registration methods.
 */
public interface MetadataInterface {

    /**
     *This method is to get ADR details.
     */
    Map<String, String> getADRDetails();

    /**
     *This method is to get Software details.
     */
    Map<String, String> getSoftwareDetails();

    /**
     * This method is update ADR details such as dataRecipientId, dataRecipientStatus.
     */
    ADRDetails postADRDetails(String dataRecipientId, String dataRecipientStatus);

    /**
     * This method is update Software details such as softwareProductId, softwareProductStatus.
     */
    SoftwareDetails postSoftwareDetails(String softwareProductId, String softwareProductStatus);

}
