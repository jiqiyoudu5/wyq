package cn.com.fri.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.fri.basic.biz.IBaseSQLBiz;
import cn.com.fri.basic.controller.BaseController;

@Controller
@RequestMapping("/sys/chart")
public class SYSUserChartController extends BaseController {

	@Autowired
	private IBaseSQLBiz sqlBiz;

	@RequestMapping(value = "/finduserschart", method = RequestMethod.GET)
	@ResponseBody
	public String findUserChart(HttpServletRequest request) {
		String year = request.getParameter("year");
		String month = request.getParameter("month");

		String data = "";
		if (StringUtils.isBlank(year) && StringUtils.isBlank(month)) {// 当前年月日的用户统计
			data = this.initEveryDay(year, month);
		} else {
			if (year.equals("全部")) {
				if (month.equals("全年")) {
					System.out.println("2013~2025每年");
					data = this.initEveryYear();
				} else {
					System.out.println("2013-YUE~2025-YUE每年某个月");
					data = this.initMonthOfEveryYear(month);
				}
			} else {
				if (month.equals("全年")) {
					System.out.println("某年1~12月每个月");
					data = this.initMonthOfYear(year);
				} else {
					System.out.println("某年某月的每一天");
					data = this.initEveryDay(year, month);
				}
			}
		}

		String jsonString = "{success : true,data : [" + data + "]}";
		return jsonString;
	}

	/**
	 * 某年的1-12月
	 * 
	 * @param month
	 * @return
	 */
	public String initMonthOfYear(String year) {
		String data = "";
		String sql;
		for (int i = 1; i < 13; i++) {
			String date = "";
			if (i < 10) {
				date = year + "-0" + i;
			} else {
				date = year + "-" + i;
			}
			sql = "SELECT count(*) FROM sys_user t WHERE t.regtime like '%"
					+ date + "%'";
			Long countValue = sqlBiz.findCount(sql);// 所有用户
			sql = "SELECT count(*) FROM sys_user t WHERE t.vip = 1 AND t.regtime like '%"
					+ date + "%'";
			Long vipValue = sqlBiz.findCount(sql);// VIP用户
			Long commonValue = countValue - vipValue;// 普通用户
			String listVale = "{count : " + countValue.toString()
					+ ", vipCount : " + vipValue.toString()
					+ ", commonCount : " + commonValue.toString() + ", date : "
					+ i + " }";
			data += listVale + ",";
		}
		data = data.substring(0, data.lastIndexOf(","));
		return data;
	}

	/**
	 * 2013-2025年的某月
	 * 
	 * @param month
	 * @return
	 */
	public String initMonthOfEveryYear(String month) {
		String data = "";
		String sql;
		for (int i = 2013; i < 2026; i++) {
			String date = i + "-" + month;
			sql = "SELECT count(*) FROM sys_user t WHERE t.regtime like '%"
					+ date + "%'";
			Long countValue = sqlBiz.findCount(sql);// 所有用户
			sql = "SELECT count(*) FROM sys_user t WHERE t.vip = 1 AND t.regtime like '%"
					+ date + "%'";
			Long vipValue = sqlBiz.findCount(sql);// VIP用户
			Long commonValue = countValue - vipValue;// 普通用户
			String listVale = "{count : " + countValue.toString()
					+ ", vipCount : " + vipValue.toString()
					+ ", commonCount : " + commonValue.toString() + ", date : "
					+ i + " }";
			data += listVale + ",";
		}
		data = data.substring(0, data.lastIndexOf(","));
		return data;
	}

	public String initEveryYear() {
		String data = "";
		String sql;
		for (int i = 2013; i < 2026; i++) {
			sql = "SELECT count(*) FROM sys_user t WHERE t.regtime like '%" + i
					+ "%'";
			Long countValue = sqlBiz.findCount(sql);// 所有用户
			sql = "SELECT count(*) FROM sys_user t WHERE t.vip = 1 AND t.regtime like '%"
					+ i + "%'";
			Long vipValue = sqlBiz.findCount(sql);// VIP用户
			Long commonValue = countValue - vipValue;// 普通用户
			String listVale = "{count : " + countValue.toString()
					+ ", vipCount : " + vipValue.toString()
					+ ", commonCount : " + commonValue.toString() + ", date : "
					+ i + " }";
			data += listVale + ",";
		}
		data = data.substring(0, data.lastIndexOf(","));
		return data;
	}

	/**
	 * 统计 某年某月的每一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public String initEveryDay(String year, String month) {
		String dateWhere;
		String sql;
		String data = "";
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
			cd.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 01);
		}
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int MaxDate = cd.get(Calendar.DATE);// 当前月共计天数
		log.info("本月最后一天日期::" + formatDate.format(cd.getTime()));
		cd.roll(Calendar.DATE, -MaxDate);
		for (int i = 1; i <= MaxDate; i++) {
			cd.roll(Calendar.DATE, +1);
			dateWhere = formatDate.format(cd.getTime());
			sql = "SELECT count(*) FROM sys_user t WHERE t.regtime like '%"
					+ dateWhere + "%'";
			Long countValue = sqlBiz.findCount(sql);// 所有用户
			sql = "SELECT count(*) FROM sys_user t WHERE t.vip = 1 AND t.regtime like '%"
					+ dateWhere + "%'";
			Long vipValue = sqlBiz.findCount(sql);// VIP用户
			Long commonValue = countValue - vipValue;// 普通用户
			String listVale = "{count : " + countValue.toString()
					+ ", vipCount : " + vipValue.toString()
					+ ", commonCount : " + commonValue.toString() + ", date : "
					+ i + " }";
			data += listVale + ",";
		}
		data = data.substring(0, data.lastIndexOf(","));
		return data;
	}
}
