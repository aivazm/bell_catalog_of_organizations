package com.am.catalog.service;

import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.ErrorMessage;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Organization;
import com.am.catalog.util.ValidationService;
import com.am.catalog.view.OrganizationView;
import com.am.catalog.view.SuccessResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для обработки запросов работы с организациями.
 * Принимает объекты-отображения OrganizationView, обрабатывает и передает в слой DA.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    /** сервис для работы с базой данных */
    private final OrganizationDao dao;
    private final ValidationService validationService;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao, ValidationService validationService) {
        this.dao = dao;
        this.validationService = validationService;
    }

    /** Добавить новую организацию */
    @Override
    @Transactional
    public SuccessResponse saveOrganization(OrganizationView organizationView) {
        Organization organization = getValidOrganization(organizationView);
        dao.saveOrganization(organization);
        return new SuccessResponse();
    }

    /** Обновить данные о существующей организации */
    @Override
    @Transactional
    public SuccessResponse updateOrganization(OrganizationView organizationView) {
        if (organizationView.getId() == null) {
            throw new EmptyFieldException(ErrorMessage.EMPTY_ID);
        }
        Organization organization = getValidOrganization(organizationView);
        if (dao.updateOrganization(organization, organizationView.getId()) > 0) {
            return new SuccessResponse();
        } else {
            throw new NoObjectException("Обновление не удалось");
        }
    }

    /** Получить организацию по идентификатору */
    @Override
    public OrganizationView getOrganizationById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException(ErrorMessage.INVALID_ID);
        }
        Organization org = dao.getOrganizationById(id);
        return OrganizationView.builder()
                .id(org.getId())
                .name(org.getName())
                .fullName(org.getFullName())
                .inn(org.getInn())
                .kpp(org.getKpp())
                .address(org.getAddress())
                .phone(org.getPhone())
                .isActive(org.getIsActive())
                .build();
    }

    /** Получить отфильтрованный список организаций */
    @Override
    public List<OrganizationView> getOrganizationList(OrganizationView organizationView) {
        if (StringUtils.isBlank(organizationView.getName())) {
            throw new EmptyFieldException("Name cannot be empty");
        }
        List<Organization> organizations = dao.getOrganizationList(organizationView.getName(),
                organizationView.getInn(),
                organizationView.getIsActive()
        );

        return (organizations.stream().map(o -> OrganizationView.builder()
                                            .id(o.getId())
                                            .name(o.getName())
                                            .isActive(o.getIsActive())
                                            .build())
                                            .collect(Collectors.toList()));


    }

    private Organization getValidOrganization(OrganizationView organizationView) {
        validationService.validate(organizationView);
        return Organization.builder()
                .name(organizationView.getName())
                .fullName(organizationView.getFullName())
                .inn(organizationView.getInn())
                .kpp(organizationView.getKpp())
                .address(organizationView.getAddress())
                .phone(organizationView.getPhone())
                .isActive(organizationView.getIsActive())
                .build();
    }

}
