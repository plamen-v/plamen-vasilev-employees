package org.task.utils;

public class NumberUtils {
    public static long parseId(Object obj) throws Exception {
        long id = Long.parseLong(((String) obj).trim());
        if(id <= 0){
            throw new Exception();
        }
        return id;
    }
}
