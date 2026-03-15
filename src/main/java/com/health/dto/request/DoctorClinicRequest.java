package com.health.dto.request;

import java.util.List;

public class DoctorClinicRequest {
    List<Long>  clinicIds;
    public List<Long> getClinicIds() {
        return clinicIds;
    }

    public void setClinicIds(List<Long> clinicIds) {
        this.clinicIds = clinicIds;
    }
}
