package com.zcy.common.utils.mail;

import com.zcy.common.constants.ContentTypeConstants;
import com.zcy.common.constants.EncodeTypeConstants;
import com.zcy.common.exception.MailSenderException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSendUtil {
    private static Logger log = Logger.getLogger(MailSendUtil.class.getName());

//    public static void main(String[] args) {
//        MailSenderInfo info = new MailSenderInfo();
//        info.setUser("zhuangcy2019@163.com");
//        info.setPassword("DHHWRXJPYXAAHGZGQ");
//        info.setSendMail("zhuangcy2019@163.com");
//        info.setRecipientMailTo("1909017815@qq.com");
//        //info.setRecipientMailCc("1909017815@qq.com");
//        //info.setRecipientMailBcc("1909017815@qq.com");
//        info.setSendTime(new Date());
//        info.setSubject("主题");
//        info.setContent("<h1>内容...........</h1>");
//        info.setAccessory(new File(MailSendUtil.class.getResource("/").getPath().concat("girl.jpg")));
//        sendMail163(info);
//    }

    private static Properties properties = null;
    private static String localhost = "smtp.163.com";
    private static String protocol = "smtp";

    static {
        properties = System.getProperties(); // 用于连接邮箱服务器的参数配置, 发送邮箱时用到
        properties.setProperty("mail.transport.protocol", protocol); // smtp协议
        properties.setProperty("mail.smtp.host", localhost); // 163邮箱的SMTP服务器地址
        properties.setProperty("mail.smtp.auth", "true"); // 是否需要认证
        //properties.setProperty("mail.smtp.port", "25"); // 163邮箱的SMTP服务器端口
        //properties.setProperty("mail.debug", "true"); // 是否开启调试模式
    }

    private static void sendMail163(MailSenderInfo mailSenderInfo) {
        /*网易邮箱
            POP3服务器: pop.163.com ssl:993/非ssl:143
            SMTP服务器: smtp.163.com ssl:465/994/非ssl:25
            IMAP服务器: imap.163.com ssl:995/非ssl:110
        */
        try {
            log.info("开始准备发送邮箱...");
            Session session = Session.getDefaultInstance(properties); // 根据配置创建会话
            MimeMessage message = getMimeMessageAccessory(session, mailSenderInfo); // 创建邮箱对象
            // 根据session获取邮件传输对象
            Transport transport = session.getTransport();
            // 连接邮箱服务器
            transport.connect(mailSenderInfo.getUser(), mailSenderInfo.getPassword());
            // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 11.关闭连接
            transport.close();
            log.info("Sent message successy....");
        } catch (Exception e) {
            log.log(Level.FINE,"Sent message fail....",e);
        }
    }

    /**
     * 获取 MimeMessage对象
     * @param session
     * @param mailSenderInfo
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private static MimeMessage getMimeMessageHtml(Session session, MailSenderInfo mailSenderInfo) throws UnsupportedEncodingException, MessagingException {
        // 创建一个邮箱对象
        MimeMessage mimeMessage = new MimeMessage(session);
        // 设置发送者/接收者
        setMailHeader(mailSenderInfo, mimeMessage);
        // 设置subject 主题
        mimeMessage.setSubject(mailSenderInfo.getSubject(), EncodeTypeConstants.UTF8);
        // 设置content 邮件正文（可以使用html标签）
        mimeMessage.setContent(mailSenderInfo.getContent(), ContentTypeConstants.TEXT_HTML_UTF8);
        // 设置设置发送时间
        mimeMessage.setSentDate(mailSenderInfo.getSendTime());
        // 保存设置
        mimeMessage.saveChanges();
        return mimeMessage;
    }

    private static MimeMessage getMimeMessageAccessory(Session session, MailSenderInfo mailSenderInfo) throws UnsupportedEncodingException, MessagingException {
        // 创建一个邮箱对象
        MimeMessage mimeMessage = new MimeMessage(session);
        // 设置发送者/接收者
        setMailHeader(mailSenderInfo, mimeMessage);
        // 设置subject 主题
        mimeMessage.setSubject(mailSenderInfo.getSubject(), EncodeTypeConstants.UTF8);

        // 创建多重消息
        MimeMultipart multipart = new MimeMultipart();
        // 创建消息部分
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText("这是一个body.........");
        multipart.addBodyPart(bodyPart);
        // 附件部分
        MimeBodyPart filePart = new MimeBodyPart();
        FileDataSource dataSource = new FileDataSource(mailSenderInfo.getAccessory());
        filePart.setFileName(mailSenderInfo.getAccessory().getName());
        filePart.setDataHandler(new DataHandler(dataSource));
        multipart.addBodyPart(filePart);

        MimeBodyPart filePart2 = new MimeBodyPart();
        filePart2.setFileName(mailSenderInfo.getAccessory().getName());
        filePart2.setDataHandler(new DataHandler(dataSource));
        multipart.addBodyPart(filePart2);
        // 设置完整消息
        mimeMessage.setContent(multipart);

        // 设置设置发送时间
        mimeMessage.setSentDate(mailSenderInfo.getSendTime());
        // 保存设置
        mimeMessage.saveChanges();
        return mimeMessage;
    }

        /**
         * 设置发送者/接收者
         * @param mailSenderInfo
         * @param mimeMessage
         * @throws MessagingException
         * @throws UnsupportedEncodingException
         */
    private static void setMailHeader(MailSenderInfo mailSenderInfo, MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
        String[] recipientMailTo = mailSenderInfo.getRecipientMailTo();
        String[] recipientMailCc = mailSenderInfo.getRecipientMailCc();
        String[] recipientMailBcc = mailSenderInfo.getRecipientMailBcc();
        if (checkString(mailSenderInfo.getSendMail())) {
            throw new MailSenderException("发送者邮箱不存在");
        }
        if (checkArray(recipientMailTo) && checkArray(recipientMailCc) && checkArray(recipientMailBcc)) {
            throw new MailSenderException("接收者邮箱不存在");
        }
        // 设置form发送人
        mimeMessage.setFrom(new InternetAddress(mailSenderInfo.getSendMail(), null, EncodeTypeConstants.UTF8));
        if (!checkArray(recipientMailTo)){
            for (int i = 0; i < recipientMailTo.length; i++) {
                // 设置to 收件人
                mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientMailTo[i], null, EncodeTypeConstants.UTF8));
            }
        }
        if (!checkArray(recipientMailCc)){
            for (int i = 0; i < recipientMailCc.length; i++) {
                // cc 抄送(可选)
                mimeMessage.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(recipientMailCc[i], null, EncodeTypeConstants.UTF8));
            }
        }
        if (!checkArray(recipientMailBcc)){
            for (int i = 0; i < recipientMailBcc.length; i++) {
                // bcc 密送(可选)
                mimeMessage.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(recipientMailBcc[i], null, EncodeTypeConstants.UTF8));
            }
        }
    }

    private static boolean checkArray(String... arr) {
        if (arr == null || arr.length == 0)
            return true;
        return false;
    }

    private static boolean checkString(String str) {
        if (str == null || str == "")
            return true;
        return false;
    }

}
