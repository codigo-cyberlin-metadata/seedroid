package id.codigo.seedroid_uikit.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by papahnakal on 01/03/18.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility =
        JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Color {
    /**
     * color : [{"name":"C3","value":"#FFFFFF","description":"White"},{"name":"C2",
     * "value":"#1E1E1E","description":"Black"}]
     */
    @JsonProperty("color")
    private List<ColorEntity> color;

    public void setColor(List<ColorEntity> color)
    {
        this.color = color;
    }

    public List<ColorEntity> getColor()
    {
        return color;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility =
            JsonAutoDetect.Visibility.NONE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ColorEntity
    {
        /**
         * name : C3
         * value : #FFFFFF
         * description : White
         */
        @JsonProperty("name")
        private String name;

        @JsonProperty("value")
        private String value;

        @JsonProperty("description")
        private String description;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }
    }
}
