package org.scf.azure.gradle.repos;


import org.scf.azure.gradle.entities.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepo extends JpaRepository<AccessLog, String> {
}
