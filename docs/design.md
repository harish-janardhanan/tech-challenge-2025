# Design Documentation

## Overview
This document describes the architecture and design decisions for the Technical Challenge 2025 project.

## Backend
- **Framework:** Spring Boot (REST API)
- **Build Tool:** Maven
- **ORM:** Hibernate & JPA
- **Database:** H2 (in-memory, with file persistence on exit)
- **Testing:** JUnit5 (unit), Cucumber (integration)
- **Documentation:** OpenAPI/Swagger
- **Containerization:** Docker

### Key Design Points
- Modular service, repository, and controller layers
- Entities reflect normalized relational model (see instructions)
- RSQL JPA for flexible querying
- Versioned entities for audit/history
- User/privilege model for role-based access

## Frontend
- **Framework:** React 19
- **Build Tool:** Vite
- **Package Manager:** pnpm
- **State Management:** MobX
- **Styling:** Tailwind CSS
- **Data Grid:** AG Grid React
- **Testing:** Vitest (unit), Playwright (E2E)

### Key Design Points
- Modular component structure
- Role-based UI rendering
- Responsive design with Tailwind
- API integration with backend

## Deployment
- Local development with Docker Compose
- Stretch: Kubernetes and Azure deployment
