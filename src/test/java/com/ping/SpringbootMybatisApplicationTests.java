package com.ping;

import com.ping.wechat.model.dao.StudentMapper_HI;
import com.ping.wechat.model.entity.Student_HI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created  on 2019/9/18.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {



    @Autowired
   private StudentMapper_HI studentMapperHI; //使用idea此处会报错，但是不影响使用

    @Test
    public void test1() {       //测试查询方法
//        System.out.println(("----- selectAll method test ------"));
        List<Student_HI> list= studentMapperHI.selectList(null);

     list.forEach(item -> System.out.println(item.getId()+item.getName()+item.getTitle()));

        System.out.println(("----- selectAll method test ------"));
      /*  Student_HI student=studentMapperHI.selectById(1);
        System.out.println(student.getId()+student.getName()+student.getTitle());*/
    }


}
