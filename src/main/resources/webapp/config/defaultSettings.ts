import { Settings as LayoutSettings } from '@ant-design/pro-layout';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'dark',
  // 拂晓蓝
  primaryColor: '#1890ff',
  layout: 'top',
  contentWidth: 'Fluid',
  fixedHeader: true,
  fixSiderbar: false,
  colorWeak: false,
  title: false,
  pwa: false,
  logo: '/images/logo.png',
  iconfontUrl: '',
  footerRender:false,
};

export default Settings;
