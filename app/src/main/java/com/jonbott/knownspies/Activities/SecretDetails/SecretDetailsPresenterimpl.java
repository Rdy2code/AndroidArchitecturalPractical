package com.jonbott.knownspies.Activities.SecretDetails;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import io.reactivex.functions.Consumer;

public class SecretDetailsPresenterimpl implements SecretDetailsPresenter {
    public String password;

    @Override
    public String getPassword() {
        return password;
    }

    private int spyId;
    private SpyDTO spy;
    private ModelLayer modelLayer;

    public SecretDetailsPresenterimpl(int spyId, ModelLayer modelLayer) {
        this.spyId = spyId;
        this.modelLayer = modelLayer;

        configureData();
    }

    private void configureData() {
        spy = this.modelLayer.spyForId(spyId);
        password = spy.password;
    }

    @Override
    public void crackPassword(Consumer<String> finished) {
        Threading.async(()-> {
            //fake processing work
            Thread.sleep(2000);
            return true;
        }, success -> {
            finished.accept(password);
        });
    }

}
