package com.hy.dtn.vnm.user.serv.intf;

import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserObjMapper {
    int deleteByPrimaryKey(@Param("yhid")String yhid);

    int insert(UserObj record);

    int insertSelective(UserObj record);

    UserObj selectByPrimaryKey(@Param("yhid")String yhid);

    int updateByPrimaryKeySelective(UserObj record);

    int updateByPrimaryKey(UserObj record);
    
    int changePsd(@Param("newpsd")String newpsd,@Param("yhid")String yhid);
    
    List<UserObj> getAllUser();
}