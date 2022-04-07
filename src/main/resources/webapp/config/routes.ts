export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        name: 'login',
        path: '/user/login',
        component: './user/login',
      },
    ],
  },
  {
    path: '/welcome',
    name: 'home',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/monitorTask',
    name: 'monitorTask',
    icon: 'crown',
    component: './monitorTask',
  },
  // {
  //   path: '/indicatorShow',
  //   name: 'indicatorShow',
  //   icon: 'crown',
  //   component: './indicatorShow',
  // },
  {
    path: '/trafficManager',
    name: 'trafficManager',
    icon: 'crown',
    component: './trafficManager',
  },
  {
    path: '/probeController',
    name: 'probeController',
    icon: 'crown',
    component: './ProbeController',
  },
  {
    path: '/networkConfig',
    name: 'networkConfig',
    icon: 'crown',
    component: './NetworkConfig',
  },


  // {
  //   path: '/deviceMonitor',
  //   name: 'deviceMonitor',
  //   icon: 'crown',
  //   component: './deviceMonitor',
  // },
  // {
  //   path: '/appMonitor',
  //   name: 'appMonitor',
  //   icon: 'crown',
  //   component: './appMonitor',
  // },
  {
    path: './systemSetting',
    name: 'systemSetting',
    icon: 'setting',
    component: './systemSetting',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
