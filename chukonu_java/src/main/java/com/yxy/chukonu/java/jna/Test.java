package com.yxy.chukonu.java.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Test {
    public interface CLibrary extends Library {
        // 调用linux下面的so文件,注意，这里只要写test就可以了，不要写libtest，也不要加后缀
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("lib", CLibrary.class);

        String recog(String line, String encoding) ;
    }

    public static void main(String[] args) {
        System.out.println(CLibrary.INSTANCE.recog("test_word", "utf-8"));
    }
}
