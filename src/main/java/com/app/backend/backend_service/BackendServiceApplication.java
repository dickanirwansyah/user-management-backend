package com.app.backend.backend_service;

import com.app.backend.backend_service.constant.ApplicationConstant;
import com.app.backend.backend_service.entities.Roles;
import com.app.backend.backend_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceApplication.class, args);
	}

}

@Component
@RequiredArgsConstructor
@Slf4j
class InitialData implements CommandLineRunner {

	private final RolesRepository rolesRepository;

	@Override
	public void run(String... args) throws Exception {
		if (rolesRepository.findAll().isEmpty()){
			String[] dataRoles = {"ROLE_ADMIN", "ROLE_USER"};
            for (String dataRole : dataRoles) {
                Roles roles = new Roles();
                roles.setName(dataRole);
                roles.setDeleted(ApplicationConstant.ACTIVE);
                rolesRepository.save(roles);
            }
		}
	}
}
