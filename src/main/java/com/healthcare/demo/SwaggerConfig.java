package com.healthcare.demo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Comprehensive Healthcare Management API")
                        .version("1.0")
                        .description("API documentation for managing doctors, patients, appointments, prescriptions, and reports"))
                .tags(List.of(
                        new Tag().name("Doctor Controller").description("Operations related to doctors"),
                        new Tag().name("Patient Controller").description("Operations related to patients"),
                        new Tag().name("Appointment Controller").description("Operations related to appointments"),
                        new Tag().name("Prescription Controller").description("Operations related to prescriptions"),
                        new Tag().name("Report Controller").description("Operations related to reports")
                ));
    }
}
