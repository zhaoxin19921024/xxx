import { MenuDataItem } from '@umijs/route-utils';
import { request } from 'umi';

export async function query() {
  return request<API.CurrentUser[]>('/api/users');
}
//查询登录信息
export async function queryCurrent() {
  return request<API.CurrentUser>('/api/tologin/getlogininfo');
}

export async function getUserMenuList() {
  return request<MenuDataItem[]>('/api/tologin/getUserMenuList');
}

export async function queryNotices(): Promise<any> {
  return request<{ data: API.NoticeIconData[] }>('/api/notices');
}
