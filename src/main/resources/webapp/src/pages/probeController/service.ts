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
 * 新增监控任务
 * @param params
 */
export async function addJkrw(params: any) {
  return request('/api/task/addJkrw', {
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
export async function editJkrw(params: any) {
  return request('/api/task/editJkrw', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

export async function delJkrw(params: any) {
  return request('/api/task/delJkrw', {
    method: 'POST',
    data: {
      ...params
    }
  });
}


/**
 * 查询监控任务列表
 * @param params
 */
export async function getJkrwList(params: any) {
  return request('/api/task/getJkrwList', {
    method: 'POST',
    data: {
      ...params
    }
  });
}


/**
 * 开始或停止任务
 * @param params
 */
export async function startOrStopTask(params: any) {
  return request('/api/task/startOrStopTask', {
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


