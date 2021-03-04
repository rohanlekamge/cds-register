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
 */

package com.wso2.finance.open.banking.au.products.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wso2.finance.open.banking.au.products.exception.MetadataException;
import com.wso2.finance.open.banking.au.products.model.ADRDetails;
import com.wso2.finance.open.banking.au.products.model.SoftwareDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Metadata Service.
 */
@Path("/")
public class MetadataService {

    private static Map<String, String> softwareProductMap;
    private static Map<String, String> dataRecipientMap;
    public static final JsonParser jsonParser = new JsonParser();

    SoftwareDetails softwareDetails = new SoftwareDetails();
    ADRDetails adrDetails = new ADRDetails();

    static {

        ObjectMapper mapper = new ObjectMapper();

        try
        {
            String jsonSoftware = "{\"740C368F-ECF9-4D29-A2EA-0514A66B0CDK\":\"Active\"}";
            String jsonAdr = "{\"740C368F-ECF9-4D29-A2EA-0514A66B0CDK\":\"Active\"}";

            //Convert Map to JSON
            softwareProductMap = mapper.readValue(jsonSoftware, new TypeReference<Map<String, String>>(){});
            dataRecipientMap = mapper.readValue(jsonAdr, new TypeReference<Map<String, String>>(){});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static {
//
//        //JSON parser object to parse read file
//        JSONParser jsonParser = new JSONParser();
//        JSONArray dataRecipientsJsonArray = new JSONArray();
//
//        try (FileReader reader = new FileReader("softwareProducts.json"))
//        {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//
//            JSONArray metadataList = (JSONArray) obj;
//            System.out.println(metadataList);
//
//            //Iterate over array
//            for(Object emp: metadataList){
//                dataRecipientsJsonArray = (JSONArray) ((JSONObject) emp).get("dataRecipients");
//            }
//
//            for (int r = 0; r < dataRecipientsJsonArray.size(); r++) {
//                JSONObject objj = (JSONObject) dataRecipientsJsonArray.get(r);
//                dataRecipientMap.put(objj.getString("dataRecipientId"), objj.getString("dataRecipientStatus"));
//            }
//
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//    }

    @GET
    @Path("/data-recipients/brands/software-products/status")
    @Produces("application/json")
    public Response getSoftwareProductsStatus() {

        Response responseGet;

        String response = softwareProductsStatusResponse(softwareProductMap);

        try {
            responseGet = Response.status(200).entity(response).build();
        } catch (MetadataException a) {
            responseGet = Response.status(a.getErrorCode()).build();
        }

        return responseGet;
    }

    @GET
    @Path("/data-recipients/status")
    @Produces("application/json")
    public Response getDataRecipientsStatus() {

        Response responseGet;

        String response = dataRecipientsStatusResponse(dataRecipientMap);

        try {
            responseGet = Response.status(200).entity(response).build();
        } catch (MetadataException a) {
            responseGet = Response.status(a.getErrorCode()).build();
        }

        return responseGet;
    }

    @POST
    @Path("/software/status-update")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postSoftwareProductsStatus(String body) {

        Response responsePost;

        if (StringUtils.isNotBlank(body)) {
            JSONArray array = new JSONArray(body);

            for(int i=0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                softwareDetails.setSoftwareProductId(object.getString("softwareProductId"));
                softwareDetails.setSoftwareProductStatus(object.getString("softwareProductStatus"));

                if (softwareDetails.getSoftwareProductStatus().equals("Active") || softwareDetails.getSoftwareProductStatus().equals("Inactive") ||  softwareDetails.getSoftwareProductStatus().equals("Removed")) {
                    softwareProductMap.put(softwareDetails.getSoftwareProductId(), softwareDetails.getSoftwareProductStatus());
                }
            }

            String response = softwareProductsStatusResponse(softwareProductMap);

            responsePost = Response.status(200).entity(response).build();

        } else {

            responsePost = Response.status(400).build();

        }

        return responsePost;
    }

    @POST
    @Path("/adr/status-update")
    @Produces("application/json; charset=utf-8")
    @Consumes("application/json")
    public Response postDataRecipientsStatus(String body) {

        Response responsePost;

        if (StringUtils.isNotBlank(body)) {
            JSONArray array = new JSONArray(body);

            for(int i=0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                adrDetails.setDataRecipientId(object.getString("dataRecipientId"));
                adrDetails.setDataRecipientStatus(object.getString("dataRecipientStatus"));

                if (adrDetails.getDataRecipientStatus().equals("Active") || adrDetails.getDataRecipientStatus().equals("Suspended") ||  adrDetails.getDataRecipientStatus().equals("Surrendered") ||  adrDetails.getDataRecipientStatus().equals("Revoked")) {
                    dataRecipientMap.put(adrDetails.getDataRecipientId(), adrDetails.getDataRecipientStatus());
                }
            }

            String response = dataRecipientsStatusResponse(dataRecipientMap);

            responsePost = Response.status(200).entity(response).build();

        } else {

            responsePost = Response.status(400).build();

        }

        return responsePost;
    }

    private static String dataRecipientsStatusResponse(Map<String, String> dataRecipientMap) {

        StringBuilder metaJson = new StringBuilder();
        int count = 0;
        metaJson.append("[");


        // Get keys and values
        for (Map.Entry<String, String> entry : dataRecipientMap.entrySet()) {

            String dataRecipientId = entry.getKey();
            String dataRecipientStatus = entry.getValue();
            String metaJsonTemp;
            count  = count + 1;

            if (dataRecipientMap.size() == 1 || dataRecipientMap.size() == count) {
                metaJsonTemp = "{\"dataRecipientId\":"+"\""+dataRecipientId+"\""+",\"dataRecipientStatus\":"+"\""+dataRecipientStatus+"\""+"}";
            } else {
                metaJsonTemp = "{\"dataRecipientId\":"+"\""+dataRecipientId+"\""+",\"dataRecipientStatus\":"+"\""+dataRecipientStatus+"\""+"},";
            }

            metaJson.append(metaJsonTemp);
        }

        metaJson.append("]");

        String metaJsonTempFinal = metaJson.toString();
        String trimmed = metaJsonTempFinal.trim();

        JsonElement dataRecipients = jsonParser.parse(trimmed);
        JsonObject response = new JsonObject();

        if (dataRecipients instanceof JsonObject) {
            JsonObject  jobject = dataRecipients.getAsJsonObject();
            response.add("dataRecipients", jobject);
        } else if (dataRecipients instanceof JsonArray) {
            JsonArray  jarray = dataRecipients.getAsJsonArray();
            response.add("dataRecipients", jarray);
        }

        Gson gsdon = new GsonBuilder().setPrettyPrinting().create();
        String formattedResponse = gsdon.toJson(response);
        return formattedResponse;
    }

    private static String softwareProductsStatusResponse(Map<String, String> softwareProductMap) {

        StringBuilder metaJson = new StringBuilder();
        int count = 0;
        metaJson.append("[");


        // Get keys and values
        for (Map.Entry<String, String> entry : softwareProductMap.entrySet()) {

            String softwareProductId = entry.getKey();
            String softwareProductStatus = entry.getValue();
            String metaJsonTemp;
            count  = count + 1;

            if (softwareProductMap.size() == 1 || softwareProductMap.size() == count) {
                metaJsonTemp = "{\"softwareProductId\":"+"\""+softwareProductId+"\""+",\"softwareProductStatus\":"+"\""+softwareProductStatus+"\""+"}";
            } else {
                metaJsonTemp = "{\"softwareProductId\":"+"\""+softwareProductId+"\""+",\"softwareProductStatus\":"+"\""+softwareProductStatus+"\""+"},";
            }

            metaJson.append(metaJsonTemp);
        }

        metaJson.append("]");

        String metaJsonTempFinal = metaJson.toString();
        String trimmed = metaJsonTempFinal.trim();

        JsonElement softwareProducts = jsonParser.parse(trimmed);
        JsonObject response = new JsonObject();

        if (softwareProducts instanceof JsonObject) {
            JsonObject  jobject = softwareProducts.getAsJsonObject();
            response.add("softwareProducts", jobject);
        } else if (softwareProducts instanceof JsonArray) {
            JsonArray  jarray = softwareProducts.getAsJsonArray();
            response.add("softwareProducts", jarray);
        }

        Gson gsdon = new GsonBuilder().setPrettyPrinting().create();
        String formattedResponse = gsdon.toJson(response);
        return formattedResponse;
    }
}
