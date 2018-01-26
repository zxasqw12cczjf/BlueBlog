package com.mrlonely.web.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.mrlonely.common.util.MenuUtils;
import com.mrlonely.web.system.entity.SysMenu;
import com.mrlonely.web.system.entity.SysUser;
import com.mrlonely.web.system.service.SysMenuService;
import com.mrlonely.web.system.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrlonely.common.base.BaseController;
import com.mrlonely.common.constant.SessionAttr;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	private Logger logger = Logger.getLogger(IndexController.class);

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysMenuService sysMenuService;
	
	@RequestMapping("")
	public String login(String theme,HttpServletRequest request){

//		String userMenu = (String)request.getSession().getAttribute(SessionAttr.USER_MENUS.getValue());
//			HttpSession session = request.getSession();
//			List<String> userMenus = sysUserService.findMenuIdByUserId(u.getId());
//			List<SysMenu> menuList = sysMenuService.getByParentId("0");
//			session.setAttribute(SessionAttr.USER_MENUS.getValue(), MenuUtils.getMenu(menuList, userMenus));


		SysUser u = (SysUser)SecurityUtils.getSubject().getPrincipal();
		List<String> userMenus = sysUserService.findMenuIdByUserId(u.getId());
		List<SysMenu> menuList = sysMenuService.getByParentId("0");
		String userMenu = MenuUtils.getMenu(menuList, userMenus);
		request.setAttribute("menus", userMenu);
		
		logger.info("login success");
		
		if(StringUtils.isNotEmpty(theme)){
			return "main/theme"+theme;
		}
		
		return "main/theme";
	}
	
	
	@RequestMapping("/icon")
	public String icon(HttpServletRequest request){
		
		return "system/icon/icon";
	}
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request){
		
		return "system/home/home";
	}
}
