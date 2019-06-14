package utfpr.edu.br.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EnvioEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarMensagem(String endereco, String assunto, String menssagem){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(endereco);
        email.setSubject(assunto);
        email.setText(menssagem);
        mailSender.send(email);
    }

    public void enviarArquivo(String endereco, String assunto, String menssagem, byte[] bytes, String nomeArquivo){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(endereco);
            helper.setSubject(assunto);
            helper.setText(menssagem);
            helper.addAttachment(nomeArquivo, new ByteArrayResource(bytes));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
