export class formItemData{
  span: number;
  //标题
  label: string;
  //字段名
  name: string;
  //样式信息
  style: object;
  //占用行数
  rows: number;
  //当前form表单元素类型
  type: string;
  //下拉框
  selectData: { label, value; }[];
  //是否隐藏
  isShow: boolean;
  /**
   * 构造器
   * @param label
   * @param name
   * @param type
   */

  constructor(label, name, type, isShow) {
    this.span = 6;
    this.label = label;
    this.name = name;
    this.style = null;
    this.rows = 3;
    this.type = type;
    this.selectData = [];
    this.isShow = isShow;
  }
}

