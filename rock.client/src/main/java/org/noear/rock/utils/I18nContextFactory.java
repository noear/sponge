package org.noear.rock.utils;

import org.noear.water.utils.TaskUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/9/8 created
 */
public class I18nContextFactory {
    Map<String, CodeContext> codeLib = new HashMap<>();
    Map<String, NameContext> nameLib = new HashMap<>();

    private static final I18nContextFactory instance = new I18nContextFactory();

    private I18nContextFactory() {
        TaskUtils.run(30 * 1000, 30 * 1000, this::update);
    }

    private void update() throws SQLException {
        for (CodeContext c : codeLib.values()) {
            c.update();
        }

        for (NameContext c : nameLib.values()) {
            c.update();
        }
    }


    /**
     * 获取代码国际化上下文
     */
    public static CodeContext getCodeContext(String service) {
        CodeContext tmp = instance.codeLib.get(service);
        if (tmp == null) {
            synchronized (service.intern()) {
                tmp = instance.codeLib.get(service);
                if (tmp == null) {
                    tmp = new CodeContext(service);
                    instance.codeLib.put(service, tmp);
                }
            }
        }

        return tmp;
    }

    /**
     * 获取名字国际化上下文
     */
    public static NameContext getNameContext(String bundleName) {
        NameContext tmp = instance.nameLib.get(bundleName);
        if (tmp == null) {
            synchronized (bundleName.intern()) {
                tmp = instance.nameLib.get(bundleName);
                if (tmp == null) {
                    tmp = new NameContext(bundleName);
                    instance.nameLib.put(bundleName, tmp);
                }
            }
        }

        return tmp;
    }
}
