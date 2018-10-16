package com.faceit.testopenplatform.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.di.components.ItemComponent;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Fragment that shows a list of Users.
 */
public class SplashFragment extends BaseFragment {

    public interface SplashClickListener{
        void onShowCommentsClick(String first, String second);
    }

    private static final int MAX_LENGTH = 5;

    @BindView(R.id.et_first_id)
    EditText firstId;
    @BindView(R.id.et_second_id)
    EditText secondId;
    @BindView(R.id.bt_show)
    Button btShow;

    private String firstNumber;
    private String secondNumber;

    private Unbinder unbind;
    private SplashClickListener listener;


    public SplashFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SplashClickListener)
            listener = (SplashClickListener)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ItemComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_splash, container, false);
        unbind = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstId.addTextChangedListener(firstTextWatcher);
        secondId.addTextChangedListener(secondTextWatcher);
    }

    private TextWatcher firstTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            firstNumber = charSequence.toString();
            checkFillField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher secondTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            secondNumber = charSequence.toString();
            checkFillField();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @OnClick(R.id.bt_show)
    void showResult(){
        if(listener != null)
            listener.onShowCommentsClick(firstNumber, secondNumber);
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

    private void checkFillField(){
        if(firstNumber != null && secondNumber != null &&
                (firstNumber.length() <= MAX_LENGTH || secondNumber.length() <= MAX_LENGTH))
            btShow.setEnabled(true);
        else
            btShow.setEnabled(false);
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
