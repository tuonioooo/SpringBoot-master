package com.optimistic.master.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <p>  </p>
 *
 * @author daizhao 2023/02/14 22:25
 */
@Table(name = "goods_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoEntity {
    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 订单编号 */
    @Column(name = "code")
    private String code;

    /** 库存数量 */
    @Column(name = "amount")
    private Integer amount;

    /** 版本号 */
    @Column(name = "version")
    private Integer version;

}
