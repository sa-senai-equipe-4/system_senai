package br.com.senai.sa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import br.com.senai.sa.client.LoginClient;
import br.com.senai.sa.dto.Usuario;
import br.com.senai.sa.dto.enums.Perfil;
import br.com.senai.sa.exception.ErroFormatter;

@Component
public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	private JTextField edtLogin;
	private JLabel lblSenha;
	private JPasswordField edtSenha;
	
	@Autowired
	private LoginClient loginClient;
	
	@Autowired
	private ErroFormatter erroFormatter;
	
	@Autowired
	private TelaPrincipalCliente telaPrincipalCliente;
	
	@Autowired
	private TelaPrincipalGestor telaPrincipalGestor;

	
	public TelaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		setForeground(Color.RED);
		setFont(new Font("Dialog", Font.BOLD, 14));
		setTitle("Login - SA System 1.4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 225);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtLogin = new JTextField();
		edtLogin.setToolTipText("Insira aqui seu login...");
		edtLogin.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtLogin.setColumns(10);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtSenha = new JPasswordField();
		edtSenha.setToolTipText("Insira a senha aqui...");
		edtSenha.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		JButton btnLogar = new JButton("Logar");
		getRootPane().setDefaultButton(btnLogar);
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String login = edtLogin.getText();
					
					String senha = new String(edtSenha.getPassword());
					Usuario usuarioLogado = loginClient.loginPor(login, senha);
					
					if(usuarioLogado.getPerfil().equals(Perfil.GESTOR)) {
						telaPrincipalGestor.carregarTela(usuarioLogado);
						setVisible(false);
					}
					if(usuarioLogado.getPerfil().equals(Perfil.CLIENTE)) {
						telaPrincipalCliente.carregarTela(usuarioLogado);
						setVisible(false);
					}
					
				} catch (HttpClientErrorException ex) {
					String msg = erroFormatter.formatar(ex);
					JOptionPane.showMessageDialog(btnLogar, msg);
				}
			}
		});
		btnLogar.setFont(new Font("Dialog", Font.BOLD, 14));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(edtSenha, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(edtLogin, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLogin))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(99)
							.addComponent(btnLogar, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogar, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
