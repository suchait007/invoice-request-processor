package com.invoice.request.processor.function.repos;

import com.invoice.request.processor.function.entities.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepo extends JpaRepository<AccessLog, String> {
}
