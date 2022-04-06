package com.hy.dtn.vnm.user.serv.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.hy.dtn.vnm.biz.vo.VoPageInfo;
import com.hy.dtn.vnm.common.CommConst;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.user.bo.BoBaseUserObj;
import com.hy.dtn.vnm.user.orm.mysql.dao.FuncObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.dao.UserObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.dao.UserRoleObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.model.FuncObj;
import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import com.hy.dtn.vnm.user.serv.intf.UserServIntf;
import com.hy.dtn.vnm.user.utils.EncodeDecodeUtil;
import com.hy.dtn.vnm.user.vo.VoBaseUserObj;
import com.hy.dtn.vnm.user.vo.VoRoleAndFuncList;
import com.hy.dtn.vnm.user.vo.VoUserSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yjz
 * @version 1.0
 * @className UserServImpl
 * @date 2020/11/11 17:38
 * @description 用户信息服务接口实现类
 * @note 说明
 */
@Service
public class UserServImpl implements UserServIntf {

    @Resource
    private UserObjMapper userObjMapper;

    @Resource
    private UserRoleObjMapper userRoleObjMapper;

    /**
     * 功能代码相关dao
     */
    @Resource
    private FuncObjMapper funcObjMapper;

    /**
     * @param dlm     登录名
     * @param dlmm    登录密码
     * @param session session
     * @methodName login
     * @author yjz
     * @description 用户登录
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    @Override
    public BoAllUserObj login(String dlm, String dlmm, HttpSession session) {
        //若session为空，则直接返回
        if (session == null) {
            return null;
        }
        //根据用户id获取用户信息
        UserObj userObj = this.userObjMapper.selectByDlm(dlm);
        //若用户信息不存在，则返回
        if (userObj == null) {
            return null;
        }
        //用户密码解码验证。TODO:后续需要进行加解密操作
        String userPsd = EncodeDecodeUtil.decodeLogPsd(userObj.getDlmm());
        //判断当前登录用户密码是否正确
        if (dlmm.equals(userPsd)) {
            //数据转换
            BoAllUserObj logUser = new BoAllUserObj(userObj);
            //设置Session信息
            session.setAttribute(CommConst.NAME_LOGUSEROBJ, logUser);
            //设置session的最大时长为一天
            session.setMaxInactiveInterval(86400);
            //TODO:根据用户id查询用户功能列表数据


            return logUser;
        }
        return null;
    }

    /**
     * @param session session
     * @methodName getlogininfo
     * @author yjz
     * @description 登录用户查询
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    @Override
    public BoAllUserObj getLogUser(HttpSession session) {
        //若Session为空，则直接返回
        if (session == null) {
            return null;
        }
        //返回用户的登录信息
        return (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
    }

    /**
     * @param voUserSearch
     * @methodName getAllUse
     * @author yjz
     * @description 获取用户列表
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    @Override
    public Result<VoPageInfo<VoBaseUserObj>> getAllUse(VoUserSearch voUserSearch) {
        //数据转换，并解决部分字段不存在的问题
        UserObj userObj = new UserObj(voUserSearch);
        //转为map对象存储
        Map<String, Object> map = BeanUtil.beanToMap(userObj);
        //分页查询
        PageHelper.startPage(voUserSearch.getCurrent(), voUserSearch.getPageSize());
        //查询所有的用户信息
        List<UserObj> page = userObjMapper.findXtXtyhByCondition(map);
        //数据转换
        PageInfo<UserObj> pageInfo = new PageInfo<>(page);
        //DTO转Vo
        List<VoBaseUserObj> baseUserObjList = new ArrayList<>();
        for (UserObj temp : page) {
            baseUserObjList.add(new VoBaseUserObj(temp));
        }
        VoPageInfo<VoBaseUserObj> voBaseUserObjVoPageInfo = new VoPageInfo<>(pageInfo, baseUserObjList);
        //返回Bo
        return Result.ok(voBaseUserObjVoPageInfo);
    }

    /**
     * @param yhid 用户id
     * @methodName getUserInfo
     * @author yjz
     * @description 获取用户信息
     * @date 2020/11/11
     * @returnParam BoUserObj
     * @note 修改说明
     */
    @Override
    public BoBaseUserObj getUserInfo(String yhid) {
        //获取用户信息
        UserObj user = this.userObjMapper.selectByPrimaryKey(yhid);
        //若为空，则直接返回
        if (null == user) {
            return null;
        }
        //数据转换(无密码数据)
        BoBaseUserObj res = new BoBaseUserObj(user);
        //获取用户授权功能列表
        List<String> funcs = this.userRoleObjMapper.getUserRoleList(yhid);
        if (funcs == null) {
            funcs = new ArrayList<>();
        }
        res.setFuncs(funcs);
        return res;
    }

    /**
     * @param yhid 用户id
     * @return com.hy.dtn.ops.common.Result<java.util.List < java.lang.String>>
     * @description 方法描述
     * @methodName getUserRoleList
     * @author yjz
     * @date 2021/02/23 9:38
     * @note 修改说明
     */
    @Override
    public Result<VoRoleAndFuncList> getUserRoleList(String yhid) {
        //查询当前用户是否存在
        UserObj user = this.userObjMapper.selectByPrimaryKey(yhid);
        //若用户不存在，则返回错误信息
        if (null == user) {
            return Result.error("user.result.userInfo.yhid.notExist");
        }
        //获取用户角色列表
        List<String> res = this.userRoleObjMapper.getUserRoleList(yhid);
        VoRoleAndFuncList voRoleAndFuncList = new VoRoleAndFuncList();
        //不存在则返回空数组
        if (res == null) {
            return Result.ok(voRoleAndFuncList);
        }
        voRoleAndFuncList.setRoleList(res);
        List<FuncObj> userFunc = funcObjMapper.getUserFunc(yhid);
        if (userFunc == null) {
            userFunc = new ArrayList<>();
        }
        voRoleAndFuncList.setFuncList(userFunc.stream().map(FuncObj::getMkdm).collect(Collectors.toList()));
        return Result.ok(voRoleAndFuncList);
    }

    /**
     * @param newUser 新增的用户信息
     * @methodName addUserInfo
     * @author yjz
     * @description 新增用户信息
     * @date 2020/11/11
     * @returnParam Result
     * @note 修改说明
     */
    @Override
    public Result<String> addUserInfo(BoAllUserObj newUser) {
        //数据转换
        UserObj user = newUser.toDto();
        //若密码不存在，则设置默认密码
        if (StringUtil.isEmpty(user.getDlmm())) {
            //设置登录初始密码
            user.setDlmm(CommConst.getInitPsd(user.getSfz()));
        }
        //查询待新增的用户是否已存在
        if (this.userObjMapper.selectByPrimaryKeyAndDlm(user.getYhid(), user.getDlm()).isEmpty()) {
            //返回执行结果
            return Result.okOrFailed(1 == this.userObjMapper.insert(user));
        }
        return Result.error("user.result.addUserInfo.yhidordlm.exist.error");
    }

    /**
     * @param newUser 更新的用户信息
     * @methodName updateUserInfo
     * @author yjz
     * @description 修改用户信息
     * @date 2020/11/12
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public boolean updateUserInfo(BoAllUserObj newUser) {
        //数据转换
        UserObj user = newUser.toDto();
        //返回执行结果
        return 1 == this.userObjMapper.updateByPrimaryKey(user);
    }

    /**
     * @param ids 批量删除
     * @methodName delUserInfo
     * @author yjz
     * @description 删除用户
     * @date 2020/11/12 10:44
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public boolean delUserInfo(List<String> ids) {
        //返回执行结果。TODO:后续需要进行鉴权
        return this.userObjMapper.deleteByPrimaryKeyList(ids) > 0;
    }

    /**
     * @param yhid   用户id
     * @param newpsd 新的密码
     * @methodName changePassword
     * @author yjz
     * @description 修改密码
     * @date 2020/11/12 10:49
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public boolean changePassword(String yhid, String newpsd) {
        //根据id获取用户信息
        UserObj user = this.userObjMapper.selectByPrimaryKey(yhid);
        //若用户不存在，则直接返回
        if (null == user) {
            return false;
        }
        //若密码一致，则直接返回
        if (newpsd.equals(user.getDlmm())) {
            return false;
        }
        //返回修改结果
        return 1 == this.userObjMapper.changePsd(newpsd, yhid);
    }

    /**
     * @param yhid
     * @return java.util.List<com.hy.dtn.ops.user.orm.mysql.model.FuncObj>
     * @description 根据用户id查询用户路由表信息
     * @methodName getUserRoute
     * @author yjz
     * @date 2021/03/09 10:36
     * @note 修改说明
     */
    @Override
    public List<String> getUserRoute(String yhid) {
        List<FuncObj> userFunc = funcObjMapper.getUserFunc(yhid);
        if (userFunc == null) {
            return new ArrayList<>();
        }
        return userFunc.stream().map(FuncObj::getLyb).collect(Collectors.toList());
    }

    /**
     * @param yhid     用户id
     * @param jsdmlist 授权列表信息
     * @methodName commitUserRoles
     * @author yjz
     * @description 提交用户授权角色信息
     * @date 2020/11/12 10:49
     * @returnParam boolean
     * @note 修改说明
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, Exception.class})
    public boolean commitUserRoles(String yhid, List<String> jsdmlist) {
        //删除用户原有的其权限信息
        this.userRoleObjMapper.delUserRole(yhid);
        //若添加的数据为空，则直接返回
        if (jsdmlist.isEmpty()) {
            return true;
        }
        //迭代器
        List<HashMap<String, Object>> list = new ArrayList<>();
        //TODO:批量执行
        for (String str : jsdmlist) {
            HashMap<String, Object> map = new HashMap<>(1);
            map.put("yhid", yhid);
            map.put("jsdm", str);
            list.add(map);
        }
        this.userRoleObjMapper.insertByList(list);
        return true;
    }

}
