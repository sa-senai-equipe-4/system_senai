package br.com.senai.sa.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.sa.dto.enums.Quitado;

public class TelaPromissoriaInserirEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtValor;
	private JTextField edtVencimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPromissoriaInserirEditar frame = new TelaPromissoriaInserirEditar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TelaPromissoriaInserirEditar() throws ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Senai\\Documents\\gustavo\\saSenai\\src\\main\\resources\\image.png"));
		setTitle("Promissória (Inserção/Edição) - SA System 1.4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnConsultar = new JButton("Consultar");
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
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblDescricao = new JLabel("Descrição dos Produtos e/ou Serviços");
		lblDescricao.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JTextPane edtDescricao = new JTextPane();
		
		JComboBox<Quitado> comboBox = new JComboBox<Quitado>();
		comboBox.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SIM", "NÃO"}));
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"TESTE1", "TESTE2"}));
		comboBox_1.setFont(new Font("Dialog", Font.BOLD, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBox_1, 0, 730, Short.MAX_VALUE)
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
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
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
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(lblCliente)
					.addGap(18)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
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
