package com.solution.applicationprojects.utils;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solution.applicationprojects.client.RetrofitClient;
import com.solution.applicationprojects.interfacepackage.Interfaceclass;
import com.solution.applicationprojects.model.PogoCLass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mymvvm  extends ViewModel {


    Interfaceclass apiInterface = RetrofitClient.getClient().create(Interfaceclass.class);


    private MutableLiveData<PogoCLass> userRegister;

    public LiveData<PogoCLass> userRegisteration(final Activity activity, String name, String phone, String email, String type, String password, String country, String reg_id, String device_type) {
        userRegister = new MutableLiveData<>();
        if (CommonUtil.isNetworkConnected(activity)) {
            CommonUtil.showProgress(activity, "loading");
            apiInterface.registration(name, phone, email, type, password, country, reg_id, device_type).enqueue(new Callback<PogoCLass>() {
                @Override
                public void onResponse(Call<PogoCLass> call, Response<PogoCLass> response) {
                    CommonUtil.dismissProgress();
                    if (response.body() != null) {
                        userRegister.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PogoCLass> call, Throwable t) {
                    CommonUtil.dismissProgress();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return userRegister;
    }

}
