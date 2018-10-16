package com.gentics.diktyo.orientdb.wrapper.factory;

import com.gentics.diktyo.orientdb.wrapper.element.AbstractWrappedVertex;
import com.gentics.diktyo.wrapper.element.WrappedElement;
import com.tinkerpop.blueprints.Vertex;

public final class WrapperFactory {

	/**
	 * Frame the given element by wrapping it using the given wrapper.
	 * 
	 * @param sourceElement
	 *            Element to be wrapped
	 * @param clazzOfR
	 *            Wrapper class
	 * @return Wrapped element
	 * 
	 */
	public static <T, R extends WrappedElement<T>> R frameElement(T sourceElement, Class<R> clazzOfR) {
		try {
			R element = clazzOfR.newInstance();
			element.init(sourceElement);
			return element;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate wrapper for class {" + clazzOfR.getName() + "}");
		}
	}

	public static <R> R  frameVertex(Vertex vertex, Class<R> clazzOfR) {
		try {
			R element = clazzOfR.newInstance();
			if (element instanceof AbstractWrappedVertex) {
				((AbstractWrappedVertex) element).init(vertex);
			} else {
				throw new RuntimeException("The specified class {" + clazzOfR + "} does not use {" + AbstractWrappedVertex.class + "}");
			}
			return element;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate wrapper for class {" + clazzOfR.getName() + "}");
		}
	}
}
