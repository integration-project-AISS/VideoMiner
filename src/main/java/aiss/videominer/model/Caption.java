package aiss.videominer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema; 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Juan C. Alonso
 */
@Schema(description = "Subtítulos o leyendas asociados a un vídeo")
@Entity
@Table(name = "Caption")
public class Caption {

    @Id
    @JsonProperty("id")
    @Schema(description = "ID único del subtítulo")
    private String id;

    @Schema(description = "URL de acceso al archivo de subtítulos", example = "https://peertube.com/api/v1/videos/123/captions/es")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Lenguaje del subtítulo", example = "es")
    @JsonProperty("language")
    private String language;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Caption{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public Caption() {
    }

    public Caption(String id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }
}
