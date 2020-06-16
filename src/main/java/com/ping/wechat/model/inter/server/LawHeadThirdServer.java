package com.ping.wechat.model.inter.server;

import com.ping.wechat.model.dao.LawHeadSecondDAO_HI;
import com.ping.wechat.model.dao.LawHeadThirdDAO_HI;
import com.ping.wechat.model.dao.readonly.LawHeadThirdDAO_HI_RO;
import com.ping.wechat.model.entity.Law_Head_Third_HI;
import com.ping.wechat.model.entity.Law_Head_second_HI;
import com.ping.wechat.model.entity.readonly.Law_Head_Third_HI_RO;
import com.ping.wechat.model.inter.ILawHeadThirdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created  on 2019/9/30.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Component("lawHeadThirdServer")
public class LawHeadThirdServer implements ILawHeadThirdServer {
    @Autowired
    private LawHeadThirdDAO_HI lawHeadThirdDAO_hi;
    @Autowired
    private LawHeadSecondDAO_HI lawHeadSecondDAO_hi;
    @Autowired
    private LawHeadThirdDAO_HI_RO lawHeadThirdDAO_hi_ro;

    public void saveOrUpdateLawHeadThirdIno() {
        //	一、首先查询 law_head_second的数量

        List<Law_Head_Third_HI_RO> law_head_third_hi_ros = lawHeadThirdDAO_hi_ro.findLowHeadListIds();
        int length = law_head_third_hi_ros.size();
        HashMap map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Law_Head_Third_HI_RO lawHeadThirdHiRo = law_head_third_hi_ros.get(i);
           int headId = lawHeadThirdHiRo.getId();
            if (lawHeadThirdHiRo.getId() == 293) {
                int x = 0;
            }
            map.clear();
            map.put("id", lawHeadThirdHiRo.getId());
            //查询满足headId分段的第一个条件id
            Law_Head_Third_HI_RO templete = lawHeadThirdDAO_hi_ro.findTemplateId(map);
                if(templete==null){
                templete = lawHeadThirdDAO_hi_ro.findTemplateId1(map);

            }
            List<Law_Head_Third_HI_RO> childList=null;
            if (i != length - 1) { //不是最后一个
                Law_Head_Third_HI_RO nextLaw = law_head_third_hi_ros.get(i + 1);
                map.clear();
                map.put("id", nextLaw.getId());
                //查询满足headId分段的最后一个条件id
                Law_Head_Third_HI_RO nextTemplete = lawHeadThirdDAO_hi_ro.findTemplateId(map);
                if(nextTemplete==null){
                    nextTemplete = lawHeadThirdDAO_hi_ro.findTemplateId1(map);

                }
                map.clear();
                map.put("id", lawHeadThirdHiRo.getId());
                Law_Head_Third_HI_RO LawHi = lawHeadThirdDAO_hi_ro.selectBySecondId(map);
                //查询下一个行标的headid 是同一个

                map.clear();
                map.put("id", nextLaw.getId());
                Law_Head_Third_HI_RO nextLawHi = lawHeadThirdDAO_hi_ro.selectBySecondId(map);

                //不相等，满足条件插入记录\
                HashMap map1 = new HashMap<>();
                map1.put("startId", templete.getId());
                if (LawHi.getId() == nextLawHi.getId()) {
                    map1.put("endId", nextTemplete.getId());
                } else {
                    map1.put("endId", nextTemplete.getId() - 1);
                }
               childList = lawHeadThirdDAO_hi_ro.findLowThirdChild(map1);


            } else {
                HashMap map1 = new HashMap<>();
                map1.put("startId", templete.getId());
               childList = lawHeadThirdDAO_hi_ro.findLowThirdChildEnd(map1);

            }
            if(childList==null||childList.size()==0)
                continue;
            System.out.println("行标题---" + lawHeadThirdHiRo.getId() + "::" + lawHeadThirdHiRo.getContent());
            System.out.println("第一条数据---" + childList.get(0).getId() + "::" + childList.get(0).getContent());
            System.out.println("第最后一条数据---" + childList.get(childList.size() - 1).getId() + "::" + childList.get(childList.size() - 1).getContent());

            for (Law_Head_Third_HI_RO item : childList) {
                Law_Head_Third_HI entity = new Law_Head_Third_HI();
                entity.setContent(item.getContent());
                entity.setSecondHeadId(headId);
                lawHeadThirdDAO_hi.insert(entity);
            }
        }


    }


    @Override
    public void updateLawHeadInfo() {
        List<Law_Head_second_HI> list = lawHeadSecondDAO_hi.selectList(null);
        for (Law_Head_second_HI entity : list) {
            String title = entity.getTitle();
            entity.setTitle(title.trim());
            lawHeadSecondDAO_hi. updateById(entity);
          /*  if (title.contains("....")) {
                title = title.substring(0,title.indexOf("...."));
//                String[] tits = title.split("....");
//                title = tits[0].trim();
                entity.setTitle(title);
                lawHeadSecondDAO_hi.updateById(entity);
            }*/
        }

    }

}
