package com.hello.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hello.crud.bean.Department;
import com.hello.crud.bean.Employee;
import com.hello.crud.dao.DepartmentMapper;
import com.hello.crud.dao.EmployeeMapper;

/*
 * 测试dao层的工作
 * 使用“spring单元测试”（需要导入springtest模块）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	
	//测试DepartmentMapper
	@Test
	public void testCRUD() {
		
		//System.out.println(departmentMapper);
		//1. 插入部门
	//	departmentMapper.insertSelective(new Department(null,"开发部"));
	//	departmentMapper.insertSelective(new Department(null,"测试部"));
		//2. 生成一个员工数据
//		 employeeMapper.insertSelective(new Employee(null,"firstEmp","M","first@hello.com",1));
//		//3. 批量插入员工，使用可以执行批量操作的sqlSession操作

//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i = 0;i<1000;i++) {
//			String name = UUID.randomUUID().toString().substring(0, 5) + i;
//			mapper.insert(new Employee(null,name,"M",name+"@126.com",1));
//		}
		
	}

}
