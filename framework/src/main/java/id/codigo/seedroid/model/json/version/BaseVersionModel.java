package id.codigo.seedroid.model.json.version;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

import id.codigo.seedroid.model.json.BaseModel;

/**
 * Created by papahnakal on 03/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseVersionModel extends BaseModel{
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("message")
    public String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    public static class Metadata {

        @JsonProperty("version")
        private String version;
        @JsonProperty("force_update")
        private Boolean forceUpdate;
        @JsonProperty("link_update")
        private String linkUpdate;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("version")
        public String getVersion() {
            return version;
        }

        @JsonProperty("version")
        public void setVersion(String version) {
            this.version = version;
        }

        @JsonProperty("force_update")
        public Boolean getForceUpdate() {
            return forceUpdate;
        }

        @JsonProperty("force_update")
        public void setForceUpdate(Boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        @JsonProperty("link_update")
        public String getLinkUpdate() {
            return linkUpdate;
        }

        @JsonProperty("link_update")
        public void setLinkUpdate(String linkUpdate) {
            this.linkUpdate = linkUpdate;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }
}
