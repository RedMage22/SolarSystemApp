package com.redmage.util;

import java.io.File;

public class Resource {
    public static String get(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file != null) {
                // Log file found!
                String urlStr = file.toURI().toString();
                return urlStr;
            }
        }
        // Log url not found!
        return null;
    }
}
