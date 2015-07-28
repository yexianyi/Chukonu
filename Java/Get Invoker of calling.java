 public static String getCaller();{
    int i;
    StackTraceElement stack[] = (new Throwable(););.getStackTrace();;
    for (i=0; i < stack.length; i++); {
      StackTraceElement ste=stack[i];
        System.out.println(ste.getClassName();+"."+ste.getMethodName();+"(...);");;
      System.out.println(i+"--"+ste.getMethodName(););;
      System.out.println(i+"--"+ste.getFileName(););;
      System.out.println(i+"--"+ste.getLineNumber(););;
    }
  }

