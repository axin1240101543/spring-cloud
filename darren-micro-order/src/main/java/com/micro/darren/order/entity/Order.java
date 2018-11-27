package com.micro.darren.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 订单id */
    private String orderId;

    /** 客户id */
    private Integer uid;

    /** 收货地址 */
    private Integer shopAddrId;

    /**  */
    private Date createTime;

    /** 状态 录入0-审核5-用户确认10-调剂15-备药完成 17-配送20-用户收货结果25 -审核失败98-用户放弃99;97用户拒收 */
    private Short status;

    /** 1在线支付,2货到付款 */
    private Short payStatus;

    /** 0 无图片 1有图片 */
    private Short recipePicStatus;

    /** 录入人员id */
    private Integer entryId;

    /** 审核人员id */
    private Integer auditId;

    /** 调剂人员id */
    private Integer adjustId;

    /** 配送人员id */
    private Integer distributionId;

    /** 送货时间 */
    private Date endDate;

    /** 订单重量 */
    private String orderWeight;

    /** 件数 */
    private String orderNumber;

    /** 物流费用 */
    private BigDecimal logisticsCost;

    /** 订单来源标示 */
    private Integer sourceId;

    /** 订单生成时间 */
    private String orderTime;

    /** 诊疗卡 */
    private String treatCard;

    /** 挂单号 */
    private String regNum;

    /** 订单加急状态 1加急，0非加急 */
    private Short urgentFlag;

    /** 对于非app注册用户,存储收货地址 */
    private String addrStr;

    /** 省份 */
    private String provinces;

    /** 城市 */
    private String city;

    /** 区 */
    private String zone;

    /** 收货人 */
    private String consignee;

    /** 收货人姓名 */
    private String conTel;

    /** 投递站信息 */
    private String deliveryRemark;

    /** 物流机构编号 */
    private String companyNum;

    /** 送货时间 */
    private String sendGoodsTime;

    /** 运单号 */
    private String wayBillNo;

    /** 0未上传 1上传成功 2上传失败 */
    private Short logisticStatus;

    /** 默认0,0药材消耗为统计,1药材消耗已经统计 */
    private Short consumeFlag;

    /** 10000 广州 10001 深圳 */
    private Integer storagetype;

    /** 0 未知(兼容以前已经接入的医院) ,1 送医院,2 送病人家里 */
    private Integer isHosAddr;

    /** 回调url(第三方平台使用) */
    private String callbackUrl;

    /** 最新修改时间:解决同步出现的问题 */
    private Date modifyTime;

    /** 原因 */
    private String reason;

    /** 班次：1早 2午 3晚 */
    private Integer classes;

    /** 订单备注 */
    private String orderRemark;

    /** 是否省中住院服药 0：否     1：是 */
    private Short isSzzy;

    /** 用于推送物流公司名称及运单号 */
    private String logisUrlCallback;

    /** 客户签收状态 1:签收  0:未签收 */
    private Integer customSign;

    /** 第几层 */
    private Short layerNum;

    /** 买家留言 */
    private String message;

    /** 药柜编号 */
    private String medicineCabNum;

    /** 药柜类型 */
    private String machineType;

    /** 检查是否批量扫描过:0未扫描,1已扫描 */
    private Short scanBatchUpdate;



}