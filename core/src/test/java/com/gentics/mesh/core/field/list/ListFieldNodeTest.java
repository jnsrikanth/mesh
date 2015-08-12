package com.gentics.mesh.core.field.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gentics.mesh.core.data.NodeFieldContainer;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.data.node.field.list.GraphBooleanFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphDateFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphHtmlFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphNodeFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphNumberFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphStringFieldList;
import com.gentics.mesh.core.data.service.ServerSchemaStorage;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.node.field.list.impl.AbstractFieldList;
import com.gentics.mesh.core.rest.node.field.list.impl.BooleanFieldListImpl;
import com.gentics.mesh.core.rest.node.field.list.impl.DateFieldListImpl;
import com.gentics.mesh.core.rest.node.field.list.impl.NodeFieldListImpl;
import com.gentics.mesh.core.rest.node.field.list.impl.NumberFieldListImpl;
import com.gentics.mesh.core.rest.node.field.list.impl.StringFieldListImpl;
import com.gentics.mesh.core.rest.schema.ListFieldSchema;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.rest.schema.impl.ListFieldSchemaImpl;
import com.gentics.mesh.json.JsonUtil;
import com.gentics.mesh.test.AbstractDBTest;

public class ListFieldNodeTest extends AbstractDBTest {

	@Autowired
	private ServerSchemaStorage schemaStorage;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		setupData();
	}

	@Test
	public void testNodeListTransformation() throws IOException, InterruptedException {
		Node node = folder("2015");
		Node newsNode = folder("news");

		Schema schema = node.getSchema();
		ListFieldSchema nodeListFieldSchema = new ListFieldSchemaImpl();
		nodeListFieldSchema.setName("nodeList");
		nodeListFieldSchema.setListType("node");
		schema.addField(nodeListFieldSchema);

		ListFieldSchema stringListFieldSchema = new ListFieldSchemaImpl();
		stringListFieldSchema.setName("stringList");
		stringListFieldSchema.setListType("string");
		schema.addField(stringListFieldSchema);

		ListFieldSchema htmlListFieldSchema = new ListFieldSchemaImpl();
		htmlListFieldSchema.setName("htmlList");
		htmlListFieldSchema.setListType("html");
		schema.addField(htmlListFieldSchema);

		ListFieldSchema numberListFieldSchema = new ListFieldSchemaImpl();
		numberListFieldSchema.setName("numberList");
		numberListFieldSchema.setListType("number");
		schema.addField(numberListFieldSchema);

		ListFieldSchema booleanListFieldSchema = new ListFieldSchemaImpl();
		booleanListFieldSchema.setName("booleanList");
		booleanListFieldSchema.setListType("boolean");
		schema.addField(booleanListFieldSchema);

		ListFieldSchema dateListFieldSchema = new ListFieldSchemaImpl();
		dateListFieldSchema.setName("dateList");
		dateListFieldSchema.setListType("date");
		schema.addField(dateListFieldSchema);

		node.getSchemaContainer().setSchema(schema);

		NodeFieldContainer container = node.getFieldContainer(english());

		GraphNodeFieldList nodeList = container.createNodeList("nodeList");
		nodeList.createNode("1", newsNode);
		nodeList.createNode("2", newsNode);

		GraphBooleanFieldList booleanList = container.createBooleanList("booleanList");
		booleanList.createBoolean(true);
		booleanList.createBoolean(null);
		booleanList.createBoolean(false);

		GraphNumberFieldList numberList = container.createNumberList("numberList");
		numberList.createNumber("1");
		numberList.createNumber("1.11");

		GraphDateFieldList dateList = container.createDateList("dateList");
		dateList.createDate("01.01.1971");
		dateList.createDate("01.01.1972");

		GraphStringFieldList stringList = container.createStringList("stringList");
		stringList.createString("dummyString1");
		stringList.createString("dummyString2");

		GraphHtmlFieldList htmlList = container.createHTMLList("htmlList");
		htmlList.createHTML("some<b>html</b>");
		htmlList.createHTML("some<b>more html</b>");

		String json = getJson(node);
		assertNotNull(json);
		NodeResponse response = JsonUtil.readNode(json, NodeResponse.class, schemaStorage);
		assertNotNull(response);

		assertList(2, "stringList", StringFieldListImpl.class, response);
		assertList(2, "htmlList", StringFieldListImpl.class, response);
		assertList(2, "dateList", DateFieldListImpl.class, response);
		assertList(2, "numberList", NumberFieldListImpl.class, response);
		assertList(2, "nodeList", NodeFieldListImpl.class, response);
		assertList(3, "booleanList", BooleanFieldListImpl.class, response);
		//		assertList(0, "microschemaList", MicroschemaFieldListImpl.class, response);

	}

	private <T extends AbstractFieldList<?>> void assertList(int expectedItems, String fieldKey, Class<T> classOfT, NodeResponse response) {
		T deserializedList = response.getField(fieldKey, classOfT);
		assertNotNull(deserializedList);
		assertEquals(expectedItems, deserializedList.getList().size());
	}
}
