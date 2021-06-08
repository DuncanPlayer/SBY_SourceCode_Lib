package org.apache.ibatis.test;

import org.apache.ibatis.annotations.Param;

public interface PersonMapper {
	
	public Person getPerson(@Param("id")Integer id);

}
