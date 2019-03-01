package com.legalfriend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "notification")
public class Notification extends Auditable<String> {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "subject")
	private String subject;

	@Column(name = "fk_user_id")
	private Long userId;

	@Column(name = "description", length = 2000)
	private String description;

	@JoinColumn(name = "fk_notification_type")
	@ManyToOne(cascade = CascadeType.MERGE)
	private NotificationType notificationType;

	@Transient
	private List<Long> sendTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getSendTo() {
		return sendTo;
	}

	public void setSendTo(List<Long> sendTo) {
		this.sendTo = sendTo;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

}
