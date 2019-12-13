package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.bean.PmsProductInfo;

import java.util.List;

public interface SpuInfoService {
    List<PmsProductInfo> spuInfoList(String catalog3Id);


    List<PmsBaseSaleAttr> saleAttrList();


    String saveSpuInfo(PmsProductInfo productInfo);
}
