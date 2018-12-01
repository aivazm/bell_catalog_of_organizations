package com.am.catalog.service;

import com.am.catalog.dao.organization.OrganizationDao;
import com.am.catalog.model.Organization;
import com.am.catalog.view.OrganizationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Service

public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDao dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public ResponseEntity<String> save(OrganizationView view) {
        Organization organization = new Organization();
        organization.setName(view.name);
        organization.setFullName(view.fullName);
        organization.setInn(view.inn);
        organization.setKpp(view.kpp);
        organization.setAddress(view.address);
        if (view.phone != null){
            organization.setPhone(view.phone);
        }
        if (view.isActive != null){
            organization.setActive(view.isActive);
        } else {
            organization.setActive(false);
        }

        String message = dao.save(organization);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> update(@Valid OrganizationView view) {
        Organization organization = new Organization();
        organization.setId(view.id);
        organization.setName(view.name);
        organization.setFullName(view.fullName);
        organization.setInn(view.inn);
        organization.setKpp(view.kpp);
        organization.setAddress(view.address);
        if (view.phone != null){
            organization.setPhone(view.phone);
        }
        if (view.isActive != null){
            organization.setActive(view.isActive);
        } else {
            organization.setActive(false);
        }
        String message = dao.update(organization);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Organization> idOrg(Long id) {
        Organization org = dao.idOrg(id);
        return new ResponseEntity<>(org, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Organization>> listOrg(String name, String inn, Boolean isActive) {
        List<Organization> ogrList = dao.listOrg(name,inn,isActive);

        return new ResponseEntity<>(ogrList,HttpStatus.OK);
    }
}
