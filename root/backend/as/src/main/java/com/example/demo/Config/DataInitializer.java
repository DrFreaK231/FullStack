package com.example.demo.Config;

import com.example.demo.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultClient(RegisteredClientRepository registeredClientRepository,
                                          PasswordEncoder passwordEncoder,
                                          UserRepository userRepository) {
        return args -> {
            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("my-client")
                    .clientSecret(passwordEncoder.encode("my-secret"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("http://localhost:8080/login/oauth2/code/my-client")
                    .scope("openid")
                    .scope("read")
                    .build();

            if (registeredClientRepository.findByClientId("my-client") == null) {
                registeredClientRepository.save(client);
            }


        };

    }

}

