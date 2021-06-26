# Справочник организаций

### Описание приложения
Все описанные возвращаемые типы данных находятся в параметре data. В случае ошибки возвращается параметр error.
В случае, если запрос корректно отработает, бэк возвращает в body ответа:

{
    “data”:{
        //то, что в параметре out
    }
}

В случае ошибки возвращает: 

{
    “error”:”текст ошибки”
}
Везде, где не написан метод, используется метод POST

### Как запустить
Из командной строки
```
mvn spring-boot:run
```
Либо кнопкой Run из среды разработки

### URL's
| № | Метод | URL | Описание |
| --- | --- | --- | --- |
| 1 | POST | http://localhost:8888/organization/list | Получить список организаций |
| 2 | GET | http://localhost:8888/organization/{id} | Получить организацию по идентификатору |
| 3 | POST | http://localhost:8888/organization/update | Обновить атрибуты организации (name, fullName, inn, kpp, address, phone, isActive) |
| 4 | POST | http://localhost:8888/organization/save | Добавить новую организацию |
| 5 | GET | http://localhost:8888/office/{id} | Получить офис по идентификатору |
| 6 | POST | http://localhost:8888/office/list | Получить список офисов по идентификатору организации (фильтр по id, name, isActive) |
| 7 | POST | http://localhost:8888/office/update | Обновить атрибуты офиса (name, address, phone, isActive) |
| 8 | POST | http://localhost:8888/office/save | Добавить новый офис |


### Примеры request - response
#### 1. Получить список организаций
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

#### 2. Получить организацию по идентификатору
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

### 3. Обновить атрибуты организации
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

### 4. Добавить новую организацию
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

#### 5. Получить офис по идентификатору
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

#### 6. Получить список офисов
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

### 7. Обновить атрибуты офиса
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

### 8. Добавить новый офис
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

### 8. api/office/save
In:
{
  “name”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}

Out:
{
    “result”:”success”
}

### 9. api/user/list
In (фильтр):
{
  “officeId”:””, //обязательный параметр
  “firstName”:””,
  “lastName”:””,
  “middleName”:””,
  “position”,””,
  “docCode”:””,
  “citizenshipCode”:””
}
Out:
{
  “id”:””,
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
}

### 10. api/user/{id}
method:GET
Out:
{
  “id”:””,
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
  “phone”,””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipName”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true”
}

### 11. api/user/update
In:
{
  “id”:””, //обязательный параметр
  “firstName”:””, //обязательный параметр
  “secondName”:””,
  “middleName”:””,
  “position”:”” //обязательный параметр
  “phone”,””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true” //пример
}

Out:
{
    “result”:”success”
}

### 12. api/user/save
In:
{
  “firstName”:””, //обязательный параметр
  “secondName”:””,
  “middleName”:””,
  “position”:”” //обязательный параметр
  “phone”,””,
  “docCode”:””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true” //пример
}
