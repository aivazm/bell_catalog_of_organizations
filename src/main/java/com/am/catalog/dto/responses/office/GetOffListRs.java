package com.am.catalog.dto.responses.office;

import com.am.catalog.dto.OfficeRs;

import java.util.List;

public class GetOffListRs {
    private List<OfficeRs> data;

    public GetOffListRs() {
    }

    public List<OfficeRs> getData() {
        return data;
    }

    public void setData(List<OfficeRs> data) {
        this.data = data;
    }
}
