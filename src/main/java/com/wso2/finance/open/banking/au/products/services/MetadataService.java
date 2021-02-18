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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wso2.finance.open.banking.au.products.exception.MetadataException;
import com.wso2.finance.open.banking.au.products.model.ADRDetails;
import com.wso2.finance.open.banking.au.products.model.SoftwareDetails;

import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Metadata Service.
 */
@Path("/register")
public class MetadataService {

    private static Map<String, String> softwareProductMap;
    private static Map<String, String> dataRecipientMap;
    private static final JsonParser jsonParser = new JsonParser();

    static {

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONArray dataRecipientsJsonArray = new JSONArray();
        JSONArray softwareProductJsonArray = new JSONArray();

        try (FileReader reader = new FileReader("metadata.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray metadataList = (JSONArray) obj;
            System.out.println(metadataList);

            //Iterate over array
            for(Object emp: metadataList){
                dataRecipientsJsonArray = (JSONArray) ((JSONObject) emp).get("dataRecipients");
                softwareProductJsonArray = (JSONArray) ((JSONObject) emp).get("softwareProducts");
            }

            for (int r = 0; r < dataRecipientsJsonArray.size(); r++) {
                JSONObject objj = (JSONObject) dataRecipientsJsonArray.get(r);
                dataRecipientMap.put(objj.getString("dataRecipientId"), objj.getString("dataRecipientStatus"));
            }

            for (int r = 0; r < softwareProductJsonArray.size(); r++) {
                JSONObject objj = (JSONObject) softwareProductJsonArray.get(r);
                softwareProductMap.put(objj.getString("softwareProductId"), objj.getString("softwareProductStatus"));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/data-recipients/brands/software-products/status")
    @Produces("application/json")
    public Response getSoftwareProductsStatus() {

        Response responseGet;

        try {
            responseGet = Response.status(200).entity(softwareProductMap).build();
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

        try {
            responseGet = Response.status(200).entity(dataRecipientMap).build();
        } catch (MetadataException a) {
            responseGet = Response.status(a.getErrorCode()).build();
        }

        return responseGet;
    }


    @POST
    @Path("/adr/status-update")
    @Produces("application/json")
    public Response postSoftwareProductsStatus(@ApiParam(value = "The details of the new Software Status Update" ,
            required = true) SoftwareDetails body) {

        Response responsePost;

        if (body.getSoftwareProductId() == null || body.getSoftwareProductStatus() == null) {
            responsePost = Response.status(400).build();
        } else {
            try {

                softwareProductMap.put(body.getSoftwareProductId(), body.getSoftwareProductStatus());

                JsonObject metadata = postSoftwareProductsStatusResponse(body.getSoftwareProductId(), body.getSoftwareProductStatus());
                String response = getResponse(metadata);

                responsePost = Response.status(200).entity(response).build();

            } catch (MetadataException a) {
                responsePost = Response.status(a.getErrorCode()).build();
            }
        }

        return responsePost;
    }

    @POST
    @Path("/software/status-update")
    @Produces("application/json")
    public Response postDataRecipientsStatus(@ApiParam(value = "The details of the new ADR Status Update" ,
            required = true) ADRDetails body) {

        Response responsePost;

        if (body.getDataRecipientId() == null || body.getDataRecipientStatus() == null) {
            responsePost = Response.status(400).build();
        } else {
            try {

                dataRecipientMap.put(body.getDataRecipientId(), body.getDataRecipientStatus());

                JsonObject metadata = postDataRecipientsStatusResponse(body.getDataRecipientId(), body.getDataRecipientStatus());
                String response = getResponse(metadata);

                responsePost = Response.status(200).entity(response).build();

            } catch (MetadataException a) {
                responsePost = Response.status(a.getErrorCode()).build();
            }
        }

        return responsePost;
    }

    protected static String getResponse(JsonObject metadata) {

        JsonObject response = new JsonObject();
        response.add("metadata", metadata);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedResponse = gson.toJson(response);
        return formattedResponse;
    }

    private static JsonObject postSoftwareProductsStatusResponse(String softwareProductId, String softwareProductStatus) {

        String metaJson = "{  \n" +
                "        \"softwareProductId\": \"" + softwareProductId + "\",\n" +
                "        \"softwareProductStatus\": \"" + softwareProductStatus + "\",\n" +
                "   }\n";
        return jsonParser.parse(metaJson).getAsJsonObject();
    }

    private static JsonObject postDataRecipientsStatusResponse(String dataRecipientId, String dataRecipientStatus) {

        String metaJson = "{  \n" +
                "        \"dataRecipientId\": \"" + dataRecipientId + "\",\n" +
                "        \"dataRecipientStatus\": \"" + dataRecipientStatus + "\",\n" +
                "   }\n";
        return jsonParser.parse(metaJson).getAsJsonObject();
    }
}
