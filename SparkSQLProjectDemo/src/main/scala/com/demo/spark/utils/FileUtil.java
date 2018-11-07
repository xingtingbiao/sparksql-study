package com.demo.spark.utils;

import java.io.*;

public class FileUtil {
    private static final String UTF_8 = "UTF-8";
    private static final String Unicode = "Unicode";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String ANSI_ASCII = "ANSI|ASCII";
    private static final String GBK = "GBK";

    public FileUtil() {
    }

    public static String codeString(InputStream inputStream) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(inputStream);
        int p = (bin.read() << 8) + bin.read();
        String code;
        switch(p) {
            case 23669:
                code = "ANSI|ASCII";
                break;
            case 61371:
                code = "UTF-8";
                break;
            case 65279:
                code = "UTF-16BE";
                break;
            case 65534:
                code = "Unicode";
                break;
            default:
                code = "GBK";
        }

        return code;
    }
}
