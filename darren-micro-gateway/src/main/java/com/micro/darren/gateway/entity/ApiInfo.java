package com.micro.darren.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 编码 */
    private Long id;

    /** 接口方法名称，ws接口需要认证的字段 */
    private String name;

    /** 访问接口URL(不包括域名前缀) */
    private String url;

    /** ws描叙链接 */
    private String wsdl;

    /** 接口级别 */
    private Integer level;

    /** 接口总请求量，包括有效和无效的请求，一天更新一次 */
    private Long requestTotal;

    /** 接口请求方法 */
    private String requestMethod;

    /** 创建者 */
    private Long createId;

    /** 创建时间 */
    private Date createTime;

    /** 最后修改时间 */
    private Date updateTime;

    /** 状态 1 正常, 0 停用 */
    private Integer status;

    /** 接口描叙 */
    private String description;

}