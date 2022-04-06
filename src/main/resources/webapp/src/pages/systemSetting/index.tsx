import React, { useState, useRef, useLayoutEffect } from 'react';
import { GridContent } from '@ant-design/pro-layout';
import { Menu } from 'antd';
import UserModule from './components/UserModule';
import RoleModule from './components/RoleModule';
import styles from './style.less';

const { Item } = Menu;

type SettingsStateKeys = 'users' | 'roles';
interface SettingsState {
  mode: 'inline' | 'horizontal';
  selectKey: SettingsStateKeys;
}

const Settings: React.FC = () => {
  const menuMap: {
    [key: string]: React.ReactNode;
  } = {
    users: '用户管理',
    roles: '角色管理',
  };

  const [initConfig, setInitConfig] = useState<SettingsState>({
    mode: 'inline',
    selectKey: 'users',
  });
  const dom = useRef<HTMLDivElement>();

  const resize = () => {
    requestAnimationFrame(() => {
      if (!dom.current) {
        return;
      }
      let mode: 'inline' | 'horizontal' = 'inline';
      const { offsetWidth } = dom.current;
      if (dom.current.offsetWidth < 641 && offsetWidth > 400) {
        mode = 'horizontal';
      }
      if (window.innerWidth < 768 && offsetWidth > 400) {
        mode = 'horizontal';
      }
      setInitConfig(
        Object.assign({}, initConfig, { mode: mode as SettingsState['mode'] }),
      );
    });
  };

  useLayoutEffect(() => {
    if (dom.current) {
      window.addEventListener('resize', resize);
      resize();
    }
    return () => {
      window.removeEventListener('resize', resize);
    };
  }, [dom.current]);

  const getMenu = () => {
    return Object.keys(menuMap).map((item) => <Item key={item}>{menuMap[item]}</Item>);
  };

  const renderChildren = () => {
    const { selectKey } = initConfig;
    switch (selectKey) {
      case 'users':
        return <UserModule />;
      case 'roles':
        return <RoleModule />;
      default:
        return null;
    }
  };

  return (
    <GridContent>
      <div
        className={styles.main}
        ref={(ref) => {
          if (ref) {
            dom.current = ref;
          }
        }}
      >
        <div className={styles.leftMenu}>
          <Menu
            mode={initConfig.mode}
            selectedKeys={[initConfig.selectKey]}
            onClick={({ key }) => {
              setInitConfig(
                Object.assign({}, initConfig, {
                  selectKey: key as SettingsStateKeys,
                }),
              );
            }}
          >
            {getMenu()}
          </Menu>
        </div>
        <div className={styles.right}>
          <div className={styles.title}>{menuMap[initConfig.selectKey]}</div>
          {renderChildren()}
        </div>
      </div>
    </GridContent>
  );
};
export default Settings;
