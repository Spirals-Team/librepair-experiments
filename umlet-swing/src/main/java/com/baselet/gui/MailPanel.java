package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baselet.control.config.Config;
import com.baselet.control.config.ConfigMail;
import com.baselet.control.constants.Constants;
import com.baselet.control.enums.Program;
import com.baselet.control.util.Path;
import com.baselet.diagram.CurrentDiagram;
import com.baselet.diagram.Notifier;
import com.baselet.diagram.io.DiagramFileHandler;

public class MailPanel extends JPanel {

	private static final Logger log = LoggerFactory.getLogger(MailPanel.class);

	private static final long serialVersionUID = 1L;

	/**
	 * Some int and String
	 */

	private static final int PADDINGTOP = 1;
	private static final int PADDINGBOTTOM = 1;
	private static final int outerPaddingLeft = 15;
	private static final int outerPaddingRight = 15;
	private static final int halfHorizontalDividerSpace = 2;
	private static final int verticalDividerSpace = 10;

	/**
	 * Components
	 */

	private static final GridBagLayout layout = new GridBagLayout();

	private static final JLabel lbFrom = new JLabel("From:");
	private static final JTextField tfFrom = new JTextField();
	private static final JLink lnkSmtpInfo = new JLink(Program.getInstance().getWebsite() + "/smtp.htm", "What is SMTP?");

	private static final JLabel lbSmtp = new JLabel("SMTP:");
	private static final JTextField tfSmtp = new JTextField();
	private static final JCheckBox cbSmtpAuth = new JCheckBox();

	private static final JLabel lbSmtpUser = new JLabel("User:");
	private static final JTextField tfSmtpUser = new JTextField();

	private static final JLabel lbSmtpPW = new JLabel("PW:");
	private static final JPasswordField pfSmtpPW = new JPasswordField();
	private static final JCheckBox cbPwSave = new JCheckBox();

	private static final JLabel lbTo = new JLabel("To:");
	private static final JTextField tfTo = new JTextField();

	private static final JLabel lbCc = new JLabel("CC:");
	private static final JTextField tfCc = new JTextField();

	private static final JLabel lbBcc = new JLabel("BCC:");
	private static final JTextField tfBcc = new JTextField();

	private static final JLabel lbSubject = new JLabel("Subject:");
	private static final JTextField tfSubject = new JTextField();

	private static final JTextArea taText = new JTextArea(5, 5);
	JScrollPane spText = new JScrollPane(taText);

	private static final JCheckBox cbAttachXml = new JCheckBox();
	private static final JCheckBox cbAttachGif = new JCheckBox();
	private static final JCheckBox cbAttachPdf = new JCheckBox();

	private static final JButton btSend = new JButton("Send");
	private static final JButton btCancel = new JButton("Cancel");

	private static final JPanel panelAttachments = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private static final JPanel panelAttachmentsWithButton = new JPanel(layout);

	// the padding between lines is different for the labels and text components of the grid bag layout
	private static final Insets paddingLeftLabel = new Insets(PADDINGTOP, outerPaddingLeft, PADDINGBOTTOM, halfHorizontalDividerSpace);
	private static final Insets paddingMessagebox = new Insets(PADDINGTOP, outerPaddingLeft, PADDINGBOTTOM, outerPaddingRight);
	private static final Insets paddingText = new Insets(PADDINGTOP, halfHorizontalDividerSpace, PADDINGBOTTOM, outerPaddingRight);
	private static final Insets paddingCheckbox = new Insets(PADDINGTOP - 2, halfHorizontalDividerSpace, PADDINGBOTTOM - 2, outerPaddingRight);
	private static final Insets paddingRightLabel = new Insets(PADDINGTOP, halfHorizontalDividerSpace, PADDINGBOTTOM, halfHorizontalDividerSpace);
	private static final Insets noPadding = new Insets(0, 0, 0, 0);

	// the label doesn't get any additional space. it's always as short as possible
	private static final double noWeight = 0;
	private static final double fullWeight = 1;
	private static final double leftWeight = 0.75;
	private static final double rightWeight = 0.25;

	// the constraint int to fill the width
	private static final int fillWidth = GridBagConstraints.HORIZONTAL;
	private static final int fillBoth = GridBagConstraints.BOTH;

	public MailPanel() {

		initAndFillComponents();

		setLayout(layout);
		setSize(new Dimension(0, Config.getInstance().getMailSplitPosition()));

		int line = 0;
		addComponent(this, layout, Box.createRigidArea(new Dimension(0, verticalDividerSpace)), 0, line, 10, 1, fillWidth, fullWeight, 0, noPadding);
		line++;
		addComponent(this, layout, lbTo, 0, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, tfTo, 1, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, lbFrom, 2, line, 1, 1, fillWidth, noWeight, 0, paddingRightLabel);
		addComponent(this, layout, tfFrom, 3, line, 1, 1, fillWidth, rightWeight, 0, paddingRightLabel);
		addComponent(this, layout, lnkSmtpInfo, 4, line, 1, 1, fillWidth, noWeight, 0, paddingText);
		line++;
		addComponent(this, layout, lbCc, 0, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, tfCc, 1, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, lbSmtp, 2, line, 1, 1, fillWidth, noWeight, 0, paddingRightLabel);
		addComponent(this, layout, tfSmtp, 3, line, 1, 1, fillWidth, rightWeight, 0, paddingRightLabel);
		addComponent(this, layout, cbSmtpAuth, 4, line, 1, 1, fillWidth, noWeight, 0, paddingText);
		line++;
		addComponent(this, layout, lbBcc, 0, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, tfBcc, 1, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, lbSmtpUser, 2, line, 1, 1, fillWidth, noWeight, 0, paddingRightLabel);
		addComponent(this, layout, tfSmtpUser, 3, line, 1, 1, fillWidth, rightWeight, 0, paddingRightLabel);
		line++;
		addComponent(this, layout, lbSubject, 0, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, tfSubject, 1, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, lbSmtpPW, 2, line, 1, 1, fillWidth, noWeight, 0, paddingRightLabel);
		addComponent(this, layout, pfSmtpPW, 3, line, 1, 1, fillWidth, rightWeight, 0, paddingRightLabel);
		addComponent(this, layout, cbPwSave, 4, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		line++;
		addComponent(this, layout, Box.createRigidArea(new Dimension(0, verticalDividerSpace)), 0, line, 10, 1, fillWidth, fullWeight, 0, noPadding);
		line++;
		addComponent(this, layout, spText, 0, line, 5, 1, fillBoth, leftWeight, 1, paddingMessagebox);
		line++;
		addComponent(this, layout, panelAttachmentsWithButton, 1, line, 5, 1, fillWidth, fullWeight, 0, noPadding);
		line++;
		addComponent(this, layout, Box.createRigidArea(new Dimension(0, verticalDividerSpace)), 0, line, 4, 1, fillWidth, fullWeight, 0, noPadding);

	}

	private void initAndFillComponents() {

		taText.setText(Constants.getDefaultMailtext());

		cbPwSave.setText("save in config");
		cbAttachXml.setText("attach " + Program.getInstance().getExtension().toUpperCase());
		cbAttachGif.setText("attach GIF");
		cbAttachPdf.setText("attach PDF");
		cbSmtpAuth.setText("authentication");

		btSend.addActionListener(new SendActionListener());
		btCancel.addActionListener(new CancelActionListener());
		cbSmtpAuth.addActionListener(new AuthentificationActionListener());

		// Set Tooltips
		String adressToolTip = "Separate multiple adresses with ','";
		cbPwSave.setToolTipText("WARNING: The password is stored as plain text in " + Program.getInstance().getConfigName());
		tfFrom.setToolTipText(adressToolTip);
		tfTo.setToolTipText(adressToolTip);
		tfCc.setToolTipText(adressToolTip);
		tfBcc.setToolTipText(adressToolTip);

		// Fill Attachment Panel
		panelAttachments.add(cbAttachXml);
		panelAttachments.add(Box.createRigidArea(new Dimension(5, 0)));
		panelAttachments.add(cbAttachGif);
		panelAttachments.add(Box.createRigidArea(new Dimension(5, 0)));
		panelAttachments.add(cbAttachPdf);

		// Fill the superpanel which holds attachments and the send button
		addComponent(panelAttachmentsWithButton, layout, panelAttachments, 0, 0, 1, 1, fillWidth, fullWeight, 0, noPadding);
		addComponent(panelAttachmentsWithButton, layout, btSend, 1, 0, 1, 1, fillWidth, fullWeight, 0, paddingText);
		addComponent(panelAttachmentsWithButton, layout, btCancel, 2, 0, 1, 1, fillWidth, fullWeight, 0, paddingText);

		setAllFonts();
		readConstants();
		checkVisibilityOfSmtpAuth();
	}

	private void sendMail() {

		/**
		 * Initialize some variables and objects
		 */

		String smtpHost = tfSmtp.getText();
		String smtpUser = tfSmtpUser.getText();
		String smtpPW = String.valueOf(pfSmtpPW.getPassword());
		String from = tfFrom.getText();
		String[] to = removeWhitespaceAndSplitAt(tfTo.getText());
		String[] cc = removeWhitespaceAndSplitAt(tfCc.getText());
		String[] bcc = removeWhitespaceAndSplitAt(tfBcc.getText());
		String subject = tfSubject.getText();
		String text = taText.getText();
		boolean useSmtpAuthentication = false;
		File diagramXml = null;
		File diagramGif = null;
		File diagramPdf = null;
		int nrOfAttachments = 0;

		// Set SMTP Authentication if the user or password field isn't empty
		if (!smtpUser.isEmpty() || !smtpPW.isEmpty()) {
			useSmtpAuthentication = true;
		}

		// Create the temp diagrams to send
		try {
			final String diagramName = "diagram_" + new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
			DiagramFileHandler fileHandler = CurrentDiagram.getInstance().getDiagramHandler().getFileHandler();
			if (cbAttachXml.isSelected()) {
				nrOfAttachments++;
				diagramXml = fileHandler.doSaveTempDiagram(diagramName, Program.getInstance().getExtension());
			}
			if (cbAttachGif.isSelected()) {
				nrOfAttachments++;
				diagramGif = fileHandler.doSaveTempDiagram(diagramName, "gif");
			}
			if (cbAttachPdf.isSelected()) {
				nrOfAttachments++;
				diagramPdf = fileHandler.doSaveTempDiagram(diagramName, "pdf");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "There has been an error with your diagram. Please make sure it's not empty.", "Diagram Error", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
			return;
		}

		/**
		 * Check if all necessary fields are filled
		 */

		String errorMsg = null;
		if (smtpHost.isEmpty()) {
			errorMsg = "The SMTP field must not be empty";
		}
		else if (from.isEmpty()) {
			errorMsg = "The FROM field must not be empty";
		}
		else if (to.length == 0) {
			errorMsg = "The TO field must not be empty";
		}

		if (errorMsg != null) {
			JOptionPane.showMessageDialog(this, errorMsg, "Error", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
			return;
		}

		/**
		 * Set up the mail
		 */

		try {
			// Get system properties and session
			Properties props = System.getProperties();
			Session session = Session.getInstance(props);

			// Define message and it's parts
			MimeMessage message = new MimeMessage(session);
			MimeBodyPart textPart = new MimeBodyPart();
			MimeBodyPart[] attachmentPart = new MimeBodyPart[nrOfAttachments];
			for (int i = 0; i < nrOfAttachments; i++) {
				attachmentPart[i] = new MimeBodyPart();
			}

			// Build multipart message
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			for (int i = 0; i < nrOfAttachments; i++) {
				multipart.addBodyPart(attachmentPart[i]);
			}
			message.setContent(multipart);

			/**
			 * Fill the message properties
			 */

			// Set the SMTP Host
			props.put("mail.smtp.host", smtpHost);

			// We want to close the connection immediately after sending
			props.put("mail.smtp.quitwait", "false");

			// We want to use encryption if needed
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.protocols", "SSLv3 TLSv1");

			// If authentication is needed we set it to true
			if (useSmtpAuthentication) {
				props.put("mail.smtp.auth", "true");
			}
			else {
				props.put("mail.smtp.auth", "false");
			}

			// Set all recipients of any kind (TO, CC, BCC)
			message.setFrom(new InternetAddress(from));
			for (String element : to) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(element));
			}
			for (String element : cc) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(element));
			}
			for (String element : bcc) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(element));
			}

			// Set subject, text and attachment
			message.setSubject(subject);
			textPart.setText(text);

			int i = 0;
			if (cbAttachXml.isSelected()) {
				attachmentPart[i++].attachFile(diagramXml);
			}
			if (cbAttachGif.isSelected()) {
				attachmentPart[i++].attachFile(diagramGif);
			}
			if (cbAttachPdf.isSelected()) {
				attachmentPart[i++].attachFile(diagramPdf);
			}

			/**
			 * Send message (if no authentication is used, we use the short variant to send a mail
			 */

			if (useSmtpAuthentication) {
				Transport transport = session.getTransport("smtp");
				try {
					transport.connect(smtpHost, smtpUser, smtpPW);
					transport.sendMessage(message, message.getAllRecipients());
				} finally {
					transport.close();
				}
			}
			else { // No SMTP Authentication
				Transport.send(message);
			}

			Notifier.getInstance().showInfo("Email sent");
			closePanel();
		}

		catch (MessagingException e) {
			log.error("SMTP Error", e);
			JOptionPane.showMessageDialog(this, "There has been an error with your smtp server." + Constants.NEWLINE + "Please recheck your smtp server and login data.", "SMTP Error", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
		} catch (IOException e) {
			log.error("Mail Error", e);
			JOptionPane.showMessageDialog(this, "There has been an error sending your mail." + Constants.NEWLINE + "Please recheck your input data.", "Sending Error", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
		} catch (Throwable e) {
			log.error("Mail Error", e);
			JOptionPane.showMessageDialog(this, "There has been an error sending your mail." + Constants.NEWLINE + "Please recheck your input data.", "Sending Error", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
		} finally {
			if (diagramXml != null) {
				Path.safeDeleteFile(diagramXml, false);
			}
			if (diagramGif != null) {
				Path.safeDeleteFile(diagramGif, false);
			}
			if (diagramPdf != null) {
				Path.safeDeleteFile(diagramPdf, false);
			}
		}
	}

	/**
	 * Adds a component to this panel
	 *
	 * @param gbl
	 *            The GridBagLayout of this component
	 * @param c
	 *            The Component to add
	 * @param x
	 *            The x value of grid where the component starts
	 * @param y
	 *            The y value of grid where the component starts
	 * @param width
	 *            How many spaces of the grid's width will be used by the component
	 * @param height
	 *            How many spaces of the grid's height will be used by the component
	 * @param fill
	 *            If the component's display area is larger than the component's requested size this param determines whether and how to resize the component
	 * @param weightx
	 *            Specifies how to distribute extra horizontal space.
	 * @param weighty
	 *            Specifies how to distribute extra vertical space.
	 * @param insets
	 *            Specifies the external padding of the component (= minimum amount of space between the component and the edges of its display area)
	 */
	private void addComponent(JPanel panel, GridBagLayout gbl, Component c, int x, int y, int width, int height, int fill, double weightx, double weighty, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		gbl.setConstraints(c, gbc);
		panel.add(c);
	}

	private String[] removeWhitespaceAndSplitAt(String inputString) {
		if (inputString.isEmpty()) {
			return new String[] {};
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < inputString.length(); i++) {
			if (inputString.charAt(i) != ' ') {
				sb.append(inputString.charAt(i));
			}
		}
		return sb.toString().split(",");
	}

	private void storeConstants() {
		ConfigMail cfgMail = ConfigMail.getInstance();
		cfgMail.setMailSmtp(tfSmtp.getText());
		cfgMail.setMailSmtpAuth(cbSmtpAuth.isSelected());
		cfgMail.setMailSmtpUser(tfSmtpUser.getText());
		cfgMail.setMailSmtpPwStore(cbPwSave.isSelected());
		if (cbPwSave.isSelected()) {
			cfgMail.setMailSmtpPw(String.valueOf(pfSmtpPW.getPassword()));
		}
		else {
			cfgMail.setMailSmtpPw("");
		}
		cfgMail.setMailFrom(tfFrom.getText());
		cfgMail.setMailTo(tfTo.getText());
		cfgMail.setMailCc(tfCc.getText());
		cfgMail.setMailBcc(tfBcc.getText());
		cfgMail.setMailXml(cbAttachXml.isSelected());
		cfgMail.setMailGif(cbAttachGif.isSelected());
		cfgMail.setMailPdf(cbAttachPdf.isSelected());
	}

	private void readConstants() {
		ConfigMail cfgMail = ConfigMail.getInstance();
		tfSmtp.setText(cfgMail.getMailSmtp());
		cbSmtpAuth.setSelected(cfgMail.isMailSmtpAuth());
		tfSmtpUser.setText(cfgMail.getMailSmtpUser());
		cbPwSave.setSelected(cfgMail.isMailSmtpPwStore());
		pfSmtpPW.setText(cfgMail.getMailSmtpPw());
		tfFrom.setText(cfgMail.getMailFrom());
		tfTo.setText(cfgMail.getMailTo());
		tfCc.setText(cfgMail.getMailCc());
		tfBcc.setText(cfgMail.getMailBcc());
		cbAttachXml.setSelected(cfgMail.isMailXml());
		cbAttachGif.setSelected(cfgMail.isMailGif());
		cbAttachPdf.setSelected(cfgMail.isMailPdf());
	}

	private void setAllFonts() {

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		Font fontBold = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		Font fontSmallItalic = new Font(Font.SANS_SERIF, Font.ITALIC, 10);

		lbSmtp.setFont(fontBold);
		tfSmtp.setFont(font);
		lbSmtpUser.setFont(fontBold);
		tfSmtpUser.setFont(font);
		lbSmtpPW.setFont(fontBold);
		pfSmtpPW.setFont(font);
		lbFrom.setFont(fontBold);
		tfFrom.setFont(font);
		lbTo.setFont(fontBold);
		tfTo.setFont(font);
		lbCc.setFont(fontBold);
		tfCc.setFont(font);
		lbBcc.setFont(fontBold);
		tfBcc.setFont(font);
		lbSubject.setFont(fontBold);
		tfSubject.setFont(font);
		taText.setFont(font);
		cbAttachXml.setFont(fontBold);
		cbAttachGif.setFont(fontBold);
		cbAttachPdf.setFont(fontBold);
		lnkSmtpInfo.setFont(fontSmallItalic);
		cbSmtpAuth.setFont(fontSmallItalic);
		cbPwSave.setFont(fontSmallItalic);
	}

	public void closePanel() {
		storeConstants();
		CurrentGui.getInstance().getGui().setMailPanelEnabled(false);
	}

	private class SendActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMail();
		}
	}

	private class CancelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			closePanel();
		}
	}

	private void checkVisibilityOfSmtpAuth() {
		boolean val = cbSmtpAuth.isSelected();
		lbSmtpUser.setVisible(val);
		tfSmtpUser.setVisible(val);
		lbSmtpPW.setVisible(val);
		pfSmtpPW.setVisible(val);
		cbPwSave.setVisible(val);
		if (!val) {
			tfSmtpUser.setText("");
			pfSmtpPW.setText("");
			cbPwSave.setSelected(false);
		}
		repaint();
	}

	private class AuthentificationActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			checkVisibilityOfSmtpAuth();
		}
	}

}
