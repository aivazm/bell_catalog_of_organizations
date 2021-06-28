package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dao.UserDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Country;
import com.am.catalog.model.DocType;
import com.am.catalog.model.Document;
import com.am.catalog.model.Office;
import com.am.catalog.model.User;
import com.am.catalog.util.ValidationService;
import com.am.catalog.view.SuccessResponse;
import com.am.catalog.view.UserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    private StringBuilder message;
    /** сервис для работы с базой данных */
    private final UserDao userDao;
    private final OfficeDao officeDao;
    private final ValidationService validationService;

    @Autowired
    public UserServiceImpl(UserDao userDao, OfficeDao officeDao, ValidationService validationService) {
        this.userDao = userDao;
        this.officeDao = officeDao;
        this.validationService = validationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse saveUser(UserView userView) {
        User user = getCrudeUser(userView);
        if (userView.getOfficeId() == null) {
            throw new EmptyFieldException("Поле officeId не может быть пустым");
        }
        Office office = officeDao.getOfficeById(userView.getOfficeId());
        if (office == null) {
            throw new NoObjectException("Офис с указанным id отсутствует");
        }
        user.setOffice(office);
        String code = userView.getDocCode();
        String name = userView.getDocName();
        String number = userView.getDocNumber();
        Date date = userView.getDocDate();
        if (code != null || name != null || number != null || date != null) {
            if (StringUtils.isBlank(code) || StringUtils.isBlank(name)|| StringUtils.isBlank(number) || date == null) {
                throw new EmptyFieldException("При добавлении работника c документом поля DocCode, DocName, DocNumber, DocDate обязательны к заполнению; ");
            } else {
                DocType docType = new DocType(code, name);
                Document document = new Document(docType, number, date);
                user.setDocument(document);
            }
        }
        if (userView.getIsIdentified() == null) {
            user.setIsIdentified(false);
        } else {
            user.setIsIdentified(userView.getIsIdentified());
        }
        userDao.saveUser(user);
        return new SuccessResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse updateUser(UserView userView) {
        User user = getCrudeUser(userView);
        if (userView.getId() == null) {
            throw new EmptyFieldException("Поле id не может быт пустым; ");
        }
        if (userView.getOfficeId() != null) {
            Office office = officeDao.getOfficeById(userView.getOfficeId());
            if (office == null) {
                throw new NoObjectException("Офис с указанным id отсутствует");
            }
            user.setOffice(office);
        }
        if (userView.getIsIdentified() != null) {
            user.setIsIdentified(userView.getIsIdentified());
        }
        String name = userView.getDocName();
        String number = userView.getDocNumber();
        Date date = userView.getDocDate();
        if (name != null || number != null || date != null) {
            if (StringUtils.isBlank(name) || StringUtils.isBlank(number) || date == null) {
                throw new EmptyFieldException("При обновлении документа работника поля DocName, DocNumber, DocDate обязательны к заполнению; ");
            } else {
                DocType docType = new DocType();
                docType.setName(name);
                Document document = new Document(docType, number, date);
                user.setDocument(document);
            }
        }
        if (userView.getIsIdentified() != null) {
            user.setIsIdentified(userView.getIsIdentified());
        }
        Long id = userView.getId();
        if (userDao.updateUser(user, id) > 0) {
            return new SuccessResponse();
        } else {
            throw new NoObjectException("Обновление работника не удалось");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserView getUserById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        User user = userDao.getUserById(id);
        String docName = null;
        String docNumber = null;
        Date docDate = null;
        String countryName = null;
        String countryCode = null;
        if (user.getDocument() != null) {
            docName = user.getDocument().getDocType().getName();
            docNumber = user.getDocument().getNumber();
            docDate = user.getDocument().getDate();
        }
        if (user.getCountry() != null) {
            countryName = user.getCountry().getName();
            countryCode = user.getCountry().getCode();
        }
        return UserView.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .middleName(user.getMiddleName())
                .position(user.getPosition())
                .phone(user.getPhone())
                .docName(docName)
                .docNumber(docNumber)
                .docDate(docDate)
                .citizenshipName(countryName)
                .citizenshipCode(countryCode)
                .isIdentified(user.getIsIdentified())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserView> getUserList(UserView userView) {
        if (userView.getOfficeId() == null || userView.getOfficeId() < 1) {
            throw new EmptyFieldException("Параметр officeId обязателен к заполнению и не может быть меньше единицы");
        }
        List<User> userList = userDao.getUserList(userView.getOfficeId(),
                userView.getFirstName(),
                userView.getSecondName(),
                userView.getMiddleName(),
                userView.getPosition(),
                userView.getDocCode(),
                userView.getCitizenshipCode()
        );

        return (userList.stream().map(user -> UserView.builder()
                                        .id(user.getId())
                                        .firstName(user.getFirstName())
                                        .secondName(user.getSecondName())
                                        .middleName(user.getMiddleName())
                                        .position(user.getPosition())
                                        .build())
                                 .collect(Collectors.toList()));
    }


    private User getCrudeUser(UserView userView) {
        validationService.validate(userView);
        User user = new User();
        user.setFirstName(userView.getFirstName());
        user.setPosition(userView.getPosition());
        if (userView.getSecondName() != null) {
            user.setSecondName(userView.getSecondName());
        }
        if (userView.getMiddleName() != null) {
            user.setMiddleName(userView.getMiddleName());
        }
        if (userView.getPhone() != null) {
            user.setPhone(userView.getPhone());
        }
        if (userView.getCitizenshipCode() != null) {
            user.setCountry(new Country(userView.getCitizenshipCode()));
        }
        return user;
    }

}
