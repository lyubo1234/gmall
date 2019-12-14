package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.manage.mapper.AttrInfoMapper;
import com.atguigu.gmall.manage.mapper.AttrValueMapper;
import com.atguigu.gmall.manage.mapper.BaseSaleAttrMapper;
import com.atguigu.gmall.service.AttrService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    AttrInfoMapper attrInfoMapper;
    @Autowired
    AttrValueMapper attrValueMapper;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    //查询平台属性
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo attrInfo = new PmsBaseAttrInfo();
        attrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> baseAttrInfoList = attrInfoMapper.select(attrInfo);

        for (PmsBaseAttrInfo pmsBaseAttrInfo : baseAttrInfoList) {

            List<PmsBaseAttrValue> pmsBaseAttrValueList=new ArrayList<>();

            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();

            pmsBaseAttrValue.setAttrId(attrInfo.getId());

            pmsBaseAttrValueList=attrValueMapper.select(pmsBaseAttrValue);

            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValueList);
        }

        return baseAttrInfoList;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if (StringUtils.isBlank(id)) {
            // id为空，保存
            // 保存属性
            attrInfoMapper.insertSelective(pmsBaseAttrInfo);//insert insertSelective 是否将null插入数据库

            // 保存属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());

                attrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        } else {
            // id不空，修改

            // 属性修改
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
            attrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo, example);


            // 属性值修改
            // 按照属性id删除所有属性值
            PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
            pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
            attrValueMapper.deleteByPrimaryKey(pmsBaseAttrValueDel);

            // 删除后，将新的属性值插入
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insertSelective(pmsBaseAttrValue);
            }

        }


        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> attrValueList(String attrId) {

        PmsBaseAttrValue baseAttrValue = new PmsBaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return attrValueMapper.select(baseAttrValue);
    }

    @Override
    public List<PmsBaseSaleAttr> findBaseSaleAttr() {
        return baseSaleAttrMapper.selectAll();
    }
}
