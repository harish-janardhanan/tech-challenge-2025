# Tech Challenge 2025 – Frontend (React + Vite + Tailwind)

This is the frontend for the Tech Challenge 2025 project. It is built with React 19, Vite, Tailwind CSS, MobX, AG Grid, and React Query.

## Prerequisites
- Node.js 18 or higher
- pnpm (recommended) or npm/yarn

## Setup & Run

1. **Install dependencies:**
   ```sh
   pnpm install
   ```
2. **Start the development server:**
   ```sh
   pnpm dev
   ```
   The app will be available at [http://localhost:5173](http://localhost:5173).

3. **Build for production:**
   ```sh
   pnpm build
   ```

4. **Lint and test:**
   ```sh
   pnpm lint
   pnpm test
   ```

## Project Structure
- `src/` – React source code
- `src/components/` – Reusable UI components
- `src/pages/` – Page-level components
- `src/stores/` – MobX stores
- `src/utils/` – API and utility functions

## API Integration
- The frontend expects the backend to be running at `http://localhost:8080/api`.
- Update `src/utils/api.ts` if your backend URL changes.

## Notes
- Uses Tailwind CSS for styling. All custom styles should use Tailwind utility classes.
- Uses React Query for data fetching and caching.
- Uses AG Grid for data tables.

