package com.sablengauto.showroomapi.repository;

import com.sablengauto.showroomapi.entity.AppLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppLogRepository
        extends JpaRepository<AppLog, Long> {

}
