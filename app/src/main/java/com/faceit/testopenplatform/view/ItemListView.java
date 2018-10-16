package com.faceit.testopenplatform.view;

import com.faceit.testopenplatform.model.Comments;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link com.faceit.testopenplatform.model.Comments}.
 */
public interface ItemListView extends LoadDataView {
    /**
     * Render a article list in the UI.
     *
     * @param commentsModelCollection The collection of {@link com.faceit.testopenplatform.model.Comments} that will be shown.
     */
    void renderList(Collection<Comments> commentsModelCollection);

    /**
     * View a {@link com.faceit.testopenplatform.model.Comments} profile/details.
     *
     * @param commentsModel The article that will be shown.
     */
    void viewEntity(Comments commentsModel);


}
