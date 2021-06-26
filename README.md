# Справочник организаций

### Описание приложения
Сервеная часть WEB-приложения Каталог организаций.
Позволяет создавать связанные сущности организаций, офисов и работников.

### Как запустить
Из командной строки
```
mvn spring-boot:run
```
Либо кнопкой Run из среды разработки

### База данных
Используется база данных H2.
БД заполняется первичными даннми при запуске приложения.
Первичные данные БД представлены в файле resources\data.sql.

### Доступные URL's
| № | Метод | URL | Описание |
| --- | --- | --- | --- |
| 1 | GET | http://localhost:8888/organization/{id} | Получить организацию по идентификатору |
| 2 | POST | http://localhost:8888/organization/list | Получить список организаций |
| 3 | POST | http://localhost:8888/organization/update | Обновить атрибуты организации (name, fullName, inn, kpp, address, phone, isActive) |
| 4 | POST | http://localhost:8888/organization/save | Добавить новую организацию |
| 5 | GET | http://localhost:8888/office/{id} | Получить офис по идентификатору |
| 6 | POST | http://localhost:8888/office/list | Получить список офисов по идентификатору организации (фильтр по id, name, isActive) |
| 7 | POST | http://localhost:8888/office/update | Обновить атрибуты офиса (name, address, phone, isActive) |
| 8 | POST | http://localhost:8888/office/save | Добавить новый офис |
| 9 | GET | http://localhost:8888/user/{id} | Получить работника по идентификатору |
| 10 | POST | http://localhost:8888/user/list | Получить список работников по идентификатору офиса (фильтр по officeId, firstName, secondName, middleName, position, docCode, citizenshipCode) |
| 11 | POST | http://localhost:8888/user/update | Обновить атрибуты работника (firstName, secondName, middleName, position, phone, docName, docNumber, docDate, citizenshipName, citizenshipCode, isIdentified) |
| 12 | POST | http://localhost:8888/user/save | Добавить нового работника |


### Примеры request - response
#### Работа с организациями
##### 1. Получить организацию по идентификатору
> http://localhost:8888/organization/1

method:GET  
`Out:`
```
{
    "data": {
        "id": 1,
        "name": "Ромашка",
        "fullName": "ООО \"Ромашка\"",
        "inn": "1111111111",
        "kpp": "222222222",
        "address": "Ромашковая, 1",
        "phone": "111-11-11",
        "isActive": true
    }
}
```

##### 2. Получить список организаций
> http://localhost:8888/organization/list

method:POST  
`In:`
```
{
    "name": "Ромашка", // обязательный параметр
    "inn": "1111111111",
    "isActive": true
}
```
`Out:`
```
{
    "data": [
        {
            "id": 1,
            "name": "Ромашка",
            "isActive": true
        }
    ]
}
```

##### 3. Обновить атрибуты организации
>http://localhost:8888/organization/update

method:POST  
`In:`
```
{
    "id": 1, // обязательный параметр
    "name": "new name", // обязательный параметр
    "fullName": "new fullName", // обязательный параметр
    "inn": "1111111116", // обязательный параметр
    "kpp": "222222213", // обязательный параметр
    "address": "new address", // обязательный параметр
    "phone": "new phone",
    "isActive": false
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```

##### 4. Добавить новую организацию
>http://localhost:8888/organization/save

method:POST  
`In:`
```
{
    "name": "org name", // обязательный параметр
    "fullName": "org fulwelName", // обязательный параметр
    "inn": "1234567890", // обязательный параметр
    "kpp": "123456789", // обязательный параметр
    "address": "org address", // обязательный параметр
    "phone": "org phone",
    "isActive": true
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```
#### Работа с офисами
##### 5. Получить офис по идентификатору
> http://localhost:8888/office/1

method:GET  
`Out:`
```
{
    "data": {
        "id": 1,
        "name": "На Ромашковой",
        "address": "Ромашковая, 1",
        "phone": "111-11-01",
        "isActive": true
    }
}
```

##### 6. Получить список офисов
> http://localhost:8888/office/list

method:POST  
`In:`
```
{
    "orgId": 1 // обязательный параметр
}
```
`Out:`
```
{
    "data": [
        {
            "id": 1,
            "name": "На Ромашковой",
            "isActive": true
        },
        {
            "id": 2,
            "name": "На Луговой",
            "isActive": true
        }
    ]
}
```

##### 7. Обновить атрибуты офиса
>http://localhost:8888/office/update

method:POST  
`In:`
```
{
    "id": 1, // обязательный параметр
    "name": "new name", // обязательный параметр
    "address": "new address", // обязательный параметр
    "phone": "new phone",
    "isActive": true
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```

##### 8. Добавить новый офис
>http://localhost:8888/office/save

method:POST  
`In:`
```
{
    "orgId": 1,
    "name": "office name",
    "address": "office address",
    "phone": "office phone",
    "isActive": true
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```

#### Работа с работниками
##### 9. Получить работника по идентификатору
> http://localhost:8888/user/1

method:GET  
`Out:`
```
{
    "data": {
        "id": 1,
        "firstName": "Роман",
        "secondName": "Иванов",
        "middleName": "Петрович",
        "position": "Агроном",
        "phone": "111-01-01",
        "docName": "Военный билет",
        "docNumber": "1201 123456",
        "docDate": "2001-01-13",
        "citizenshipName": "Российская Федерация",
        "citizenshipCode": "643",
        "isIdentified": true
    }
}
```

##### 10. Получить список работников
> http://localhost:8888/user/list

method:POST  
`In:`
```
{
    "officeId": 1 // обязательный параметр
}
```
`Out:`
```
{
    "data": [
        {
            "id": 1,
            "firstName": "Роман",
            "secondName": "Иванов",
            "middleName": "Петрович",
            "position": "Агроном"
        },
        {
            "id": 2,
            "firstName": "Иван",
            "secondName": "Петров",
            "middleName": "Романович",
            "position": "Тракторист"
        }
    ]
}
```

##### 11. Обновить атрибуты работника
>http://localhost:8888/user/update

method:POST  
`In:`
```
{
    "id": 1, //обязательный параметр
    "firstName": "new name", //обязательный параметр
    "secondName": "new second name",
    "middleName": "new middle name",
    "position": "new position", //обязательный параметр
    "phone": "new phone",
    "docName": "Паспорт",
    "docNumber": "new doc number",
    "docDate": "2021-01-02",
    "citizenshipCode": "643",
    "isIdentified": true
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```

##### 12. Добавить нового работника
>http://localhost:8888/user/save

method:POST  
`In:`
```
{
    "firstName": "user first name", //обязательный параметр
    "secondName": "user second name",
    "middleName": "user middle name",
    "position": "user position", //обязательный параметр
    "phone": "user phone",
    "officeId": "2", //обязательный параметр
    "docName": "Паспорт", //обязательный параметр при добавлении работника с документом
    "docCode": "21", //обязательный параметр при добавлении работника с документом
    "docNumber": "123456", //обязательный параметр при добавлении работника с документом
    "docDate": "2021-01-02", //обязательный параметр при добавлении работника с документом
    "citizenshipCode": "643",
    "isIdentified": true
}
```
`Out:`
```
{
    "data": {
        "result": "success"
    }
}
```

### Используемые технологии и сервисы
Java
Spring Boot
Hibernate
H2
lombok
