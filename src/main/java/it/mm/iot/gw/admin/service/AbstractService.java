package it.mm.iot.gw.admin.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.mm.iot.gw.admin.service.exception.IssueOperationFactory;
import it.mm.iot.gw.admin.service.exception.SeverityEnum;

public class AbstractService {

	@Autowired
	protected IssueOperationFactory issueOperationFactory;

	public AbstractService() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected <T> T onlyOneRecord(List<T> lista) {
		if (lista.isEmpty() || lista.size() > 1) {
			issueOperationFactory.addIssue(SeverityEnum.FATAL, 0 + "", "Attenzione errore nella query sulla classe "
					+ ((T) ((ParameterizedType) lista.getClass().getGenericSuperclass())).getClass());
		}
		return lista.get(0);
	}

	protected <T> T onlyOneRecord(List<T> lista, Integer codiceErroreNoDataFound, String msgErroreNoDataFound) {
		return onlyOneRecord(lista, codiceErroreNoDataFound, msgErroreNoDataFound, null, null);
	}

	@SuppressWarnings("unchecked")
	protected <T> T onlyOneRecord(List<T> lista, Integer codiceErroreNoDataFound, String msgErroreNoDataFound,
			Integer codiceErroreTooManyRows, String msgErroreTooManyRows) {
		String genericMessage = "Attenzione errore nella query sulla classe "
				+ ((T) ((ParameterizedType) lista.getClass().getGenericSuperclass())).getClass();
		Integer codErrNoDataFound = codiceErroreNoDataFound != null ? codiceErroreNoDataFound : 0;
		String msgErrNoDataFound = msgErroreNoDataFound != null ? msgErroreNoDataFound : genericMessage;
		Integer codErrTooManyRows = codiceErroreTooManyRows != null ? codiceErroreTooManyRows : 0;
		String msgErrTooManyRows = msgErroreTooManyRows != null ? msgErroreTooManyRows : genericMessage;
		if (lista == null || lista.isEmpty()) {
			issueOperationFactory.addIssue(SeverityEnum.FATAL, codErrNoDataFound + "", msgErrNoDataFound);
		} else if (lista.size() > 1) {
			issueOperationFactory.addIssue(SeverityEnum.FATAL, codErrTooManyRows + "", msgErrTooManyRows);
		}
		return lista.get(0);
	}

	protected <T> T notEmpty(T record) {
		if (record == null) {
			issueOperationFactory.addIssue(SeverityEnum.FATAL, 0 + "", "Attenzione errore nella query sulla classe ");
		}
		return record;
	}

	protected <T> T firstRecord(List<T> lista) {
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

}