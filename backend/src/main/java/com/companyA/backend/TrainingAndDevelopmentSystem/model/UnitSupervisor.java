package com.companyA.backend.TrainingAndDevelopmentSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UnitSupervisor {
    @Id
    private String id;
    private int uSupervisorId;
    private String uSupervisorName;
    private String uPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getuSupervisorId() {
        return uSupervisorId;
    }

    public void setuSupervisorId(int uSupervisorId) {
        this.uSupervisorId = uSupervisorId;
    }

    public String getuSupervisorName() {
        return uSupervisorName;
    }

    public void setuSupervisorName(String uSupervisorName) {
        this.uSupervisorName = uSupervisorName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }
}
