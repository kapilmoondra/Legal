package com.legalfriend.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.DocumentTemplate;
import com.legalfriend.entities.UserDocumentTemplate;
import com.legalfriend.repository.DocumentTemplateRepository;
import com.legalfriend.util.CustomQuery;

@RestController
@RequestMapping("/document/template")
public class DocumentTemplateController {

	@Autowired
	private DocumentTemplateRepository documentTemplateRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping
	@SuppressWarnings("unchecked")
	public List<UserDocumentTemplate> getDocumentTemplates() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(CustomQuery.FETCH_ALL_DOCS);
		List<Object[]> rows = (List<Object[]>) query.list();
		List<UserDocumentTemplate> documentTemplates = new ArrayList<UserDocumentTemplate>();
		for (Object[] row : rows) {
			UserDocumentTemplate documentTemplate = new UserDocumentTemplate();
			documentTemplate.setFirstName(row[0].toString());
			documentTemplate.setLastName(row[1].toString());
			documentTemplate.setDocument((byte[]) row[2]);
			documentTemplate
					.setCreatedDate(row[3] != null ? formatter.format(formatter.parse(row[3].toString())) : null);
			documentTemplate
					.setUpdatedDate(row[4] != null ? formatter.format(formatter.parse(row[4].toString())) : null);
			documentTemplate.setDescription(row[5] != null ? row[5].toString() : "");
			documentTemplate.setId(Long.valueOf(row[6].toString()));
			documentTemplates.add(documentTemplate);
		}
		session.close();
		return documentTemplates;
	}

	@PostMapping
	public ResponseEntity<Long> createDocumentTemplate(@RequestBody DocumentTemplate documentTemplate) {
		DocumentTemplate persistedDoc = documentTemplateRepository.save(documentTemplate);
		return new ResponseEntity<>(persistedDoc.getId(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Long> updateDocumentTemplate(@RequestBody DocumentTemplate documentTemplate) {
		DocumentTemplate persistedDoc = documentTemplateRepository.findOne(documentTemplate.getId());
		documentTemplateRepository.save(documentTemplate);
		return new ResponseEntity<>(persistedDoc.getId(), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteDocumentTemplate(@RequestParam Long documentTemplateId) {
		documentTemplateRepository.delete(documentTemplateRepository.findOne(documentTemplateId));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
