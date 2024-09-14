# 'microservices_project'
Projekt, w którym przedstawiam proste REST API posiadające bramkę proxy, eurekę rejestrującą wszystkie usługi, korzysta z bazy lokalnej oraz zewnętrznej. 
Aplikacja ma rejestrować studentów i kursy oraz dawać możliwość zapisania studentów na dany kurs. 

### EUREKA SERVICE

Usługa, która dynamicznie rejestruje instancje serwisów. Możliwość podglądu zarejestrowanych usług w konsoli

> http://localhost:8761/


### GATEWAY-SERVICE

Bramka proxy na porcie: 8000

Przekierowanie zapytań do STUDENT-SERVICE:

> http://localhost:8000/student-service


Przekierowanie zapytań do COURSE-SERVICE:
> http://localhost:8000/course-service


### STUDENT-SERVICE
Usługa posiadająca lokalną bazę danych POSTGRES, logika biznesowa zakłada kompleksową obsługę rejestracji studentów.
Zawiera end-pointy pozwalające na dodawanie, edycję i usuwanie konkretnych pozycji.
Obsługa wyjątków poprzez klasę 'StudentError'

### COURSE-SERVICE

Usługa z logiką biznesową przewidującą tworzenie, edycję kursów oraz zapisywanie tylko studentów o statusie "ACTIVE" na kursy o statusie "ACTIVE". 
Zastosowanie FEIGN-CLIENT pozwala na komunikację między COURSE-SERVICE a STUDENT-SERVICE.
Usługa korzysta z zewnętrznej bazy danych MongoDB.
Obsługa wyjątków poprzez klasę 'CourseError'









