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

package com.wso2.finance.open.banking.au.products.dao;

import com.wso2.finance.open.banking.au.products.exception.MetadataException;
import com.wso2.finance.open.banking.au.products.model.ADRDetails;
import com.wso2.finance.open.banking.au.products.model.SoftwareDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Organization DAO file to handle database data.
 **/

public class MetadataDAO {

    private final Log log = LogFactory.getLog(MetadataDAO.class);

    /**
     * Insert Software  details.
     **/
    public static void postSoftwareDetails(String softwareProductId, String softwareProductStatus) {

    }

    /**
     * Insert ADR details.
     **/
    public static void postADRDetails(String dataRecipientId, String dataRecipientStatus) {

    }

    /**
     * Get registered Software  details.
     **/
    public List<SoftwareDetails> getSoftwareDetails() throws MetadataException {

        log.info("Retrieving registered software list");

        List<SoftwareDetails> objectList = new ArrayList<>();

        return objectList;
    }

    /**
     * Get registered ADR details.
     **/
    public List<ADRDetails> getADRDetails() throws MetadataException {

        log.info("Retrieving registered adr list");

        List<ADRDetails> objectList = new ArrayList<>();

        return objectList;
    }
}
