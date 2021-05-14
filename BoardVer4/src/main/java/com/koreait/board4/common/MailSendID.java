package com.koreait.board4.common;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSendID {
	public static void idMailSend(HisVo hisVo) {

		Properties prop = System.getProperties();
		
        // 로그인시 TLS를 사용할 것인지 설정
		prop.put("mail.smtp.starttls.enable", "true");
		
        
		// 이메일 발송을 처리해줄 SMTP서버
		prop.put("mail.smtp.host", "smtp.gmail.com");
        
		// SMTP 서버의 인증을 사용한다는 의미
		prop.put("mail.smtp.auth", "true");
        
		// TLS의 포트번호는 587이며 SSL의 포트번호는 465이다.
		prop.put("mail.smtp.port", "587");
		
		// soket문제와 protocol문제 해결
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Authenticator auth = new MailAuth();

		Session session = Session.getDefaultInstance(prop, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			// 보내는 날짜 지정
			msg.setSentDate(new Date());

			// 발송자를 지정한다. 발송자의 메일, 발송자명
			msg.setFrom(new InternetAddress("goonturtle4u@gmail.com", "나는야거북이"));
			
            // 수신자의 메일을 생성한다.
			InternetAddress to = new InternetAddress(hisVo.getHisEmail());
			
            // Message 클래스의 setRecipient() 메소드를 사용하여 수신자를 설정한다. setRecipient() 메소드로 수신자, 참조,
			// 숨은 참조 설정이 가능하다.
			// Message.RecipientType.TO : 받는 사람
			// Message.RecipientType.CC : 참조
			// Message.RecipientType.BCC : 숨은 참조
			msg.setRecipient(Message.RecipientType.TO, to);
			
			String gender="";
			if (hisVo.getHisGender()==0) gender = "남";
			else gender = "여";
			
			String mailSubject = hisVo.getHisName()+"("+gender+")"+"님의 아이디를 알려드립니다.";
			String mailText = hisVo.getHisName()+"("+gender+")"+"님의 아이디는 ↓\n"
					+hisVo.getHisId()+"\n↑ 이랍니다~^^";
			
            // 메일의 제목 지정
			msg.setSubject(mailSubject, "UTF-8");
			
            // Transport는 메일을 최종적으로 보내는 클래스로 메일을 보내는 부분이다.
			msg.setText(mailText, "UTF-8");

			Transport.send(msg);

		} catch (AddressException ae) {
			System.out.println("AddressException : " + ae.getMessage());
		} catch (MessagingException me) {
			System.out.println("MessagingException : " + me.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
		}
	}
}