package com.hzih.mmcs.utils.constant;

/**
 * 程序常量、静态变量
 * 
 * @author xiangqi
 * 
 */
public class ConstantUtils {
	/** * */
	public final static int PAGERESULT_PAGE_LENGTH = 15;

	/** 日志等级 * */
	public final static String SYSLOG_LEVEL_ERROR = "ERROR";
	public final static String SYSLOG_LEVEL_WARN = "WARN";
	public final static String SYSLOG_LEVEL_INFO = "INFO";
	public final static String SYSLOG_LEVEL_DEBUG = "DEBUG";

	/** 报警设置xml * */
	public final static String XML_ALERT_CONFIG_PATH = "pages/data/alert-config.xml";

	/** 数据库配置 */
	public final static String XML_DB_CONFIG_PATH = "pages/data/db-config.xml";

	/** 审计备份策略 */
	public static final String BACKUP_CONFIG_PATH = "pages/data/backup-config.xml";

    /** 数据库版本对应驱动 */
	public static final String DATABASE_VERSION_CONFIG_PATH = "pages/data/driver-config.xml";

	public static final String SAFEPOLICY_SERVICE = "safePolicyService";

    public static final String InitRoleType_0 = "0";
    public static final String InitRoleType_1 = "1";
    public static final String InitRoleType_2 = "2";

    public static final String LocalHostUrl = "http://localhost:8000/mmcs/LoginAction_login.action";

    public static final String UPLOADPATH = "F:/data/uploadfiles/";
}
