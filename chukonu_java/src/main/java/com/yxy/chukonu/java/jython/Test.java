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
package com.yxy.chukonu.java.jython;

import java.util.Properties;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class Test {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
        interp.exec("import sys");
//        interp.exec("sys.path.append('D:/Program Files (x86)/jython2.7.0/Lib')");//jython自己的
//        interp.exec("sys.path.append('D:/Program Files (x86)/jython2.7.0/Lib/site-packages')");//jython自己的
        PyFunction func = (PyFunction) interp.get("test.predict_mock()", PyFunction.class);
        int a = 2010, b = 2;
      PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
      System.out.println("anwser = " + pyobj.toString());
        
    }

}
