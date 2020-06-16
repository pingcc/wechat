package com.ping.wechat.model.entity.readonly;

/**
 * @author CPing
 * @date 2020/5/24 13:55
 */
public class Law_Head_Third_HI_RO {

    public static final String QUERY ="\tSELECT\n" +
            "\tid\n" +
            "FROM template_copy1\n" +
            "\tWHERE \n" +
            "\t(SELECT title  FROM law_head_second WHERE id= #{id} )\n" +
            "\t= title ";
    public static final String QUERY1 ="\tSELECT\n" +
            "\tid\n" +
            "FROM template_copy1\n" +
            "\tWHERE \n" +
            "\t(SELECT title  FROM law_head_second WHERE id= #{id} )\n" +
            "\tLIKE CONCAT(title ,'%') ORDER BY title desc limit 1";





    public static final String QUERY_LAW_HEAD_SECOND_IDS ="SELECT id  ,title as content  FROM law_head_second";

    public static final String query_third_query ="SELECT id ,title as content FROM template_copy1 WHERE id >#{startId} and id <#{endId}";

    public static final String query_third_query_end ="SELECT id ,title as content FROM template_copy1 WHERE id >#{startId}";


    public static final String QUERY_LAW_HEAD="SELECT head_id as id FROM law_head_second WHERE id=#{id}";
    //    public static final String QUERY_LAW_HEAD_SECOND_IDS ="SELECT id FROM law_head_second where ";
    private Integer id;//自增id
    private Integer headId;//大标题id

    public Integer getHeadId() {
        return headId;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }

    private Integer secondHeadId;//自增id
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSecondHeadId() {
        return secondHeadId;
    }

    public void setSecondHeadId(Integer secondHeadId) {
        this.secondHeadId = secondHeadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
