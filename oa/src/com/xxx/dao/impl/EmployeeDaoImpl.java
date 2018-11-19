package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.EmployeeDao;
import com.xxx.entity.Department;
import com.xxx.entity.Employee;
import com.xxx.entity.Position;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 添加员工
     * @param emp
     * @return
     */
    public int add(Employee emp) {
        //对离职日期进行判断, 如果接受到的emp离职日期为空, 就直接把null提交给数据库
        java.sql.Date leavedate2 = null;
        if(emp.getLeaveDate() != null){
            leavedate2 = new java.sql.Date(emp.getLeaveDate().getTime());
        }

        try{
            String sql = "insert into employee values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {
                    emp.getEmpid(),
                    emp.getPassword(),
                    emp.getDept().getDeptno(),
                    emp.getPos().getPosid(),
                    emp.getMgr().getEmpid(),
                    emp.getRealname(),
                    emp.getSex(),
                    new java.sql.Date(emp.getBirthday().getTime()),
                    new java.sql.Date(emp.getHireDate().getTime()),
                    leavedate2,
                    emp.getOnDuty(),
                    emp.getEmptype(),
                    emp.getPhone(),
                    emp.getQq(),
                    emp.getEmerContactperson(),
                    emp.getIdcard()
            };
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据员工的emptype查找员工
     * @param i
     * @return
     */
    public List<Employee> findByType(int i) {
        try{
            String sql = "select * from employee where emptype=?";
            return qr.query(sql, new BeanListHandler<Employee>(Employee.class), i);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找全部员工
     * @return
     */
    public List<Employee> findAll() {
        try{
            //按页面显示的内容编写sql语句
            String sql = "SELECT e.empid,e.realname,d.deptname,p.pname,e.hiredate,e.phone" +
                    " FROM employee e" +
                    " JOIN dept d" +
                    " ON e.deptno=d.deptno" +
                    " JOIN POSITION p" +
                    " ON e.posid = p.posid";
            //获得结果集
            List<Map<String, Object>> resultList = qr.query(sql, new MapListHandler());

            //并把结果集封装成Employee对象后放入集合
            List<Employee> empList = new ArrayList<>();
            for(Map<String, Object> map : resultList){
                //封装好一个部门
                Department dept = new Department();
                dept.setDeptname((String) map.get("deptname"));
                //封装好一个岗位
                Position pos = new Position();
                pos.setPname((String) map.get("pname"));
                //封装好单个Employee对象
                Employee emp= new Employee();
                emp.setEmpid((String) map.get("empid"));
                emp.setRealname((String) map.get("realname"));
                emp.setHireDate((Date) map.get("hiredate"));
                emp.setPhone((String) map.get("phone"));
                emp.setDept(dept);
                emp.setPos(pos);
                //把对象放入集合中
                empList.add(emp);
            }
            return empList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据员工的条件查找员工
     * @param emp
     * @return
     */
    public List<Employee> find(Employee emp) {
        try{
            //按页面显示的内容编写sql语句
            StringBuilder sql = new StringBuilder("SELECT e.empid,e.realname,d.deptname,p.pname,e.hiredate,e.phone" +
                    " FROM employee e" +
                    " JOIN dept d" +
                    " ON e.deptno=d.deptno" +
                    " JOIN POSITION p" +
                    " ON e.posid = p.posid" +
                    " WHERE 1=1");
            //根据参数添加查找时的判断条件
            if(emp.getEmpid() != null && !emp.getEmpid().trim().equals("")){//empid不为空, 且不为空字符串
                sql.append(" and e.empid like '%" + emp.getEmpid() + "%'");
            }
            if(0 != emp.getDept().getDeptno()){
                sql.append(" and e.deptno=" + emp.getDept().getDeptno());
            }
            //这里可能要进行完善, 目前在Servlet控制层, findEmp方法中封装的emp的onduty默认是1, 也就是在职
            sql.append(" AND e.onduty=" + emp.getOnDuty());

            //添加日期条件, 查找大于当前日期的员工
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if(emp.getHireDate() != null){
                String shireDate = df.format(emp.getHireDate());
                System.out.println(shireDate);
                sql.append(" AND DATE_FORMAT(e.hiredate, '%Y-%m-%d') >= '"+ shireDate +"'");
            }
            //获得结果集
            List<Map<String, Object>> resultList = qr.query(sql.toString(), new MapListHandler());
            //并把结果集封装成Employee对象后放入集合
            List<Employee> empList = new ArrayList<>();
            for(Map<String, Object> map : resultList){
                //封装好一个部门
                Department dept = new Department();
                dept.setDeptname((String) map.get("deptname"));
                //封装好一个岗位
                Position pos = new Position();
                pos.setPname((String) map.get("pname"));
                //封装好单个Employee对象
                Employee em= new Employee();
                em.setEmpid((String) map.get("empid"));
                em.setRealname((String) map.get("realname"));
                em.setHireDate((Date) map.get("hiredate"));
                emp.setPhone((String) map.get("phone"));
                em.setDept(dept);
                em.setPos(pos);
                //把对象放入集合中
                empList.add(em);
            }


            return empList;
        }catch (SQLException e){
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 删除员工
     * @param empid
     * @return
     */
    @Override
    public int delete(String empid) {
        try{
            String sql = "delete from employee where empid=?";
            return qr.update(sql, empid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据员工的id查找员工
     * @param empid
     * @return
     */
    @Override
    public Employee findById(String empid) {
        try {
            String sql = "select * from employee where empid=?";
            //获得一个emp对象的所有信息, 把deptno, mrgid, posid封装成响应的对象再赋给emp对象
            Map<String, Object> resultMap = qr.query(sql, new MapHandler(), empid);
            //封装dept
            Department dept = new Department();
            int deptno = (int) resultMap.get("deptno");
            dept.setDeptno(deptno);
            //封装position
            Position pos = new Position();
            int posid = (int) resultMap.get("posid");
            pos.setPosid(posid);
            //封装上级
            Employee mrg = new Employee();
            String mgrid = (String) resultMap.get("mgrid");
            mrg.setEmpid(mgrid);
            //获得其他属性
            String empid2 = (String) resultMap.get("empid");
            String password = (String) resultMap.get("password");
            String realname = (String) resultMap.get("realname");
            String sex = (String) resultMap.get("sex");
            Date birthday = (Date) resultMap.get("birthday");//觉得可能会出错, 如果数据库查出来是字符串,我不能直接转换
            Date hiredate = (Date) resultMap.get("hiredate");
            Date leavedate = (Date) resultMap.get("leavedate");
            int onDuty = (int) resultMap.get("onduty");
            int empType = (int) resultMap.get("emptype");
            String phone = (String) resultMap.get("phone");
            String qq = (String) resultMap.get("qq");
            String emerContactPerson = (String) resultMap.get("emercontactperson");
            String idcard = (String) resultMap.get("idcard");

            //封装到一个Employee对象中
            Employee e = new Employee(empid2, password, dept, pos, mrg, realname, sex, birthday, hiredate,
                    leavedate, onDuty, empType, phone, qq, emerContactPerson, idcard);
            //返回查询结果
            return e;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新员工信息
     * @param emp
     * @return
     */
    @Override
    public int update(Employee emp) {
        try{
            String sql = "update employee set password=?, deptno=?, posid=?, mgrid=?, realname=?, " +
                    "sex=?, birthday=?, hiredate=?, leavedate=?, onduty=?, emptype=?, phone=?, " +
                    "qq=?, emercontactperson=?, idcard=? where empid=?";
            Object[] params = {
                    emp.getPassword(),
                    emp.getDept().getDeptno(),
                    emp.getPos().getPosid(),
                    emp.getMgr().getEmpid(),
                    emp.getRealname(),
                    emp.getSex(),
                    new java.sql.Date(emp.getBirthday().getTime()),
                    new java.sql.Date(emp.getHireDate().getTime()),
                    new java.sql.Date(emp.getLeaveDate().getTime()),
                    emp.getOnDuty(),
                    emp.getEmptype(),
                    emp.getPhone(),
                    emp.getQq(),
                    emp.getEmerContactperson(),
                    emp.getIdcard(),
                    emp.getEmpid()
            };
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
