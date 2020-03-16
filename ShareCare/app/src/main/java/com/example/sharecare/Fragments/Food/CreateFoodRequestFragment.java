package com.example.sharecare.Fragments.Food;

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

import androidx.annotation.NonNull;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodRequest;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.FormValidator;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;
import com.example.sharecare.RestaurantActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateFoodRequestFragment extends BaseFragment {

    private static final String FOODREQUEST_JSON = "foodRequest_json";
    public static String LOG_TAG = "CreateRequest";
    private static final int MAX_PARTICIPANTS = 256;
    private static final int MIN_PARTICIPANTS = 2;
    private static final double MAX_FEE = 100000.0;
    private static final double MIN_FEE = 0.0;

    AppPreferences appPreferences;



    private int restaurant_id=0;
    private String restaurant_name="";
    private Button createRequestBtn;
    private EditText nameEdit;
    private EditText feeEdit;
    private EditText descriptionEdit;
    private EditText maxParticipantsEdit;
    private CheckBox termsConditionCheckBox;

    private TextView startDateTxt;
    private TextView startTimeTxt;
    private TextView lastDateTxt;
    private TextView venueLabel;
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



    public static CreateFoodRequestFragment newInstance() {
        CreateFoodRequestFragment fragment = new CreateFoodRequestFragment();
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
        appPreferences=AppPreferences.getInstance(getContext());
        return inflater.inflate(R.layout.fragment_create_request, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        container = view.findViewById(R.id.create_request_view);
        mProgressView = view.findViewById(R.id.progressView);

        createRequestBtn = view.findViewById(R.id.create_request);
        nameEdit = view.findViewById(R.id.requestTitle);
        feeEdit = view.findViewById(R.id.fee);
        descriptionEdit = view.findViewById(R.id.request_description);
        maxParticipantsEdit = view.findViewById(R.id.maxParticipants);
        venueLabel = view.findViewById(R.id.select_restaurant);
        startDateTxt = view.findViewById(R.id.request_start_date);
        startTimeTxt = view.findViewById(R.id.request_start_time);
        lastDateTxt = view.findViewById(R.id.request_last_date);
        venueLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickRestaurant();
            }
        });

        startDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateTxt);
            }
        });

        startTimeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(startTimeTxt);
            }
        });

        lastDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(lastDateTxt);
            }
        });
        createRequestBtn.setOnClickListener(new View.OnClickListener() {
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

            validationErrors.put("maxParticipantsEdit", FormValidator.isTextFieldValid(maxParticipantsEdit, getString(R.string.error_field_required)));
            validationErrors.put("feeEdit", FormValidator.isTextFieldValid(feeEdit, getString(R.string.error_field_required)));

            if (validationErrors.get("maxParticipantsEdit"))
                //validationErrors.put("maxParticipantsEdit", FormValidator.inRange(maxParticipantsEdit, MIN_PARTICIPANTS, MAX_PARTICIPANTS, getString(R.string.field_not_in_range, MIN_PARTICIPANTS, MAX_PARTICIPANTS)));

            if (validationErrors.get("feeEdit"))
                //validationErrors.put("feeEdit", FormValidator.inRange(feeEdit, MIN_FEE, MAX_FEE, getString(R.string.field_not_in_range, MIN_FEE, MAX_FEE)));

            validationErrors.put("startTimeTxt", FormValidator.isTextFieldValid(startTimeTxt, getString(R.string.error_field_required)));
            validationErrors.put("startDateTxt", FormValidator.isTextFieldValid(startDateTxt, getString(R.string.error_field_required)));

            validationErrors.put("lastDateTxt", FormValidator.isTextFieldValid(lastDateTxt, getString(R.string.error_field_required)));
            validationErrors.put("venueLabel", FormValidator.isTextFieldValid(venueLabel, getString(R.string.error_field_required)));

            if (!termsConditionCheckBox.isChecked()) {
                validationErrors.put("termsConditionCheckBox", termsConditionCheckBox.isChecked());
                termsConditionCheckBox.setError(getString(R.string.error_field_required));
            }



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
            final FoodRequest foodRequest = buildFoodRequest();
            createRequestTask(foodRequest);

        } else {
            Toast.makeText(getContext(), "Request form invalid", Toast.LENGTH_SHORT).show();
        }
    }
//
    @NonNull
    private FoodRequest buildFoodRequest() {
        String title=nameEdit.getText().toString();
        int fee=Integer.parseInt(feeEdit.getText().toString());
        String description=descriptionEdit.getText().toString();
        int limit=Integer.parseInt(maxParticipantsEdit.getText().toString());
        FoodRequest foodRequest=new FoodRequest(title,description,restaurant_id,fee,limit,startTimeTxt.getText().toString()+":00",startDateTxt.getText().toString(),lastDateTxt.getText().toString());
        return foodRequest;
    }

    private void createRequestTask(final FoodRequest foodRequest) {
        
        showProgress(true, container, mProgressView);

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<FoodResponse> call=apiInterface.createFoodRequest(appPreferences.getToken(),foodRequest);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                showProgress(false, container, mProgressView);
                if (response.body().getError().equals("false"))
                {
                    Toast.makeText(getContext(), "Request created Successfully", Toast.LENGTH_SHORT).show();
                    replaceFragment(new FoodTabFragment());
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

    private void pickRestaurant() {

        Intent intent = new Intent(getContext(), RestaurantActivity.class);
        startActivityForResult(intent, RestaurantActivity.SELECT_REQUEST_CODE);
    }

    private void showTimePickerDialog(final TextView time) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(
                            TimePicker picker, int hours, int minutes) {
                            String output = String.format("%02d:%02d", hours, minutes);
                            time.setText(output);
                            time.setTag(output);

                    }
                },
                calendar.get(GregorianCalendar.HOUR),
                calendar.get(GregorianCalendar.MINUTE),
                android.text.format.DateFormat.is24HourFormat(getContext()));
        dialog.show();
    }

    private void showDatePickerDialog(final TextView dateTxt) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int month=monthOfYear+1;
                dateTxt.setText(year+"-"+month+"-"+dayOfMonth);
                //String date = DateFormatter.dateToStr(newDate.getTime());
                //dateTxt.setText(date);
                //dateTxt.setTag(date);
                //    setListItemData(date, position, AppConstants.SELECT_DATE_ACTION);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.getDatePicker().setMinDate(System.currentTimeMillis());
        StartTime.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (RestaurantActivity.SELECT_REQUEST_CODE): {
                if (resultCode == RestaurantActivity.SELECTED_OK) {
                    restaurant_name=data.getStringExtra("RESTAURANT_NAME");
                    restaurant_id=data.getIntExtra("RESTAURANT_ID",0);
                    venueLabel.setText(restaurant_name);
                }
                break;
            }

        }
    }
}
