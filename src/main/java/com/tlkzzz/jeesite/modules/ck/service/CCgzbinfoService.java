/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.ck.service;

import java.util.Date;
import java.util.List;

import com.tlkzzz.jeesite.common.utils.StringUtils;
import com.tlkzzz.jeesite.modules.ck.dao.*;
import com.tlkzzz.jeesite.modules.ck.entity.*;
import com.tlkzzz.jeesite.modules.sys.utils.ToolsUtils;
import com.tlkzzz.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tlkzzz.jeesite.common.persistence.Page;
import com.tlkzzz.jeesite.common.service.CrudService;

/**
 * 采购订单Service
 * @author xrc
 * @version 2017-03-17
 */
@Service
@Transactional(readOnly = true)
public class CCgzbinfoService extends CrudService<CCgzbinfoDao, CCgzbinfo> {

	@Autowired
	private CHgoodsService cHgoodsService;
	@Autowired
	private CHouseDao cHouseDao;
	@Autowired
	private CGoodsDao cGoodsDao;
	@Autowired
	private CRkckddinfoDao cRkckddinfoDao;
	@Autowired
	private CDdinfoDao cDdinfoDao;

	public CCgzbinfo get(String id) {
		return super.get(id);
	}
	
	public List<CCgzbinfo> findList(CCgzbinfo cCgzbinfo) {
		return super.findList(cCgzbinfo);
	}
	
	public Page<CCgzbinfo> findPage(Page<CCgzbinfo> page, CCgzbinfo cCgzbinfo) {
		page = super.findPage(page, cCgzbinfo);
		for(CCgzbinfo cc:page.getList()){
			String[] unit = {cc.getGoods().getBig().getName(),cc.getGoods().getZong().getName(),cc.getGoods().getSmall().getName()};
			cc.setNub(ToolsUtils.unitTools(cc.getGoods().getSpec().getName(), unit, Integer.parseInt(cc.getNub())));
			if(StringUtils.isNotBlank(cc.getRknub()))
				cc.setRknub(ToolsUtils.unitTools(cc.getGoods().getSpec().getName(), unit, Integer.parseInt(cc.getRknub())));
		}
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(CCgzbinfo cCgzbinfo) {
		Date date = new Date();
		CGoods goods = cGoodsDao.get(cCgzbinfo.getGoods());
		/**	生成并保存采购统计订单 **/
		cCgzbinfo.setRknub(cCgzbinfo.getNub());
		cCgzbinfo.setRkDate(date);
		cCgzbinfo.setState("2");
		super.save(cCgzbinfo);
		/**	生成并保存入库总订单	**/
		CRkckddinfo cRkckddinfo = new CRkckddinfo();
		cRkckddinfo.preInsert();
		cRkckddinfo.setDdbh("Z"+date.getTime());
		cRkckddinfo.setLx("0");//入库
		cRkckddinfo.setState("1");//其他入库
		double sumMoney = Integer.parseInt(cCgzbinfo.getNub())*Double.parseDouble(cCgzbinfo.getJg());
		cRkckddinfo.setJe(String.valueOf(sumMoney));
		cRkckddinfo.setIssp("1");//已审批
		cRkckddinfo.setSpr(UserUtils.getUser().getId());
		cRkckddinfo.setSpsj(date);
		cRkckddinfoDao.insert(cRkckddinfo);
		/**	生成并保存入库子订单	**/
		CDdinfo cDdinfo = new CDdinfo();
		cDdinfo.preInsert();
		cDdinfo.setRkckddinfo(cRkckddinfo);
		cDdinfo.setCgzbinfo(cCgzbinfo);
		cDdinfo.setJe(String.valueOf(sumMoney));
		cDdinfo.setGoods(cCgzbinfo.getGoods());
		cDdinfo.setHouse(cCgzbinfo.getHouse());
		cDdinfo.setSupplier(cCgzbinfo.getRkinfo().getSupplier());
		cDdinfo.setRkckdate(date);
		cDdinfo.setDdbh("P"+date.getTime());
		cDdinfo.setNub(cCgzbinfo.getNub());
		cDdinfo.setRkqcbj(goods.getCbj());
		cDdinfo.setRksjcbj(cCgzbinfo.getJg());
		cDdinfoDao.insert(cDdinfo);
		/**	生成库存对象并添加库存	**/
		CHgoods cHgoods = new CHgoods();
		cHgoods.setRkState("0");
		cHgoods.setGoods(cCgzbinfo.getGoods());
		cHgoods.setHouse(cCgzbinfo.getHouse());
		cHgoods.setNub(cCgzbinfo.getNub());
		cHgoods.setCkState(cCgzbinfo.getId());
		cHgoods.setCbj(Double.parseDouble(cCgzbinfo.getJg()));
		cHgoods.setSupplierid(cCgzbinfo.getRkinfo().getSupplier().getId());
		cHgoods.setRemarks(cCgzbinfo.getRemarks());
		cHgoodsService.save(cHgoods);
	}

	@Transactional(readOnly = false)
	public void saveInfo(CDdinfo cDdinfo) {//保存基本信息
		CCgzbinfo cCgzbinfo = dao.getZbByGoodsAndState(cDdinfo.getGoods().getId(),"0");
		if(cCgzbinfo!=null){//未采购总表存在
			int goodsNum = Integer.parseInt(cCgzbinfo.getNub())+Integer.parseInt(cDdinfo.getNub());
			cCgzbinfo.setNub(String.valueOf(goodsNum));
		}else {//总表订单不存在
			cCgzbinfo = new CCgzbinfo();
			cCgzbinfo.setGoods(cDdinfo.getGoods());
			cCgzbinfo.setNub(cDdinfo.getNub());
			cCgzbinfo.setState("0");
		}
		super.save(cCgzbinfo);
		cDdinfo.setCgzbinfo(cCgzbinfo);
		cDdinfoDao.updateCgzbInfo(cDdinfo);
	}

	@Transactional(readOnly = false)
	public void savePrice(CHgoods cHgoods){
		Date date = new Date();
		CCgzbinfo cCgzbinfo = dao.get(cHgoods.getCkState());//ckState字段值保存的采购订单ID
		cCgzbinfo.setRknub(cHgoods.getNub());
		cCgzbinfo.setRkDate(date);
		cCgzbinfo.setJg(String.valueOf(cHgoods.getCbj()));
		cCgzbinfo.setState("2");
		super.save(cCgzbinfo);
		/**	保存入库信息到子订单中	 **/
		CDdinfo cDdinfo = new CDdinfo();
		cDdinfo.setCgzbinfo(cCgzbinfo);
		List<CDdinfo> cdList = cDdinfoDao.findList(cDdinfo);
		CGoods goods = cGoodsDao.get(cHgoods.getGoods());
		double sumMoney = 0.0;
		for(CDdinfo cd: cdList){
			if(cDdinfo.getRkckddinfo()==null)//取得总订单ID
				cDdinfo.setRkckddinfo(cd.getRkckddinfo());
			cd.setRkckdate(date);
			cd.setRkqcbj(goods.getCbj());
			cd.setRksjcbj(String.valueOf(cHgoods.getCbj()));
			double money = Integer.parseInt(cd.getNub())*cHgoods.getCbj();
			cd.setJe(String.valueOf(money));
			sumMoney += money;
			cDdinfoDao.update(cd);
		}
		/**	保存入库信息到总订单中	**/
		cDdinfo.getRkckddinfo().setJe(String.valueOf(sumMoney));
		cRkckddinfoDao.updateJe(cDdinfo.getRkckddinfo());
	}

	@Transactional(readOnly = false)
	public void changeState(String id,String state){
		dao.changeState(id,state);
	}
	
	@Transactional(readOnly = false)
	public void delete(CCgzbinfo cCgzbinfo) {
		super.delete(cCgzbinfo);
	}
	
}