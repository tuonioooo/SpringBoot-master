package com.optimistic.master.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 数据请求封装 </p>
 *
 * @author daizhao 2023/02/14 22:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoRequest {

    /**
     *
     */
    private Integer id;


    /**
     *
     */
    private String code;


    /**
     *
     */
    private Integer amount;


    /**
     *
     */
    private Integer version;

    /**
     * 操作人Id
     */
    private Integer opId;

    /**
     * 操作人姓名
     */
    private String opName;

}
