/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
