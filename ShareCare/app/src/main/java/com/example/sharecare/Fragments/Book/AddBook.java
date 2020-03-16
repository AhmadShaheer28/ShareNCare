package com.example.sharecare.Fragments.Book;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodRequest;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.FormValidator;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.Fragments.Food.CreateFoodRequestFragment;
import com.example.sharecare.Fragments.Food.FoodTabFragment;
import com.example.sharecare.Model.Book;
import com.example.sharecare.R;
import com.example.sharecare.RestaurantActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBook extends BaseFragment {

    private static final String FOODREQUEST_JSON = "foodRequest_json";
    public static String LOG_TAG = "CreateRequest";
    private static final int MAX_PARTICIPANTS = 256;
    private static final int MIN_PARTICIPANTS = 2;
    private static final double MAX_FEE = 100000.0;
    private static final double MIN_FEE = 0.0;

    AppPreferences appPreferences;


    private int restaurant_id = 0;
    private String restaurant_name = "";
    private Button addBookBtn;
    private EditText nameEdit;
    private EditText quantity;
    private EditText descriptionEdit;
    private EditText publisher;

    private EditText pickup_address;
    private TextView startTimeTxt;
    private TextView lastDateTxt;
    private EditText authorName;
    private TextView serviceChargeLabel;
    private TextView termsAndConditionsLink;

    private View mProgressView;
    private View container;


    private String organizerContactNumber;

    public static CreateFoodRequestFragment newInstance(String requestJSON) {
        CreateFoodRequestFragment fragment = new CreateFoodRequestFragment();
        Bundle args = new Bundle();
        if (requestJSON != null)
            args.putString(FOODREQUEST_JSON, requestJSON);

        fragment.setArguments(args);
        return fragment;
    }


    public static AddBook newInstance() {
        AddBook fragment = new AddBook();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getString(FOODREQUEST_JSON) != null) {
//
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        appPreferences = AppPreferences.getInstance(getContext());
        return inflater.inflate(R.layout.book_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        container = view.findViewById(R.id.addBook_view);
        mProgressView = view.findViewById(R.id.progressView);

        addBookBtn = view.findViewById(R.id.addBook);
        nameEdit = view.findViewById(R.id.bookTitle);
        quantity = view.findViewById(R.id.quantity);
        descriptionEdit = view.findViewById(R.id.bookDescription);
        publisher = view.findViewById(R.id.publisher);
        authorName = view.findViewById(R.id.authorName);
        pickup_address = view.findViewById(R.id.pickup_address);
        startTimeTxt = view.findViewById(R.id.request_start_time);
        lastDateTxt = view.findViewById(R.id.request_last_date);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodRequest();
            }
        });

    }


    private boolean validateCreateRequestForm() {

        Map<String, Boolean> validationErrors = new HashMap<>();
        try {

            validationErrors.put("nameEdit", FormValidator.isTextFieldValid(nameEdit, getString(R.string.error_field_required)));
            validationErrors.put("descriptionEdit", FormValidator.isTextFieldValid(descriptionEdit, getString(R.string.error_field_required)));

            validationErrors.put("publisher", FormValidator.isTextFieldValid(publisher, getString(R.string.error_field_required)));
            validationErrors.put("quantity", FormValidator.isTextFieldValid(quantity, getString(R.string.error_field_required)));

            if (validationErrors.get("publisher"))
                //validationErrors.put("publisher", FormValidator.inRange(publisher, MIN_PARTICIPANTS, MAX_PARTICIPANTS, getString(R.string.field_not_in_range, MIN_PARTICIPANTS, MAX_PARTICIPANTS)));

                if (validationErrors.get("quantity"))
                    //validationErrors.put("quantity", FormValidator.inRange(quantity, MIN_FEE, MAX_FEE, getString(R.string.field_not_in_range, MIN_FEE, MAX_FEE)));

                    validationErrors.put("startTimeTxt", FormValidator.isTextFieldValid(startTimeTxt, getString(R.string.error_field_required)));
            validationErrors.put("pickup_address", FormValidator.isTextFieldValid(pickup_address, getString(R.string.error_field_required)));

            validationErrors.put("lastDateTxt", FormValidator.isTextFieldValid(lastDateTxt, getString(R.string.error_field_required)));
            validationErrors.put("authorName", FormValidator.isTextFieldValid(authorName, getString(R.string.error_field_required)));



        } catch (ParseException e) {
            validationErrors.put("datesInvalid", true);
        } catch (Exception e) {
            validationErrors.put("field", true);
        }

        for (Map.Entry<String, Boolean> validationError : validationErrors.entrySet()) {
            Map.Entry pair = validationError;
            if ((Boolean) pair.getValue() == false)
                return false;
        }
        return true;
    }


    private void saveFoodRequest() {

        if (validateCreateRequestForm()) {
            final Book foodRequest = buildFoodRequest();
            createRequestTask(foodRequest);

        } else {
            Toast.makeText(getContext(), "Request form invalid", Toast.LENGTH_SHORT).show();
        }
    }

    //
    @NonNull
    private Book buildFoodRequest() {
        String title = nameEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        Book book = new Book(title, appPreferences.getUserData().getName(), description, pickup_address.getText().toString(), authorName.getText().toString(), "Available", appPreferences.getUserData().getId(),
                Integer.parseInt(quantity.getText().toString()));
        return book;
    }

    private void createRequestTask(final Book foodRequest) {

        showProgress(true, container, mProgressView);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FoodResponse> call = apiInterface.addBook(foodRequest);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                showProgress(false, container, mProgressView);
                if (response.body().getError().equals("false")) {
                    Toast.makeText(getContext(), "Book added Successfully", Toast.LENGTH_SHORT).show();
                    replaceFragment(new BookTabFragment());
                }

            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {
                showProgress(false, container, mProgressView);
                Toast.makeText(getContext(), "Error Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
//


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Create Food Request");
    }

    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (RestaurantActivity.SELECT_REQUEST_CODE): {
                if (resultCode == RestaurantActivity.SELECTED_OK) {
                    restaurant_name = data.getStringExtra("RESTAURANT_NAME");
                    restaurant_id = data.getIntExtra("RESTAURANT_ID", 0);
                    authorName.setText(restaurant_name);
                }
                break;
            }

        }
    }
}
