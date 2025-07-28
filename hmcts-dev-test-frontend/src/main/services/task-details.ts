import axios from 'axios';

export async function fetchTaskDetails(taskId: string) {
  const response = await axios.get(`http://localhost:8080/api/tasks/${taskId}`);
  return response.data;
}
