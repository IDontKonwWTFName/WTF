package com.a.mail;

import javax.swing.text.AbstractDocument.Content;

public class SendMailDemo {
	private  String toAddress;
	private  String content;
	public SendMailDemo(String address,String content) {
		// TODO Auto-generated constructor stub
		 toAddress =address;
		 this.content=content;
	}
 public void send() {
  // 设置邮件服务器信息
  MailSenderInfo mailInfo = new MailSenderInfo();
  mailInfo.setMailServerHost("smtp.qq.com");
  mailInfo.setMailServerPort("25");
  mailInfo.setValidate(true);
  System.out.println("ok?");
  
  // 邮箱用户名
  mailInfo.setUserName("824499468@qq.com");
  // 邮箱密码
  mailInfo.setPassword("LJ1120310323");
  System.out.println("ok?");
  // 发件人邮箱
  mailInfo.setFromAddress("824499468@qq.com");
  // 收件人邮箱
  mailInfo.setToAddress(toAddress);
  System.out.println(toAddress+"ooooo");
  System.out.println("ok?");
  // 邮件标题
  mailInfo.setSubject("测试Java程序发送邮件");
  // 邮件内容
  StringBuffer buffer = new StringBuffer();
  buffer.append("您好，新密码是：");
  buffer.append(content);
  mailInfo.setContent(buffer.toString());
  System.out.println("ok?");
  
  // 发送邮件
  SimpleMailSender sms = new SimpleMailSender();
  // 发送文体格式
  System.out.println("ok?");
  sms.sendTextMail(mailInfo);
  System.out.println("ok?");
  // 发送html格式
  SimpleMailSender.sendHtmlMail(mailInfo);
  System.out.println("邮件发送完毕");
 }
}