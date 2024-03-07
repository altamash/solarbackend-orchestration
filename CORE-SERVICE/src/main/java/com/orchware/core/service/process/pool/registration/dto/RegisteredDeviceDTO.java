package com.orchware.core.service.process.pool.registration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.core.model.DeviceTypeConfig;
import com.orchware.core.model.DeviceVarExt;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisteredDeviceDTO {

    private Long deviceId;
//    private DeviceTypeConfig deviceTypeConfig;
    private String alias;
    private String authMethod;
    private String password;
    private String connectionKey;
    private String connectionKey2;
    private String username;
    private String uniqueId;
    private String state; //Operational, Unreachable, PendingConfig, Warning, Failed
    private String warningLevel; //Number 1-5
    private Long warningDetails;
    private String mobility;
    private String currentLoc;
    private TimeZone timeZone;
    private Long scheduleId; //from comm_schedule_tz
    private Long contactScheduleId; //inherited from brand if empty.
    private Date activeStartTime;
    private Date activeEndTime;
    private DeviceVarExtDTO deviceVarExt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
