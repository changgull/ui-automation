package com.stonecress.core;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class ApiBase extends Base {
    private RequestSpecification request = null;

    protected RequestSpecification buildRequest() {
        setRequest(RestAssured.given());
        return request;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    protected void setRequest(RequestSpecification req) {
        request = req;
    }

    public Response postJson(JSONObject payload, String id) {
        return getRequest()
                .header("Content-Type", "application/json").body(payload.toString())
                .post(id);
    }

    public Response postJson(JSONObject payload) {
        return getRequest()
                .header("Content-Type", "application/json").body(payload.toString())
                .post();
    }

    public Response deleteJson(JSONObject payload, String id) {
        return getRequest()
                .header("Content-Type", "application/json").body(payload.toString())
                .delete(id);
    }

    public Response patchJson(JSONObject payload, String id) {
        return getRequest()
                .header("Content-Type", "application/json").body(payload.toString())
                .patch(id);
    }

    public Response putJson(JSONObject payload, String id) {
        return getRequest()
                .header("Content-Type", "application/json").body(payload.toString())
                .put(id);
    }
}
