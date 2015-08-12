package com.gentics.mesh.core.data;

import com.gentics.mesh.core.data.node.field.nesting.GraphMicroschemaField;

public interface MicroschemaFieldContainer {

	GraphMicroschemaField createMicroschema(String key);

	GraphMicroschemaField getMicroschema(String key);

}
