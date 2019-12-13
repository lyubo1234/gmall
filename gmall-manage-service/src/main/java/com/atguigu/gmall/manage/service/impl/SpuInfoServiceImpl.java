package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.manage.mapper.SaleAttrMapper;
import com.atguigu.gmall.manage.mapper.SpuAttrMapper;
import com.atguigu.gmall.manage.mapper.SpuInfoMapper;
import com.atguigu.gmall.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SpuInfoServiceImpl implements SpuInfoService {
    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuAttrMapper spuAttrMapper;
    @Autowired
    SaleAttrMapper saleAttrMapper;
    @Override
    public List<PmsProductInfo> spuInfoList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(pmsProductInfo);
    }

    @Override
    public List<PmsBaseSaleAttr> saleAttrList() {

        return saleAttrMapper.selectAll();
    }

//    保存
    @Override
    public String saveSpuInfo(PmsProductInfo productInfo) {
        spuInfoMapper.insertSelective(productInfo);

        List<PmsProductSaleAttr> spuSaleAttrList = productInfo.getSpuSaleAttrList();

        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {

            spuAttrMapper.insertSelective(pmsProductSaleAttr);
        }
        return "success";
    }


}
