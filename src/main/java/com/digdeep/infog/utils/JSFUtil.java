package com.digdeep.infog.utils;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;

public class JSFUtil {

	public ELContext getELContext() throws Exception {
		FacesContext currentCxt = FacesContext.getCurrentInstance();
		ELContext elCxt = currentCxt.getELContext();
		return elCxt;
	}

	public Object getBeanByName(String beanName) throws Exception {
		Object bean = getELContext().getELResolver().getValue(getELContext(),
				null, beanName);
		return bean;
	}

	public ValueExpression getValueExpression(String expression)
			throws Exception {
		FacesContext currentCxt = FacesContext.getCurrentInstance();
		ELContext elCxt = currentCxt.getELContext();
		ValueExpression vExp = currentCxt.getApplication()
				.getExpressionFactory()
				.createValueExpression(elCxt, expression, Object.class);
		return vExp;
	}

	public void setValueInExpression(Object value, String expression, Object obj)
			throws Exception {
		ValueExpression vExp = getValueExpression(expression);
		vExp.setValue(getELContext(), value);
	}

	public MethodExpression getMethodExpression(String expression,
			Class<?> returnType, Class<?>[] params) throws Exception {
		FacesContext currentCxt = FacesContext.getCurrentInstance();
		ELContext elCxt = currentCxt.getELContext();
		return currentCxt.getApplication().getExpressionFactory()
				.createMethodExpression(elCxt, expression, returnType, params);
	}
	
	public MethodExpressionActionListener getMethodExpressionActionListener (String expression,
			Class<?> returnType, Class<?>[] params) throws Exception {
		MethodExpression exp = getMethodExpression(expression, returnType, params);
		MethodExpressionActionListener mExpAl = new MethodExpressionActionListener(exp);
		return mExpAl;
	}
}
