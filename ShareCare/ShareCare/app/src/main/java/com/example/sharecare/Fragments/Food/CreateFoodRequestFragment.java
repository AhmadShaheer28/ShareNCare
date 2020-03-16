package com.example.sharecare.Fragments.Food;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.sharecare.ApiResponse.LoginResponse.LoginResponse;
import com.example.sharecare.ApiResponse.RestaurantListResponse.Restaurant;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.DateFormatter;
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

    private static final String TOURNAMENT_JSON = "tournament_json";
    public static String LOG_TAG = "CreateTournament";
    private static final int MAX_PARTICIPANTS = 256;
    private static final int MIN_PARTICIPANTS = 2;
    private static final double MAX_FEE = 100000.0;
    private static final double MIN_FEE = 0.0;

    AppPreferences appPreferences;


//    private Tournament tournament;
//    private ServiceCharge serviceChargeTournamentCreation;
//    private ServiceCharge serviceChargeTournamentRequest;


    private int restaurant_id=0;
    private String restaurant_name="";
    private Button createTournamentBtn;
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
//    private Location selectedVenue;

    private String organizerContactNumber;

    public static CreateFoodRequestFragment newInstance(String tournamentJSON) {
        CreateFoodRequestFragment fragment = new CreateFoodRequestFragment();
        Bundle args = new Bundle();
        if (tournamentJSON != null)
            args.putString(TOURNAMENT_JSON, tournamentJSON);

        fragment.setArguments(args);
        return fragment;
    }

//    public void setTournament(Tournament tournament) {
//        this.tournament = tournament;
//    }


    public static CreateFoodRequestFragment newInstance() {
        CreateFoodRequestFragment fragment = new CreateFoodRequestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getString(TOURNAMENT_JSON) != null) {
//                tournament = Tournament.fromJSON(getArguments().getString(TOURNAMENT_JSON));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        appPreferences=AppPreferences.getInstance(getContext());
        return inflater.inflate(R.layout.fragment_create_tournament, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
//        selectedVenue = getSession().getArena();
        container = view.findViewById(R.id.create_tournament_view);
        mProgressView = view.findViewById(R.id.progressView);

        createTournamentBtn = view.findViewById(R.id.createTournament);
        //      addTeamSwitch = view.findViewById(R.id.add_team_switch);
        nameEdit = view.findViewById(R.id.tournamentName);
        feeEdit = view.findViewById(R.id.fee);
        descriptionEdit = view.findViewById(R.id.tournament_description);
        maxParticipantsEdit = view.findViewById(R.id.maxParticipants);
        venueLabel = view.findViewById(R.id.select_arena);
        startDateTxt = view.findViewById(R.id.tournament_start_date);
        startTimeTxt = view.findViewById(R.id.tournament_start_time);
        lastDateTxt = view.findViewById(R.id.tournament_last_date);
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

//        termsAndConditionsLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.challengenow.se/terms"));
//                    startActivity(myIntent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(getContext(), "No application can handle this request. Please install a webbrowser.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        createTournamentBtn.setTag(EventActionTag.EVENT_CREATE);
        lastDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(lastDateTxt);
            }
        });
        createTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodRequest();
            }
        });
        //checkTeamProfileRegistration();

    }

//    private void checkTeamProfileRegistration() {
//        try {
//            organizerContactNumber = TeamHelper.getContactNumber(getSession().getTeam());
//            initLoad();
//        } catch (TeamProfileException e) {
//            Toast.makeText(getContext(), getString(R.string.complete_your_profile), Toast.LENGTH_SHORT).show();
//            replaceFragment(EditProfileFragment.newInstance(getSession().getTeam()));
//        }
//    }

//    private void initLoad() {
//        showProgress(true, container, mProgressView);
//        getTournamentServiceCharge();
//    }
//
//    private void getTournamentServiceCharge() {
//        TournamentViewModel viewModel;
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.findChargesByServiceType().observe(this, new Observer<ServiceCharge>() {
//            @Override
//            public void onChanged(ServiceCharge serviceCharge) {
//                showProgress(false, container, mProgressView);
//                serviceChargeTournamentCreation =serviceCharge;
//                getTournamentRequestServiceCharge();
//            }
//        });
//        /*
//        Call<ServiceCharge> restApiCall = getChallengeNowApiClient().findChargesByServiceType(Payment.PaidServiceType.TOURNAMENT);
//        restApiCall.enqueue(new Callback<ServiceCharge>() {
//            @Override
//            public void onResponse(Call<ServiceCharge> call, Response<ServiceCharge> response) {
//
//                if (response.isSuccessful()) {
//                    serviceChargeTournamentCreation = response.body();
//                    getTournamentRequestServiceCharge();
//                } else {
//                    Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ServiceCharge> call, Throwable t) {
//                Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
//                showProgress(false, container, mProgressView);
//            }
//        });*/
//    }
//
//    private void getTournamentRequestServiceCharge() {
//
//        TournamentViewModel viewModel;
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.findChargesByServiceType().observe(this, new Observer<ServiceCharge>() {
//            @Override
//            public void onChanged(ServiceCharge serviceCharge) {
//                serviceChargeTournamentRequest=serviceCharge;
//                serviceChargeLabel.setText(getString(R.string.service_charge_for_paid_tournaments, serviceChargeTournamentCreation.fixCost(), serviceChargeTournamentRequest.variableCostInPercentage()));
//                if (tournament != null) {
//                    setTournament(tournament);
//                    setTournamentUI(tournament);
//                }
//            }
//        });
//        /*Call<ServiceCharge> restApiCall = getChallengeNowApiClient().findChargesByServiceType(Payment.PaidServiceType.TOURNAMENT_REQUEST);
//        restApiCall.enqueue(new Callback<ServiceCharge>() {
//            @Override
//            public void onResponse(Call<ServiceCharge> call, Response<ServiceCharge> response) {
//                if (response.isSuccessful()) {
//                    serviceChargeTournamentRequest = response.body();
//                    serviceChargeLabel.setText(getString(R.string.service_charge_for_paid_tournaments, serviceChargeTournamentCreation.fixCost(), serviceChargeTournamentRequest.variableCostInPercentage()));
//                    if (tournament != null) {
//                        setTournament(tournament);
//                        setTournamentUI(tournament);
//                    }
//
//                } else {
//                    Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
//                }
//                showProgress(false, container, mProgressView);
//            }
//
//            @Override
//            public void onFailure(Call<ServiceCharge> call, Throwable t) {
//                Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
//                showProgress(false, container, mProgressView);
//            }
//        });*/
//    }


    private boolean validateCreateTournamentForm() {

        Map<String, Boolean> validationErrors = new HashMap<>();
        try {

            validationErrors.put("nameEdit", FormValidator.isTextFieldValid(nameEdit, getString(R.string.error_field_required)));
            validationErrors.put("descriptionEdit", FormValidator.isTextFieldValid(descriptionEdit, getString(R.string.error_field_required)));

            validationErrors.put("maxParticipantsEdit", FormValidator.isTextFieldValid(maxParticipantsEdit, getString(R.string.error_field_required)));
            validationErrors.put("feeEdit", FormValidator.isTextFieldValid(feeEdit, getString(R.string.error_field_required)));

            if (validationErrors.get("maxParticipantsEdit"))
                validationErrors.put("maxParticipantsEdit", FormValidator.inRange(maxParticipantsEdit, MIN_PARTICIPANTS, MAX_PARTICIPANTS, getString(R.string.field_not_in_range, MIN_PARTICIPANTS, MAX_PARTICIPANTS)));

            if (validationErrors.get("feeEdit"))
                validationErrors.put("feeEdit", FormValidator.inRange(feeEdit, MIN_FEE, MAX_FEE, getString(R.string.field_not_in_range, MIN_FEE, MAX_FEE)));

            validationErrors.put("startTimeTxt", FormValidator.isTextFieldValid(startTimeTxt, getString(R.string.error_field_required)));
            validationErrors.put("startDateTxt", FormValidator.isTextFieldValid(startDateTxt, getString(R.string.error_field_required)));

            validationErrors.put("lastDateTxt", FormValidator.isTextFieldValid(lastDateTxt, getString(R.string.error_field_required)));
            validationErrors.put("venueLabel", FormValidator.isTextFieldValid(venueLabel, getString(R.string.error_field_required)));

            if (!termsConditionCheckBox.isChecked()) {
                validationErrors.put("termsConditionCheckBox", termsConditionCheckBox.isChecked());
                termsConditionCheckBox.setError(getString(R.string.error_field_required));
            }


//            long startDateTime = DateFormatter.createDateTimeToEpoch(startDateTxt.getText().toString(), startTimeTxt.getText().toString());
//            long lastApplicationDate = DateFormatter.formatToEpoch(DateFormatter.dateToDateTimeEndOfDay(lastDateTxt.getText().toString()));
//            if (lastApplicationDate > startDateTime) {
//
//                startDateTxt.setError(getString(R.string.start_date_earlier_than_last_application_date));
//                lastDateTxt.setError(getString(R.string.start_date_earlier_than_last_application_date));
//                validationErrors.put("datesInvalid", true);
//            }

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

//    private void setTournamentUI(Tournament tournament) {
//
//        Timber.i(tournament.toJson());
//
//        if (tournament.getMessage() == null || tournament.getMessage().isEmpty())
//            descriptionEdit.setText(getString(R.string.no_description));
//
//        nameEdit.setText(tournament.getName());
//        descriptionEdit.setText(tournament.getMessage());
//        venueLabel.setText(tournament.getLocationName());
//        startDateTxt.setText(DateFormatter.convertDate(tournament.getStartDateTime()));
//        maxParticipantsEdit.setText(Integer.toString(tournament.getMaxParticipants()));
//        feeEdit.setText(Double.toString(tournament.getFee()));
//        lastDateTxt.setText(DateFormatter.convertDate(tournament.getLastApplicationDate()));
//        startTimeTxt.setText(DateFormatter.convertTime(tournament.getStartDateTime()));
//        startDateTxt.setText(DateFormatter.convertDate(tournament.getStartDateTime()));
//        selectedVenue = new Location();
//        selectedVenue.setId(tournament.getLocationId());
//        selectedVenue.setName(tournament.getLocationName());
//
//        if (tournament.getId() != null && !tournament.isPublished()) {
//            createTournamentBtn.setTag(EventActionTag.EVENT_PUBLISH);
//            createTournamentBtn.setText(getString(R.string.publish));
//        }
//    }
//
    private void saveFoodRequest() {

        if (validateCreateTournamentForm()) {
            final FoodRequest foodRequest = buildFoodRequest();
            createTournamentTask(foodRequest);

        } else {
            Toast.makeText(getContext(), getString(R.string.tournament_invalid), Toast.LENGTH_SHORT).show();
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
//
//
//    private void initiatePaymentRequest(Tournament tournament) {
//        Intent resultIntent = new Intent(getContext(), PaymentActivity.class);
//        SportEvent sportEvent = TournamentHelper.createSportEventFromTournament(tournament, serviceChargeTournamentCreation.getFee());
//        resultIntent.putExtra("sportEvent", sportEvent.toJson());
//        startActivityForResult(resultIntent, PaymentActivity.PAYMENT_INITIATE);
//    }
//
//
    private void createTournamentTask(final FoodRequest foodRequest) {
        //Timber.i("create tournament request %s", tournament.toJson());
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
                    replaceFragment(new FoodRequestListFragment());
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
//
//    private void sendTournamentRequest(final Tournament tournament) {
//        TournamentRequest tournamentRequest = TournamentHelper.createTournamentRequest(tournament, getSession().getTeam());
//        Call<TournamentRequest> restApiCall = getChallengeNowApiClient().createTournament(tournamentRequest);
//        restApiCall.enqueue(new Callback<TournamentRequest>() {
//            @Override
//            public void onResponse(Call<TournamentRequest> call, Response<TournamentRequest> response) {
//                Timber.tag(LOG_TAG).w("tournament response code = %s", response.code());
//                if (response.isSuccessful()) {
//                    Toast.makeText(getContext(), getString(R.string.joined) + " " + nameEdit.getText(), Toast.LENGTH_LONG).show();
//                } else if (response.code() == 400) {
//                    if (tournament.hasLastDatePassed())
//                        Toast.makeText(getContext(), getString(R.string.can_not_join) + " " + tournament.getName() + " " + getString(R.string.registration_closed), Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getContext(), getString(R.string.can_not_join) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    //Toast.makeText(getContext(), getString(R.string.error_occured) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TournamentRequest> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.error_occured) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
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
                dateTxt.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
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
