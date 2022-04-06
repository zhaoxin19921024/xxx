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
 *  主机监控列表查询接口
 * @param params
 */
export async function searchByKeyWords(params: { current:1,pageSize:1000 }) {
  return request('/api/assets/searchByKeyWords', {
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




