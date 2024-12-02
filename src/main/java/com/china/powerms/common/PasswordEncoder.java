package com.china.powerms.common;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    /**
     * 加密强度，默认为10
     */
    private static final int ROUNDS = 10;

    /**
     * 加密密码
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(ROUNDS));
    }

    /**
     * 验证密码
     * @param password 原始密码
     * @param hashedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String password, String hashedPassword) {
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
