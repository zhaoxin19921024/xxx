import React from "react";
import { Progress } from "@ant-design/charts";

interface MiniProgressProps {
  bgColor?:string;
  percent?:number;
  width?:number;
}

const MiniProgress: React.FC<MiniProgressProps> = ({bgColor = '#5B8FF9',percent = 0.3,width}) => {
  let config = {
    height:20,
    width:width,
    barWidthRatio:0.5,
    percent:percent,
    color:[bgColor,'#E8EDF3'],
  }
  return <Progress {...config}/>;
};

export  default MiniProgress;
