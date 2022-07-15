package br.com.senai.sa.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.sa.dto.Cliente;

public class ClienteListagemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 2;
	
	private List<Cliente> clientes;	
	
	public ClienteListagemTableModel(List<Cliente> clientes) {	
		this.clientes = clientes;
	}

	@Override
	public int getRowCount() {
		return clientes.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		}else if (column == 1) {
			return "Nome Completo";
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	public Cliente getPor(int rowIndex) {
		return clientes.get(rowIndex);
	}
	
	public void removerPor(int rowIndex) {
		clientes.remove(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (columnIndex == 0) {
			return clientes.get(rowIndex).getCodigo();
		}else if (columnIndex == 1) {
			return clientes.get(rowIndex).getNomeCompleto();
		}
		
		throw new IllegalArgumentException("Indice inválido");
	}
	
}
