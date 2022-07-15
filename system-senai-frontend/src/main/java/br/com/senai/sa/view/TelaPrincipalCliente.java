package br.com.senai.sa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

@Component
public class TelaPrincipalCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	
	public TelaPrincipalCliente() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("image.png"));
		setTitle("Principal (acesso Cliente) - SA System 1.4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPanel = new JPanel();
		contentPanel.setToolTipText("");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		
		JButton btnCliente = new JButton("Cliente");
		btnCliente.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JTextPane txtpnUsuarioLogado = new JTextPane();
		txtpnUsuarioLogado.setText(" ");
		txtpnUsuarioLogado.setEditable(false);
		txtpnUsuarioLogado.setForeground(Color.BLACK);
		txtpnUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JTextPane txtpnTextoUsuarioLogado = new JTextPane();
		txtpnTextoUsuarioLogado.setBackground(Color.WHITE);
		txtpnTextoUsuarioLogado.setText("UsuárioLogado");
		txtpnTextoUsuarioLogado.setForeground(Color.BLACK);
		txtpnTextoUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 14));
		txtpnTextoUsuarioLogado.setEditable(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtpnTextoUsuarioLogado, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtpnUsuarioLogado, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCliente, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
							.addGap(264))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnTextoUsuarioLogado, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(144)
							.addComponent(btnCliente, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
							.addComponent(txtpnUsuarioLogado, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(13))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
