import {request} from 'umi';
// @ts-ignore
import {TableListItem, TableListParams, UpdateWlbsFl,TableListItemRole} from './data.d';

export async function queryRule(params: TableListParams) {
  return request('/api/tologin/getAllUse', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function removeRule(params: { ids: String[] }) {
  return request('/api/tologin/delUserInfoByIds', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function addRule(params: TableListItem) {
  return request('/api/rule', {
    method: 'POST',
    data: {
      ...params,
      method: 'post',
    },
  });
}

export async function updateRule(params: TableListParams) {
  return request('/api/rule', {
    method: 'POST',
    data: {
      ...params,
      method: 'update',
    },
  });
}

/**
 * 新增用户
 * @param params
 */
export async function addUserInfo(params: TableListItem) {
  return request('/api/tologin/addUserInfo', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 修改用户
 * @param params
 */
export async function updateUserInfo(params: TableListItem) {
  return request('/api/tologin/updateUserInfo', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 删除用户
 * @param params
 */
export async function delUserInfoById(params: { id: String }) {
  return request('/api/tologin/delUserInfoById', {
    params
  });
}

/**
 * 获取角色列表
 * @param params
 */
export async function getRoleList(params: TableListParams) {
  return request('/api/torole/getRoleList', {
    params
  });
}

/**
 * 新增角色
 * @param params
 */
export async function addRoleInfo(params: TableListItemRole) {
  return request('/api/torole/addRoleInfo', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 修改角色
 * @param params
 */
export async function updateRoleInfo(params: TableListItemRole) {
  return request('/api/torole/updateRoleInfo', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 删除角色
 * @param params
 */
export async function delRoleInfo(params: { jsdm: String }) {
  return request('/api/torole/delRoleInfo', {
    params
  });
}

/**
 * 获取菜单树
 * @param params
 */
export async function getFuncTreeTable(params: {}) {
  return request('/api/torole/getFuncTreeTable', {
    params
  });
}

/**
 * 获取用户的角色列表
 * @param params
 */
export async function getUserRoleList(params: { yhid: String }) {
  return request('/api/tologin/getUserRoleList', {
    params
  });
}

/**
 * 提交用户授权角色信息
 * @param params
 */
export async function commitUserRoles(params: { jsdmlist:[], yhid:string }) {
  return request('/api/tologin/commitUserRoles', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

/**
 * 获取角色功能列表
 * @param params
 */
export async function getRoleFunc(params: { jsdm: String }) {
  return request('/api/torole/getRoleFunc', {
    params
  });
}

/**
 * 提交角色的功能授权信息
 * @param params
 */
export async function commitRoleFunc(params: { gnlist:[], jsdm:string }) {
  return request('/api/torole/commitRoleFunc', {
    method: 'POST',
    data: {
      ...params
    }
  });
}



