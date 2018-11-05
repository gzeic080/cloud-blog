package cn.xiaojunyun.cloud.blog.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.Map.Entry;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: 类描述
 *
 * @author junyunxiao
 * @date 2018-9-12 16:12
 */
public class DataUtils {
    private static ResourceBundle resourceBundle = null;
    private static Map<String, String> platformPropertyData = new HashMap<String, String>();

    public static Map<String, Object> convertToMap(Object parameter) {
        if (parameter != null) {
            if (parameter instanceof Map) {
                return (Map) parameter;
            }

            try {
                return PropertyUtils.describe(parameter);
            } catch (Exception arg1) {
                System.out.println("DataUtils.convertToMap错误：" + arg1.getMessage());
            }
        }

        return new HashMap();
    }

}
