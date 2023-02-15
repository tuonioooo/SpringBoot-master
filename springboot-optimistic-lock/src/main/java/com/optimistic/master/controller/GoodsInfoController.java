package com.optimistic.master.controller;

import com.optimistic.master.service.GoodsInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoodsInfoController{

	private final GoodsInfoService goodsInfoService;


}
