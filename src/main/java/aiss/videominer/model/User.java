package aiss.videominer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Juan C. Alonso
 */
@Schema(description = "Información del creador de contenido o usuario de la plataforma")
@Entity
@Table(name = "VMUser")
public class User {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del usuario")
    private Long id;

    @Schema(description = "Nombre de usuario o alias", example = "AISS_Integrator")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Enlace al perfil del usuario", example = "https://dailymotion.com/user/aiss")
    @JsonProperty("user_link")
    private String user_link;

    @Schema(description = "URL de la imagen de perfil del usuario", example = "https://dailymotion.com/user/aiss/avatar.jpg")
    @JsonProperty("picture_link")
    private String picture_link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_link() {
        return user_link;
    }

    public void setUser_link(String user_link) {
        this.user_link = user_link;
    }

    public String getPicture_link() {
        return picture_link;
    }

    public void setPicture_link(String picture_link) {
        this.picture_link = picture_link;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", user_link='" + user_link + '\'' +
                ", picture_link='" + picture_link + '\'' +
                '}';
    }

    public User() {
    }

    public User(String name, String user_link, String picture_link) {
        this.name = name;
        this.user_link = user_link;
        this.picture_link = picture_link;
    }

}
