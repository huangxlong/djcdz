package com.djc.djcdz.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;


import com.djc.djcdz.R;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/4/28.
 */
public class InputValidUtils {
    //手机号码
    public static boolean isMobilePhoneValid(String phone) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        return p.matcher(formatPhoneNumber(phone)).matches();
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() == 0) {
            return phoneNumber;
        }

        String phone = phoneNumber;
        if (phoneNumber.startsWith("+86")) {
            phone = phoneNumber.substring(3, phoneNumber.length());
        }

        StringBuffer strBufNumber = new StringBuffer(phone.length());
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (c == ' ' || c == '-' || c == ')' || c == '(' || c == '_') {
                continue;
            }
            strBufNumber.append(c);
        }
        return strBufNumber.toString();
    }

    public static Set<String> sZoneCodes = null;

    public static boolean isZone(Context context, String code) {
        if (sZoneCodes == null) {
            String[] codes = context
                    .getResources().getStringArray(R.array.zone_code);
            int size = codes.length;
            sZoneCodes = new HashSet<String>(size);
            for (int i = 0; i < size; i++) {
                sZoneCodes.add(codes[i]);
            }

        }

        return sZoneCodes.contains(code);
    }

    public static boolean isTelephoneValid(String phone) {
        Pattern p = Pattern.compile("^(0[0-9]{2,3})(\\s\\-\\s)?([2-9][0-9]{6,7})$");
        return p.matcher(phone).matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            return false;
        }
        boolean valid = isMobilePhoneValid(phone);

        if (!valid) {
            Pattern p = Pattern.compile("^(0[0-9]{2,3})([2-9][0-9]{6,7})$");
            valid = p.matcher(phone).matches();
        }

        return valid;
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidQQ(String qq) {
        Pattern p = Pattern.compile("^[0-9]{5,12}$");
        return p.matcher(qq).matches();
    }

    public static boolean hasChina(String name) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        return p.matcher(name).find();
    }

    public static boolean isChinaName(String name) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]{2,10}$");
        return p.matcher(name).matches();
    }

    /*
     * 身份证15位编码规则：dddddd yymmdd xx p
     * dddddd：6位地区编码
     * yymmdd: 出生年(两位年)月日，如：910215
     * xx: 顺序编码，系统产生，无法确定
     * p: 性别，奇数为男，偶数为女
     *
     * 身份证18位编码规则：dddddd yyyymmdd xxx y
     * dddddd：6位地区编码
     * yyyymmdd: 出生年(四位年)月日，如：19910215
     * xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
     * y: 校验码，该位数值可通过前17位计算获得
     *
     * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
     * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
     * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
     * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
     * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
    */
    public static boolean isIDCardValid(String idCard) {
        boolean valid = false;
        Pattern p = Pattern.compile("(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)" +
                "|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)");

        valid = p.matcher(formatPhoneNumber(idCard)).matches();

        if (valid) {
            if (idCard.length() == 18) {
                int[] idCardWi = new int[]{
                        7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                int[] idCardY = new int[]{
                        1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2
                };
                int idCardWiSum = 0;
                for (int i = 0; i < 17; i++) {
                    idCardWiSum += Integer.parseInt(
                            idCard.substring(i, i + 1)) * idCardWi[i];
                }

                int idCardMod = idCardWiSum % 11;
                String idCardLast = idCard.substring(17);
                if (idCardMod == 2) {
                    if (idCardLast.equalsIgnoreCase("x")) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                } else {
                    int last = Integer.parseInt(idCard.substring(17));
                    if (last == idCardY[idCardMod]) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                }
            }
        }

        return valid;
    }


    public static boolean isKeyWordValid(String keyWord, int minLen, int maxLen) {
        Pattern p = Pattern.compile("^\\w{"
                + minLen + "," + maxLen + "}$");
        return p.matcher(formatPhoneNumber(keyWord)).matches();
    }

    public static boolean isNumber(String keyWord, int len) {
        Pattern p = Pattern.compile("^[0-9]{"
                + len + "}$");
        return p.matcher(formatPhoneNumber(keyWord)).matches();
    }

    public static boolean isNumberLimit(String keyWord, int min, int max) {
        Pattern p = Pattern.compile("^[0-9]{"
                + min + "," + max + "}$");
        return p.matcher(formatPhoneNumber(keyWord)).matches();
    }


    public static boolean isBankCardVaild(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 判别是否包含Emoji表情
     *
     * @param str
     * @return
     */
    public static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 验证str是否为正确的车牌号
     *
     * @param no
     * @return
     */
    public static boolean isPlateNo(String no) {
        if (no == null || no.equals("")) {
            return false;
        }
        Pattern p = Pattern.compile("(^[\\u4e00-\\u9fa5]{1}[a-z_A-Z]{1}[a-z_A-Z_0-9]{5}$)");
        return p.matcher(no).find();
    }


    /**
     * 正则表达式验证密码
     * @param input
     * @return
     */
    public static boolean checkPassword(String input) {
        // 6-20 位，字母、数字、字符
        //String reg = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
        String regStr = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,20}$";
        return input.matches(regStr);
    }
}
