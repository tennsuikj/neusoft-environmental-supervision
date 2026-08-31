# Neusoft Environmental Public Supervision System

A full-stack environmental supervision system covering **public AQI feedback, grid member inspection, administrator review, and a data visualization dashboard**.

[中文版 README](README_CN.md)

## Tech Stack

- **Backend**: Java, Spring Boot 3.4.9, MyBatis, MySQL 8, PageHelper, Hutool
- **Frontend**: Vue 3.5 (Composition API), Element Plus, Vue Router, Vuex, Axios, ECharts, @wangeditor

## Project Structure

```
nep-system/
├── backend/    # Spring Boot backend (port 9090)
├── frontend/   # Vue 3 frontend (port 8080, proxied to 9090)
└── sql/        # Database scripts (schema + test data)
```

## Feature Modules

| Role | Features |
|------|----------|
| Public Supervisor | Register/login, submit AQI feedback (province/city/address/photo), view feedback history |
| Grid Member | Login, query inspection tasks by province/city |
| Administrator | Review feedback, view statistics reports |
| Decision Maker | Login, data dashboard (AQI distribution, trends, province/city coverage) |

## Quick Start

1. **Database**: Create a `nep` database in MySQL 8 and run the scripts in `sql/` in order (DB credentials are configured in `backend/src/main/resources/application.yml`)
2. **Backend**: Open `backend/` in IntelliJ IDEA and run the main class — port **9090**
3. **Frontend**: Open `frontend/` in VS Code, run `npm install` then `npm run serve` — visit http://localhost:8080

## Test Accounts

See the account data in `sql/admins_test_data.sql` and `sql/补全数据.sql`.
