package com.ping.wechat.util;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import freemarker.template.Configuration;

public class FreemarkerTemplateUtil {

    //配置信息,代码本身写的还是很可读的,就不过多注解了
    private static Configuration configuration = null;

    public FreemarkerTemplateUtil(Class c,String templatePath) {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setEncoding(Locale.SIMPLIFIED_CHINESE,"UTF-8");
        try {
            configuration.setClassForTemplateLoading(c,templatePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FreemarkerTemplateUtil(String templateFolder) {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setEncoding(Locale.SIMPLIFIED_CHINESE,"UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FreemarkerTemplateUtil() {
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        FreemarkerTemplateUtil.configuration = configuration;
    }
}
