package com.gentics.mesh.core.field.bool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gentics.mesh.core.data.impl.AbstractFieldContainerImpl;
import com.gentics.mesh.core.data.node.field.basic.BooleanGraphField;
import com.gentics.mesh.core.data.node.field.impl.basic.BooleanGraphFieldImpl;
import com.gentics.mesh.test.AbstractDBTest;

public class BooleanFieldTest extends AbstractDBTest {

	@Test
	public void testSimpleBoolean() {
		AbstractFieldContainerImpl container = fg.addFramedVertex(AbstractFieldContainerImpl.class);
		BooleanGraphFieldImpl field = new BooleanGraphFieldImpl("test", container);
		assertEquals(2, container.getPropertyKeys().size());
		assertNull(container.getProperty("test-boolean"));
		field.setBoolean(new Boolean(true));

		assertEquals("true", container.getProperty("test-boolean"));
		//assertEquals(5, container.getPropertyKeys().size());
		field.setBoolean(new Boolean(false));
		assertEquals("false", container.getProperty("test-boolean"));
		field.setBoolean(null);
		assertEquals("null", container.getProperty("test-boolean"));
		assertNull(field.getBoolean());
	}

	@Test
	public void testBooleanField() {
		AbstractFieldContainerImpl container = fg.addFramedVertex(AbstractFieldContainerImpl.class);
		BooleanGraphField booleanField = container.createBoolean("booleanField");
		assertEquals("booleanField", booleanField.getFieldKey());
		booleanField.setBoolean(true);
		assertTrue(booleanField.getBoolean());
		booleanField.setBoolean(false);
		assertFalse(booleanField.getBoolean());
		booleanField.setBoolean(null);
		assertNull(booleanField.getBoolean());
		BooleanGraphField bogusField2 = container.getBoolean("bogus");
		assertNull(bogusField2);
		BooleanGraphField reloadedBooleanField = container.getBoolean("booleanField");
		assertNotNull(reloadedBooleanField);
		assertEquals("booleanField", reloadedBooleanField.getFieldKey());
	}
}
