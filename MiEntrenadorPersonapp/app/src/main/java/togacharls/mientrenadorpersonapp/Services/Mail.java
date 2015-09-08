package togacharls.mientrenadorpersonapp.Services;

import java.util.Date; 
import java.util.Properties; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.mail.BodyPart; 
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart;

 
public class Mail extends javax.mail.Authenticator { 
	private String usuario; 
	private String pass; 
 
	private String[] to;  
	private String puerto;  
	private String host; 
	private String asunto; 
	private String body; 
 
	private boolean auth; 
 
	private boolean debuggable; 
 
	private Multipart multipart;
	public Mail() { 
		host = "smtp.gmail.com"; // default smtp server 
		puerto = "587";//SSL="465"; SMTP=25; TLS=587;
 
		usuario = "mientrenadorpesonapp@gmail.com"; // username 
		pass = "mientrenadorpersopass"; // password 

		asunto = "Seguimiento"; // email subject 
		body = "En este correo se adjunta la gráfica con tus resultados.\n" +
				"Gracias por utilizar MiEntrenadorPersonapp"; // email body 
 
		debuggable = true; // debug mode on or off - default off 
		auth = true; // smtp authentication - default on 
 
		multipart = new MimeMultipart(); 
	} 
 
	/*
	 * Envía el correo electrónico y devuelve	1 si todo ha salido bien
	 * 											0 si falta algún campo por rellenar
	 * 											-X si salta la excepción referenciada por 'X'
	 * */		
	public int send(){
		Properties props = setProperties();
		
		if(!usuario.equals("") && !pass.equals("") && to.length > 0 && !asunto.equals("") && !body.equals("")) { 
			Session session =
					Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(usuario, pass);
	            }
	        });
			
			session.setDebug(true);		
 
			MimeMessage msg = new MimeMessage(session); 
 
			try{
				msg.setFrom(new InternetAddress(usuario));
			}catch(Exception e){
				e.printStackTrace();
				return -2;
			}
			
			InternetAddress[] addressTo = new InternetAddress[to.length]; 
			for (int i = 0; i < to.length; i++) { 
				try{
					addressTo[i] = new InternetAddress(to[i]);
				}catch(Exception e){
					e.printStackTrace();
					return -3;
				}	
			} 
			
			try{
				msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);
			}catch(Exception e){
				e.printStackTrace();
				return -4;
			}
			
			try{
				msg.setSubject(asunto);
			}catch(Exception e){
				e.printStackTrace();
				return -5;
			}
						
			try{
				msg.setSentDate(new Date());
			}catch(Exception e){
				e.printStackTrace();
				return -6;
			}
			
			BodyPart messageBodyPart = new MimeBodyPart();
					
			try{
				messageBodyPart.setText(body);	
			}catch(Exception e){
				e.printStackTrace();
				return -7;
			}

			try{
				multipart.addBodyPart(messageBodyPart);			
			}catch(Exception e){
				e.printStackTrace();
				return -8;
			}

			try{
				msg.setContent(multipart);			
			}catch(Exception e){
				e.printStackTrace();
				return -9;
			}
 
			Transport t = null;
			try{
				t = session.getTransport("smtps");
			}catch(Exception e){
				e.printStackTrace();
				return -10;
			}
			
		    try {
		    	t.connect("173.194.66.108",usuario, pass);		//IP del HOST de GMAIL
		    }catch(Exception e){
				e.printStackTrace();
		    	e.printStackTrace();
		    	return -11;
		    }
		    
		    try{
		    	t.sendMessage(msg, addressTo);
		    }catch(Exception e){
				e.printStackTrace();
		    	return -12;
		    }
		    try{
		    	t.close();
		    }catch(Exception e){
				e.printStackTrace();
		    	return -13;
		    }    	
			return 1;

		} else {
			return 0; 
		} 
	} 
	
	/*
	 * Se adjunta un fichero al correo electrónico
	 * 
	 * */
	public void addAttachment(String filename) throws Exception { 
		BodyPart messageBodyPart = new MimeBodyPart(); 
		DataSource source = new FileDataSource(filename); 
		messageBodyPart.setDataHandler(new DataHandler(source)); 
		messageBodyPart.setFileName(filename); 
 
		multipart.addBodyPart(messageBodyPart); 
	}
	
	private Properties setProperties() { 
		Properties props = new Properties(); 
		
		if(auth) { 
			props.put("mail.smtp.auth", "true"); 
		}
		props.put("mail.smtp.starttls.enable", "true");	//Se habilita ttls
		props.put("mail.smtp.host", host); 
		props.put("mail.smtp.port", puerto); //puerto->587 tls
		
		if(debuggable) { 
			props.put("mail.debug", "true"); 
		} 
		return props; 
	} 
	
	public String getBody() { 
		return body; 
	} 
 
	public void setBody(String body) { 
		this.body = body; 
	} 
 
	public void setTo(String[] toArr) {
		this.to = toArr;
	}
 
	public void setSubject(String string) {
		this.asunto = string;
	}
}