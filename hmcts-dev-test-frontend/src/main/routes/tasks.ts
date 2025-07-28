import { Application } from 'express';
import { getTaskDetails, showAddTaskForm, handleAddTask, showEditTaskForm, handleEditTask, handleDeleteTask } from '../controllers/tasksController';

export default function (app: Application): void {
  // Route to display task details
  app.get('/task/:id', getTaskDetails);
  app.get('/add-task', showAddTaskForm);
  app.post('/add-task', handleAddTask);
  app.get('/edit-task/:id', showEditTaskForm);
  app.post('/edit-task/:id', handleEditTask);
  app.post('/delete-task/:id', handleDeleteTask);
}
