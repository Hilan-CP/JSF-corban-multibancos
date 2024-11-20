package util;

import java.io.Serializable;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/*
 * código obtido do curso JSF e PrimeFaces Essencial
 * https://youtu.be/ezwgBvsd6Ps?feature=shared
 * https://github.com/algaworks/curso-jsf-primefaces-essencial/blob/master/gerenciando-as-transacoes-com-cdi/src/main/java/com/algaworks/erp/util/TransacionalInterceptor.java
 */

@Interceptor
@Transaction
@Priority(Interceptor.Priority.APPLICATION)
public class TransactionInterceptor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception {
		EntityTransaction transaction = entityManager.getTransaction();
		boolean startedByInterceptor = false;

		try {
			if (!transaction.isActive()) {
				// truque para fazer rollback no que já passou
				// (senão, um futuro commit confirmaria até mesmo operações sem transação)
				transaction.begin();
				transaction.rollback();

				// agora sim inicia a transação
				transaction.begin();
				startedByInterceptor = true;
			}
			return context.proceed();
		} catch (Exception e) {
			if (transaction != null && startedByInterceptor) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (transaction != null && transaction.isActive() && startedByInterceptor) {
				transaction.commit();
			}
		}
	}
}
