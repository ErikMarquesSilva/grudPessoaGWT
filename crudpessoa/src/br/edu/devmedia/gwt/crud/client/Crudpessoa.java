package br.edu.devmedia.gwt.crud.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Crudpessoa implements EntryPoint {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel addPanel = new VerticalPanel();
	private HorizontalPanel sexoPanel = new HorizontalPanel();
	
	private FlexTable tbPessoas = new FlexTable();

	private Label lblNome = new Label("Nome:");
	private TextBox txtNome = new TextBox();
	
	private Label lblEndereco = new Label("Endereço:");	
	private TextBox txtEndereco = new TextBox();
	
	private Button btnCadastrar = new Button("Cadastrar");
	
	private Label lblSexo = new Label("Sexo:");
	private RadioButton rbtFem = new RadioButton("sexo", "Feminino");
	private RadioButton rbtMasc = new RadioButton("sexo", "Masculino");	

	private List<Pessoa> listaPessoas = new ArrayList<>();
	
	private int posSelecionada;
	
	public void onModuleLoad() {
		tbPessoas.setText(0, 0, "Nome");
		tbPessoas.setText(0, 1, "Endereço");		
		tbPessoas.setText(0, 2, "Sexo");		
		tbPessoas.setText(0, 3, "Alterar");
		tbPessoas.setText(0, 4, "Deletar");		
		tbPessoas.setCellPadding(5);
		tbPessoas.setCellSpacing(5);		
		tbPessoas.addStyleName("tb-pessoa");		
		
		addPanel.add(lblNome);
		addPanel.add(txtNome);
		addPanel.add(lblEndereco);
		addPanel.add(txtEndereco);
		addPanel.add(lblSexo);
		
		rbtMasc.setValue(true);
	
		sexoPanel.add(rbtMasc);
		sexoPanel.add(rbtFem);		
		
		addPanel.add(sexoPanel);
		addPanel.add(btnCadastrar);
		
		mainPanel.add(addPanel);
		mainPanel.add(tbPessoas);
		
		RootPanel.get("crud").add(mainPanel);
		
		
		btnCadastrar.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				adicionarPessoa();
				
			}
		});
		
	}

	private void adicionarPessoa(){
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome(txtNome.getText());
		pessoa.setEndereco(txtEndereco.getText());
		pessoa.setSexo(rbtMasc.getValue() ? "Masculino" : "Feminino");		
		
		listaPessoas.add(pessoa);
		
		int row = tbPessoas.getRowCount();
		
		tbPessoas.setText(row, 0, pessoa.getNome());
		tbPessoas.setText(row, 1, pessoa.getEndereco());		
		tbPessoas.setText(row, 2, pessoa.getSexo());
		
		Button btnAlterar = new Button(">");
		btnAlterar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				posSelecionada = listaPessoas.indexOf(pessoa);
				alterarPessoa(pessoa);
			}
		});
		tbPessoas.setWidget(row, 3, btnAlterar);
		
		Button btnRemover= new Button("X");
		btnRemover.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int index = listaPessoas.indexOf(pessoa);
				listaPessoas.remove(index);
				tbPessoas.removeRow(index + 1);
			}
		});
		tbPessoas.setWidget(row, 4, btnRemover);		
	}
	
	private void alterarPessoa(Pessoa pessoa){
		
		VerticalPanel mainPanel = new VerticalPanel();
		VerticalPanel addPanel = new VerticalPanel();
		HorizontalPanel sexoPanel = new HorizontalPanel();
		
		Label lblNome = new Label("Nome:");
		final TextBox txtNome = new TextBox();
		
		Label lblEndereco = new Label("Endereço:");	
		final TextBox txtEndereco = new TextBox();
		
		Label lblSexo = new Label("Sexo");
		final RadioButton rbtFem = new RadioButton("sexo", "Feminino");
		final RadioButton rbtMasc = new RadioButton("sexo", "Masculino");

		Button btnAlterar = new Button("Alterar");
		
		final DialogBox dialogBox = new DialogBox();
		btnAlterar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tbPessoas.setText(posSelecionada + 1, 0, txtNome.getText());
				tbPessoas.setText(posSelecionada + 1, 1, txtEndereco.getText());				
				tbPessoas.setText(posSelecionada + 1, 2, rbtMasc.getValue() ? "Masculino" : "Feminino");
				dialogBox.hide();
				
			}
		});		
		
		txtNome.setText(pessoa.getNome());
		txtEndereco.setText(pessoa.getEndereco());
		rbtMasc.setValue(pessoa.getSexo().equals("Masculino"));
		rbtFem.setValue(pessoa.getSexo().equals("Feminino"));		
		
		addPanel.add(lblNome);
		addPanel.add(txtNome);
		addPanel.add(lblEndereco);
		addPanel.add(txtEndereco);
		addPanel.add(lblSexo);
		
		sexoPanel.add(rbtMasc);
		sexoPanel.add(rbtFem);		
		
		addPanel.add(sexoPanel);
		addPanel.add(btnAlterar);
		
		
		mainPanel.add(addPanel);	
		
		dialogBox.setText("Editar Pessoa");
		dialogBox.setAnimationEnabled(true);

		dialogBox.add(mainPanel);
		
		dialogBox.show();
		dialogBox.center();
	}
}
