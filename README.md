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
   - [x] GET /courses
     - [x] GET /{courseId}
     - [x] PATCH /{courseId}
     - [ ] DELETE /{courseId}

      ### Joing and approve
      - [ ] POST /{courseId}/join
      - [ ] POST /{courseId}/approve 

      ### Members
      - [ ] GET /members
         - [ ] GET /{memberId}
         - [ ] DELETE /{memberId}
         - [ ] GET /confirm
  
      ### Groups
      - [ ] GET /groups
      - [ ] POST /groups
      - [ ] DELETE /groups
        - [ ] GET /leave
  
      ### Tasks
      - [ ] GET /tasks
      - [ ] POST /tasks

   ## Tasks
   - [ ] GET /tasks/{taskId}
   - [ ] PATCH /tasks/{taskId}
   - [ ] DELETE tasks/{taskId}
  
   - [ ] GET tasks/{taskId}/solutions
   - [ ] POST tasks/{taskId}/solutions
  
   - [ ] DELETE /tasks/{taskId}/solutions/{solutionId}
   - [ ] PATCH /tasks/{taskId}/solutions/{solutionId}
  
   - [ ] GET /tasks/{taskId}/download
   - [ ] GET /tasks/{taskId}/solutions/download
