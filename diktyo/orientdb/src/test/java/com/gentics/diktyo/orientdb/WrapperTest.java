package com.gentics.diktyo.orientdb;

import static com.gentics.diktyo.db.DatabaseType.MEMORY;

import org.junit.Test;

import com.gentics.diktyo.Diktyo;
import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.orientdb.domain.Job;
import com.gentics.diktyo.orientdb.domain.JobImpl;
import com.gentics.diktyo.tx.Tx;

public class WrapperTest {

	@Test
	public void testWrapperAPI() {
		Diktyo diktyo = Diktyo.diktyo();
		diktyo.db().create("test", MEMORY);
		try (Database db = diktyo.db().open("test", MEMORY)) {
			try (Tx tx = db.tx()) {
				Job job = db.createVertex(JobImpl.class);
			}
		}

	}
}
