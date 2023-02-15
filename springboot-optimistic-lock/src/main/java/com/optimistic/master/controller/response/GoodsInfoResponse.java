package com.optimistic.master.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 数据返回封装 </p>
 *
 * @author daizhao 2023/02/14 22:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoResponse {
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
}
