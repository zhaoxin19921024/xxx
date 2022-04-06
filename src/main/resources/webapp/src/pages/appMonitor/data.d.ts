export interface formDataMap {
  //机柜
  JG: formItemData[],
  //设备
  SB: formItemData[],
  //CPU
  CPU: formItemData[],
  //内存
  NC: formItemData[],
  //磁盘
  CP: formItemData[],
  //接口
  JK: formItemData[],
}

export interface TableListItem {
  id: string;
  flid: string,
  name: string;
  wlmbmc: string;
  yyfwlx: number;
  yyfwlxmc: string;
  dzfpfs: number;
  dzfpfsmc: string;
  zt: number;
  ztmc: string;
  bjt: string;
  yycjid: string;
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListData {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  name?: string;
  desc?: string;
  flid?: string;
  pageSize?: number;
  currentPage?: number;
  id?: string;
  filter?: { [id: string]: any[] };
  sorter?: { [id: string]: any };
}

//更新的网络模板树节点数据
export interface UpdateWlbsFl {
  flid?: string;
  fjcjm?: string;
  flmc?: string;
  xssx?: string;
}

export interface DataNode {
  title: string;
  key: string;
  isLeaf?: boolean;
  children?: DataNode[];
}

export interface test {
  key: any;
  children: any;
}
