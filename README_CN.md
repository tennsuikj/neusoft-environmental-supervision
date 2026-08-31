# 东软环保公众监督系统（Neusoft Environmental Public Supervision System）

一个覆盖**公众 AQI 反馈、网格员检测、管理员审核、数据可视化大屏**的全栈环保监督系统。

## 技术栈

- **后端**：Java、Spring Boot 3.4.9、MyBatis、MySQL 8、PageHelper、Hutool
- **前端**：Vue 3.5（Composition API）、Element Plus、Vue Router、Vuex、Axios、ECharts、@wangeditor

## 目录结构

```
nep-system/
├── backend/    # Spring Boot 后端（端口 9090）
├── frontend/   # Vue 3 前端（端口 8080，代理到 9090）
└── sql/        # 数据库脚本（表结构 + 测试数据）
```

## 功能模块

| 角色 | 功能 |
|------|------|
| 公众监督员 | 注册登录、提交 AQI 反馈（省/市/地址/照片）、历史反馈查询 |
| 网格员 | 登录、按省/市查询检测任务 |
| 管理员 | 审核反馈、查看统计报表 |
| 决策者 | 登录、数据可视化大屏（AQI 分布、趋势、省市覆盖） |

## 快速启动

1. **数据库**：MySQL 8 中创建数据库 `nep`，依次执行 `sql/` 下的脚本（数据库账号配置见 `backend/src/main/resources/application.yml`）
2. **后端**：IDEA 打开 `backend/`，运行主类，端口 **9090**
3. **前端**：VS Code 打开 `frontend/`，执行 `npm install` 后 `npm run serve`，访问 http://localhost:8080

## 测试账号

见 `sql/admins_test_data.sql` 与 `sql/补全数据.sql` 中的账号数据。
