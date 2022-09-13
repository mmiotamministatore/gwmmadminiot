package it.mm.iot.gw.admin.web.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mm.iot.gw.admin.service.exception.IssueOperationOutcome;

@JsonInclude(Include.NON_NULL)
public class OperationOutcome {
	private List<IssueOperationOutcome> issue;

	public List<IssueOperationOutcome> getIssue() {
		return issue;
	}

	public void setIssue(List<IssueOperationOutcome> issue) {
		this.issue = issue;
	}
}
