import React from 'react';
import { Table } from 'antd';
import MiniProgress from './MiniProgress';

export default ({data = [{}]}) => {
  /*const data = [
    {
      key: '1',
      name: 'CPU占用率',
      percent: 0.2,
      flow: 2335,
    },
    {
      key: '2',
      name: '内存占用率',
      percent: 0.4,
      flow: 3245,
    },
    {
      key: '3',
      name: '磁盘占用率',
      percent: 0.6,
      flow: 3445,
    },
  ];*/
  const columns = [
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
      render: (text: string) => <a>{text}</a>,
    },
    {
      title: '占比',
      dataIndex: 'percent',
      key: 'percent',
      render: (percent: number) => <MiniProgress percent={percent} bgColor={percent>0.4?"red":''} />,
    }
  ];
  return <Table columns={columns} dataSource={data} pagination={false} style={{ marginTop: 16 }} showHeader={false} size="small" />;
};
