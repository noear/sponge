package webapp.dso;

import webapp.Config;

/**
 * 2018.07.26
 *
 * @author konar
 */
public class NameBuildUtil {

    public static String buildPushTagName(String tag) {
        return tag + Config.push_suffix;
    }

    public static String buildRubberName(String tag, String name) {
        return buildPushTagName(tag) + "/" + name;
    }

}
