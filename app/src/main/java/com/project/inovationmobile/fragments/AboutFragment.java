package com.project.inovationmobile.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;


import com.project.inovationmobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    ConstraintLayout constraintLayoutProfil, constraintLayoutUnsur, constraintLayoutTugas, constraintLayoutStrategi, constraintLayoutKontak;
    ImageView iconArrowProfil, iconArrowUnsur, iconArrowTugas, iconArrowStrategi, iconArrowKontak;
    CardView cardProfil, cardUnsur, cardTugas, cardStrategi, cardKontak;
    View padProfil, padUnsur, padTugas, padStrategi, padKontak;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        cardProfil = rootView.findViewById(R.id.cardProfil);
        iconArrowProfil = rootView.findViewById(R.id.icon_arrow_profil);
        constraintLayoutProfil = rootView.findViewById(R.id.hidden_layout_profil);
        padProfil = rootView.findViewById(R.id.pad_bottom_profil);

        cardProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (constraintLayoutProfil.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(cardProfil, new AutoTransition());
                    padProfil.setVisibility(View.VISIBLE);
                    constraintLayoutProfil.setVisibility(View.GONE);
                    iconArrowProfil.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardProfil, new AutoTransition());
                    padProfil.setVisibility(View.GONE);
                    constraintLayoutProfil.setVisibility(View.VISIBLE);
                    iconArrowProfil.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        cardTugas = rootView.findViewById(R.id.cardTugas);
        iconArrowTugas = rootView.findViewById(R.id.icon_arrow_tugas);
        constraintLayoutTugas = rootView.findViewById(R.id.hidden_layout_tugas);
        padTugas = rootView.findViewById(R.id.pad_bottom_tugas);

        cardTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(constraintLayoutTugas.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardTugas, new AutoTransition());
                    padTugas.setVisibility(View.VISIBLE);
                    constraintLayoutTugas.setVisibility(View.GONE);
                    iconArrowTugas.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardTugas, new AutoTransition());
                    padTugas.setVisibility(View.GONE);
                    constraintLayoutTugas.setVisibility(View.VISIBLE);
                    iconArrowTugas.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        cardStrategi = rootView.findViewById(R.id.cardStrategi);
        iconArrowStrategi = rootView.findViewById(R.id.icon_arrow_strategi);
        constraintLayoutStrategi = rootView.findViewById(R.id.hidden_layout_strategi);
        padStrategi = rootView.findViewById(R.id.pad_bottom_strategi);

        cardStrategi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constraintLayoutStrategi.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardStrategi, new AutoTransition());
                    padStrategi.setVisibility(View.VISIBLE);
                    constraintLayoutStrategi.setVisibility(View.GONE);
                    iconArrowStrategi.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardStrategi, new AutoTransition());
                    padStrategi.setVisibility(View.GONE);
                    constraintLayoutStrategi.setVisibility(View.VISIBLE);
                    iconArrowStrategi.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        cardKontak = rootView.findViewById(R.id.cardKontak);
        iconArrowKontak = rootView.findViewById(R.id.icon_arrow_kontak);
        constraintLayoutKontak = rootView.findViewById(R.id.hidden_layout_kontak);
        padKontak = rootView.findViewById(R.id.pad_bottom_kontak);

        cardKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constraintLayoutKontak.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardKontak, new AutoTransition());
                    padKontak.setVisibility(View.VISIBLE);
                    constraintLayoutKontak.setVisibility(View.GONE);
                    iconArrowKontak.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
                    TransitionManager.beginDelayedTransition(cardKontak, new AutoTransition());
                    padKontak.setVisibility(View.GONE);
                    constraintLayoutKontak.setVisibility(View.VISIBLE);
                    iconArrowKontak.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

}