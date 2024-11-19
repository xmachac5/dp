package org.master.auth;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;

@OpenAPIDefinition(
        info = @Info(
                title = "Your API",
                version = "1.0.0",
                description = "API with Basic Authentication"
        ),
        security = @SecurityRequirement(name = "basic-auth")
)
@SecurityScheme(
        securitySchemeName = "basic-auth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class ApiSecurityConfig {
        // Configuration for OpenAPI security
}
