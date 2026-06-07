package org.distributeddebugger.orderservicev1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// OpenAPIDefinition, info-title,version,description,contact, licence
@OpenAPIDefinition(
        info = @Info(
                title = "Order Service V1 API",
                version = "v1",
                description = "API documentation for creating and managing orders in the distributed transactions debugger.",
                contact = @Contact(name = "Distributed Debugger Team"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
        )
)
@SpringBootApplication
public class OrderServiceV1Application {

    public static void main(String[] args) {

        SpringApplication.run(OrderServiceV1Application.class, args);
    }

}
