# TO-DO
   ## Authorization
   - [ ] /auth
     - [x] POST /register
     - [x] POST /login
     - [x] GET /refresh
     - [ ] GET /confirm/{confirmId}
  
   ## Account
   - [x] GET /account
     - [x] GET /courses
     - [ ] PUT /avatar
     - [ ] DELETE /avatar
  
   ## Courses
   - [x] POST /coruses
   - [x] GET /courses
     - [x] GET /{courseId}
     - [x] PATCH /{courseId}
     - [x] DELETE /{courseId}

      ### Joing and approve
      - [x] POST /{courseId}/join
      - [x] POST /{courseId}/approve 

      ### Members
      - [x] GET /members
         - [ ] PATCH /{memberId}
         - [x] DELETE /{memberId}
         - [ ] GET /confirm
  
      ### Groups
      - [ ] GET /groups
      - [ ] POST /groups
      - [ ] DELETE /groups
        - [ ] GET /leave
  
      ### Tasks
      - [x] GET /tasks
      - [x] POST /tasks

   ## Tasks
   - [x] GET /tasks/{taskId}
   - [x] PATCH /tasks/{taskId}
   - [x] DELETE tasks/{taskId}
  
   - [x] GET tasks/{taskId}/solutions
   - [x] POST tasks/{taskId}/solutions
  
   - [x] DELETE /tasks/{taskId}/solutions/{solutionId}
   - [ ] PATCH /tasks/{taskId}/solutions/{solutionId}
  
   - [ ] GET /tasks/{taskId}/download
   - [ ] GET /tasks/{taskId}/solutions/download
