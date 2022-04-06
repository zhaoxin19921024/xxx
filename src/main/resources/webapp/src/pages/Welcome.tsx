import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import { Card, Alert, Typography } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import styles from './Welcome.less';

export default () => {
  return (
    <PageContainer
      style={{margin:"0"}}
      pageHeaderRender={false}
      className={styles.container}
    />
  );
};
