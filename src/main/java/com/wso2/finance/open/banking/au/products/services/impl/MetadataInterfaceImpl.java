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

package com.wso2.finance.open.banking.au.products.services.impl;

import com.wso2.finance.open.banking.au.products.exception.MetadataException;
import com.wso2.finance.open.banking.au.products.model.ADRDetails;
import com.wso2.finance.open.banking.au.products.model.SoftwareDetails;
import com.wso2.finance.open.banking.au.products.services.MetadataInterface;
import com.wso2.finance.open.banking.au.products.utils.MetadataHolder;
import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Metadata Interface Implement Class.
 */
public class MetadataInterfaceImpl implements MetadataInterface {

    private static Map<String, String> softwareProductMap;
    private static Map<String, String> dataRecipientMap;

    private final Log log = LogFactory.getLog(MetadataInterfaceImpl.class);

    /**
     * This method is to get ADR Details.
     *
     */
    @Override
    public Map<String, String> getADRDetails() {

        JSONObject json;
        try {

            json = readJsonFromUrl();
        } catch (IOException e) {
            throw new MetadataException("Error while reading Data Recipient Endpoint", e);
        } catch (NullPointerException e) {
            log.debug("Unable to retrieve status from Directory. " +
                    "Possible because Common HttpPool is not initialized yet.");
            return null;
        }

        JSONArray dataRecipientsJsonArray = json.getJSONArray("dataRecipients");

        for (int r = 0; r < dataRecipientsJsonArray.length(); r++) {
            JSONObject obj = dataRecipientsJsonArray.getJSONObject(r);
            dataRecipientMap.put(obj.getString("softwareProductId"), obj.getString("dataRecipientStatus"));
        }

        return dataRecipientMap;
    }

    /**
     * This method is to get Software Details.
     *
     */
    @Override
    public Map<String, String> getSoftwareDetails() {

        JSONObject json;
        try {

        } catch (IOException e) {
            throw new MetadataException("Error while reading Software Product Endpoint.", e);
        } catch (NullPointerException e) {
            log.debug("Unable to retrieve status from Directory. " +
                    "Possible because Common HttpPool is not initialized yet.");
            return null;
        }

        JSONArray softwareProductsJsonArray = json.getJSONArray("softwareProducts");

        for (int jsonElementIndex = 0; jsonElementIndex < softwareProductsJsonArray.length(); jsonElementIndex++) {
            JSONObject obj = softwareProductsJsonArray.getJSONObject(jsonElementIndex);
            softwareProductMap.put(obj.getString("softwareProductId"), obj.getString("dataRecipientStatus"));
        }

        return softwareProductMap;
    }

    /**
     * This method is save organization name,customer username, and spec in local database.
     *
     * @param dataRecipientId - Id of the Data Recipient.
     * @param dataRecipientStatus - Status of the data recipient.
     */
    @Override
    public ADRDetails postADRDetails(String dataRecipientId, String dataRecipientStatus) {

        /*
         *  Updated response is set to  this object.
         */
        ADRDetails adrDetails = new ADRDetails();

        log.debug("Metadata Scheduled Task is executing.");

        try {
            // Get Data Recipient statuses
            dataRecipientMap = getADRDetails();
        } catch (MetadataException e) {
            log.error("Error while getting statuses from directory", e);
            MetadataHolder.setDataRecipient(null);
            return null;
        }

        log.debug("Metadata Scheduled Task is finished.");

        return adrDetails;
    }

    /**
     * This method is save organization name,customer username, and spec in local database.
     *
     * @param softwareProductId - Id of the Software product.
     * @param softwareProductStatus - Status of the Software product.
     */
    @Override
    public SoftwareDetails postSoftwareDetails(String softwareProductId, String softwareProductStatus) {

        /*
         *  updated response is set to  this object.
         */
        SoftwareDetails softwareDetails = new SoftwareDetails();

        log.debug("Metadata Scheduled Task is executing.");

        try {
            // Get Software Product statuses
            softwareProductMap = getSoftwareDetails();
        } catch (MetadataException e) {
            log.error("Error while getting statuses from directory", e);
            MetadataHolder.setSoftwareProduct(null);
            return null;
        }

        log.debug("AU Metadata Scheduled Task is finished.");

        return softwareDetails;
    }

//    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//
//        URL urlObject = new URL(url);
//
//        try (CloseableHttpClient httpclient = (CloseableHttpClient)
//                HTTPClientUtils.getHttpClient(urlObject.getPort(), urlObject.getProtocol())) {
//
//            HttpGet httpGet = new HttpGet(url);
//            CloseableHttpResponse responseBody = httpclient.execute(httpGet);
//            return getResponse(responseBody);
//        }
//    }
}
