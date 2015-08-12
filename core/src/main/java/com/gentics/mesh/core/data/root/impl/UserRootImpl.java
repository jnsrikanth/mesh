package com.gentics.mesh.core.data.root.impl;

import static com.gentics.mesh.core.data.relationship.GraphRelationships.HAS_USER;

import org.apache.commons.lang.NotImplementedException;

import com.gentics.mesh.core.data.Group;
import com.gentics.mesh.core.data.MeshAuthUser;
import com.gentics.mesh.core.data.User;
import com.gentics.mesh.core.data.impl.MeshAuthUserImpl;
import com.gentics.mesh.core.data.impl.UserImpl;
import com.gentics.mesh.core.data.root.UserRoot;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class UserRootImpl extends AbstractRootVertex<User>implements UserRoot {

	private static final Logger log = LoggerFactory.getLogger(UserRootImpl.class);

	@Override
	protected Class<? extends User> getPersistanceClass() {
		return UserImpl.class;
	}

	@Override
	protected String getRootLabel() {
		return HAS_USER;
	}

	@Override
	public void addUser(User user) {
		addItem(user);
	}

	@Override
	public void removeUser(User user) {
		removeItem(user);
	}

	// TODO unique index

	@Override
	public User create(String username, Group group, User creator) {
		UserImpl user = getGraph().addFramedVertex(UserImpl.class);
		user.setUsername(username);
		user.enable();
		if (group != null) {
			group.addUser(user);
		}
		if (creator != null) {
			user.setCreator(creator);
			user.setCreationTimestamp(System.currentTimeMillis());
			user.setEditor(creator);
			user.setLastEditedTimestamp(System.currentTimeMillis());
		}
		addItem(user);
		return user;
	}

	@Override
	public User findByUsername(String username) {
		return out(HAS_USER).has(UserImpl.class).has(UserImpl.USERNAME_PROPERTY_KEY, username).nextOrDefaultExplicit(UserImpl.class, null);
	}

	@Override
	public MeshAuthUser findMeshAuthUserByUsername(String username) {
		return out(HAS_USER).has(UserImpl.class).has(UserImpl.USERNAME_PROPERTY_KEY, username).nextOrDefaultExplicit(MeshAuthUserImpl.class, null);
	}

	@Override
	public void delete() {
		throw new NotImplementedException("The user root should never be deleted");
	}

}
