package id.codigo.seedroid.model.json.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import id.codigo.seedroid.model.json.BaseModel;

/**
 * Created by Lukma on 5/17/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseAuthModel extends BaseModel {
    @JsonProperty("user_id")
    public String userId;
    @JsonProperty("user_access_token")
    public String userAccessToken;
    @JsonProperty("user_access_token_expired")
    public String userAccessTokenExpired;
}
