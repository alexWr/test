package com.faceit.testopenplatform.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.di.components.ItemComponent;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment that shows a list of Users.
 */
public class CommentsDetailFragment extends BaseFragment {


    @BindView(R.id.section)
    TextView section;
    @BindView(R.id.title)
    TextView title;

    private Comments comments;
    private Unbinder unbind;


    public CommentsDetailFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ItemComponent.class).inject(this);
        if(getArguments() != null)
            comments = getArguments().getParcelable("COMMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_detail, container, false);
        unbind = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(comments != null) {
            section.setText(comments.getBody());
            title.setText(comments.getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
