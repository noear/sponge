package org.noear.rock.impl;

import org.noear.solon.Solon;

public class App {
    public static void main(String[] args) {

        Solon.start(App.class, args, app -> {
            app.cfg().stopSafe(app.cfg().isFilesMode() == false);
        });
    }
}
