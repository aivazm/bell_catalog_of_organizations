package com.am.catalog.dto.responses.organization;

import com.am.catalog.model.Organization;

public class FindOrgByIdRs {

    private Organization data;

    public FindOrgByIdRs() {
    }

    public Organization getData() {
        return data;
    }

    public void setData(Organization organization) {
        this.data = organization;
    }
}
