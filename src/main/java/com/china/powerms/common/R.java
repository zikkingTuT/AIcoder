package com.china.powerms.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private Integer code; // 状态码
    private String msg;   // 消息
    private boolean success;
    private T data;      // 数据

    // 成功静态方法
    public static <T> R<T> ok() {
        return new R<>(200, "操作成功",true, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(200, "操作成功",true, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(200, msg, true,data);
    }

    // 失败静态方法
    public static <T> R<T> failed() {
        return new R<>(200, "操作失败",false, null);
    }

    public static <T> R<T> failed(String msg) {
        return new R<>(200, msg, false,null);
    }

    public static <T> R<T> failed(Integer code, String msg) {
        return new R<>(code, msg,false ,null);
    }

}
