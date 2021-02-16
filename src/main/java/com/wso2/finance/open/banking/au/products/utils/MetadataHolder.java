/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 Inc. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein is strictly forbidden, unless permitted by WSO2 in accordance with
 * the WSO2 Commercial License available at http://wso2.com/licenses.
 * For specific language governing the permissions and limitations under this
 * license, please see the license as well as any agreement you’ve entered into
 * with WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.finance.open.banking.au.products.utils;

import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * OSGi component containing resource objects.
 * Holds the JSON data of software product and data recipients.
 * Will be used by status validator to get the data.
 * Can be considered as primary cache that gets overridden every n minutes.
 */
@Component(
        name = "com.wso2.finance.open.banking.periodical.updater.AUMetadataHolder",
        immediate = true,
        service = MetadataHolder.class
)
public class MetadataHolder {

    private static Map<String, String> softwareProduct;
    private static Map<String, String> dataRecipient;

    public Map<String, String> getSoftwareProduct() {
        return softwareProduct;
    }

    public static synchronized void setSoftwareProduct(Map<String, String> softwareProduct) {
        MetadataHolder.softwareProduct = softwareProduct;
    }

    public Map<String, String> getDataRecipient() {
        return dataRecipient;
    }

    public static synchronized void setDataRecipient(Map<String, String> dataRecipient) {
        MetadataHolder.dataRecipient = dataRecipient;
    }
}
