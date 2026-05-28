package com.sablengauto.showroomapi.service;

import com.sablengauto.showroomapi.entity.AppLog;
import com.sablengauto.showroomapi.repository.AppLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppLoggerService {
    private final AppLogRepository appLogRepository;

    public AppLoggerService(AppLogRepository appLogRepository) {
        this.appLogRepository = appLogRepository;
    }

    public void log(String method,
            String endpoint,
            String message) {

        AppLog appLog = new AppLog();

        appLog.setMethod(method);
        appLog.setEndpoint(endpoint);
        appLog.setMessage(message);
        appLog.setCreatedAt(LocalDateTime.now());

        appLogRepository.save(appLog);

    }
}
