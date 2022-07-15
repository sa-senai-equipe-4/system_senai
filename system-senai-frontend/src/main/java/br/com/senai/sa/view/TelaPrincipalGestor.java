package br.com.senai.sa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.sa.dto.Usuario;
import lombok.Getter;

@Component
public class TelaPrincipalGestor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	
	@Getter
	private Usuario usuarioLogado;
	
	@Autowired
	private TelaListagem telaListagem;
	
	@Autowired
	private TelaListagemPromissoria telaListagemPromissoria;
	
	@Lazy
	@Autowired
	private TelaLogin telaLogin;
	
	private JTextPane txtpnUsuarioLogado = new JTextPane();
	
	public void carregarTela(Usuario usuarioLogado) {
		this.txtpnUsuarioLogado.setText(usuarioLogado.getLogin());
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public TelaPrincipalGestor() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaLogin.setVisible(true);
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		setTitle("Principal (acesso Gestor) - SA System 1.4");
		setBounds(100, 100, 780, 525);
		contentPanel = new JPanel();
		contentPanel.setToolTipText("");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaListagem.setLocationRelativeTo(null);
				telaListagem.setVisible(true);
				setVisible(false);
			}
		});
		btnClientes.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnPromissórias = new JButton("Promissórias");
		btnPromissórias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaListagemPromissoria.setVisible(true);
				setVisible(false);
			}
		});
		btnPromissórias.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Dialog", Font.BOLD, 14));
		
		
		txtpnUsuarioLogado.setEditable(false);
		txtpnUsuarioLogado.setForeground(Color.BLACK);
		txtpnUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JTextPane txtpnTextoUsuarioLogado = new JTextPane();
		txtpnTextoUsuarioLogado.setBackground(Color.WHITE);
		txtpnTextoUsuarioLogado.setText("Usuário logado");
		txtpnTextoUsuarioLogado.setForeground(Color.BLACK);
		txtpnTextoUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 14));
		txtpnTextoUsuarioLogado.setEditable(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnClientes, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(btnPromissórias, 0, 0, Short.MAX_VALUE))
							.addGap(264))
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addComponent(txtpnTextoUsuarioLogado, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtpnUsuarioLogado, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnTextoUsuarioLogado, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(103)
							.addComponent(btnClientes, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnPromissórias, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
							.addComponent(txtpnUsuarioLogado, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(13))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
