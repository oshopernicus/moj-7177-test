import { Request, Response } from 'express';
import axios from 'axios';

/**
 * Fetches and renders details for a single task.
 * @param req Express request object
 * @param res Express response object
 */
export async function getTaskDetails(req: Request, res: Response) {
  const taskId = req.params.id;
  try {
    const response = await axios.get(`http://localhost:8080/api/tasks/${taskId}`);
    const task = response.data;
    // Show success banner if redirected after update
    const successType = req.query.success;
    res.render('task-details', { task, success: successType });
  } catch (error) {
    console.error('Error fetching task details:', error);
    renderError(res, 'task-details', 'Could not fetch task details.', { task: null });
  }
}

/**
 * Renders the add task form.
 * @param req Express request object
 * @param res Express response object
 */
export function showAddTaskForm(req: Request, res: Response) {
  res.render('add-task');
}

/**
 * Handles creation of a new task and redirects to details page.
 * @param req Express request object
 * @param res Express response object
 */
export async function handleAddTask(req: Request, res: Response) {
  const { title, description, status, dueDateTime } = req.body;
  try {
    const response = await axios.post('http://localhost:8080/api/tasks', {
      title,
      description,
      status,
      dueDateTime
    });
    const newTask = response.data;
    res.redirect(`/task/${newTask.id}?success=created`);
  } catch (error) {
    renderError(res, 'add-task', 'Failed to create task', { form: req.body });
  }
}

/**
 * Renders the edit task form for a given task.
 * @param req Express request object
 * @param res Express response object
 */
export async function showEditTaskForm(req: Request, res: Response) {
  const taskId = req.params.id;
  try {
    const response = await axios.get(`http://localhost:8080/api/tasks/${taskId}`);
    const task = response.data;
    res.render('edit-task', { task });
  } catch (error) {
    renderError(res, 'edit-task', 'Could not fetch task details.', { task: null });
  }
}

/**
 * Handles updating a task and redirects to details page.
 * @param req Express request object
 * @param res Express response object
 */
export async function handleEditTask(req: Request, res: Response) {
  const taskId = req.params.id;
  const { title, description, status, dueDateTime } = req.body;
  try {
    const response = await axios.get(`http://localhost:8080/api/tasks/${taskId}`);
    const originalTask = response.data;
    const updatedTask = {
      id: taskId,
      title,
      description,
      status,
      dueDateTime,
      createdAt: originalTask.createdAt,
      updatedAt: new Date().toISOString()
    };
    await axios.put(`http://localhost:8080/api/tasks/${taskId}`, updatedTask);
    // Redirect to task details with success banner
    res.redirect(`/task/${taskId}?success=updated`);
  } catch (error) {
    let errorMsg = 'Failed to update task.';
    if (error.response && error.response.data && error.response.data.message) {
      errorMsg += ' ' + error.response.data.message;
    }
    renderError(res, 'edit-task', errorMsg, { task: { id: taskId, title, description, status, dueDateTime, createdAt: '', updatedAt: '' } });
  }
}

/**
 * Handles deletion of a task and redirects appropriately.
 * @param req Express request object
 * @param res Express response object
 */
export async function handleDeleteTask(req: Request, res: Response) {
  const taskId = req.params.id;
  try {
    await axios.delete(`http://localhost:8080/api/tasks/${taskId}`);
    res.redirect('/?deleteSuccess=1');
  } catch (error) {
    let errorMsg = 'Failed to delete task.';
    if (error.response && error.response.data && error.response.data.message) {
      errorMsg += ' ' + error.response.data.message;
    }
    // Optionally, redirect back to details with error
    res.redirect(`/task/${taskId}?deleteError=1`);
  }
}

/**
 * Renders a view with a formatted error message.
 * @param res Express response object
 * @param view View name to render
 * @param errorMsg Error message to display
 * @param extraData Any extra data to pass to the view
 */
export function renderError(res: Response, view: string, errorMsg: string, extraData: object = {}) {
  res.render(view, { error: errorMsg, ...extraData });
}
