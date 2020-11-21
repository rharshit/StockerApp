package com.rharshit.stocker.ui.portfolio;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.Joke;
import com.rharshit.stocker.service.ChuckNorrisService;

import static com.rharshit.stocker.constant.APIConstants.BASE_URL_CHUCK;

public class PortfolioViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;
    private ChuckNorrisService chuckNorrisService;

    public PortfolioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is portfolio fragment");
    }

    @Override
    public void initService() {
        chuckNorrisService = new ChuckNorrisService(this, BASE_URL_CHUCK);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void showJoke() {
        BaseAsyncTask<Void, Void, Joke> joke = chuckNorrisService.getJokeTask(this::displayJoke);
        joke.execute();
    }

    private void displayJoke(Joke joke) {
        getActivity().makeToast(joke.value, Toast.LENGTH_LONG);
    }
}