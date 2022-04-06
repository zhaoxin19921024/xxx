import React, { Component } from 'react';
import Chart from './chart';
import { treeMapOptions } from './options';

class TreeMapChart extends Component {
  constructor(props) {
    super(props);
    this.state = {
      renderer: 'canvas',
      option: '',
    };
  }

  render() {
    const { renderer } = this.state;
    const { data } = this.props;
    return (
      <div
        style={{
          width: '100%',
          height: '500px',
          margin: 'auto',
        }}>
        {Object.keys(data).length > 0 ? (
          <Chart
            renderer={renderer}
            option={treeMapOptions(data)}
          />
        ) : (
          ''
        )}
      </div>
    );
  }
}

export default TreeMapChart;
