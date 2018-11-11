package com.master.dao;

import com.master.bean.TGoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tuonioooo
 * @Date  2018-11-05
 * @info  基础dao接口
 */

public interface TGoodsInfoRepository extends JpaRepository<TGoodsInfo,  Integer> {


    public TGoodsInfo findByCode(String code);

    public TGoodsInfo findById(int id);
}

