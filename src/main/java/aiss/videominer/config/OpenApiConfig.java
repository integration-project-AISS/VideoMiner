package aiss.videominer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI videoMinerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Video Miner API")
                        .version("1.0.0")
                        .description("API central para la gestión y agregación de contenido multimedia de diversos mineros.")
                        .contact(new Contact()
                                .name("Angel - Equipo AISS")
                                .email("angelnolasco13022@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                );
    }
}