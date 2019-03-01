package com.legalfriend.cron;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.legalfriend.entities.LegalCase;
import com.legalfriend.repository.AgainstInstitutionalCaseRepository;
import com.legalfriend.repository.CaseRepository;
import com.legalfriend.repository.ForInstitutionalCaseRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.util.EmailService;

@Service
public class ScheduleJob {

	@Autowired
	CaseRepository caseRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	ForInstitutionalCaseRepository forInstitutionalCaseRepo;

	@Autowired
	AgainstInstitutionalCaseRepository againstInstitutionalCaseRepo;

	@Scheduled(cron = "0 0 7 ? * *")
	public void sendNextHearingReminder() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.DATE, 3);
		List<Object[]> objects = (List<Object[]>) caseRepo.findAllCasesWithNextHearingDateTommorrow();
		Map<String, List<LegalCase>> caseListByDate = new HashMap<>();
		Map<String, String> names = new HashMap<>();
		Set<Date> dateSet = new TreeSet<>();
		for (Object[] object : objects) {
			LegalCase legalCase = new LegalCase();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String s = object[2].toString().split(" ")[0];
			Date date1 = new Date();
			try {
				date1 = df.parse(s);
				legalCase.setCaseId(object[3].toString());
				legalCase.setNextHearingDate(date1);
				dateSet.add(date1);

				if (caseListByDate.containsKey(object[0].toString())) {
					caseListByDate.get(object[0].toString()).add(legalCase);
				} else {
					List<LegalCase> caseList = new ArrayList<>();
					caseList.add(legalCase);
					names.put(object[0].toString(), object[1].toString());
					caseListByDate.put(object[0].toString(), caseList);
				}
			} catch (ParseException e) {
				throw new RuntimeException(
						"Exception occurred while sending next hearing date email " + e.getMessage());
			}

		}
		caseListByDate.forEach((email, cases) -> {
			emailService.sendHearingDateReminderEmail(email, names.get(email), cases, dateSet);
		});
	}
}
