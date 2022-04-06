import React from 'react';
import ProTable from '@ant-design/pro-table';

export default () => {
  const data = [
    {
      key: '1',
      name: 'eth1',
      upValue: 0.2,
      downValue: 2335,
      status:'error',
    },
    {
      key: '2',
      name: 'eth2',
      upValue: 0.4,
      downValue: 3245,
      status:'error',
    },
    {
      key: '3',
      name: 'eth3',
      upValue: 0.6,
      downValue: 3445,
      status:'error',
    },
  ];
  const columns = [
    {
      title: '端口名称',
      dataIndex: 'name',
      key: 'name',
      render: (text: string) => <a>{text}</a>,
    },
    {
      title: '接收速率',
      dataIndex: 'upValue',
      key: 'upValue',
      render: (upValue: number) => <a>{upValue}</a>,
    },
    {
      title: '发送速率',
      dataIndex: 'downValue',
      key: 'downValue',
      render: (downValue: number) => <a>{downValue}</a>,
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      initialValue: 'all',
      filters: true,
      onFilter: true,
      valueEnum: {
        all: { text: '全部', status: 'Default' },
        close: { text: '关闭', status: 'Default' },
        running: { text: '运行中', status: 'Processing' },
        online: { text: '已上线', status: 'Success' },
        error: { text: '异常', status: 'Error' },
      },
    },
  ];
  // @ts-ignore
  return <ProTable columns={columns} dataSource={data} pagination={false} style={{ marginTop: 16 }} search={false} toolBarRender={false}/>;
};
