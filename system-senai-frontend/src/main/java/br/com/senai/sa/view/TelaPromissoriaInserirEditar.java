package br.com.senai.sa.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DateTimeException;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
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
	private JTextArea edtDescricao = new JTextArea();
	
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
		
		this.clientesRegistrados.forEach(c -> {
			this.comboBoxClientes.addItem(c);
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
	
	public BigDecimal converter(String valor) {
		String valorFormatado = valor.replaceAll(",", ".");
		BigDecimal valorDaPromissoria = null;
		
		try {
			valorDaPromissoria = new BigDecimal(valorFormatado);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valor deve ser informada");
		}
		return valorDaPromissoria;
	}
	
	public LocalDate converter(String[] valores) {
		Integer dia = 0;
		Integer mes = 0;
		Integer ano = 0;
		try {
			dia = Integer.parseInt(valores[0]);
			mes = Integer.parseInt(valores[1]);
			ano = Integer.parseInt(valores[2]);
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Data de vencimento deve ser informada");
		}
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
		btnConsultar.setToolTipText("Ao Clicar esse botão você ira para tela de listagem de promissórias...");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				telaListagemPromissoria.setVisible(true);
				telaListagemPromissoria.setLocationRelativeTo(null);
				comboBoxClientes.removeAllItems();
				comboBoxClientes.setSelectedIndex(VAZIO);
				promissoriaSalva = new Promissoria();
				telaListagemPromissoria.dispatchEvent(
						new WindowEvent(telaListagemPromissoria, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnConsultar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblValor = new JLabel("Valor (R$)");
		lblValor.setToolTipText("Campo Obrigatório");
		lblValor.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtValor = new JFormattedTextField(new DecimalFormat("0.00"));
		edtValor.setToolTipText("Insira aqui o valor da promissória...");
		edtValor.setHorizontalAlignment(JTextField.RIGHT);
		edtValor.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtValor.setColumns(10);
		
		JLabel lblVencimento = new JLabel("Vencimento");
		lblVencimento.setToolTipText("Campo Obrigatório");
		lblVencimento.setFont(new Font("Dialog", Font.BOLD, 14));
		
		edtVencimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
		edtVencimento.setToolTipText("Insira aqui a data de vencimento da promissória...");
		edtVencimento.setFont(new Font("Dialog", Font.PLAIN, 14));
		edtVencimento.setColumns(10);
		
		JLabel lblQuitado = new JLabel("Quitado");
		lblQuitado.setToolTipText("Campo Obrigatório");
		lblQuitado.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setToolTipText("Campo Obrigatório");
		lblCliente.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setToolTipText("Ao Clicar esse botão você ira salvar os dados acima...");
		getRootPane().setDefaultButton(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (promissoriaSalva != null) {
						
						promissoriaSalva.setCliente(clienteSelecionado);
						
						promissoriaSalva.setDataDeVencimento(converter(edtVencimento.getText().split("/")));
						promissoriaSalva.setDescricao(edtDescricao.getText());
						promissoriaSalva.setQuitado((Quitado)comboBoxQuitado.getSelectedItem());
						
						promissoriaSalva.setValor(converter(edtValor.getText()));
						
						promissoriaClient.alterar(promissoriaSalva);
						JOptionPane.showMessageDialog(null, "Promissória atualizada com sucesso");
						
					}else {
						
						Promissoria novaPromissoria = new Promissoria();
						novaPromissoria.setCliente(clienteSelecionado);
						
						novaPromissoria.setDataDeVencimento(converter(edtVencimento.getText().split("/")));
						novaPromissoria.setDescricao(edtDescricao.getText());
						novaPromissoria.setQuitado((Quitado)comboBoxQuitado.getSelectedItem());
						
						novaPromissoria.setValor(converter(edtValor.getText()));
						
						promissoriaSalva = promissoriaClient.inserir(novaPromissoria);
						JOptionPane.showMessageDialog(null, "Promissória inserida com sucesso");
						
					}
				} catch (DateTimeException dtex) {
					JOptionPane.showMessageDialog(null, "Informe uma data válida");
				} catch (HttpClientErrorException ex) {
					String msg = erroFormatter.formatar(ex);
					JOptionPane.showMessageDialog(null, msg);
				} 
			}
		});
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblDescricao = new JLabel("Descrição dos Produtos e/ou Serviços");
		lblDescricao.setToolTipText("Campo Obrigatório");
		lblDescricao.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		comboBoxQuitado = new JComboBox<Quitado>();
		comboBoxQuitado.setToolTipText("Selecione sim ou não para a quitação da promissória...");
		comboBoxQuitado.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBoxQuitado.addItem(Quitado.SIM);
		comboBoxQuitado.addItem(Quitado.NAO);
		
		comboBoxClientes = new JComboBox<Cliente>();
		comboBoxClientes.setToolTipText("Selecione o cliente da promissória...");
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
							.addComponent(comboBoxClientes, 0, 732, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(0)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
							.addGap(2))
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
							.addContainerGap(278, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCliente)
							.addContainerGap(694, Short.MAX_VALUE))
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDescricao)
							.addContainerGap(478, Short.MAX_VALUE))))
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		);
		edtDescricao.setToolTipText("Insira aqui a descrição da promissória...");
		edtDescricao.setFont(new Font("Dialog", Font.PLAIN, 14));
		scrollPane.setViewportView(edtDescricao);
		edtDescricao.setWrapStyleWord(true);
		edtDescricao.setLineWrap(true);
		contentPane.setLayout(gl_contentPane);
	}
}
