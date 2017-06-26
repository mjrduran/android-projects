package fragmentbasics.moacir.com.fragmentbasics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class HeadlinesFragment extends Fragment {

    private static final String ARG_HEADLINE_TITLE = "headline_title";

    private String headlineTitle;

    public HeadlinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param headlineTitle Parameter 1.
     * @return A new instance of fragment HeadlinesFragment.
     */
    public static HeadlinesFragment newInstance(String headlineTitle) {
        HeadlinesFragment fragment = new HeadlinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HEADLINE_TITLE, headlineTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headlineTitle = getArguments().getString(ARG_HEADLINE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_headlines, container, false);

        if (headlineTitle != null){
            TextView textView = (TextView) rootView.findViewById(R.id.headlineTitle);
            textView.setText(headlineTitle);
        }

        return rootView;
    }

}
