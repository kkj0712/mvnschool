package com.mvnschool.vo;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	static String host = "smtp.naver.com";
	static int port = 465;
	static String username = "네이버이메일";
	static final String password = "네이버비밀번호";
	
	static String subject = "교사 승인";
	static String contents = "교사 승인 알람 메일";

	public static void sendEmail(List<String> emailList) throws AddressException, MessagingException{
		//서버정보 설정
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		//세션 생성 & 발신자 smtp 서버 로그인 인증
		Session session = Session.getInstance(props, new javax.mail.Authenticator(){
			protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true); //디버그 모드
		
		//MimeMessage 생성 & 메일 세팅
		Message mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(username)); //발신자
		
		System.out.println("mailSession success");
		
		//가져온 리스트의 사이즈만큼 InternetAddress 배열 크기 정하기
		InternetAddress[] addArray = new InternetAddress[emailList.size()];
		for(int i=0; i<emailList.size(); i++){ //배열에 이메일을 저장
			addArray[i] = new InternetAddress(emailList.get(i));
		}
		mimeMessage.setRecipients(Message.RecipientType.TO, addArray);
		mimeMessage.setSubject(subject); //메일 제목
		mimeMessage.setText(contents); //메일 내용
		
		//메일 보내기
		Transport.send(mimeMessage);
		System.out.println("success sending email");
		
	}
}