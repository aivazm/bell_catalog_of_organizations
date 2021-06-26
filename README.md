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


### 5. api/office/list/{orgId}
In (фильтр):
{
  “orgId”:””, //обязательный параметр
  “name”:””,
  “phone”:””,
  “isActive” 
}

Out:
[
  {
    “id”:””,
    “name”:””,
    “isActive”:”true”
  },
  ...
]

### 6. api/office/{id}
method:GET
Out:
{
  “id”:””,
  “name”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}

### 7. api/office/update
In:
{
  “id”:””, //обязательный параметр
  “name”:””, //обязательный параметр
  “address”:””, //обязательный параметр
  “phone”,””,
  “isActive”:”true” //пример
}

Out:
{
    “result”:”success”
}

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
