import tasks from './tasks';
import { Application } from 'express';
import axios from 'axios';


export default function (app: Application): void {
  // Initialize task routes
  tasks(app);

  app.get('/', async (req, res) => {
    try {
      const response = await axios.get('http://localhost:8080/api/tasks');
      let tasks = response.data;
      // Filter by search and status
      const { search, status } = req.query;
      let searchStr = '';
      if (typeof search === 'string') {
        searchStr = search;
      } else if (Array.isArray(search) && search.length > 0 && typeof search[0] === 'string') {
        searchStr = search[0];
      }
      if (tasks && Array.isArray(tasks)) {
        if (searchStr) {
          const searchLower = searchStr.toLowerCase();
          tasks = tasks.filter((task: any) => task.title && task.title.toLowerCase().includes(searchLower));
        }
        if (status) {
          const statusStr = Array.isArray(status) ? status[0] : status;
          tasks = tasks.filter((task: any) => task.status === statusStr);
        }
        tasks = tasks.slice().sort((a: any, b: any) => a.id - b.id);
      }
      res.render('home', { tasks, search: searchStr, status });
    } catch (error) {
      console.error('Error:', error);
      res.render('home', { tasks: [] });
    }
  });
}
