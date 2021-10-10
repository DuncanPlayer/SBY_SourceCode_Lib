package org.apache.ibatis.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Test {

	public static void main(String[] args) throws Exception{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession();
        try {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            List<Person> person = mapper.getPerson(1);

            if (null != person && person.size() > 0) {
                person.stream().forEach(item -> {
                    System.out.println(item);
                });
            }
        } finally {
            session.close();
        }
	}

}
