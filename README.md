# Tech Challenge 2025

This repository contains a full-stack trade management platform with a Spring Boot backend and a React (Vite + Tailwind) frontend.

## Quick Start

### Backend (Spring Boot)
- **Location:** `backend/`
- **Setup:**
  1. Install Java 21+ and Maven 3.8+
  2. Run:
     ```sh
     cd backend
     mvn clean install
     mvn spring-boot:run
     ```
  3. API docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Frontend (React + Vite + Tailwind)
- **Location:** `frontend/`
- **Setup:**
  1. Install Node.js 18+ and pnpm
  2. Run:
     ```sh
     cd frontend
     pnpm install
     pnpm dev
     ```
  3. App: [http://localhost:5173](http://localhost:5173)

## Project Structure
- `backend/` – Spring Boot backend (REST API, H2/PostgreSQL, Swagger, CORS enabled)
- `frontend/` – React 19, Vite, Tailwind CSS, MobX, AG Grid, React Query

## API Integration
- The frontend expects the backend at `http://localhost:8080/api`.
- Update `frontend/src/utils/api.ts` if backend URL changes.

## Troubleshooting
- **CORS errors:** Backend must be running and CORS enabled for your frontend's origin.
- **Port conflicts:** Default ports are 8080 (backend) and 5173 (frontend).
- **Database:** H2 in-memory by default; update backend config for PostgreSQL.

For detailed setup, see the `README.md` in each subfolder.

