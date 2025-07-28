import * as path from 'path';

import * as express from 'express';
import * as nunjucks from 'nunjucks';
import { formatDate } from '../../utils/date';

export class Nunjucks {
  constructor(public developmentMode: boolean) {
    this.developmentMode = developmentMode;
  }

  enableFor(app: express.Express): void {
    app.set('view engine', 'njk');
    const env = nunjucks.configure(path.join(__dirname, '..', '..', 'views'), {
      autoescape: true,
      watch: this.developmentMode,
      express: app,
    });

    // Add a date formatting filter
    env.addFilter('date', formatDate);

    app.use((req, res, next) => {
      res.locals.pagePath = req.path;
      next();
    });
  }
}
