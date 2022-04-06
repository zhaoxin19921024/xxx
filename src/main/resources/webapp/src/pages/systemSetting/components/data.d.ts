export interface TableListItem {
  yhid: string;
  yhxm: string,
  dlm: string;
  dlmm: string;
  sfz: string;
  csny: string;
  xb: string;
  zw: string;
  lxdh: string;
}

export interface TableListItemRole {
  jsdm: string;
  jsmc: string,
  jssm: string;
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
