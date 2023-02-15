package com.optimistic.master.mapper;

import com.optimistic.master.entity.GoodsInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
* <p> Mapper接口 </p>
*
* @author daizhao 2023/02/14 22:25
*/
public interface GoodsInfoMapper extends Mapper<GoodsInfoEntity>, MySqlMapper<GoodsInfoEntity> {


    @Select(" select * from goods_info where code = #{code}")
    GoodsInfoEntity selectByCode(@Param("code") String code);

    /**
     * 基于数据库乐观锁的状态实现并发控制
     * @param code  订单编号
     * @param buys  购买数量
     * @return
     */
    @Update(" update goods_info set amount = amount - #{buys} where code = #{code} and amount - #{buys} >=0 ")
    int updateByStatus(@Param("code") String code, @Param("buys") Integer buys);

    /**
     * 基于数据库乐观锁的版本号实现并发控制
     * @param code  订单编号
     * @param buys  购买数量
     * @return
     */
    @Update(" update goods_info set amount = amount - #{buys}, version = version + 1 where code = #{code} and version = #{version} ")
    int updateByVersion(@Param("code") String code, @Param("buys") Integer buys, @Param("version") Integer version);

    /**
     * 更新库存数量
     * @param code  订单编号
     * @param buys  购买数量
     * @return
     */
    @Update(" update goods_info set amount = amount - #{buys} where code = #{code}")
    int updateAmountByProvider(@Param("code") String code, @Param("buys") Integer buys);

}
