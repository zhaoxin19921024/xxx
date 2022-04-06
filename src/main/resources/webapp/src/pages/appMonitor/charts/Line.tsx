import React from 'react';
import { Line } from '@ant-design/charts';

interface CreateLineProps {
  autoFit: boolean;
  height: number;
  data: [];
}

const DeviceLine: React.FC<CreateLineProps> = (props) => {
  const {autoFit,height,data} = props;
  const config = {
    autoFit,
    height,
    data,
    xField: 'year',
    yField: 'value',
    seriesField: 'category',
    yAxis: {
      label: {
        formatter: function formatter(v) {
          return ''.concat(v).replace(/\d{1,3}(?=(\d{3})+$)/g, function (s) {
            return ''.concat(s, ',');
          });
        },
      },
    },
    /*point: {
      size: 5,
      shape: 'diamond',
      style: {
        fill: 'white',
        stroke: '#5b8ff9',
        lineWidth: 2,
      },
    },*/
  };
  return <Line {...config} />;
};

export default DeviceLine;
