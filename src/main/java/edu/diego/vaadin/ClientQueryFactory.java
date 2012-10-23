package edu.diego.vaadin;

import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

public class ClientQueryFactory implements QueryFactory {


	public ClientQueryFactory() {
	}

	@Override
	public Query constructQuery(Object[] sortPropertyIds, boolean[] sortStates) {
		return new ClientQuery();
	}

	@Override
	public void setQueryDefinition(QueryDefinition definition) {
		
	}

	
	
}
