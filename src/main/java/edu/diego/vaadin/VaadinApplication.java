package edu.diego.vaadin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;

public class VaadinApplication extends Application {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		final Window mainWindow = new Window("Myproject Application");

		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);

		mainWindow.addComponent(new Button("Add new product",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						Label dateLabel = new Label("The time is " + new Date());
						mainWindow.addComponent(dateLabel);
						mainWindow.addComponent(new ClassificationsView());
					}
				}));
		
		
		final List<Client> clientes = loadClientes();
		BeanItemContainer<Client> conteiner = new BeanItemContainer<>(Client.class, clientes);
		
		Table table = new Table("Clientes");
		table.setContainerDataSource(conteiner);
		table.setColumnHeader("id", "id");
		table.setColumnHeader("name",    "name");
		table.setPageLength(table.size());	
		table.setVisibleColumns(new Object[]{"id", "name"});
		
		mainWindow.addComponent(table);
		setMainWindow(mainWindow);
	}
	
	@Override
	public void terminalError(com.vaadin.terminal.Terminal.ErrorEvent event) {
		super.terminalError(event);
		System.out.println(event);
	}
	
	private List<Client> loadClientes() {
		int total = 10;
		List<Client> ret = new ArrayList<>(total);
		for (int i = 0; i < total; i++) {
			ret.add(new Client(i, "name-" + i));
		}
		return ret;
	}
	

}
