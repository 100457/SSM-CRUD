package com.hello.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hello.crud.bean.Employee;
import com.hello.crud.bean.Msg;
import com.hello.crud.service.EmployeeService;

@Controller
public class EmployeeControllor {

	@Autowired
	EmployeeService employeeService;
	
	/**
	 *删除员工 
	 */
	@RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		if (ids.contains("-")) {
			String[] strIds = ids.split("-");
			List<Integer> del_ids = new ArrayList<Integer>();
			for (String str : strIds) {
				del_ids.add(Integer.parseInt(str));
			}
			employeeService.deleteBatchEmp(del_ids);

		} else {
			employeeService.deleteEmp(Integer.parseInt(ids));
		}
		return Msg.success();
	}
	
	
	/**
	 * 保存更新员工
	 */
	
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	
	/**
	 * 查询员工信息
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee emp = employeeService.getEmp(id);
		return Msg.success().add("emp", emp);
	}

	
	
	
	/**
	 * 校验用户名是否可用
	 */
	@RequestMapping(value = "/checkuser", method = RequestMethod.POST)
	@ResponseBody
	public Msg checkuser(@RequestParam("empName") String empName) {
		// 判断用户名是否符合正则表达式

		String regex = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
		if (!empName.matches(regex)) {
			// System.out.println(empName.matches(regex));
			return Msg.fail().add("va_msg", "名字必须是2-5个中文或者6-16位英文数字组合");
		}

		if (employeeService.checkUser(empName)) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	/**
	 * 查询所有员工（分页查询） 支持移动端
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 引入PageHelper分页插件
		// 查询前调用，传入页码和每页大小
		PageHelper.startPage(pn, 5);
		// startPage紧跟着的这个查询就是一个分页查询
		List<Employee> emps = employeeService.getAll();
		// PageInfo包装查询结果，封装了详细的分页信息和详细数据
		// 连续显示5页
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 查询所有员工（分页查询） 只支持浏览器
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 引入PageHelper分页插件
		// 查询前调用，传入页码和每页大小
		PageHelper.startPage(pn, 5);
		// startPage紧跟着的这个查询就是一个分页查询
		List<Employee> emps = employeeService.getAll();
		// PageInfo包装查询结果，封装了详细的分页信息和详细数据
		// 连续显示5页
		PageInfo page = new PageInfo(emps, 5);
		// 把PageInfo交给页面即可
		model.addAttribute("pageInfo", page);

		return "list";
	}
	
	/**
	 * 保存员工信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}


}
