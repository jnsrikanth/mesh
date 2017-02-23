package com.gentics.mesh.core.data.root;

import com.gentics.mesh.core.data.User;
import com.gentics.mesh.core.data.schema.SchemaContainer;
import com.gentics.mesh.core.data.schema.SchemaContainerVersion;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.rest.schema.SchemaReference;
import com.gentics.mesh.error.MeshSchemaException;

/**
 * A schema container root is an aggregation vertex which is used to aggregate schema container vertices.
 */
public interface SchemaContainerRoot extends RootVertex<SchemaContainer> {

	public static final String TYPE = "schemas";

	/**
	 * Create new schema container.
	 * 
	 * @param schema
	 *            Schema that should be stored in the container
	 * @param creator
	 *            User that is used to set editor and creator references
	 * @return Created schema container
	 * @throws MeshSchemaException
	 */
	default SchemaContainer create(Schema schema, User creator) throws MeshSchemaException {
		return create(schema, creator, null);
	}

	/**
	 * Create new schema container.
	 * 
	 * @param schema
	 *            Schema that should be stored in the container
	 * @param creator
	 *            User that is used to set editor and creator references
	 * @param uuid
	 *            Optional uuid
	 * @return Created schema container
	 * @throws MeshSchemaException
	 */
	SchemaContainer create(Schema schema, User creator, String uuid) throws MeshSchemaException;

	/**
	 * Add the schema to the aggregation node.
	 * 
	 * @param schemaContainer
	 */
	void addSchemaContainer(SchemaContainer schemaContainer);

	/**
	 * Remove the schema container from the aggregation node.
	 * 
	 * @param schemaContainer
	 */
	void removeSchemaContainer(SchemaContainer schemaContainer);

	/**
	 * Check whether the given schema is assigned to this root node.
	 * 
	 * @param schema
	 * @return
	 */
	boolean contains(SchemaContainer schema);

	/**
	 * Find the referenced schema container version. Throws an error, if the referenced schema container version can not be found
	 * 
	 * @param reference
	 *            reference
	 * @return Resolved container version
	 */
	SchemaContainerVersion fromReference(SchemaReference reference);
}
