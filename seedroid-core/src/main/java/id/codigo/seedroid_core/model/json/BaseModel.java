package id.codigo.seedroid_core.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Lukma on 5/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseModel {
    @JsonProperty("status")
    public int status;
    @JsonProperty("message")
    public String message;
    @JsonProperty("display_message")
    public String displayMessage;
}
