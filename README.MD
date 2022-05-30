# Web app about Yoga v.0.7.8 "Application for scheduling yoga events and lessons".
---
This is app based on Spring Boot tools + Docker-compose + MySQL..SCSS + Bootstrap elements + JS ...

---

## Features

### Role - Admin

    - create single event
    - create group multiple events
    - update event
    - delete event
    - check info about users who want to participate in event
    - check list with confirmed to events users
    - delete any user from event
    - confirm any user to participate in event 
    - give to any user credits

### Role - User

    - can be register like new user
    - can read and open any event
    - can press button to participate in event
    - can cancel participation in event
    - can automaticaly pay for event if have anough credits
    - can get back credits when cancel event participation
    - can check in user page - information and status of requests to participate

### Role - User
    
    - can open schedule
    - can sort schedule by theme
    - can open any event
    - can be registred like new user with User role.

---

## Requirement to start application:

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

## Test users' connection:

Admin:
   email: admin@admin.com
   password: admin

Admin:
   email: user@user.com
   password: user

---

### Pages without functionality

    - Home page
    - Events
    - About me
    - Contacts