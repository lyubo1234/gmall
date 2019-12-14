package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuInfoService {
    List<PmsProductInfo> spuInfoList(String catalog3Id);


    String saveSpuInfo(PmsProductInfo productInfo);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
}
