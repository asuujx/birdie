# ENDPOINTS
   ## Authorization
   - [ ] /auth
     - [x] POST /register
     - [x] POST /login
     - [x] GET /refresh
     - [ ] GET /confirm/{confirmId}
  
   ## Account
   - [x] GET /account
     - [x] GET /courses
     - [ ] DELETE /delete
     - [ ] PUT /avatar
     - [ ] DELETE /avatar
  
   ## Courses
   - [x] POST /coruses
   - [x] GET /courses
     - [x] GET /{courseId}
     - [x] PATCH /{courseId}
     - [x] DELETE /{courseId}
     - [x] POST /{courseId}/join

      ### Members
      - [x] GET /members
         - [x] PATCH /{memberId}
         - [x] DELETE /{memberId}
  
      ### Groups
      - [x] GET /groups
      - [x] POST /groups
      - [ ] PATCH /groups/group{Id}
      - [x] DELETE /groups/{groupId}
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
   - [x] PATCH /tasks/{taskId}/solutions/{solutionId}
  
   - [ ] GET /tasks/{taskId}/download
   - [ ] GET /tasks/{taskId}/solutions/download
