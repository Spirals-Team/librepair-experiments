# _4thYearPMS
Sprint 1:
The project we are building is a web app for a fourth year project management system 
* It will allow project coordinators to create deadlines and manage events. 
* Professors will be able to create/supervise projects and allow students to join their project. 
* Students will be able to view projects, register for projects, and view upcoming events.


Current features in localhost only: fetch from MySQL db and update.

# Requirements
Prof:
* sysc4806.Prof enters 4th year project
* Delete projects that are no longer offered
* Archive projects for later use

Project:
* Has description
* Program restrictions
* no. of students required (can’t go over limit)

Student:
* Look up projects
* Apply for projects

Project coordinator:
* Search for students without projects
* Issue reminders
* Issue presentation/report deadlines

# Looking Ahead:
- add authorization
- change from sql to jpa?
- add more pages

Oral Presentation: 
* Prof enters availability
* Student enters availability
* Coordinator allocates presentation rooms based on availability (manually or algorithm based)

Final presentation:
* Submit report based on deadline
* Deadline enforced by the system
