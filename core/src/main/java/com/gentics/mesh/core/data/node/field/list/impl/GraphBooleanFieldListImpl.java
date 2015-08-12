package com.gentics.mesh.core.data.node.field.list.impl;

import com.gentics.mesh.core.data.node.field.basic.BooleanGraphField;
import com.gentics.mesh.core.data.node.field.impl.basic.BooleanGraphFieldImpl;
import com.gentics.mesh.core.data.node.field.list.AbstractBasicGraphFieldList;
import com.gentics.mesh.core.data.node.field.list.GraphBooleanFieldList;

public class GraphBooleanFieldListImpl extends AbstractBasicGraphFieldList<BooleanGraphField> implements GraphBooleanFieldList {

	@Override
	public BooleanGraphField getBoolean(int index) {
		return getField(index);
	}

	@Override
	public BooleanGraphField createBoolean(Boolean flag) {
		BooleanGraphField field = createField();
		field.setBoolean(flag);
		return field;
	}

	@Override
	protected BooleanGraphField createField(String key) {
		return new BooleanGraphFieldImpl(key, getImpl());
	}

	@Override
	public Class<? extends BooleanGraphField> getListType() {
		return BooleanGraphFieldImpl.class;
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
