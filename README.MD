# Web app about Yoga v.0.3.9
---
This is app based on Spring Boot tools + Docker-compose + MySQL..SCSS + Bootstrap elements + JS ...

- **You can find structure plan of this App in this repo -> /materials/Pages_plan_draw_io.pdf**

---

## Requirement to start this application:

1. installed Java version 17 or higher;
2. maven
3. installed docker and docker-compose;
4. installed IntelliJ IDEA - community version is enough;

___

## How to start app:

1. open project folder in IntelliJ IDEA;
2. open terminal in root folder "yogaApp";
3. to start MySQL database - type in terminal command  `docker-compose up -d` or `sudo docker-compose up -d` in Linux;
4. run application "YogaForumApplication"
5. open in browser - localhost:8080;

---

### Last changes and TODOs
- MAIN TODO: 
  - to add comments to explain all fields and functions (added already: Event)
  - to add errors pages 4xx and 5xx;
  - to add user registration
  
- Changes:
  - refactor Payments from 2 entities KISS =);
  - add exception logging - exceptionHandler.