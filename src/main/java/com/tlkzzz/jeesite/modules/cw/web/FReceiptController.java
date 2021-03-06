/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.cw.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tlkzzz.jeesite.common.config.Global;
import com.tlkzzz.jeesite.common.persistence.Page;
import com.tlkzzz.jeesite.common.web.BaseController;
import com.tlkzzz.jeesite.common.utils.StringUtils;
import com.tlkzzz.jeesite.modules.cw.entity.FReceipt;
import com.tlkzzz.jeesite.modules.cw.service.FReceiptService;

/**
 * 收款Controller
 * @author xrc
 * @version 2017-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cw/fReceipt")
public class FReceiptController extends BaseController {

	@Autowired
	private FReceiptService fReceiptService;
	
	@ModelAttribute
	public FReceipt get(@RequestParam(required=false) String id) {
		FReceipt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fReceiptService.get(id);
		}
		if (entity == null){
			entity = new FReceipt();
		}
		return entity;
	}
	
	@RequiresPermissions("cw:fReceipt:view")
	@RequestMapping(value = {"list", ""})
	public String list(FReceipt fReceipt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FReceipt> page = fReceiptService.findPage(new Page<FReceipt>(request, response), fReceipt); 
		model.addAttribute("page", page);
		return "modules/cw/fReceiptList";
	}

	@RequiresPermissions("cw:fReceipt:view")
	@RequestMapping(value = "form")
	public String form(FReceipt fReceipt, Model model) {
		model.addAttribute("fReceipt", fReceipt);
		return "modules/cw/fReceiptForm";
	}

	@RequiresPermissions("cw:fReceipt:edit")
	@RequestMapping(value = "save")
	public String save(FReceipt fReceipt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fReceipt)){
			return form(fReceipt, model);
		}
		fReceiptService.save(fReceipt);
		addMessage(redirectAttributes, "保存收款成功");
		return "redirect:"+Global.getAdminPath()+"/cw/fReceipt/?repage";
	}
	
	@RequiresPermissions("cw:fReceipt:edit")
	@RequestMapping(value = "delete")
	public String delete(FReceipt fReceipt, RedirectAttributes redirectAttributes) {
		fReceiptService.delete(fReceipt);
		addMessage(redirectAttributes, "删除收款成功");
		return "redirect:"+Global.getAdminPath()+"/cw/fReceipt/?repage";
	}

}