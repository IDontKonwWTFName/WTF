package com.a.mail;
public class SendMailDemo {
	private  String toAddress;
	public SendMailDemo(String address) {
		// TODO Auto-generated constructor stub
		 toAddress =address;
	}
 public void send() {
  // �����ʼ���������Ϣ
  MailSenderInfo mailInfo = new MailSenderInfo();
  mailInfo.setMailServerHost("smtp.qq.com");
  mailInfo.setMailServerPort("25");
  mailInfo.setValidate(true);
  System.out.println("ok?");
  
  // �����û���
  mailInfo.setUserName("824499468@qq.com");
  // ��������
  mailInfo.setPassword("LJ1120310323");
  System.out.println("ok?");
  // ����������
  mailInfo.setFromAddress("824499468@qq.com");
  // �ռ�������
  mailInfo.setToAddress(toAddress);
  System.out.println(toAddress+"ooooo");
  System.out.println("ok?");
  // �ʼ�����
  mailInfo.setSubject("����Java�������ʼ�");
  // �ʼ�����
  StringBuffer buffer = new StringBuffer();
  buffer.append("�Է�ȥ\n");
  buffer.append("ok");
  mailInfo.setContent(buffer.toString());
  System.out.println("ok?");
  
  // �����ʼ�
  SimpleMailSender sms = new SimpleMailSender();
  // ���������ʽ
  System.out.println("ok?");
  sms.sendTextMail(mailInfo);
  System.out.println("ok?");
  // ����html��ʽ
  SimpleMailSender.sendHtmlMail(mailInfo);
  System.out.println("�ʼ��������");
 }
}