[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6508207&assignment_repo_type=AssignmentRepo)


*ENSF 607 - Advanced Software Development and Architecture*

*Sprint 4 & Hachathon*

by Yuhua Guo, Pang-Cheng Chang, Jintao Wang

**Animal Request Management**

In this hackathon, you will develop the animal request management epic for the vet management system. The workflow is such that the instructor submits a request for an animal, then the admin and technician may approve or reject the request respectively. After approval from both the admin and the technician, the animal is ready to be delivered. 

You can assume that there are only 3 types of animals: dogs, cats, and horses, and there are only 8 animals available for each type. 

The application has three users as follows:



#Instructor

    1. Can request an animal 
    
![pic1](pic1.jpg)

    2. Can cancel a request
    
![pic2](pic2.png)

    3. Can view available animals
    
![pic3](pic3.png)

#Admin

    4. Can view new requests
    
![pic4](pic4.png)

    5. Can Approve a request
    
![pic5](pic5.png)
![pic5-1](pic5-1.png)

    6. Can reject a request
    
![pic6](pic6.png)
![pic6-1](pic6-1.png)

#Technician

    7. Can view new requests
    
![pic7](pic7.png)

    8. Can Approve a request
    
![pic8](pic8.png)
![pic8-1](pic8-1.png)

    9. Can reject a request
    
![pic9](pic9.png)
![pic9-1](pic9-1.png)

* Instructors can only cancel requests when their request is in "new" or "accepted_by_admin" states.
* The application must have a backend and frontend, and the API must connect the two.
* The application must be consistent when two users concurrently change a request state.
* Every team must commit partially every few hours.
* Each team must be based on final-project teams.
* Different roles login with these credentials:
    * Instructor_1: pass: pt@123
    * Admin_1: pass: pa
    * Technician: pass: pe


**Due date: Before 11:59 PM on Friday December 3rd**

Groups will present their final submission during the lecture on Tuesday, December 6th. Each presentation will be no longer than **5 minutes**. During this presentation, the teams must present the architecture, backend, and the frontend of their application. 

Note: All group members must be present (and active) during their presentations. All students must attend all presentations. 
