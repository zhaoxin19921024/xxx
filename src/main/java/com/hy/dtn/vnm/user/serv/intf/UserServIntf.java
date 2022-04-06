
package com.hy.dtn.vnm.user.serv.intf;

import com.hy.dtn.vnm.biz.vo.VoPageInfo;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.user.bo.BoBaseUserObj;
import com.hy.dtn.vnm.user.vo.VoBaseUserObj;
import com.hy.dtn.vnm.user.vo.VoRoleAndFuncList;
import com.hy.dtn.vnm.user.vo.VoUserSearch;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className UserServIntf
 * @date 2020/11/11 17:38
 * @description 用户信息服务接口类
 * @note 说明
 */
public interface UserServIntf {

    /**
     * @param dlm
     * @param dlmm
     * @param session
     * @methodName login
     * @author yjz
     * @description 用户登录
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    BoAllUserObj login(String dlm, String dlmm, HttpSession session);

    /**
     * @param session
     * @methodName getlogininfo
     * @author yjz
     * @description 登录用户查询
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    BoAllUserObj getLogUser(HttpSession session);

    /**
     * @methodName getAllUse
     * @author yjz
     * @description 获取用户列表
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     * @param voUserSearch 查询条件
     */
    Result<VoPageInfo<VoBaseUserObj>> getAllUse(VoUserSearch voUserSearch);

    /**
     * @param yhid
     * @methodName getUserInfo
     * @author yjz
     * @description 获取用户信息
     * @date 2020/11/11
     * @returnParam com.hy.dtn.ops.common.Result<?>
     * @note 修改说明
     */
    BoBaseUserObj getUserInfo(String yhid);

    /**
     * @className getUserRoleList
     * @author yjz
     * @date 2020/11/11 18:06
     * @version 1.0
     * @description 获取用户的角色列表
     * @note 说明
     */
    Result<VoRoleAndFuncList> getUserRoleList(String yhid);

    /**
     * @param newUser
     * @methodName addUserInfo
     * @author yjz
     * @description 新增用户信息
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    Result<String> addUserInfo(BoAllUserObj newUser);

    /**
     * @param newUser
     * @methodName updateUserInfo
     * @author yjz
     * @description 修改用户信息
     * @date 2020/11/12
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean updateUserInfo(BoAllUserObj newUser);

    /**
     * @param ids
     * @methodName delUserInfo
     * @author yjz
     * @description 删除用户
     * @date 2020/11/12 10:44
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean delUserInfo(List<String> ids);

    /**
     * @param yhid, jsdmlist
     * @methodName commitUserRoles
     * @author yjz
     * @description 提交用户授权角色信息
     * @date 2020/11/12 10:49
     * @returnParam boolean
     * @note 修改说明
     */
    boolean commitUserRoles(String yhid, List<String> jsdmlist);

    /**
     * @param yhid, newpsd
     * @methodName changePassword
     * @author yjz
     * @description 修改密码
     * @date 2020/11/12 10:49
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean changePassword(String yhid, String newpsd);

    /**
     * @description 根据用户id查询用户路由表信息
     * @methodName getUserRoute
     * @author yjz
     * @date 2021/03/09 10:36
     * @param yhid 用户id
     * @return java.util.List<com.hy.dtn.ops.user.orm.mysql.model.FuncObj>
     * @note 修改说明
     */
    List<String> getUserRoute(String yhid);

}
