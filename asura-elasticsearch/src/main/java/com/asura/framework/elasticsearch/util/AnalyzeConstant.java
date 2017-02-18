package com.asura.framework.elasticsearch.util;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR> 修改记录
 * <BR>-----------------------------------------------
 * <BR> 修改日期 修改人 修改内容
 * </PRE>
 *
 * @author jiangn18
 * @version 1.0
 * @since 1.0
 */
public class AnalyzeConstant {

    /**
     * ik分词枚举
     */
    public enum IKAnalyze {
        IK_MAX_WORD("ik_max_word", ""),
        IK_SMART("ik_smart", ""),
        IK("ik", "");


        private String code;
        private String status;

        IKAnalyze(String code, String status) {
            this.code = code;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public String getCode() {
            return code;
        }

        public static IKAnalyze get(String code) {
            if (code == null) return null;
            for (IKAnalyze type : IKAnalyze.values()) {
                if (code.equals(type.getCode())) return type;
            }
            return null;
        }
    }

}
