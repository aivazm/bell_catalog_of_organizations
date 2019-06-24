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
import com.am.catalog.view.SuccessResponse;
import com.am.catalog.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    private StringBuilder message;
    private final UserDao userDao;
    private final OfficeDao officeDao;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserDao userDao, OfficeDao officeDao, Validator validator) {
        this.userDao = userDao;
        this.officeDao = officeDao;
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse saveUser(UserView userView) {
        message = new StringBuilder();
        User user = getCrudeUser(userView);
        if (userView.getOfficeId() != null) {
            Office office = officeDao.getOfficeById(userView.getOfficeId());
            if (office != null) {
                user.setOffice(office);
            } else {
                message.append("Офис с указанным id отсутствует");
            }
        } else {
            message.append("Поле officeId не может быть пустым");
        }
        String code = userView.getDocCode();
        String name = userView.getDocName();
        String number = userView.getDocNumber();
        Date date = userView.getDocDate();
        if (code != null || name != null || number != null || date != null) {
            if (code == null || code.isEmpty() || name == null || name.isEmpty() ||
                    number == null || number.isEmpty() || date == null
            ) {
                message.append("При добавлении работника c документом поля DocCode, DocName, DocNumber, DocDate обязательны к заполнению; ");
            } else {
                DocType docType = new DocType(code, name);
                Document document = new Document(docType, number, date);
                user.setDocument(document);
            }
        }
        if (userView.getIsIdentified() != null) {
            user.setIsIdentified(userView.getIsIdentified());
        } else {
            user.setIsIdentified(false);
        }
        if (message.length() == 0) {
            userDao.saveUser(user);
            return new SuccessResponse("success");
        } else {
            throw new EmptyFieldException(message.toString().trim());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse updateUser(UserView userView) {
        message = new StringBuilder();
        User user = getCrudeUser(userView);
        if (userView.getId() == null) {
            message.append("Поле id не может быт пустым; ");
        }
        if (userView.getOfficeId() != null) {
            Office office = officeDao.getOfficeById(userView.getOfficeId());
            if (office != null) {
                user.setOffice(office);
            } else {
                message.append("Офис с указанным id отсутствует");
            }
        }
        if (userView.getIsIdentified() != null) {
            user.setIsIdentified(userView.getIsIdentified());
        }
        String name = userView.getDocName();
        String number = userView.getDocNumber();
        Date date = userView.getDocDate();
        if (name != null || number != null || date != null) {
            if (name == null || name.isEmpty() || number == null || number.isEmpty() || date == null) {
                message.append("При обновлении документа работника поля DocName, DocNumber, DocDate обязательны к заполнению; ");
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
        if (message.length() == 0) {
            Long id = userView.getId();
            if (userDao.updateUser(user, id) > 0) {
                return new SuccessResponse("success");
            } else {
                throw new NoObjectException("Обновление не удалось");
            }
        } else {
            throw new EmptyFieldException(message.toString().trim());
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
        List<UserView> userViewList = new ArrayList<>();
        for (User user : userList) {
            userViewList.add(UserView.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .secondName(user.getSecondName())
                    .middleName(user.getMiddleName())
                    .position(user.getPosition())
                    .build());
        }
        return userViewList;
    }


    private User getCrudeUser(UserView userView) {
        Set<ConstraintViolation<UserView>> validate = validator.validate(userView);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<UserView> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
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
