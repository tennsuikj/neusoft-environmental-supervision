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

## URLs

### Frontend (port 8080)

Base URL: `http://localhost:8080`

**Login / Register pages:**

| Page | URL |
|------|-----|
| Grid Member Login (default) | `http://localhost:8080/gridMemberLogin` |
| Administrator Login | `http://localhost:8080/adminsLogin` |
| Public Supervisor Login | `http://localhost:8080/supervisorLogin` |
| Public Supervisor Register | `http://localhost:8080/supervisorRegister` |
| Decision Maker Login | `http://localhost:8080/decisionMakerLogin` |

**Role pages (after login):**

| Role | Page | URL |
|------|------|-----|
| Administrator | Public supervision data list | `http://localhost:8080/admins/publicSupervise` |
| Administrator | Confirm AQI data list | `http://localhost:8080/admins/aqiConfirm` |
| Administrator | Province-grouped inspection statistics | `http://localhost:8080/admins/provinceStat` |
| Administrator | AQI distribution statistics | `http://localhost:8080/admins/aqiDist` |
| Administrator | AQI trend statistics | `http://localhost:8080/admins/aqiTrend` |
| Administrator | Other statistics | `http://localhost:8080/admins/otherStat` |
| Grid Member | Feedback task list | `http://localhost:8080/gridMember/feedbackTask` |
| Public Supervisor | Submit feedback | `http://localhost:8080/supervisor/feedback` |
| Public Supervisor | Feedback history | `http://localhost:8080/supervisor/feedbackHistory` |
| Decision Maker | Data dashboard | `http://localhost:8080/decisionMaker/visionData` (or `/visionData`) |

### Backend (port 9090)

Base URL: `http://localhost:9090`

**Auth:**
- `POST /adminsLogin` / `/gridMemberLogin` / `/supervisorLogin` / `/decisionMakerLogin` — role login
- `POST /supervisorRegister` — supervisor register
- `PUT /updatePassword` — change password

**Feedback (`/aqiFeedback`):**
- `POST /saveAqiFeedback` — submit AQI feedback
- `GET /listAqiFeedbackByTelId` / `listAqiFeedbackAll` / `listAqiFeedbackPage` — query feedback
- `GET /getAqiFeedbackById` — feedback detail
- `PUT /updateAqiFeedbackAssign` / `updateAqiFeedbackState` — assign / review feedback

**Statistics (`/statistics`):**
- `GET /listStatisticsPage` — paged statistics
- `GET /listAqiDistributeTotalStatis` / `listAqiTrendTotalStatis` / `listProvinceItemTotalStatis` — charts data
- `GET /getAqiCount` / `getAqiGoodCount` / `getProvinceCoverage` / `getCityCoverage` — counts & coverage
- `POST /saveStatistics` — save statistics

**Others:**
- `GET /admins/getAdminsByCode`
- `GET /gridMember/listGridMemberByProvinceIdByCityId`
- `GET /gridCity/getProvinceAndCity`
- `GET /aqi/listAqiAll`
- `POST /files/upload` / `GET /files/download/{fileName}`
- `GET /hello` — health check

## Quick Start

1. **Database**: Create a `nep` database in MySQL 8 and run the scripts in `sql/` in order (DB credentials are configured in `backend/src/main/resources/application.yml`)
2. **Backend**: Open `backend/` in IntelliJ IDEA and run the main class — port **9090**
3. **Frontend**: Open `frontend/` in VS Code, run `npm install` then `npm run serve` — visit http://localhost:8080

## Test Accounts

See the account data in `sql/admins_test_data.sql` and `sql/补全数据.sql`.
