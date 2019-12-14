package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
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
    SpuAttrValueMapper spuAttrValueMapper;
    @Autowired
    SpuImagMapper spuImagMapper;
    @Autowired
    PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Override
    public List<PmsProductInfo> spuInfoList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(pmsProductInfo);
    }


    //    保存spu
    @Override
    public String saveSpuInfo(PmsProductInfo productInfo) {
//        保存商品信息
        spuInfoMapper.insertSelective(productInfo);
//        生成商品主键
        String productInfoId = productInfo.getId();

        List<PmsProductSaleAttr> spuSaleAttrList = productInfo.getSpuSaleAttrList();

//        保存图片
        List<PmsProductImage> spuImageList = productInfo.getSpuImageList();

        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(productInfoId);

            spuImagMapper.insertSelective(pmsProductImage);

        }

        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(productInfoId);

            spuAttrMapper.insertSelective(pmsProductSaleAttr);

//            保存销售属性值
            List<PmsProductSaleAttrValue> pmsProductSaleAttrList = pmsProductSaleAttr.getSpuSaleAttrValueList();

            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttrList) {
                pmsProductSaleAttrValue.setProductId(productInfoId);

                spuAttrValueMapper.insertSelective(pmsProductSaleAttrValue);


            }

        }
        return "success";
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {

        PmsProductImage pmsProductImage = new PmsProductImage();

        pmsProductImage.setProductId(spuId);

        return pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr productSaleAttr = new PmsProductSaleAttr();

        productSaleAttr.setProductId(spuId);

        List<PmsProductSaleAttr> productSaleAttrList = pmsProductSaleAttrMapper.select(productSaleAttr);


        for (PmsProductSaleAttr pmsProductSaleAttr : productSaleAttrList) {

            PmsProductSaleAttrValue productSaleAttrValue = new PmsProductSaleAttrValue();

            productSaleAttrValue.setProductId(spuId);

            productSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());

            List<PmsProductSaleAttrValue> productSaleAttrValues = spuAttrValueMapper.select(productSaleAttrValue);

            pmsProductSaleAttr.setSpuSaleAttrValueList(productSaleAttrValues);

        }

        return productSaleAttrList;
    }


}
