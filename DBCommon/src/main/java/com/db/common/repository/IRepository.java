package com.db.common.repository;

public interface IRepository<T> {
	public boolean isExist(String key);
	public void save(String key, T data);
	public T find(String key);
	public void remove(String key);
	public boolean setNX(String key, T data);
}
