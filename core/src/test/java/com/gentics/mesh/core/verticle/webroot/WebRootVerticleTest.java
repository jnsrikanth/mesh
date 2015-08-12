package com.gentics.mesh.core.verticle.webroot;

import static com.gentics.mesh.core.data.relationship.GraphPermission.READ_PERM;
import static com.gentics.mesh.demo.DemoDataProvider.PROJECT_NAME;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import io.vertx.core.Future;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gentics.mesh.core.AbstractWebVerticle;
import com.gentics.mesh.core.data.node.Node;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.verticle.WebRootVerticle;
import com.gentics.mesh.test.AbstractRestVerticleTest;

public class WebRootVerticleTest extends AbstractRestVerticleTest {

	@Autowired
	private WebRootVerticle webRootVerticle;

	@Override
	public AbstractWebVerticle getVerticle() {
		return webRootVerticle;
	}

	@Test
	public void testReadFolderByPath() throws Exception {

		Node folder = folder("2015");
		String path = "/News/2015";

		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, path);
		latchFor(future);
		assertSuccess(future);
		NodeResponse restNode = future.result();
		test.assertMeshNode(folder, restNode);
		//		assertNull("The path {" + path + "} leads to the english version of this tag thus the german properties should not be loaded",
		//				restNode.getProperties());
		//		assertNotNull("The path {" + path + "} leads to the english version of this tag thus the english properties should be loaded.",
		//				restNode.getProperties());
	}

	@Test
	public void testReadContentByPath() throws Exception {
		String path = "/api/v1/" + PROJECT_NAME + "/webroot/Products/Concorde.en.html?lang=en,de";
		Node concordeNode = content("concorde");

		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, path);
		latchFor(future);
		assertSuccess(future);
		NodeResponse restNode = future.result();

		test.assertMeshNode(concordeNode, restNode);
		//		assertNotNull(restNode.getProperties());

	}

	@Test
	public void testReadFolderWithBogusPath() throws Exception {
		String path = "/blub";
		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, path);
		latchFor(future);
		expectException(future, NOT_FOUND, "node_not_found_for_path", "blub");
	}

	@Test
	public void testReadFolderByPathWithoutPerm() throws Exception {
		String englishPath = "News/2015";
		Node newsFolder = folder("2015");
		role().revokePermissions(newsFolder, READ_PERM);

		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, englishPath);
		latchFor(future);
		expectException(future, FORBIDDEN, "error_missing_perm", newsFolder.getUuid());
	}

	@Test
	public void testReadContentByInvalidPath() throws Exception {
		String invalidPath = "News/2015/no-valid-content.html";

		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, invalidPath);
		latchFor(future);
		expectException(future, NOT_FOUND, "node_not_found_for_path", invalidPath);
	}

	@Test
	public void testReadContentByInvalidPath2() throws Exception {
		String invalidPath = "News/no-valid-folder/no-valid-content.html";

		Future<NodeResponse> future = getClient().webroot(PROJECT_NAME, invalidPath);
		latchFor(future);
		expectException(future, NOT_FOUND, "node_not_found_for_path", invalidPath);
	}

}
