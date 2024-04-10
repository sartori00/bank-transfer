package br.com.itau.banktransfer.config;

import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.exception.handler.ErrorsValidateData;
import br.com.itau.banktransfer.exception.handler.ProblemDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Transfer - Itaú")
                        .version("v1")
                        .description("API Rest for Itaú Challenge ")
                        .contact(new Contact()
                                .name("Rodrigo Sartori")
                                .email("93sartori@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Challenge Original Repository")
                        .url("https://github.com/mllcarvalho/DesafioItau"))
                .components(new Components().schemas(
                    this.generateSchemas()
                ));
    }

    private Map<String, Schema> generateSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ProblemDto.class);
        Map<String, Schema> errorValidationSchema = ModelConverters.getInstance().read(ErrorsValidateData.class);
        Map<String, Schema> transferResponseOk = ModelConverters.getInstance().read(TransferResponseDto.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(errorValidationSchema);
        schemaMap.putAll(transferResponseOk);

        return schemaMap;
    }


}
