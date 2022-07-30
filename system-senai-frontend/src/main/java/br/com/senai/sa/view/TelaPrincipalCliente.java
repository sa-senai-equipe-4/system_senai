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
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.sa.client.ClienteClient;
import br.com.senai.sa.dto.Cliente;
import br.com.senai.sa.dto.Usuario;

@Component
public class TelaPrincipalCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	
	@Lazy
	@Autowired
	private TelaLogin telaLogin;
	
	@Autowired
	private TelaEditarCliente telaEditarCliente;
	
	@Autowired
	private ClienteClient clienteClient;
	
	JTextPane txtpnUsuarioLogado = new JTextPane();
	
	private Cliente clienteLogado;
	
	public void carregarTela(Usuario usuarioLogado) {
		this.txtpnUsuarioLogado.setText(usuarioLogado.getLogin());
		this.clienteLogado = clienteClient.buscarPorUsuarioCom(usuarioLogado.getCodigo());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public TelaPrincipalCliente() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		setTitle("Principal (acesso Cliente) - SA System 1.4");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaLogin.setVisible(true);
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPanel = new JPanel();
		contentPanel.setToolTipText("");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		
		JButton btnCliente = new JButton("Cliente");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaEditarCliente.carregar(clienteLogado);
				setVisible(false);
			}
		});
		btnCliente.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Dialog", Font.BOLD, 14));
		txtpnUsuarioLogado.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		txtpnUsuarioLogado.setEditable(false);
		txtpnUsuarioLogado.setForeground(Color.BLACK);
		txtpnUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JTextPane txtpnTextoUsuarioLogado = new JTextPane();
		txtpnTextoUsuarioLogado.setBorder(new LineBorder(new Color(0, 0, 0)));
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
