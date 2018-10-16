package com.faceit.testopenplatform.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.model.Comments;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Adaptar that manages a collection of {@link com.faceit.testopenplatform.model.Comments}.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.EntityViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(Comments entityModel);
    }

    private List<Comments> articleCollection;

    private OnItemClickListener onItemClickListener;
    private Context context;

    @Inject
    CommentsAdapter(Context context) {
        this.articleCollection = Collections.emptyList();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return (this.articleCollection != null) ? this.articleCollection.size() : 0;
    }

    @Override
    public EntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_comments, parent, false);
        return new EntityViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityViewHolder holder, final int position) {
        final Comments article = this.articleCollection.get(position);
        holder.bind(article);
        holder.cv_item.setOnClickListener(v -> {
            if (CommentsAdapter.this.onItemClickListener != null) {
                CommentsAdapter.this.onItemClickListener.onItemClicked(article);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setArticleCollection(Collection<Comments> articleCollection) {
        this.validateUsersCollection(articleCollection);
        this.articleCollection = (List<Comments>) articleCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateUsersCollection(Collection<Comments> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class EntityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.body)
        TextView body;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tv_comment_id)
        TextView commentId;
        @BindView(R.id.cv_item)
        CardView cv_item;

        EntityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Comments comments){
            this.name.setText(comments.getName());
            this.body.setText(comments.getBody());
            this.commentId.setText(String.format("id - %s", comments.getId()));
        }
    }
}
