package com.gentics.mesh.search.index.entry;

import static com.gentics.mesh.core.rest.error.Errors.error;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

import com.gentics.mesh.core.data.HandleContext;
import com.gentics.mesh.core.data.IndexableElement;
import com.gentics.mesh.core.data.search.IndexHandler;
import com.gentics.mesh.core.data.search.SearchQueueEntryAction;
import com.gentics.mesh.core.data.search.UpdateBatchEntry;

import rx.Completable;

/**
 * Basic implementation for most indexable elements.
 * 
 * @see UpdateBatcheEntry
 */
public class UpdateBatchEntryImpl extends AbstractEntry implements UpdateBatchEntry {

	private String elementUuid;
	private IndexHandler indexHandler;
	private HandleContext context;

	/**
	 * Create a new batch entry.
	 * 
	 * @param indexHandler
	 * @param element
	 * @param context
	 * @param action
	 */
	public UpdateBatchEntryImpl(IndexHandler indexHandler, IndexableElement element, HandleContext context, SearchQueueEntryAction action) {
		super(action);
		this.context = context;
		this.elementUuid = element.getUuid();
		this.indexHandler = indexHandler;
	}

	@Override
	public String getElementUuid() {
		return elementUuid;
	}

	@Override
	public Completable process() {
		switch (elementAction) {
		case STORE_ACTION:
			return indexHandler.store(this);

		case DELETE_ACTION:
			return indexHandler.delete(this);

		default:
			throw error(INTERNAL_SERVER_ERROR, "Can't process entry of for action {" + elementAction + "}");
		}

	}

	@Override
	public HandleContext getContext() {
		return context;
	}

	@Override
	public String toString() {
		return "Update Entry {" + getElementAction() + "} for {" + elementUuid + "} and handler {" + indexHandler.getClass().getSimpleName()
				+ "} with context {" + getContext().toString() + "}";
	}
}