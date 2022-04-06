import {DndProvider, useDrag} from 'react-dnd';
import React from "react";
import ProCard from '@ant-design/pro-card';
import { Button } from 'antd';
import { Line } from '@ant-design/charts';

export const DashBoard = (props:any) => {

  const { editDataEvent, gcStatus, delDataEvent } = props

  const config = {
    data : [],
    xField: 'time',
    yField: 'value',
    seriesField: 'categoryName',
    xAxis: {
      // type: 'time',
    },
    yAxis: {
      label: {
        // 数值格式化为千分位
        formatter: (v) => `${v}`.replace(/\d{1,3}(?=(\d{3})+$)/g, (s) => `${s},`),
      },
    },
  };

  return (
    <ProCard direction="column" ghost gutter={[0, 16]} >
      {
        props?.txList.map(item=> {
          return (
              <ProCard
                style={{ height: 250 }}
                title={item.txmc}
                extra={
                  gcStatus !== 0 && gcStatus !== 2?
                  [
                    <Button
                      size="small"
                      onClick={(e) => {
                        editDataEvent(item)
                      }}
                    >
                      编辑
                    </Button>,
                    <Button
                      size="small"
                      style={{marginLeft:10}}
                      onClick={(e) => {
                        delDataEvent(item)
                      }}
                    >
                      删除
                    </Button>
                  ] : []
                }>
                <Line
                  {...config}
                  data = {JSON.stringify(props?.txData) !== "{}"? props?.txData[item?.txbs] : []}
                />
              </ProCard>
          )
        })
      }
    </ProCard>
  );
};
