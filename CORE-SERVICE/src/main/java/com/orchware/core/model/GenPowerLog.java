package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

@Entity
@Table(name = "gen_power_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenPowerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_power_log_id", length = 25)
    private Long genPowerLogId;
    @Column(name = "agent_id", length = 25)
    private String agentId;
    @Column(name = "agen_record_id", length = 25)
    private String agentRecordId;
    @Column(name = "dev_id", length = 25)
    private String devId;
    @Column(name = "time", length = 25)
    private String time;
    @Column(name = "dev_type", length = 25)
    private String devType;
    @Column(name = "pac", length = 25)
    private String pac;
    @Column(name = "pac_delta", length = 25)
    private String pacDelta;
    @Column(name = "grid_connected", length = 25)
    private String gridConnected;
    @Column(name = "grid_status", length = 25)
    private String gridStatus;
    @Column(name = "grid_out_pac", length = 25)
    private String gridOutPac;
    @Column(name = "grid_out_pac_delta", length = 25)
    private String gridOutPacDelta;
    @Column(name = "battery_connected", length = 25)
    private String batteryConnected;
    @Column(name = "battery_status", length = 25)
    private String batteryStatus;
    @Column(name = "pac_to_battery", length = 25)
    private String pacToBattery;
    @Column(name = "pac_to_battery_delta", length = 25)
    private String pacToBatteryDelta;
}
