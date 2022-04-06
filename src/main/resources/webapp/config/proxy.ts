/**
 * 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 */
export default {
  dev: {
    '/api/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://172.171.50.62:8095',
      changeOrigin: true,
    },
    '/stomp/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://192.168.1.3:8091',
      changeOrigin: true,
    },
  },
  test: {
    '/api/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://192.168.1.3:8091',
      changeOrigin: true,
    },
    '/stomp/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://192.168.1.3:8091',
      changeOrigin: true,
    },
  },
  pre: {
    '/api/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://192.168.1.3:8091',
      changeOrigin: true,
    },
    '/stomp/': {
      target: 'http://127.0.0.1:8095',
      // target: 'http://192.168.1.3:8091',
      changeOrigin: true,
    },
  },
};
