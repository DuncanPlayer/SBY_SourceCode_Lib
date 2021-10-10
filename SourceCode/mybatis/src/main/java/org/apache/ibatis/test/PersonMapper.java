package org.apache.ibatis.test;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonMapper {
	
	List<Person> getPerson(@Param("id")Integer id);

}
