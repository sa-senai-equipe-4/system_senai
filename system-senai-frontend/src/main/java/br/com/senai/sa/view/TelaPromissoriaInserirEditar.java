package br.com.senai.sa.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import br.com.senai.sa.client.PromissoriaClient;
import br.com.senai.sa.dto.Cliente;
import br.com.senai.sa.dto.Promissoria;
import br.com.senai.sa.dto.enums.Quitado;
import br.com.senai.sa.exception.ErroFormatter;
import lombok.Setter;

@Component
public class TelaPromissoriaInserirEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtValor;
	private JTextField edtVencimento;
	private JTextPane edtDescricao;
	private JComboBox<Cliente> comboBoxClientes;
	private JComboBox<Quitado> comboBoxQuitado;

	@Autowired
	private ErroFormatter erroFormatter;
	
	private Promissoria promissoriaSalva;
	
	private final static Integer VAZIO = -1;
	
	@Lazy
	@Autowired
	private PromissoriaClient promissoriaClient;
	
	private Cliente clienteSelecionado;
	
	@Lazy
	@Autowired
	private TelaListagemPromissoria telaListagemPromissoria;
	
	@Setter
	private List<Cliente> clientesRegistrados;
	
	public void carregarTela(Promissoria promissoriaSalva) {
		this.promissoriaSalva = promissoriaSalva;
		this.edtValor.setText(promissoriaSalva.getValor().toString());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String dataFormatada = promissoriaSalva.getDataDeVencimento().format(formatter);
		
		this.edtVencimento.setText(dataFormatada);
		this.edtDescricao.setText(promissoriaSalva.getDescricao());
		
		this.comboBoxQuitado.setSelectedItem(promissoriaSalva.getQuitado());
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void carregarCombos(List<Cliente> clientes) {
		this.clientesRegistrados = clientes;
		
		clientes.forEach(c -> {
			comboBoxClientes.addItem(c);
			if(promissoriaSalva != null && promissoriaSalva.getCliente().equals(c)) {
				this.comboBoxClientes.setSelectedItem(c);
			}
		});
		
		if(promissoriaSalva == null) {
			this.comboBoxClientes.setSelectedIndex(VAZIO);;
		}
		
	}
	
	public void modoDeInsercao() {
		this.promissoriaSalva = null;
		this.edtValor.setText("");
		this.edtDescricao.setText("");
		this.edtVencimento.setText("");
		this.comboBoxQuitado.setSelectedItem(Quitado.NAO);
		this.comboBoxClientes.setSelectedIndex(VAZIO);;
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public LocalDate converter(String[] valores) {
		Integer dia = Integer.parseInt(valores[0]);
		Integer mes = Integer.parseInt(valores[1]);
		Integer ano = Integer.parseInt(valores[2]);
		return LocalDate.of(ano, mes, dia);
	}
	
	public TelaPromissoriaInserirEditar() throws ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/image.png"));
		setTitle("Promissória (Inserção/Edição) - SA System 1.4");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaListagemPromissoria.setVisible(true);
				telaListagemPromissoria.setLocationRelativeTo(null);
				comboBoxClientes = new JComboBox<Cliente>();
				comboBoxClientes.setSelectedIndex(VAZIO);
				promissoriaSalva = new Promissoria();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				telaListagemPromissoria.setVisible(true);
				telaListagemPromissoria.setLocationRelativeTo(null);
				comboBoxClientes = new JComboBox<Cliente>();
				comboBoxClientes.setSelectedIndex(VAZIO);
				promissoriaSalva = new Promissoria();
			}
		});
		btnConsultar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblValor = new JLabel("Valor (R$)");
		lblValor.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtValor = new JTextField();
		edtValor.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtValor.setColumns(10);
		
		JLabel lblVencimento = new JLabel("Vencimento");
		lblVencimento.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtVencimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
		edtVencimento.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtVencimento.setColumns(10);
		
		JLabel lblQuitado = new JLabel("Quitado");
		lblQuitado.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSalvar = new JButton("Salvar");
		getRootPane().setDefaultButton(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (promissoriaSalva != null) {
						
						promissoriaSalva.setCliente(clienteSelecionado);
						promissoriaSalva.setDataDeVencimento(converter(edtVencimento.getText().split("/")));
						promissoriaSalva.setDescricao(edtDescricao.getText());
						promissoriaSalva.setQuitado((Quitado)comboBoxQuitado.getSelectedItem());
						promissoriaSalva.setValor(new BigDecimal(edtValor.getText()));
						
						promissoriaClient.alterar(promissoriaSalva);
						JOptionPane.showMessageDialog(contentPane, "Promissória atualizada com sucesso");
						
					}else {
						
						Promissoria novaPromissoria = new Promissoria();
						novaPromissoria.setCliente(clienteSelecionado);
						novaPromissoria.setDataDeVencimento(converter(edtVencimento.getText().split("/")));
						novaPromissoria.setDescricao(edtDescricao.getText());
						novaPromissoria.setQuitado((Quitado)comboBoxQuitado.getSelectedItem());
						novaPromissoria.setValor(new BigDecimal(edtValor.getText()));
						
						promissoriaSalva = promissoriaClient.inserir(novaPromissoria);
						JOptionPane.showMessageDialog(contentPane, "Promissória inserida com sucesso");
						
					}
					
				} catch (HttpClientErrorException ex) {
					String msg = erroFormatter.formatar(ex);
					JOptionPane.showMessageDialog(btnSalvar, msg);
				}
			}
		});
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblDescricao = new JLabel("Descrição dos Produtos e/ou Serviços");
		lblDescricao.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtDescricao = new JTextPane();
		
		comboBoxQuitado = new JComboBox<Quitado>();
		comboBoxQuitado.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBoxQuitado.addItem(Quitado.SIM);
		comboBoxQuitado.addItem(Quitado.NAO);
		
		comboBoxClientes = new JComboBox<Cliente>();
		comboBoxClientes.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBoxClientes.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
		        clienteSelecionado = ((Cliente) comboBoxClientes.getSelectedItem()); 
		    }
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBoxClientes, 0, 730, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(edtDescricao, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
						.addComponent(btnConsultar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblValor)
								.addComponent(edtValor, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVencimento)
								.addComponent(edtVencimento, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblQuitado)
								.addComponent(comboBoxQuitado, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(276, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCliente)
							.addContainerGap(692, Short.MAX_VALUE))
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDescricao)
							.addContainerGap(473, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnConsultar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor)
						.addComponent(lblVencimento)
						.addComponent(lblQuitado))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(edtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(edtVencimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(comboBoxQuitado, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(lblCliente)
					.addGap(18)
					.addComponent(comboBoxClientes, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblDescricao)
					.addGap(18)
					.addComponent(edtDescricao, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
