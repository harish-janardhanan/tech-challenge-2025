# Functionality Documentation

## Main Features
- User authentication (sign in/sign up)
- Role-based access and privileges
- Trade capture and management (create, amend, cancel, terminate)
- Trade leg and cashflow management
- Entity management (books, counterparties, etc.)
- Blotter and reporting screens

## Business Rules
- Trades must reference active users, books, and counterparties
- Trade and leg dates must follow validation rules
- Cashflows generated per schedule and maturity
- Only users with correct privileges can perform certain actions
- Versioning for all entities (audit/history)

## User Flows
- Sign up and select user profile
- Sign in and redirect based on role
- Create/amend trades via modal
- View and manage trades in blotter
- Admin/support users manage reference data

---

# PowerPoint Slide Outlines

## Slide 1: Overall Design
- High-level architecture overview
  - Frontend (React 19, Vite, Tailwind CSS, MobX, AG Grid)
  - Backend (Spring Boot, Maven, JPA, H2, Docker)
  - Database (H2 in-memory with file persistence)
- Communication via RESTful APIs
- Modular separation of concerns (frontend/backend)
- Role-based access control enforced at API and UI
- Entity versioning for audit/history
- Extensible data model using additional info table
- Designed for local, Docker, and cloud deployment

## Slide 2: Business Functionality
- Trade lifecycle management
  - Create, amend, cancel, terminate trades
  - Each trade has two legs and multiple cashflows
- Entity management
  - Books, counterparties, users, reference data
- Business rules enforcement
  - Only active users/books/counterparties can be referenced
  - Date validation for trade, start, and maturity dates
  - Cashflow generation logic based on schedule and maturity
  - Versioning for all entities (audit/history)
  - Only users with correct privileges can perform certain actions
- Audit and compliance support

## Slide 3: User Features
- Authentication and authorization
  - Sign in/sign up with user profile selection
  - Redirect based on user role
- Role-based UI and features
  - Trader: create/amend/cancel/terminate trades
  - Sales: book/amend trades
  - Middle Office: amend/view trades
  - Support: view trades, manage reference data
- Modern, responsive UI
  - Tailwind CSS for styling
  - AG Grid for blotter/trade tables
  - Overlay modals for trade entry/amendment
  - Snackbar notifications for actions
- Example UI mockups
  - Sign in page: avatar, login fields, modern buttons
  - Trade modal: two-column layout, dropdowns, expandable cashflow section
  - Blotter screen: AG Grid table, filter/search, action buttons
