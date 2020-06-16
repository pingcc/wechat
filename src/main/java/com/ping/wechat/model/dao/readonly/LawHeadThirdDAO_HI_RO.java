package com.ping.wechat.model.dao.readonly;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.wechat.model.entity.readonly.Law_Head_Third_HI_RO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created  on 2018/11/28.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */


/**
 * 如果不配置需要对WechatApplication 进行MapperScan注解
 *
 * @link WechatApplication
 */
//@Mapper
@Component("lawHeadThirdDAO_HI_RO")
public interface LawHeadThirdDAO_HI_RO  extends BaseMapper<Law_Head_Third_HI_RO> {





  @Select(Law_Head_Third_HI_RO.QUERY)
  Law_Head_Third_HI_RO findTemplateId(Map<String, Object> map);

  @Select(Law_Head_Third_HI_RO.QUERY1)
  Law_Head_Third_HI_RO findTemplateId1(Map<String, Object> map);

  @Select(Law_Head_Third_HI_RO.QUERY_LAW_HEAD_SECOND_IDS)
  List<Law_Head_Third_HI_RO>findLowHeadListIds();

  @Select(Law_Head_Third_HI_RO.query_third_query )
  List<Law_Head_Third_HI_RO>  findLowThirdChild(Map<String, Object> map);

  @Select(Law_Head_Third_HI_RO.query_third_query_end)
  List<Law_Head_Third_HI_RO>  findLowThirdChildEnd(Map<String, Object> map);

  @Select(Law_Head_Third_HI_RO.QUERY_LAW_HEAD)
  Law_Head_Third_HI_RO selectBySecondId(Map<String, Object> map);

}

