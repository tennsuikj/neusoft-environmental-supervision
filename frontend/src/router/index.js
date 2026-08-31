import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // {
  //   path: '/',
  //   redirect: '/adminsLogin'
  // },
  {
    path: '/',
    redirect: '/gridMemberLogin'
  },
  // {
  //   path: '/',
  //   redirect: '/supervisorLogin'
  // },
  {
    path: '/admins', name: 'admins', meta: { title: "管理员" }, component: () => import('../views/Admins/Admins.vue'), children: [
      {
        path: 'publicSupervise',
        name: 'publicSupervise',
        meta: { title: "公众监督数据列表" },
        component: () => import('../views/Admins/PublicSupervise.vue')
      },
      {
        path: 'aqiConfirm',
        name: 'aqiConfirm',
        meta: { title: "确认AQI数据列表" },
        component: () => import('../views/Admins/AqiConfirm.vue')
      },
      {
        path: 'provinceStat',
        name: 'provinceStat',
        meta: { title: "省分组检查统计" },
        component: () => import('../views/Admins/ProvinceStat.vue')
      },
      {
        path: 'aqiDist',
        name: 'aqiDist',
        meta: { title: "AQI指数分布统计" },
        component: () => import('../views/Admins/AqiDist.vue')
      },
      {
        path: 'aqiTrend',
        name: 'aqiTrend',
        meta: { title: "AQI指数趋势统计" },
        component: () => import('../views/Admins/AqiTrend.vue')
      },
      {
        path: 'otherStat',
        name: 'otherStat',
        meta: { title: "其它数据统计" },
        component: () => import('../views/Admins/OtherStat.vue')
      }

    ]
  },
  {
    path: '/gridMember', name: 'gridMember', meta: { title: "网格员" }, component: () => import('../views/GridMember/GridMember.vue'), children: [
      {
        path: 'feedbackTask',
        name: 'feedbackTask',
        meta: { title: "反馈任务列表" },
        component: () => import('../views/GridMember/FeedbackTask.vue')
      },

    ]
  },
  {
    path: '/supervisor', name: 'supervisor', meta: { title: "公众监督员" }, component: () => import('../views/Supervisor/Supervisor.vue'), children: [
      {
        path: 'feedback',
        name: 'feedback',
        meta: { title: "反馈信息" },
        component: () => import('../views/Supervisor/Feedback.vue')
      },
      {
        path: 'feedbackHistory',
        name: 'feedbackHistory',
        meta: { title: "历史反馈信息" },
        component: () => import('../views/Supervisor/FeedbackHistory.vue')
      },

    ]
  },
   {
     path: '/decisionMaker', name: 'decisionMaker', meta: { title: "决策员" }, component: () => import('../views/DecisionMaker/DecisionMaker.vue'), children: [
      {
        path: 'visionData',
         name: 'visionData',
         meta: { title: "确认AQI数据列表" },
         component: () => import('../views/DecisionMaker/VisionData.vue')
       }
    ]
  },
     {
        path: '/visionData',
        name: 'visionData',
        meta: { title: "确认AQI数据列表" },
        component: () => import('../views/DecisionMaker/VisionData.vue')
      },
  {
    path: '/adminsLogin',
    name: 'adminsLogin',
    meta: { title: "管理员登录" },
    component: () => import('../views/AdminsLogin.vue')
  },
  {
    path: '/gridMemberLogin',
    name: 'gridMemberLogin',
    meta: { title: "网格员登录" },
    component: () => import('../views/GridMemberLogin.vue')
  },
  {
    path: '/supervisorLogin',
    name: 'supervisorLogin',
    meta: { title: "公众监督员登录" },
    component: () => import('../views/SupervisorLogin.vue')
  },
  {
    path: '/supervisorRegister',
    name: 'supervisorRegister',
    meta: { title: "注册" },
    component: () => import('../views/SupervisorRegister.vue')
  },
  {
    path:'/decisionMakerLogin',
    name:'decisionMakerLogin',
    meta:{title:'决策员登录'},
    component:()=>import('../views/DecisionMakerLogin.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
//路由守卫
router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  next()
})
export default router
