package com.optimistic.master.redisson.constant;

/**
 * <p> redis 对应的key 字符串常量</p>
 *
 * @author daizhao  2019-05-22 15:13
 */
public class RedisConstants {

    /**
     * 采集分页列表KEY redis数据类型HASH
     * format:
     *      key NOVEL_PAGE_LIST_KEY:规则Id
     *      value HASH
     */
    public final static String NOVEL_PAGE_LIST_KEY = "NOVEL_PAGE_LIST_KEY:%s";

    /**
     * 采集目录列表KEY redis数据类型HASH
     * format:
     *      key NOVEL_DIR_LIST_KEY:规则Id:小说书号
     *      value HASH
     */
    public final static String NOVEL_DIR_LIST_KEY = "NOVEL_DIR_LIST_KEY:%s:%s";
    /**
     * 采集小说基本信息KEY redis数据类型HASH
     * format:
     *      key NOVEL_INFO_KEY:规则Id
     *      value HASH
     */
    public final static String NOVEL_INFO_KEY = "NOVEL_INFO_KEY:%s";

    /**
     * 采集批次Key
     * format:
     *      key NOVEL_CRAWL_BATCH_KEY:批次uuid/jobGroup的uuid
     *      value HASH
     */
    public final static String NOVEL_CRAWL_BATCH_KEY = "NOVEL_CRAWL_BATCH_KEY:%s";


    public final static String NOVEL_SEARCH_KEY = "NOVEL_SEARCH_KEY";


    public final static String NOVEL_RULE_REDIS_MODEL_KEY = "NOVEL_RULE_REDIS_MODEL_KEY";


    public final static String NOVEL_SITE_REDIS_MODEL_KEY = "NOVEL_SITE_REDIS_MODEL_KEY";



    /**
     * 书源小说基本信息KEY redis数据类型HASH
     * format:
     *      key SOURCE_BOOK_INFO_KEY:规则Id
     *      value HASH
     */
    public final static String SOURCE_BOOK_INFO_KEY = "SOURCE_BOOK_INFO_KEY:%s";

    /**
     * 系统信息对象Key
     */
    public final static String OSI_SYSTEM_KEY = "OSI_SYSTEM_KEY";

    /**
     * 视频平台登录cookie信息Key
     */
    public final static String MEDIA_COOKIE_KEY = "MEDIA_COOKIE_KEY";

    /**
     * 点赞评论key
     */
    public static final String SKR_COMMENT_MAP_KEY = "SKR_COMMENT_MAP_KEY";

    /**
     * 点赞评论数量key
     */
    public static final String SKR_COMMENT_COUNT_MAP_KEY = "SKR_COMMENT_COUNT_MAP_KEY";

    /**
     * 点赞资源key
     */
    public static final String SKR_RESOURCE_MAP_KEY = "SKR_RESOURCE_MAP_KEY";

    /**
     * 点赞资源数量key
     */
    public static final String SKR_RESOURCE_COUNT_MAP_KEY = "SKR_RESOURCE_COUNT_MAP_KEY";
}

