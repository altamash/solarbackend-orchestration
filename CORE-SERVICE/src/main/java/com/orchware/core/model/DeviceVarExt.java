package com.orchware.core.model;

import lombok.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "device_var_exts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceVarExt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", length = 25)
    private Long deviceId;
    @Column(name = "acct_id")
    private Long accountId;
    @Column(name = "var01", length = 25)
    private String var1;
    @Column(name = "var02", length = 25)
    private String var2;
    @Column(name = "var03", length = 25)
    private String var3;
    @Column(name = "var04", length = 25)
    private String var4;
    @Column(name = "var05", length = 25)
    private String var5;
    @Column(name = "var06", length = 25)
    private String var6;
    @Column(name = "var07", length = 25)
    private String var7;
    @Column(name = "var08", length = 25)
    private String var8;
    @Column(name = "var09", length = 25)
    private String var9;
    @Column(name = "var10", length = 25)
    private String var10;
    @Column(name = "var11", length = 25)
    private String var11;
    @Column(name = "var12", length = 25)
    private String var12;
    @Column(name = "var13", length = 25)
    private String var13;
    @Column(name = "var14", length = 25)
    private String var14;
    @Column(name = "var15", length = 25)
    private String var15;
    @Column(name = "var16", length = 25)
    private String var16;
    @Column(name = "var17", length = 25)
    private String var17;
    @Column(name = "var18", length = 25)
    private String var18;
    @Column(name = "var19", length = 25)
    private String var19;
    @Column(name = "var20", length = 25)
    private String var20;
    @Column(name = "num01", length = 25)
    private String num1;
    @Column(name = "num02", length = 25)
    private String num2;
    @Column(name = "num03", length = 25)
    private String num3;
    @Column(name = "num04", length = 25)
    private String num4;
    @Column(name = "num05", length = 25)
    private String num5;
    @Column(name = "num06", length = 25)
    private String num6;
    @Column(name = "num07", length = 25)
    private String num7;
    @Column(name = "num08", length = 25)
    private String num8;
    @Column(name = "num09", length = 25)
    private String num9;
    @Column(name = "num10", length = 25)
    private String num10;
    @Column(name = "date01", length = 25)
    private String date1;
    @Column(name = "date02", length = 25)
    private String date2;
    @Column(name = "date03", length = 25)
    private String date3;
    @Column(name = "date04", length = 25)
    private String date4;
    @Column(name = "date05", length = 25)
    private String date5;
    @Column(name = "o_json", length = 25)
    private String oJson;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_br_var_def_id")
    private DeviceBrVarDef deviceBrVarDef;

}
