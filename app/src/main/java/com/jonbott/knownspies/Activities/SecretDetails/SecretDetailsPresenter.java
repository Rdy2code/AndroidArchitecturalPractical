package com.jonbott.knownspies.Activities.SecretDetails;

import io.reactivex.functions.Consumer;

public interface SecretDetailsPresenter {

    String getPassword();
    void crackPassword(Consumer<String> finished);
}
