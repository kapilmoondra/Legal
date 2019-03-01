package com.legalfriend.util;

public class CustomQuery {

	public static final String CASE_COUNT_BY_MONTH = "SELECT concat(year(creation_date),'-',month(creation_date)) as month , count(id) as total FROM legal_case"
			+ " where fk_user_id  = ?1 group by month(creation_date) order by month(creation_date) asc";

	public static final String CASE_COUNT_BY_DATE = "SELECT concat(date(creation_date)) as date, count(id) as total FROM legal_case where"
			+ " fk_user_id = ?1 and creation_date>=?2 and creation_date<=?3 group by creation_date order by creation_date asc";

	public static final String CASE_COUNT_BY_WEEK = "SELECT week(creation_date) as week, count(id) as total FROM legal_case"
			+ " where fk_user_id = ?1 group by week(creation_date) order by week(creation_date) asc";

	public static final String USER_CREATED_BY_MONTH = "SELECT concat(year(creation_date),'-',month(creation_date)) as month , count(id) as total "
			+ " FROM user where client_id =?1 group by month(creation_date) order by month(creation_date) asc";

	public static final String USER_CREATED_BY_DATE = "SELECT concat(date(creation_date)) as date, count(id) as total FROM user where "
			+ " client_id =?1 and creation_date>=?2 and creation_date<=?3 group by"
			+ " creation_date order by creation_date asc";

	public static final String USER_CREATED_BY_WEEK = "SELECT week(creation_date) as week, count(id) as total FROM user"
			+ " where client_id =?1 group by week(creation_date) order by week(creation_date) asc";

	public static final String RECENT_UPDATE_CASES = "SELECT  lc.case_id, cf.file_name,date(lc.next_hearing_date), br.branch_name,"
			+ " CONCAT(u.first_name, ' ', u.last_name), lc.lastModifiedDate, r.recourse_name FROM legal_case lc"
			+ " LEFT JOIN case_files cf ON lc.case_id = cf.case_id" + " JOIN  branch br ON lc.fk_branch_id = br.id"
			+ "	JOIN  recourse r ON lc.fk_recourse_id = r.id" + "	JOIN  user u ON lc.fk_user_id = u.id"
			+ " WHERE lc.fk_user_id = ?1" + " ORDER BY lc.last_modified_date DESC";

	// public static final String RECENT_UPDATE_CASES = "SELECT lc.case_id,
	// cf.file_name, lc.next_hearing_date, br.branch_name,"
	// + " concat(u.first_name,' ',u.last_name) ,lc.lastModifiedDate,
	// r.recourse_name from legal_case lc"
	// + " join case_files cf on lc.case_id = cf.case_id" + " join branch br on
	// lc.fk_branch_id = br.id"
	// + " join recourse r on lc.fk_recourse_id = r.id" + " join user u on
	// lc.fk_user_id = u.id"
	// + " join user_relationship ur on ur.fk_user_to_id = u.id"
	// + " where ur.fk_user_from_id =?1 order by lc.last_modified_date desc
	// limit 5";

	public static final String ACTIVE_USERS = "select concat(u.first_name,' ',u.last_name) as name, ur.fk_user_role_to_id"
			+ " as designation, total, date from (select max(lt2.logindate) as date, lt2.fk_user_Id as id, count(id)"
			+ " as total from login_history lt2 group by lt2.fk_user_Id) as data" + "	join user u on data.id = u.id"
			+ "	join user_relationship ur on u.id = ur.fk_user_to_id" + "	where ur.fk_user_from_id = ?1";
	// public static final String ACTIVE_USERS = "SELECT concat(u.first_name,'
	// ',u.last_name) as name, ur.role_name as designation,
	// count(lt1.fk_user_Id)"
	// + " as total, lt3.date from login_history lt1, (select max(lt2.logindate)
	// as date, lt2.fk_user_Id as id from"
	// + " login_history lt2 group by lt2.fk_user_Id) as lt3, user u, user_role
	// ur where lt1.fk_user_Id = lt3.id and"
	// + " lt1.fk_user_Id = u.id and u.id = ur.user_id and u.id in (SELECT
	// fk_user_to_id from user_relationship where fk_user_from_id = ?1)"
	// + " group by fk_user_Id order by count(fk_user_Id) desc limit 5";

	public static final String LOGIN_COUNT_MONTH = "SELECT concat(year(lt.logindate),'-',month(lt.logindate)) as date,"
			+ "	count(lt.id) as total from login_history lt"
			+ "	join user_relationship ur on lt.fk_user_Id = ur.fk_user_to_id"
			+ "	where ur.fk_user_from_id = ?1  and ur.fk_user_role_to_id in ?2"
			+ "	group by month(logindate) order by month(logindate)";

	// public static final String LOGIN_COUNT_MONTH = "SELECT
	// concat(year(lt.logindate),'-',month(lt.logindate)) as date, count(lt.id)
	// as total"
	// + " from login_history lt join user_relationship ur on lt.fk_user_Id =
	// ur.fk_user_to_id"
	// + " join user_role urole on lt.fk_user_Id = urole.user_id where
	// ur.fk_user_from_id = ?1 and urole.role_name in(?2)"
	// + " group by month(logindate) order by month(logindate)";

	public static final String LOGIN_COUNT_WEEK = "SELECT week(logindate) as date,"
			+ "	count(lt.id) as total from login_history lt"
			+ "	join user_relationship ur on lt.fk_user_Id = ur.fk_user_to_id"
			+ "	where ur.fk_user_from_id = ?1  and ur.fk_user_role_to_id in ?2"
			+ "	group by week(logindate) order by week(logindate)";

	// public static final String LOGIN_COUNT_WEEK = "SELECT Week(logindate) as
	// date, count(lt.id) as total from login_history lt join user_relationship
	// ur"
	// + " on lt.fk_user_Id = ur.fk_user_to_id join user_role urole on
	// lt.fk_user_Id = urole.user_id where ur.fk_user_from_id = ?1 and"
	// + " urole.role_name in(?2) group by week(lt.logindate) order by
	// month(lt.logindate)";

	public static final String LOGIN_COUNT_DATE = "SELECT concat(date(logindate)) as date, count(lt.id) as total from login_history lt"
			+ "	join user_relationship ur on lt.fk_user_Id = ur.fk_user_to_id"
			+ "	where ur.fk_user_from_id = ?1 and ur.fk_user_role_to_id in ?2"
			+ "	and lt.logindate>=?3 and lt.logindate <=?4" + " group by date(logindate) order by date(logindate)";

	// public static final String LOGIN_COUNT_DATE = "SELECT
	// concat(date(logindate)) as date, count(lt.id) as total from login_history
	// lt"
	// + " join user_relationship ur on lt.fk_user_Id = ur.fk_user_to_id"
	// + " join user_role urole on lt.fk_user_Id = urole.user_id"
	// + " where ur.fk_user_from_id = ?1 and urole.role_name in(?2)"
	// + " and lt.logindate>=?3 and lt.logindate <=?4"
	// + " group by date(lt.logindate) order by date(lt.logindate)";

	public static final String BILLING_BY_INSTITUTION = "SELECT concat(year(billing_date),'-',month(billing_date)), sum(amount)"
			+ " from billing where fk_user_id=?1 group by month(billing_date) order by billing_date asc";

	// public static final String BILLING_BY_INSTITUTION = "SELECT
	// month(b.billing_date), sum(b.amount) from billing b join institution i"
	// + " on b.fk_institution_id = i.id join user_relationship ur on
	// ur.fk_user_to_id = b.fk_user_id"
	// + " where ur.fk_user_from_id=?1 group by month(b.billing_date) order by
	// b.billing_date asc";

	public static final String BILLING_BY_INSTITUTION_NAME = "SELECT concat(year(b.billing_date),'-',month(b.billing_date)), sum(b.amount) from billing b"
			+ "	join institution i on b.fk_institution_id = i.id"
			+ "	where b.fk_user_id=?1 and i.institution_name = ?2"
			+ " group by month(b.billing_date) order by b.billing_date asc";

	// public static final String BILLING_BY_INSTITUTION_NAME = "SELECT
	// month(b.billing_date), sum(b.amount) from billing b"
	// + " join institution i on b.fk_institution_id = i.id join
	// user_relationship ur on ur.fk_user_to_id = b.fk_user_id"
	// + " where ur.fk_user_from_id=?1 and i.institution_name = ?2 group by
	// month(b.billing_date) order by b.billing_date asc";

	public static final String INDIVIDUAL_BILLING = "select concat(year(billing_date),'-',month(billing_date)), sum(amount)"
			+ " from individual_billing where fk_user_id=?1"
			+ " group by month(billing_date) order by billing_date asc";

	// public static final String INDIVIDUAL_BILLING = "SELECT
	// month(b.billing_date), sum(b.amount) from individual_billing b join
	// user_relationship ur"
	// + " on ur.fk_user_to_id = b.fk_user_id where ur.fk_user_from_id=?1"
	// + " group by month(b.billing_date) order by month(b.billing_date)";

	public static final String TOTAL_CUSTOMERS = "SELECT count(id) from user_relationship where fk_user_from_id = ?1 and"
			+ "	fk_user_role_to_id = ?2";

	// public static final String TOTAL_CUSTOMERS = "SELECT count(id) from
	// user_relationship where fk_user_from_id = ?1 and "
	// + " fk_user_to_id in (select user_id from user_role where role_name =
	// ?2)";

	public static final String TOTAL_BILLING = "SELECT count(id), sum(amount) from billing where fk_user_id = ?1";

	// public static final String TOTAL_BILLING = "SELECT count(id), sum(amount)
	// from billing where fk_user_id in (SELECT fk_user_to_id from
	// user_relationship"
	// + " where fk_user_from_id = ?1)";

	public static final String TOTAL_CASES = "SELECT count(lc.id) as count from legal_case lc where lc.fk_user_id =?1";

	// public static final String TOTAL_CASES = "SELECT count(id) from
	// legal_case where fk_user_id in ( select fk_user_to_id from
	// user_relationship"
	// + " where fk_user_from_id = ?1)";

	public static final String TOTAL_LOGIN = "SELECT count(id) from login_history where month(logindate) = month(sysdate()) "
			+ "and fk_user_Id in ( select fk_user_to_id from user_relationship	where fk_user_from_id = ?1)";

	public static final String TRIAL_USERS = "SELECT u.* from user u join user_subscription us on u.id = us.fk_user_id join subscription s"
			+ " on us.fk_subscription = s.id WHERE s.name = 'TRIAL'";

	public static final String PREMIUM_USERS = "(select * from user where id in (SELECT ur.fk_user_to_id from user_relationship ur"
			+ " join user_subscription us on us.fk_user_id = ur.fk_user_from_id"
			+ " join subscription s on s.id = us.fk_subscription where s.name = 'PREMIUM'))" + "	union"
			+ " (select u.* from user u" + "	join user_subscription us on us.fk_user_id = u.id"
			+ "	join subscription s on s.id = us.fk_subscription where s.name = 'PREMIUM')";

	public static final String ORGANIZATION = "select * from user where organization is not  null";

	public static final String ORGANIZATION_COUNT = "select COUNT(distinct organization) as totalOrg from user where organization is not  null";

	public static final String TRIAL_USERS_COUNT = "SELECT COUNT(u.id) from user u join user_subscription us on u.id = us.fk_user_id join subscription s"
			+ " on us.fk_subscription = s.id WHERE s.name = 'TRIAL'";

	public static final String PREMIUM_USERS_COUNT = "Select sum(total) from("
			+ " (select count(*) as total from user where id in ("
			+ "	SELECT ur.fk_user_to_id from user_relationship ur"
			+ "	join user_subscription us on us.fk_user_id = ur.fk_user_from_id"
			+ "	join subscription s on s.id = us.fk_subscription where s.name = 'PREMIUM'))" + "	union"
			+ "	(select count(*) as total from user u" + "	join user_subscription us on us.fk_user_id = u.id"
			+ "	join subscription s on s.id = us.fk_subscription where s.name = 'PREMIUM')) as Data";

	public static final String USERS_AMONTH_INACTIVE_COUNT = "select count(id) from user where id not in (select distinct fk_user_Id from login_history"
			+ " where logindate >= sysdate() - interval 1 month)";

	public static final String USERS_AMONTH_INACTIVE = "select * from user where id not in (select distinct fk_user_Id from login_history"
			+ " where logindate >= sysdate() - interval 1 month)";

	public static final String TRIAL_EXPIRY_ORGANIZATION = " select distinct u.organization from user u join user_subscription us"
			+ " on u.id = us.fk_user_id join subscription s on us.fk_subscription = s.id where s.name = 'Trial'"
			+ " and sysdate()>= thru_date - interval 1 week";

	public static final String ORG_USERS_COUNT = "Select organization, sum(total) from ("
			+ "	select u.organization, count(urel.fk_user_to_id) as total  from user_relationship urel"
			+ "	join user u on u.id = urel.fk_user_from_id"
			+ " join user_subscription us on urel.fk_user_from_id = us.fk_user_id"
			+ " join subscription s on s.id = us.fk_subscription"
			+ "	where urel.fk_user_role_from_id = 'Admin' and s.name = 'PREMIUM'" + " group by urel.fk_user_from_id"
			+ "	union" + "	select u.organization, count(us.fk_user_id) as total from user u"
			+ " join user_subscription us on u.id = us.fk_user_id" + " join subscription s on s.id = us.fk_subscription"
			+ " where s.name = 'PREMIUM'" + " group by u.organization) as data" + " group by organization";

	public static final String ORG_TRIAL_USERS_COUNT = "select u.organization, count(urel.fk_user_to_id) from user_relationship urel"
			+ " join user u on u.id = urel.fk_user_from_id join user_role ur on urel.fk_user_to_id = ur.user_id	 join user_subscription"
			+ " us on u.id = us.fk_user_id	 join subscription s on us.fk_subscription = s.id where ur.role_name = 'Admin' AND"
			+ " s.name = 'TRIAL' group by urel.fk_user_from_id";

	public static final String ORG_USERS_DETAILS = "select u.*	 from  user u  join user_relationship urel on u.id = urel.fk_user_to_id"
			+ " join user_role ur on urel.fk_user_from_id = ur.user_id where ur.role_name = 'Admin' and fk_user_from_id=?1";

	public static final String ORG_WITH_INACTIVE_USERS = "select u.organization	 from user_relationship urel join user u on"
			+ " u.id = urel.fk_user_from_id join user_role ur on urel.fk_user_from_id = ur.user_id where ur.role_name = 'Admin' "
			+ " and  u.organization not in (select u.organization from user_relationship urel join user u on"
			+ " u.id = urel.fk_user_from_id join user_role ur on urel.fk_user_from_id = ur.user_id join login_history lt"
			+ " on urel.fk_user_to_id = lt.fk_user_Id where ur.role_name = 'Admin' and  lt.logindate>= sysdate()  - interval 1 month"
			+ " group by urel.fk_user_from_id) group by urel.fk_user_from_id";

	public static final String ORG_LEGAL_CASE_COUNT = "select u.organization, count(lc.case_id) from legal_case lc"
			+ " right join user u on lc.fk_user_id = u.id where u.organization is not null group by u.organization";

	public static final String ORG_FOR_INST_COUNT = "select u.organization, count(fc.case_id) from for_institutional_case fc"
			+ " right join user u on fc.fk_user_id = u.id where u.organization is not null group by u.organization";

	public static final String ORG_AGAINST_INST_COUNT = "select u.organization, count(ac.case_id) from against_institutional_case ac"
			+ " right join user u on ac.fk_user_id = u.id where u.organization is not null group by u.organization";

	// public static final String CUSTOMERS_DETAIL = "SELECT u.* FROM user u"
	// + " join customer_type ct on u.fk_customer_type = ct.id"
	// + " join user_relationship ur on ur.fk_user_to_id = u.id"
	// + " join user_role urole on ur.fk_user_to_id = urole.user_id"
	// + " where urole.role_name=?2 and ur.fk_user_from_id =?1";

	public static final String CUSTOMERS_DETAIL = "SELECT u.* FROM user u"
			+ "	join user_relationship ur on ur.fk_user_to_id = u.id"
			+ "	where ur.fk_user_role_to_id=?2 and ur.fk_user_from_id =?1";

	public static final String MONTH_SUBSCRIBE_USERS_COUNT = "SELECT concat(year(us.from_date),'-',month(us.from_date)) as date , count(us.fk_user_id) from user u join user_subscription us on u.id = us.fk_user_id"
			+ " join subscription s on us.fk_subscription = s.id join user_relationship ur on u.id = ur.fk_user_to_id"
			+ "	join user_role urole on us.fk_user_id = urole.user_id WHERE s.name = ?2 and ur.fk_user_from_id =?1"
			+ " and urole.role_name in (?3) group by month(us.from_date) order by month(us.from_date)";

	public static final String WEEK_SUBSCRIBE_USERS_COUNT = "SELECT concat(week(us.from_date)) as date , count(us.fk_user_id) from user u join user_subscription us on u.id = us.fk_user_id"
			+ " join subscription s on us.fk_subscription = s.id join user_relationship ur on u.id = ur.fk_user_to_id"
			+ "	join user_role urole on us.fk_user_id = urole.user_id WHERE s.name = ?2 and ur.fk_user_from_id =?1"
			+ " and urole.role_name in (?3) group by week(us.from_date) order by week(us.from_date)";

	public static final String DATE_SUBSCRIBE_USERS_COUNT = "SELECT concat(date(us.from_date)) as date , count(us.fk_user_id) from user u join user_subscription us on u.id = us.fk_user_id"
			+ "	join subscription s on us.fk_subscription = s.id join user_relationship ur on u.id = ur.fk_user_to_id"
			+ "	join user_role urole on us.fk_user_id = urole.user_id WHERE s.name = ?2 and ur.fk_user_from_id =?1 and urole.role_name in (?3)"
			+ " and us.from_date>=?4 and us.from_date<=?5 group by date(us.from_date) order by date(us.from_date)";

	public static final String ORG_BRANCHES = "select b.branch_name, count(b.fk_user_id) from branch b join user_relationship ur on"
			+ " ur.fk_user_to_id = b.fk_user_id where ur.fk_user_from_id =?1 group by b.branch_name";

	public static final String INSTITUTIONAL_CASES_MONTH = "Select concat(monthvalue), count(num) from ( select"
			+ " concat(year(fc.creation_date),'-',month(fc.creation_date)) as monthvalue , count(fc.id) as num from"
			+ " for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by month(fc.creation_date) union all select concat(year(ac.creation_date),'-',month(ac.creation_date))"
			+ " as monthvalue , count(ac.id) as num from against_institutional_case ac"
			+ " join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1 group by month(ac.creation_date))	 as institution"
			+ " group by monthvalue order by monthvalue";

	public static final String INSTITUTIONAL_CASES_WEEK = "Select concat(weekvalue), count(num) from ( select week(fc.creation_date) as weekvalue ,"
			+ " count(fc.id) as num from for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by week(fc.creation_date) union all select week(ac.creation_date) as monthvalue , count(ac.id)"
			+ " as num from against_institutional_case ac join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by week(ac.creation_date)) as institution group by weekvalue order by weekvalue";

	public static final String INSTITUTIONAL_CASES_DATE = "Select concat(datevalue), count(num) from ( select date(fc.creation_date) as datevalue ,"
			+ " count(fc.id) as num from for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 and fc.creation_date>=?2 and fc.creation_date<=?3 group by date(fc.creation_date)"
			+ " union all select date(ac.creation_date) as monthvalue , count(ac.id) as num from against_institutional_case ac"
			+ " join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1 and ac.creation_date>=?2"
			+ " and ac.creation_date<=?3 group by date(ac.creation_date)) as institution group by datevalue order by datevalue";

	public static final String ALL_CASES_MONTH = "Select monthvalue, count(num) from (select concat(year(fc.creation_date),'-',month(fc.creation_date)) as monthvalue , count(fc.id) as num"
			+ " from for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by month(fc.creation_date) union all"
			+ " select concat(year(ac.creation_date),'-',month(ac.creation_date)) as monthvalue , count(ac.id) as num from against_institutional_case ac"
			+ " join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1 group by"
			+ " month(ac.creation_date) union all select concat(year(lc.creation_date),'-',month(lc.creation_date)) as monthvalue , count(lc.id) as num from legal_case lc"
			+ " join user_relationship ur on lc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by month(lc.creation_date))"
			+ " as institution group by monthvalue order by monthvalue";

	public static final String ALL_CASES_WEEK = "Select weekvalue, count(num) from ( select week(fc.creation_date) as weekvalue , count(fc.id) as num"
			+ " from for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by week(fc.creation_date) union all"
			+ " select week(ac.creation_date) as monthvalue , count(ac.id) as num from against_institutional_case ac"
			+ " join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1"
			+ " group by week(ac.creation_date) union all"
			+ " select week(lc.creation_date) as monthvalue , count(lc.id) as num from legal_case lc"
			+ " join user_relationship ur on lc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 group by week(lc.creation_date))"
			+ " as institution group by weekvalue order by weekvalue";

	public static final String ALL_CASES_DATE = "Select datevalue, count(num) from ( select date(fc.creation_date) as datevalue , count(fc.id) as num"
			+ " from for_institutional_case fc join user_relationship ur on fc.fk_user_id = ur.fk_user_to_id"
			+ " where ur.fk_user_from_id =?1 and fc.creation_date>=?2 and fc.creation_date<=?3"
			+ " group by date(fc.creation_date) union all"
			+ " select date(ac.creation_date) as monthvalue , count(ac.id) as num from against_institutional_case ac"
			+ " join user_relationship ur on ac.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1"
			+ " and ac.creation_date>=?2 and ac.creation_date<=?3" + " group by date(ac.creation_date) union all"
			+ " select date(lc.creation_date) as monthvalue , count(lc.id) as num from legal_case lc"
			+ " join user_relationship ur on lc.fk_user_id = ur.fk_user_to_id where ur.fk_user_from_id =?1"
			+ " and lc.creation_date>=?2 and lc.creation_date<=?3 group by date(lc.creation_date))"
			+ " as institution group by datevalue order by datevalue";

	// public static final String BRANCH_BILLING = "Select date, sum(total) from
	// ("
	// + " (select concat(year(b.billing_date),'-',month(b.billing_date)) as
	// date, sum(b.amount)"
	// + " as total from branch br join user_relationship ur on ur.fk_user_to_id
	// = br.fk_user_id"
	// + " join institution i on i.fk_user_id = ur.fk_user_from_id"
	// + " join billing b on b.fk_institution_id = i.id"
	// + " where ur.fk_user_from_id =?1 and b.billing_date>=?2 and
	// b.billing_date<=?3"
	// + " group by month(b.billing_date) order by month(b.billing_date))" + "
	// union"
	// + " (select concat(year(b.billing_date),'-',month(b.billing_date)) as
	// date, sum(b.amount)"
	// + " as total from branch br join user_relationship ur on ur.fk_user_to_id
	// = br.fk_user_id"
	// + " join individual_billing b on b.fk_user_id = ur.fk_user_to_id"
	// + " where ur.fk_user_from_id =?1 and b.billing_date>=?2 and
	// b.billing_date<=?3"
	// + " group by month(b.billing_date) order by month(b.billing_date))) as
	// Billing_DATA"
	// + " group by date order by date";

//	public static final String BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
//			+ ",'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
//			+ " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join institution i on i.fk_user_id = ur.fk_user_from_id"
//			+ " join billing b on b.fk_institution_id = i.id"
//			+ " where ur.fk_user_from_id =?1 and b.billing_date>=?2  and b.billing_date<=?3"
//			+ " group by month(b.billing_date) order by month(b.billing_date))" + " union"
//			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
//			+ " as total from branch br" + " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join individual_billing b on b.fk_user_id = ur.fk_user_from_id"
//			+ " where ur.fk_user_from_id =?1 and b.billing_date>=?2  and b.billing_date<=?3"
//			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
//			+ "	group by date order by date";

	public static final String BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
	+ " ,'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
	+ " join billing b on b.fk_branch_id = br.id"
	+ " join institution i on i.id = b.fk_institution_id"
	+ " where br.fk_user_id =?1  and b.billing_date>=?2  and b.billing_date<=?3"
	+ " group by month(b.billing_date) order by month(b.billing_date))"
	+ " union"
	+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
	+ " as total from branch br join individual_billing b on b.fk_branch_id = br.id"
	+ " where br.fk_user_id =?1 and b.billing_date>=?2  and b.billing_date<=?3"
	+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
	+ " group by date order by date";
	
//	public static final String YEAR_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
//			+ ",'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
//			+ " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join institution i on i.fk_user_id = ur.fk_user_from_id"
//			+ " join billing b on b.fk_institution_id = i.id"
//			+ " where ur.fk_user_from_id =?1 and year(b.billing_date)=?2"
//			+ " group by month(b.billing_date) order by month(b.billing_date))" + " union"
//			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
//			+ " as total from branch br" + " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join individual_billing b on b.fk_user_id = ur.fk_user_from_id"
//			+ " where ur.fk_user_from_id =?1 and year(b.billing_date)=?2"
//			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
//			+ "	group by date order by date";

	public static final String YEAR_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
	+ " ,'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
	+ " join billing b on b.fk_branch_id = br.id"
	+ " join institution i on i.id = b.fk_institution_id"
	+ " where br.fk_user_id =?1  and year(b.billing_date)=?2"
	+ " group by month(b.billing_date) order by month(b.billing_date))"
	+ " union"
	+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
	+ " as total from branch br join individual_billing b on b.fk_branch_id = br.id"
	+ " where br.fk_user_id =?1 and year(b.billing_date)=?2"
	+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
	+ " group by date order by date";

//	public static final String SELECTED_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
//			+ ",'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
//			+ " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join institution i on i.fk_user_id = ur.fk_user_from_id"
//			+ " join billing b on b.fk_institution_id = i.id"
//			+ " where ur.fk_user_from_id =?1 and br.branch_name in (?2) and b.billing_date>=?3 and b.billing_date<=?4"
//			+ " group by month(b.billing_date) order by month(b.billing_date))" + " union"
//			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
//			+ " as total from branch br" + " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join individual_billing b on b.fk_user_id = ur.fk_user_from_id"
//			+ " where ur.fk_user_from_id =?1 and br.branch_name in (?2) and b.billing_date>=?3 and b.billing_date<=?4"
//			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
			//+ "	group by date order by date";
	
	public static final String SELECTED_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
			+ " ,'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
			+ " join billing b on b.fk_branch_id = br.id"
			+ " join institution i on i.id = b.fk_institution_id"
			+ " where br.fk_user_id =?1  and b.billing_date>=?3 and b.billing_date<=?4 and br.branch_name in (?2)"
			+ " group by month(b.billing_date) order by month(b.billing_date))"
			+ " union"
			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
			+ " as total from branch br join individual_billing b on b.fk_branch_id = br.id"
			+ " where br.fk_user_id =?1 and b.billing_date>=?3 and b.billing_date<=?4 and br.branch_name in (?2)"
			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
			+ " group by date order by date";

	public static final String YEAR_SELECTED_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
			+ " ,'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
			+ " join billing b on b.fk_branch_id = br.id"
			+ " join institution i on i.id = b.fk_institution_id"
			+ " where br.fk_user_id =?1  and year(b.billing_date)=?3 and br.branch_name in (?2)"
			+ " group by month(b.billing_date) order by month(b.billing_date))"
			+ " union"
			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
			+ " as total from branch br join individual_billing b on b.fk_branch_id = br.id"
			+ " where br.fk_user_id =?1 and year(b.billing_date)=?3 and br.branch_name in (?2)"
			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
			+ " group by date order by date";

//	public static final String YEAR_SELECTED_BRANCH_BILLING = "Select date, sum(total) from ((select concat(year(b.billing_date)"
//			+ ",'-',month(b.billing_date)) as date, sum(b.amount) as total from branch br"
//			+ " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join institution i on i.fk_user_id = ur.fk_user_from_id"
//			+ " join billing b on b.fk_institution_id = i.id"
//			+ " where ur.fk_user_from_id =?1 and br.branch_name in (?2) and year(b.billing_date)=?3"
//			+ " group by month(b.billing_date) order by month(b.billing_date))" + " union"
//			+ " (select concat(year(b.billing_date),'-',month(b.billing_date)) as date, sum(b.amount)"
//			+ " as total from branch br" + " join user_relationship ur on ur.fk_user_from_id = br.fk_user_id"
//			+ " join individual_billing b on b.fk_user_id = ur.fk_user_from_id"
//			+ " where ur.fk_user_from_id =?1 and br.branch_name in (?2) and year(b.billing_date)=?3"
//			+ " group by month(b.billing_date) order by month(b.billing_date))) as Billing_DATA"
//			+ "	group by date order by date";


	public static final String SEL_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and b.billing_date>=?3 and b.billing_date<=?4"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and b.billing_date>=?3 and b.billing_date<=?4"
			+ " GROUP BY br.fk_user_id)"; 
			
//			
//			"(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)"
//			+ " and b.billing_date>=?3 and b.billing_date<=?4 GROUP BY i.institution_name"
//			+ " ORDER BY i.institution_name) UNION (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)"
//			+ " and b.billing_date>=?3 and b.billing_date<=?4" + " GROUP BY ur.fk_user_from_id)";

	public static final String YEAR_SEL_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and year(b.billing_date)=?3"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and year(b.billing_date)=?3"
			+ " GROUP BY br.fk_user_id)"; 
	
//	public static final String YEAR_SEL_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)"
//			+ " and year(b.billing_date)=?3 GROUP BY i.institution_name"
//			+ " ORDER BY i.institution_name) UNION (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2) and year(b.billing_date)=?3"
//			+ " GROUP BY ur.fk_user_from_id)";

	// public static final String BRANCH_INST_BILLING = "(SELECT
	// i.institution_name AS Institutions,"
	// + " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON
	// ur.fk_user_to_id = br.fk_user_id"
	// + " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing
	// b ON b.fk_institution_id = i.id"
	// + " WHERE ur.fk_user_from_id = ?1 and b.billing_date>=?2 and
	// b.billing_date<=?3 GROUP BY i.institution_name"
	// + " ORDER BY i.institution_name) UNION (SELECT 'Individual' AS
	// Institutions, SUM(b.amount) AS amount"
	// + " FROM branch br JOIN user_relationship ur ON ur.fk_user_to_id =
	// br.fk_user_id"
	// + " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_to_id"
	// + " WHERE ur.fk_user_from_id = ?1 and b.billing_date>=?2 and
	// b.billing_date<=?3"
	// + " GROUP BY ur.fk_user_from_id)";
	
	public static final String BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 and b.billing_date>=?2 and b.billing_date<=?3"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 and b.billing_date>=?2 and b.billing_date<=?3"
			+ " GROUP BY br.fk_user_id)";

//	public static final String BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1  and b.billing_date>=?2 and b.billing_date<=?3 GROUP BY i.institution_name"
//			+ " ORDER BY i.institution_name)" + " UNION"
//			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 and b.billing_date>=?2 and b.billing_date<=?3 GROUP BY ur.fk_user_from_id)";

	public static final String YEAR_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND year(b.billing_date)=?2"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND year(b.billing_date)=?2"
			+ " GROUP BY br.fk_user_id)";
	
//	public static final String YEAR_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1  and year(b.billing_date)= ?2 GROUP BY i.institution_name"
//			+ " ORDER BY i.institution_name)" + " UNION"
//			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 and year(b.billing_date)= ?2  GROUP BY ur.fk_user_from_id)";

	// public static final String YEAR_BRANCH_INST_BILLING = "(SELECT
	// i.institution_name AS Institutions,"
	// + " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON
	// ur.fk_user_to_id = br.fk_user_id"
	// + " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing
	// b ON b.fk_institution_id = i.id"
	// + " WHERE ur.fk_user_from_id = ?1 and year(b.billing_date)= ?2 GROUP BY
	// i.institution_name"
	// + " ORDER BY i.institution_name) UNION (SELECT 'Individual' AS
	// Institutions, SUM(b.amount) AS amount"
	// + " FROM branch br JOIN user_relationship ur ON ur.fk_user_to_id =
	// br.fk_user_id"
	// + " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_to_id"
	// + " WHERE ur.fk_user_from_id = ?1 and year(b.billing_date)= ?2" + " GROUP
	// BY ur.fk_user_from_id)";

	public static final String YEAR_MONTH_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND year(b.billing_date)=?3 and LEFT(monthname(b.billing_date),3) = ?2"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND year(b.billing_date)=?3 and LEFT(monthname(b.billing_date),3) = ?2"
			+ " GROUP BY br.fk_user_id)";
	
//	public static final String YEAR_MONTH_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1  and LEFT(monthname(b.billing_date),3) = ?2"
//			+ " and year(b.billing_date)= ?3 GROUP BY i.institution_name" + " ORDER BY i.institution_name)" + " UNION"
//			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 and LEFT(monthname(b.billing_date),3) = ?2"
//			+ " and year(b.billing_date)= ?3  GROUP BY ur.fk_user_from_id)";

	// public static final String MONTH_BRANCH_INST_BILLING = "(SELECT
	// i.institution_name AS Institutions,"
	// + " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON
	// ur.fk_user_to_id = br.fk_user_id"
	// + " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing
	// b ON b.fk_institution_id = i.id"
	// + " WHERE ur.fk_user_from_id = ?1 and LEFT(monthname(b.billing_date),3) =
	// ?2"
	// + " and b.billing_date>=?3 and b.billing_date<=?4 GROUP BY
	// i.institution_name"
	// + " ORDER BY i.institution_name) UNION (SELECT 'Individual' AS
	// Institutions, SUM(b.amount) AS amount"
	// + " FROM branch br JOIN user_relationship ur ON ur.fk_user_to_id =
	// br.fk_user_id"
	// + " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_to_id"
	// + " WHERE ur.fk_user_from_id = ?1 and LEFT(monthname(b.billing_date),3) =
	// ?2"
	// + " and b.billing_date>=?3 and b.billing_date<=?4" + " GROUP BY
	// ur.fk_user_from_id)";

	public static final String MONTH_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 and LEFT(monthname(b.billing_date),3) = ?2 and b.billing_date>=?3 and b.billing_date<=?4"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 and LEFT(monthname(b.billing_date),3) = ?2 and b.billing_date>=?3 and b.billing_date<=?4"
			+ " GROUP BY br.fk_user_id)";
	
//	public static final String MONTH_BRANCH_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ " WHERE ur.fk_user_from_id = ?1 and LEFT(monthname(b.billing_date),3) = ?2"
//			+ " and b.billing_date>=?3 and b.billing_date<=?4 GROUP BY i.institution_name"
//			+ " ORDER BY i.institution_name)" + " UNION"
//			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ " FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ " WHERE ur.fk_user_from_id = ?1 and LEFT(monthname(b.billing_date),3) = ?2 "
//			+ " and b.billing_date>=?3 and b.billing_date<=?4 GROUP BY ur.fk_user_from_id)";


	public static final String SEL_MONTH_BR_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and LEFT(monthname(b.billing_date),3) = ?3"
			+ " and b.billing_date>=?4 and b.billing_date<=?5"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) and LEFT(monthname(b.billing_date),3) = ?3"
			+ " and b.billing_date>=?4 and b.billing_date<=?5"
			+ " GROUP BY br.fk_user_id)";
	
//	public static final String SEL_MONTH_BR_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ "	WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)"
//			+ " and LEFT(monthname(b.billing_date),3) = ?3" + " and b.billing_date>=?4 and b.billing_date<=?5"
//			+ "	GROUP BY i.institution_name"
//			+ "	ORDER BY i.institution_name) UNION (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ "	FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ "	JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ "	WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)"
//			+ "	and LEFT(monthname(b.billing_date),3) = ?3" + " and b.billing_date>=?4 and b.billing_date<=?5"
//			+ "	GROUP BY ur.fk_user_from_id)";

	public static final String YEAR_SEL_MONTH_BR_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
			+ " SUM(b.amount) AS amount FROM branch br"
			+ " JOIN billing b on br.id= b.fk_branch_id"
			+ " JOIN institution i ON i.id = b.fk_institution_id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) AND year(b.billing_date)=?4 and LEFT(monthname(b.billing_date),3) = ?3"
			+ " GROUP BY i.institution_name ORDER BY i.institution_name)"
			+ " UNION"
			+ " (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
			+ " FROM branch br JOIN individual_billing b ON b.fk_branch_id = br.id"
			+ " WHERE br.fk_user_id = ?1 AND br.branch_name IN (?2) AND year(b.billing_date)=?4 and LEFT(monthname(b.billing_date),3) = ?3"
			+ " GROUP BY br.fk_user_id)";
	
//	public static final String YEAR_SEL_MONTH_BR_INST_BILLING = "(SELECT i.institution_name AS Institutions,"
//			+ " SUM(b.amount) AS amount FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ " JOIN institution i ON i.fk_user_id = ur.fk_user_from_id JOIN billing b ON b.fk_institution_id = i.id"
//			+ "	WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)" + " and year(b.billing_date)=?4"
//			+ " and LEFT(monthname(b.billing_date),3) = ?3" + "	GROUP BY i.institution_name"
//			+ "	ORDER BY i.institution_name) UNION (SELECT 'Individual' AS Institutions, SUM(b.amount) AS amount"
//			+ "	FROM branch br JOIN user_relationship ur ON ur.fk_user_from_id = br.fk_user_id"
//			+ "	JOIN individual_billing b ON b.fk_user_id = ur.fk_user_from_id"
//			+ "	WHERE ur.fk_user_from_id = ?1 AND br.branch_name IN (?2)" + " and year(b.billing_date)=?4"
//			+ "	and LEFT(monthname(b.billing_date),3) = ?3" + "	GROUP BY ur.fk_user_from_id)";


	public static final String ALL_USERS_MONTH = "SELECT concat(year(us.from_date),'-',month(us.from_date)) as date ,"
			+ " count(us.fk_user_id) from user u join user_subscription us on u.id = us.fk_user_id"
			+ " join user_relationship ur on u.id = ur.fk_user_from_id"
			+ " WHERE ur.fk_user_from_id =?1 and ur.fk_user_role_to_id in ?2"
			+ " and us.from_date>= sysdate()-interval 3 month group by month(us.from_date)"
			+ " order by month(us.from_date)";

	// public static final String ALL_USERS_WEEK = "SELECT
	// concat(week(us.from_date)) as date ,"
	// + " count(us.fk_user_id) from user u join user_subscription us on u.id =
	// us.fk_user_id"
	// + " join user_relationship ur on u.id = ur.fk_user_to_id"
	// + " join user_role urole on us.fk_user_id = urole.user_id"
	// + " WHERE ur.fk_user_from_id =?1 and urole.role_name in (?2)"
	// + " and us.from_date>= sysdate()-interval 3 month group by
	// week(us.from_date) order by week(us.from_date)";

	public static final String ALL_USERS_WEEK = "SELECT week(us.from_date) as date ,"
			+ "	 count(us.fk_user_id) from user u join user_subscription us on u.id = us.fk_user_id"
			+ "	 join user_relationship ur on u.id = ur.fk_user_from_id"
			+ "	 WHERE ur.fk_user_from_id =?1 and ur.fk_user_role_to_id in ?2"
			+ "	 and us.from_date>= sysdate()-interval 3 month group by week(us.from_date) order by week(us.from_date)";

	// public static final String ALL_USERS_DATE = "SELECT
	// concat(date(us.from_date)) as date, count(us.fk_user_id) from"
	// + " user u join user_subscription us on u.id = us.fk_user_id join
	// user_relationship ur on u.id = ur.fk_user_to_id"
	// + " join user_role urole on us.fk_user_id = urole.user_id WHERE
	// ur.fk_user_from_id =?1"
	// + " and urole.role_name in (?2) and us.from_date>= ?3"
	// + " and us.from_date<=?4 and us.from_date>= ?4- interval 3 month"
	// + " group by date(us.from_date) order by date(us.from_date)";

	public static final String ALL_USERS_DATE = "SELECT concat(date(us.from_date)) as date , count(us.fk_user_id)"
			+ " from user u join user_subscription us on u.id = us.fk_user_id"
			+ " join user_relationship ur on u.id = ur.fk_user_from_id"
			+ " WHERE ur.fk_user_from_id =?1 and ur.fk_user_role_to_id in ?2"
			+ " and us.from_date>= ?3 and us.from_date<=?4" + " and us.from_date>= ?4 - interval 3 month"
			+ " group by date(us.from_date) order by date(us.from_date)";

	public static final String YEAR_INVOICE_AMOUNT = "select concat(year(i.created_date),'-',month(i.created_date)) as date,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and year(i.created_date) = ?2"
			+ " group by month(i.created_date) order by month(i.created_date)";
//	public static final String YEAR_INVOICE_AMOUNT = "select concat(year(created_date),'-',month(created_date)) as date,"
//			+ " sum(amount) as amount from invoice where fk_user_id =?1 and year(created_date) = ?2"
//			+ " group by month(created_date) order by month(created_date)";

	public static final String DATE_INVOICE_AMOUNT = "select concat(year(i.created_date),'-',month(i.created_date)) as date,"
					+ " sum(i.amount) as amount from invoice i"
					+ " join branch br on br.id = i.fk_branch_id"
					+ " where i.fk_user_id =?1 and created_date>=?2 and created_date<=?3"
					+ " group by month(i.created_date) order by month(i.created_date)";
					
	public static final String YEAR_INVOICE_INST = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and year(i.created_date) =?2 group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and year(i.created_date) =?2 group by i.fk_user_id) as invoice_data";


	public static final String DATE_INVOICE_INST = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and i.created_date>=?2 and i.created_date<=?3 group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and i.created_date>=?2 and i.created_date<=?3 group by i.fk_user_id) as invoice_data";
	

	public static final String YEAR_INVOICE_INST_MONTH = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and year(i.created_date) =?2 and LEFT(monthname(created_date),3) = ?3 group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and year(i.created_date) =?2 and LEFT(monthname(created_date),3) = ?3 group by i.fk_user_id) as invoice_data";
	
	public static final String DATE_INVOICE_INST_MONTH = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and LEFT(monthname(i.created_date),3) = ?4 group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and LEFT(monthname(i.created_date),3) = ?4 group by i.fk_user_id) as invoice_data";
	
//	public static final String DATE_INVOICE_INST_MONTH = "Select * from( select inst.institution_name as Institution,"
//			+ " sum(amount) as amount from invoice i" + " join institution inst on i.fk_institution_id = inst.id"
//			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
//			+ " and i.created_date>=?2 and i.created_date<=?3 and LEFT(monthname(i.created_date),3) = ?4 group by i.fk_institution_id"
//			+ " union" + " select 'Individual' as Institution, sum(amount) as amount from invoice"
//			+ " where fk_user_id =?1 and fk_institution_id is null"
//			+ " and created_date>=?2 and created_date<=?3 and LEFT(monthname(created_date),3) = ?4 group by fk_user_id) as invoice_data";

	public static final String CASES_WITH_HEARING_DATE = "SELECT u.email,concat(u.first_name,' ',u.last_name),"
			+ " concat(lc.next_hearing_date), lc.case_Id, lc.title FROM legal_case lc"
			+ "	JOIN user u on lc.fk_user_id = u.id" + " WHERE date(lc.next_hearing_date)>=date(sysdate())"
			+ " and lc.next_hearing_date<=sysdate() + interval 3 day order by u.email";

	public static final String FOR_INST_HEARING_DATE = "SELECT u.email, concat(u.first_name,' ',u.last_name),"
			+ " fc.case_Id, fc.title FROM for_institutional_case fc" + " JOIN user u on fc.fk_user_id = u.id"
			+ "	WHERE date(fc.next_hearing_date)>=date(sysdate())"
			+ " and fc.next_hearing_date<=sysdate() + interval 3 day order by u.email";

	public static final String AGAINST_INST_HEARING_DATE = "SELECT u.email, concat(u.first_name,' ',u.last_name),"
			+ " ac.case_Id, ac.title FROM against_institutional_case ac" + "	JOIN user u on ac.fk_user_id = u.id"
			+ "	WHERE date(ac.next_hearing_date)>=date(sysdate())"
			+ " and ac.next_hearing_date<=sysdate() + interval 3 day order by u.email";

	public static final String ALL_LEGAL_CASES_YEAR = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM legal_case" + " where year(creation_date) = ?1"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_LEGAL_CASES_DATE = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM legal_case" + " where creation_date >= ?1 and creation_date<=?2"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_FOR_CASES_YEAR = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM for_institutional_case" + " where year(creation_date) = ?1"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_FOR_CASES_DATE = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM for_institutional_case"
			+ " where creation_date >= ?1 and creation_date<=?2"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_AGAINST_CASES_YEAR = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM against_institutional_case" + " where year(creation_date) = ?1"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_AGAINST_CASES_DATE = "SELECT concat(year(creation_date),'-',month(creation_date))"
			+ " as month , count(id) as total FROM against_institutional_case"
			+ " where creation_date >= ?1 and creation_date<=?2"
			+ " group by month(creation_date) order by month(creation_date) asc";

	public static final String ALL_TRIAL_USERS_YEAR = "SELECT concat(year(us.from_date),'-',month(us.from_date)), count(u.id)"
			+ " from user u join user_subscription us on u.id = us.fk_user_id join subscription s"
			+ " on us.fk_subscription = s.id WHERE s.name = 'TRIAL'"
			+ " and year(us.from_date)=?1  group by month(us.from_date)";

	public static final String ALL_TRIAL_USERS_DATE = "SELECT concat(year(us.from_date),'-',month(us.from_date)), count(u.id)"
			+ " from user u join user_subscription us on u.id = us.fk_user_id join subscription s"
			+ " on us.fk_subscription = s.id WHERE s.name = 'TRIAL'"
			+ " and date(us.from_date)>=?1 and date(us.from_date)<=?2 group by month(us.from_date)";

	public static final String ALL_PREMIUM_USERS_YEAR = "Select date, sum(total) from("
			+ " SELECT concat(year(u.creation_date),'-',month(u.creation_date)) as date, count(u.id) as total from user u"
			+ " join user_relationship ur on u.id = ur.fk_user_from_id"
			+ " join user_subscription us on ur.fk_user_from_id = us.fk_user_id"
			+ " join subscription s on us.fk_subscription = s.id"
			+ " WHERE s.name = 'PREMIUM' and year(us.from_date)=?1" + " group by month(u.creation_date)" + " union"
			+ " SELECT concat(year(us.from_date),'-',month(us.from_date)) as date, count(u.id) as total from user u"
			+ " join user_subscription us on u.id = us.fk_user_id" + " join subscription s on us.fk_subscription = s.id"
			+ " WHERE s.name = 'PREMIUM' and year(us.from_date)=?1" + " group by month(us.from_date)) as Data"
			+ " group by date order by date";

	public static final String ALL_PREMIUM_USERS_DATE = "Select date, sum(total) from("
			+ " SELECT concat(year(u.creation_date),'-',month(u.creation_date)) as date, count(u.id) as total from user u"
			+ " join user_relationship ur on u.id = ur.fk_user_from_id"
			+ " join user_subscription us on ur.fk_user_from_id = us.fk_user_id"
			+ " join subscription s on us.fk_subscription = s.id"
			+ " WHERE s.name = 'PREMIUM' and date(u.creation_date)>=?1 and date(u.creation_date)<=?2"
			+ " group by month(u.creation_date)" + " union"
			+ " SELECT concat(year(us.from_date),'-',month(us.from_date)) as date, count(u.id) as total from user u"
			+ " join user_subscription us on u.id = us.fk_user_id" + " join subscription s on us.fk_subscription = s.id"
			+ " WHERE s.name = 'PREMIUM' and date(us.from_date)>=?1 and date(us.from_date)<=?2"
			+ " group by month(us.from_date)) as Data" + " group by date order by date";

	public static final String ADMIN_PREMIUM_COUNT = "select count(u.id) from user u"
			+ "	join user_subscription us on u.id = us.fk_user_id"
			+ "	join subscription s on s.id = us.fk_subscription" + "	where s.name = 'PREMIUM'";

	public static final String PREMIUM_USER_BYROLE_COUNT = "select ur.fk_user_role_to_id , count(ur.fk_user_to_id) from user_relationship ur"
			+ "	join user_subscription us on ur.fk_user_from_id = us.fk_user_id"
			+ " join subscription s on s.id = us.fk_subscription"
			+ "	where s.name = 'PREMIUM' and ur.fk_user_role_to_id in ('MANAGER','CLIENT','EMPLOYEE')"
			+ "	group by ur.fk_user_role_to_id order by ur.fk_user_role_to_id";

	public static final String FETCH_ALL_DOCS = "select u.first_name,u.last_name, dt.document, dt.created_date, dt.updated_date, dt.description, dt.id "
			+ " from user u inner join document_template dt on dt.fk_user_updated_by=u.id";

	public static final String YEAR_INVOICE_BRANCH_AMOUNT = "select concat(year(i.created_date),'-',month(i.created_date)) as date,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and year(i.created_date) = ?2 and br.branch_name in (?3)"
			+ " group by month(i.created_date) order by month(i.created_date)";
	
	public static final String DATE_INVOICE_BRANCH_AMOUNT = "select concat(year(i.created_date),'-',month(i.created_date)) as date,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and date(b.created_date)>=?2 and date(b.created_date)<=?3 and br.branch_name in (?4)"
			+ " group by month(i.created_date) order by month(i.created_date)";
	
	public static final String YEAR_INVOICE_BRANCH_INST = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and year(i.created_date) =?2 and br.branch_name in (?3) group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and year(i.created_date) =?2 and br.branch_name in (?3) group by i.fk_user_id) as invoice_data";		
			
	public static final String DATE_INVOICE_BRANCH_INST = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and br.branch_name in (?4) group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and br.branch_name in (?4) group by i.fk_user_id) as invoice_data";
	

	public static final String YEAR_INVOICE_BRANCH_INST_MONTH = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and year(i.created_date) =?2 and LEFT(monthname(created_date),3) = ?3 and br.branch_name in (?4) group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and year(i.created_date) =?2 and LEFT(monthname(created_date),3) = ?3 and br.branch_name in (?4) group by i.fk_user_id) as invoice_data";
	
	public static final String DATE_INVOICE_BRANCH_INST_MONTH = "Select * from(select inst.institution_name as Institution,"
			+ " sum(i.amount) as amount from invoice i"
			+ " join institution inst on i.fk_institution_id = inst.id"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is not null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and LEFT(monthname(i.created_date),3) = ?4 and br.branch_name in (?5) group by i.fk_institution_id"
			+ " union"
			+ " select 'Individual' as Institution, sum(amount) as amount from invoice i"
			+ " join branch br on br.id = i.fk_branch_id"
			+ " where i.fk_user_id =?1 and i.fk_institution_id is null"
			+ " and i.created_date>=?2 and i.created_date<=?3 and LEFT(monthname(i.created_date),3) = ?4 and br.branch_name in (?5) group by i.fk_user_id) as invoice_data";
	
	
}
