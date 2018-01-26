package com.mrlonely.web.system.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrlonely.common.base.BaseController;
import com.mrlonely.common.constant.SessionAttr;
import com.mrlonely.common.util.CacheUtil;
import com.mrlonely.common.util.DesUtil;
import com.mrlonely.common.util.MenuUtils;
import com.mrlonely.web.system.entity.SysMenu;
import com.mrlonely.web.system.entity.SysUser;
import com.mrlonely.web.system.service.SysMenuService;
import com.mrlonely.web.system.service.SysUserService;

import net.sf.json.JSONObject;

@Controller 
@RequestMapping("/login")
public class LoginController extends BaseController {

	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysMenuService sysMenuService;

	@RequestMapping("")
	public String toLogin(HttpServletRequest request){


		return "login/login";
	}
	/**
	 * 验证用户
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public String checkUser(SysUser user,HttpServletRequest request){
		JSONObject json = new JSONObject();
		
		try {
			
			SysUser u = sysUserService.findByUsername(user);
			
			if(u != null){
				
				HttpSession session = request.getSession();
				session.setAttribute(SessionAttr.USER_LOGIN.getValue(), u);
				List<String> userMenus = sysUserService.findMenuIdByUserId(u.getId());
				List<SysMenu> menuList = sysMenuService.getByParentId("0");
				session.setAttribute(SessionAttr.USER_MENUS.getValue(), MenuUtils.getMenu(menuList, userMenus));
				
				logger.info("login success");
				json.put("result", "login_success");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return json.toString();
	}
	/**
	 * 验证用户
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkUserNew")
	@ResponseBody
	public String checkUserNew(SysUser user,HttpServletRequest request){
		JSONObject json = new JSONObject();

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null && currentUser.getPrincipal() != null
				&& currentUser.getPrincipal() instanceof SysUser) {
			json.put("result", "login_success");
			return json.toString();
		}

		UsernamePasswordToken token = new UsernamePasswordToken(
				user.getUsername(), user.getPassword());


		String username = user.getUsername();

		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			System.out.println("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			System.out.println("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			// redirectAttributes.addFlashAttribute("message", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			// redirectAttributes.addFlashAttribute("message", "密码不正确");
		} catch (LockedAccountException lae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			// redirectAttributes.addFlashAttribute("message", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			// redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			// redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}

		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			System.out.println("用户[" + username+ "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			json.put("result", "login_success");

			Object o  = currentUser.getPrincipal();

			System.out.println("=======" + (currentUser.getPrincipal() instanceof SysUser));


		} else {
			token.clear();
		}

		return json.toString();
	}
	
	/**
	 * 登出
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.removeAttribute(SessionAttr.USER_LOGIN.getValue());
		session.removeAttribute(SessionAttr.USER_ROLES.getValue());
		session.removeAttribute(SessionAttr.USER_MENUS.getValue());
		
		return "login/login";
	}
	
	/**
	 * 登录(移动端访问接口)
	 * @param user
	 * @param reuqest
	 * @return
	 */
	@RequestMapping("/appLogin")
	@ResponseBody
	public String appLogin(SysUser user,HttpServletRequest reuqest){
		
		JSONObject json = new JSONObject();
		
		try {
			
			SysUser u = sysUserService.findByUsername(user);
			
			if(u != null){
				
				String dateTime = String.valueOf(new Date().getTime());
				String token = new DesUtil("test").encrypt(dateTime);
				
				CacheUtil.putCache(token, u);
				
				json.put("token", token);
				json.put("result", true);
			}
			else{
				
				json.put("result", false);
			}
			
		} catch (Exception e) {
			
			json.put("result", false);
			logger.error(e.getMessage(), e);
		}
		
		return json.toString();
	}
	
	/**
	 * 登录(移动端访问接口)
	 * @param reuqest
	 * @return
	 */
	@RequestMapping("/appLogout")
	@ResponseBody
	public String appLogout(String token,HttpServletRequest reuqest){
		
		JSONObject json = new JSONObject();
		
		try {
			
			CacheUtil.removeCache(token);
			json.put("result", true);
		} catch (Exception e) {
			
			json.put("result", false);
			logger.error(e.getMessage(), e);
		}
		
		return json.toString();
	}
	
	/**
	 * 移动端测试接口
	 * @param reuqest
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public String test(HttpServletRequest reuqest){
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("result", true);
			json.put("msg", "Hello World");
			
		} catch (Exception e) {
			
			json.put("result", false);
			logger.error(e.getMessage(), e);
		}
		
		return json.toString();
	}
	
}
