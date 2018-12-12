package com.jt.payment.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jt.payment.utils.PaymentUtil;


@Controller
@RequestMapping("/yibao")
public class PaymentController {
	
	private String accountID="10001126856";
	private String keyValue="69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
	@RequestMapping("/submitOrder")
	public String submitOrder(String id,Model model) throws Exception {
			model.addAttribute("orderId", id);
		return "payment";
	} 
	
	@RequestMapping("/paymentSend")
	public String paymentSend(String orderId, String bankId, Model model) throws Exception{
		//整理易宝支付所需要的所有请求参数
		String message="";
		if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(bankId) ) {
			message = "您的订单状态好像有点问题哦！";
			model.addAttribute("message", message);
			return "pay_fail";
		}
		
		//判断订单状态为未付款
		
		// 测试商户：商户接收支付成功数据的地址
		String accountCallbackURL = "http://payment.jt.com/yibao/checkPayment.html";
		String businessType = "Buy"; // 业务类型。Buy为在线支付
		String currency = "CNY"; // 交易币种。CNY为人民币
		String productDesc = ""; // 商品描述
		String productCategory = ""; // 商品种类
		String productID = ""; // 商品ID
		String addressFlag = ""; // 送货地址。0为不需要，1为需要
		String accountMoreInfo = ""; // 商户扩展信息
		String pr_NeedResponse = "0"; // 应答机制
		String payment = ""; //订单价格
		//测试数据payment=0.01
		payment = 0.01+"";
		String md5hmac = PaymentUtil.buildHmac(businessType, accountID, orderId, payment, currency, productID,
				productCategory, productDesc, accountCallbackURL, addressFlag, accountMoreInfo, bankId,
				pr_NeedResponse, keyValue);
		model.addAttribute("businessType", businessType);
		model.addAttribute("accountID", accountID);
		model.addAttribute("orderID", orderId);
		model.addAttribute("amount", payment);
		model.addAttribute("currency", currency);
		model.addAttribute("productID", productID);
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("productDesc", productDesc);
		model.addAttribute("accountCallbackURL", accountCallbackURL);
		model.addAttribute("addressFlag", addressFlag);
		model.addAttribute("accountMoreInfo", accountMoreInfo);
		model.addAttribute("accountBankID", bankId);
		model.addAttribute("needResponse", pr_NeedResponse);
		model.addAttribute("md5hmac", md5hmac);
		return "connection";
	}
	@RequestMapping("/checkPayment")
	public String checkPayment(String hmac, String p1_MerId, String r0_Cmd, String r1_Code, String r2_TrxId,
			String r3_Amt, String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid, String r8_MP, String r9_BType,
			String keyValue, HttpServletResponse resp, Model model)throws Exception{
		//做hmac校验   验证hmac
		boolean ok=PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(!ok){
			return "pay_fail";
		}
		if(r9_BType.equals("1")){
			//验证订单的状态 是否为已付款  转发到付款成功页面即可
			
			return "pay_success";
		}else{
			//修改订单信息,返回信息给易宝
			
			PrintWriter out = resp.getWriter();
			out.print("success");
			out.close();
			return "";
		}
	}
}






