package com.br.dao;

import java.util.List;

public interface InterfaceDao<T> {

	public long save(T entidade);
    public long update(T entidade);
    public long delete(T entidade);
    public List<T> list();
    public T getEntidade(int prID);    	
}
