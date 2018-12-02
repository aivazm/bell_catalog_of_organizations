package com.am.catalog.dto.responses.organization;

import com.am.catalog.dto.OrganizationRs;

import java.util.List;

public class GetOrgListRs {

    private List<OrganizationRs> data;

    public GetOrgListRs() {
    }

    public List<OrganizationRs> getData() {
        return data;
    }

    public void setData(List<OrganizationRs> data) {
        this.data = data;
    }
}
