import {request} from 'umi';
// @ts-ignore
import {TableListItem, TableListParams, UpdateWlbsFl} from './data.d';

/**
 * 获取数据字典信息
 * @param params
 */
export async function getDictObjById(params: { zdbs: string | undefined }) {
  return request('/api/dict/getDictObjById', {
    params,
  });
}

/**
 * 新增工程
 * @param params
 */
export async function addJkgczb(params: any) {
  return request('/api/vnm/addJkgczb', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 获取工程列表
 * @param params
 */
export async function getJkgczbList(params: any) {
  return request('/api/vnm/getJkgczbList', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 获取工程列表
 * @param params
 */
export async function addJkzbtx(params: any) {
  return request('/api/vnm/addJkzbtx', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 获取工程列表
 * @param params
 */
export async function getGcxxTxList(params: any) {
  return request('/api/vnm/getGcxxTxList', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 获取工程列表
 * @param params
 */
export async function getGcTxDataFromServer(params: any) {
  return request('/api/vnm/getGcTxDataFromServer', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 删除工程图形
 * @param params
 */
export async function delGcTxData(params: any) {
  return request('/api/vnm/delGcTxData', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

export async function delJkgczb(params: any) {
  return request('/api/vnm/delJkgczb', {
    method: 'POST',
    data: {
      ...params
    }
  });
}





/**
 * 更新资产
 * @param params
 */
export async function updateAssets(params: { data: object, type: string }) {
  return request('/api/assets/updateAssets', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 根据id删除资产信息
 * @param params
 */
export async function delAssetsById(params: { id: string, type: string }) {
  return request('/api/assets/delAssetsById', {
    params,
  });
}

/**
 * 根据id查询资产的详细信息
 * @param params
 */
export async function getAssetsInfo(params: { id: string, type: string }) {
  return request('/api/assets/getAssetsInfo', {
    params
  });
}

/**
 * 获取资产信息，初始化返回机柜树结构数据，后续返回设备List数据格式
 * @param params
 */
export async function getAssetsTree(params: { key: string, type: string }) {
  return request('/api/assets/getAssetsTree', {
    params
  });
}


