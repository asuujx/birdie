# TO-DO
   ## Authorization
   - [ ] /auth
     - [ ] POST /register
     - [ ] POST /login
     - [ ] GET /refresh
     - [ ] GET /confirm/{confirmId}
  
   ## Account
   - [ ] GET /account
     - [ ] GET /courses
     - [ ] PUT /avatar
     - [ ] DELETE /avatar
  
   ## Courses
   - [ ] GET /courses
     - [ ] GET /{courseId}
     - [ ] PATCH /{courseId}
     - [ ] DELETE /{courseId}

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
