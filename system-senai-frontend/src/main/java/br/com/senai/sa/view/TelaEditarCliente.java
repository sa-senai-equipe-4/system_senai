package br.com.senai.sa.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import br.com.senai.sa.client.ClienteClient;
import br.com.senai.sa.dto.Cliente;
import br.com.senai.sa.exception.ErroFormatter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class TelaEditarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField edtNomeCompleto;
	private JTextField edtCpf;
	private JTextField edtRg;
	private JTextField edtEndereco;
	private JTextField edtLogin;
	private JPasswordField edtSenha;
	
	@Lazy
	@Autowired
	private TelaPrincipalCliente telaPrincipalCliente;
	
	private Cliente clienteSalvo;
	
	@Autowired
	private ClienteClient clienteClient;
	
	@Autowired
	private ErroFormatter erroFormatter;
	
	public void carregar(Cliente clienteSalvo) {
		this.edtNomeCompleto.setText(clienteSalvo.getNomeCompleto());
		this.edtCpf.setText(clienteSalvo.getCpf());
		this.edtRg.setText(clienteSalvo.getRg());
		this.edtEndereco.setText(clienteSalvo.getEnderecoCompleto());
		this.edtLogin.setText(clienteSalvo.getUsuario().getLogin());
		this.edtSenha.setText(clienteSalvo.getUsuario().getSenha());
		this.clienteSalvo = clienteSalvo;
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	public TelaEditarCliente() throws ParseException {
		setTitle("Cliente (Inserção/Edição) - SA System 1.4");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaPrincipalCliente.setVisible(true);
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNomeCompleto = new JLabel("Nome Completo");
		lblNomeCompleto.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtNomeCompleto = new JTextField();
		edtNomeCompleto.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtNomeCompleto.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		edtCpf.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtCpf.setColumns(10);
		
		edtRg = new JFormattedTextField(new MaskFormatter("##.###.###"));
		edtRg.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtRg.setColumns(10);
		
		JLabel lblRg = new JLabel("RG");
		lblRg.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblEndereco = new JLabel("Endereço Completo");
		lblEndereco.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtEndereco = new JTextField();
		edtEndereco.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtEndereco.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtLogin = new JTextField();
		edtLogin.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSalvar = new JButton("Salvar");
		getRootPane().setDefaultButton(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clienteSalvo.setNomeCompleto(edtNomeCompleto.getText());
					clienteSalvo.setCpf(edtCpf.getText());
					clienteSalvo.setEnderecoCompleto(edtEndereco.getText());
					clienteSalvo.setRg(edtRg.getText());
					clienteSalvo.getUsuario().setLogin(edtLogin.getText());
					
					if(!edtSenha.getPassword().toString().isEmpty()) {
						String senha = new String(edtSenha.getPassword());
						clienteSalvo.getUsuario().setSenha(senha);
					}
					
					clienteClient.alterar(clienteSalvo);
					JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
					
				} catch (HttpClientErrorException ex) {
					String msg = erroFormatter.formatar(ex);
					JOptionPane.showMessageDialog(null, msg);
				}
			}
		});
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtSenha = new JPasswordField();
		edtSenha.setFont(new Font("Dialog", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(edtEndereco, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(lblNomeCompleto)
						.addComponent(edtNomeCompleto, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCpf))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRg)
								.addComponent(edtRg, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))
						.addComponent(lblEndereco)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLogin))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblSenha)
									.addPreferredGap(ComponentPlacement.RELATED, 333, Short.MAX_VALUE))
								.addComponent(edtSenha, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)))
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(52)
					.addComponent(lblNomeCompleto)
					.addGap(18)
					.addComponent(edtNomeCompleto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCpf)
						.addComponent(lblRg))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtRg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblEndereco)
					.addGap(18)
					.addComponent(edtEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin)
						.addComponent(lblSenha))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
