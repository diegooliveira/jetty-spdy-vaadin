package edu.diego.vaadin;

import java.util.List;

import org.vaadin.addons.lazyquerycontainer.AbstractBeanQuery;

public class ClientQuery extends AbstractBeanQuery<Client>{

	private List<Client> clientes;

	public ClientQuery() {
	}
	
	

	@Override
	protected Client constructBean() {
		return null;
	}

	@Override
	protected List<Client> loadBeans(int startIndex, int count) {
		return clientes;//.subList(startIndex, startIndex + count);
	}

	@Override
	protected void saveBeans(List<Client> added, List<Client> modified,
			List<Client> removed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		return clientes.size();
	}


}
