package com.example.mahmoud.movies_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmoud.movies_app.Fragments.DetailFragment;
import com.example.mahmoud.movies_app.R;

public class DetailActivity extends AppCompatActivity {

    Bundle bundle =new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        bundle = getIntent().getExtras();

        DetailFragment detail_fragment = new DetailFragment();
        detail_fragment.setArguments(bundle);

        if (savedInstanceState==null) {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, detailFragment)
                    .commit();

        }

    }


}
