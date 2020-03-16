package com.example.sharecare.Fragments.Book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.getAllBookResponse.AllBookResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.Model.Book;
import com.example.sharecare.Model.GetBookModel;
import com.example.sharecare.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBooks extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static String LOG_TAG = "FoodRequestListFragment";
    private ListView bookListView;
    private View mProgressView;
    private View emptyView;
    AppPreferences appPreferences;


    public AllBooks() {
        // Required empty public constructor
    }


    public static AllBooks newInstance() {
        AllBooks fragment = new AllBooks();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.all_books, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appPreferences= AppPreferences.getInstance(getContext());
        mProgressView = view.findViewById(R.id.progressView);
        bookListView = view.findViewById(R.id.bookList);
        emptyView = view.findViewById(R.id.empty_view);

        setListAdapter(new ArrayList<Book>());
        //showProgress(true, bookListView, mProgressView);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        GetBookModel bookModel=new GetBookModel(appPreferences.getUserData().getId());
        Call<AllBookResponse> call=apiInterface.getAllBooks(bookModel);
        call.enqueue(new Callback<AllBookResponse>() {
            @Override
            public void onResponse(Call<AllBookResponse> call, Response<AllBookResponse> response) {
                //showProgress(false, bookListView, mProgressView);
                if(response.body().getError().equals("false"))
                {
                    setListAdapter(response.body().getData());
                }


            }

            @Override
            public void onFailure(Call<AllBookResponse> call, Throwable t) {

            }
        });


        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Book requestListModel = (Book) bookListView.getItemAtPosition(position);
                replaceFragment(BookDetailPage.newInstance(requestListModel,position%5,"join"));
            }
        });

    }

    private void setListAdapter(ArrayList<Book> list) {


        BookListAdapter listAdapter = new BookListAdapter(getContext(), R.id.bookList, list, true);
        bookListView.setAdapter(listAdapter);

        if (list == null || list.size() == 0) {

            bookListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);


        }
    }
}
