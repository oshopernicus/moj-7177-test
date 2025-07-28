# HMCTS Dev Test Project

This repository contains both the backend and frontend for the HMCTS case management system.

## Project Structure
- `hmcts-dev-test-backend/` — Spring Boot backend service (Java)
- `hmcts-dev-test-frontend/` — Node.js/TypeScript frontend application

## Prerequisites
- Java 11 or higher (for backend)
- Node.js v16+ and Yarn (for frontend)
- Gradle (or use the included Gradle wrapper)

## API Endpoints
See the individual `README.md` files in the backend and frontend folders for detailed documentation.


# User Flows Documentation

This document describes the main user flows for the HMCTS Dev Test Project. Each flow outlines the steps a user takes to complete a specific task in the system.

---
## 1. View All Tasks
**Actors:** User
**Description:** User views a list of all tasks.
**Steps:**
1. Navigate to the "Home" page.
2. Frontend requests all tasks from the backend.
3. Backend returns the list of tasks.
4. User sees the list displayed in the UI.

### View all tasks screen
<img width="1020" height="795" alt="Screenshot 2025-07-25 at 11 41 08" src="https://github.com/user-attachments/assets/e7c9551e-94e9-4c18-91e7-d1b5f16bfce0" />

---

## 2. Search and Filter Tasks
**Actors:** User
**Description:** User searches and filters tasks on the home page.
**Steps:**
1. Navigate to the home page.
2. Enter search keywords to match the "Title" or filter by "Status".
3. Frontend sends search/filter parameters to the backend.
4. Backend returns matching tasks.
5. User sees the filtered list displayed in the UI.

### Filter by Title
<img width="1034" height="332" alt="Screenshot 2025-07-28 at 03 12 42" src="https://github.com/user-attachments/assets/79c7e582-6160-42dd-a321-6b75fa5cc90f" />


### Filter by Task State
<img width="1057" height="321" alt="Screenshot 2025-07-28 at 03 12 18" src="https://github.com/user-attachments/assets/07e3f64b-6aa1-49a1-bfbf-d2dce6339115" />

---

## 3. View Task Details
**Actors:** User
**Description:** User views details of a specific task.
**Steps:**
1. Navigate to the "Task List" page.
2. Select a task to view details.
3. Frontend requests task details from backend.
4. Backend returns task details.
5. User sees details displayed in the UI.

### View task details screen
<img width="1079" height="824" alt="Screenshot 2025-07-25 at 11 17 26" src="https://github.com/user-attachments/assets/daa3c21b-fccf-4205-b338-c31b4d07a3fc" />


---

## 4. Add New Task
**Actors:** User
**Description:** User creates a new task using the frontend interface.
**Steps:**
1. Click the button "Add New Task" in the home page
2. Fill in task details (e.g., title, description).
3. Submit the form.
4. Backend receives the request and creates the task.
5. User is redirected to the task details and sees a success message.

### Add task screen
<img width="1015" height="823" alt="Screenshot 2025-07-25 at 11 27 39" src="https://github.com/user-attachments/assets/fc8f5b35-70cf-4ac4-9a93-579f3986b248" />

### Add task success screen
<img width="1000" height="821" alt="Screenshot 2025-07-25 at 11 28 31" src="https://github.com/user-attachments/assets/288b33a3-3379-4289-8623-03ff3fe1fb21" />

---

## 5. Update a Task
**Actors:** User
**Description:** User updates an existing task.
**Steps:**
1. Navigate to the "Task Details" page for a specific task by clicking the task.
2. Clicks the "Update" button
3. Modify task details.
4. Submit the changes.
5. Backend updates the task.
6. User sees confirmation or updated task details.

### Update task screen
<img width="1000" height="823" alt="Screenshot 2025-07-25 at 11 35 36" src="https://github.com/user-attachments/assets/3584075d-9168-478f-b200-60552cb848c7" />

### Update task success screen
<img width="1020" height="823" alt="Screenshot 2025-07-25 at 11 35 23" src="https://github.com/user-attachments/assets/4b07a282-92a5-4f0d-83e5-7b6dcaff8b02" />

---

## 6. Delete a Task
**Actors:** User
**Description:** User deletes a task.
**Steps:**
1. Navigate to the "Task Details" page for a specific task by clicking the task.
2. Clicks the "Delete" button
3. Confirm deletion.
4. Backend deletes the task.
5. User sees updated task list.

### Update task confirmation screen
<img width="1002" height="824" alt="Screenshot 2025-07-25 at 11 35 54" src="https://github.com/user-attachments/assets/761b6a18-e69e-4442-bcdd-cebbb8722dbf" />
