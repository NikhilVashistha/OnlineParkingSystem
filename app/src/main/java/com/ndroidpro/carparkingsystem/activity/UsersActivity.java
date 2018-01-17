package com.ndroidpro.carparkingsystem.activity;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.adapter.UsersAdapter;
import com.ndroidpro.carparkingsystem.model.UserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UsersActivity extends BaseActivity {

    private DatabaseReference mDatabase;
    ArrayList<UserProfile> mUserProfileArrayList;
    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private UsersAdapter mUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_list_recyclerview);
        recyclerView.setLayoutManager(manager);

        mUserProfileArrayList = new ArrayList<>();
        mUsersAdapter = new UsersAdapter(UsersActivity.this, mUserProfileArrayList);

        recyclerView.setAdapter(mUsersAdapter);

        mUsersAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position, UserProfile userProfile ) {
               /* Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.INTENT_CLIENT, client);*/
            }
        });

        swipeRefreshRecyclerList = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_recycler_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllCustomersList();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllCustomersList();
    }

    private void refreshed() {
        if (swipeRefreshRecyclerList.isRefreshing()) {
            swipeRefreshRecyclerList.setRefreshing(false);
        }
    }

    private void getAllCustomersList() {
        if(isInternetNotAvailable()){
            refreshed();
            return;
        }
        showProgressDialog();

        Query myTopPostsQuery = mDatabase
                                    .child(Constants.DB_USERS);

        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserProfileArrayList.clear();

                if(dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        mUserProfileArrayList.add(
                                postSnapshot
                                .child(Constants.DB_PROFILE)
                                .getValue(UserProfile.class)
                        );
                    }

                    Collections.sort(mUserProfileArrayList, new Comparator<UserProfile>() {
                        @Override
                        public int compare(UserProfile userProfile, UserProfile userProfile2) {
                            return userProfile2.getUserName().compareToIgnoreCase(userProfile.getUserName());
                        }
                    });

                    mUsersAdapter.notifyDataChange(mUserProfileArrayList);
                }else {
                    ToastUtils.showLong("No Customer Added. Please add Customer");
                }
                refreshed();
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                refreshed();
                hideProgressDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Retrieve the SearchView and plug it into SearchManager
        MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) item.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //changing edittext color
        EditText searchEdit = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search User");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetter(source.charAt(i)))
                        return "";
                }

                return null;
            }
        };

        searchEdit.setFilters(fArray);

        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 0) {
                    ArrayList<UserProfile> filterList = new ArrayList<>();
                    for (UserProfile userProfile : mUserProfileArrayList) {
                        if (userProfile.getUserName().toLowerCase().contains(query.toLowerCase())) {
                            filterList.add(userProfile);
                            mUsersAdapter.notifyDataChange(filterList);
                        }
                    }

                } else {
                    mUsersAdapter.notifyDataChange(mUserProfileArrayList);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
