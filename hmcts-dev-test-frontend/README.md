# HMCTS Dev Test Frontend

This is the frontend for the HMCTS case management system. It provides a user interface for interacting with backend services and managing cases and tasks.

## Prerequisites
- Node.js (v16 or later)
- Yarn

## Setup & Running

1. **Install dependencies:**
   ```sh
   yarn install
   ```
2. **Build the application:**
   ```sh
   yarn webpack
   ```
3. **Start the development server:**
   ```sh
   yarn start:dev
   ```
4. **Access the app:**
   Open your browser and go to `http://localhost:3000` (or the port specified in your environment).

## Project Structure
- `src/main/` - Main application code
- `src/models/` - Data models
- `src/services/` - Service layer for API calls
- `webpack/` - Webpack configuration

## Testing
Run tests with:
```sh
yarn test
```

---
You are free to change the structure and add features as needed!
