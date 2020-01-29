package com.jonbott.knownspies.Dependencies;

import android.os.Bundle;
import android.provider.DocumentsContract;

import com.google.gson.Gson;
import com.jonbott.knownspies.Activities.Details.SpyDetailsActivity;
import com.jonbott.knownspies.Activities.Details.SpyDetailsPresenter;
import com.jonbott.knownspies.Activities.Details.SpyDetailsPresenterimpl;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsActivity;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsPresenter;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsPresenterimpl;
import com.jonbott.knownspies.Activities.SpyList.SpyListActivity;
import com.jonbott.knownspies.Activities.SpyList.SpyListPresenter;
import com.jonbott.knownspies.Activities.SpyList.SpyListPresenterimpl;
import com.jonbott.knownspies.Coordinators.RootCoordinator;
import com.jonbott.knownspies.Helpers.Constants;
import com.jonbott.knownspies.ModelLayer.Database.DataLayer;
import com.jonbott.knownspies.ModelLayer.Database.DataLayerimpl;
import com.jonbott.knownspies.ModelLayer.ModelLayer;
import com.jonbott.knownspies.ModelLayer.ModelLayerimpl;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayer;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayerimpl;
import com.jonbott.knownspies.ModelLayer.Translation.SpyTranslator;
import com.jonbott.knownspies.ModelLayer.Translation.SpyTranslatorimpl;
import com.jonbott.knownspies.ModelLayer.Translation.TranslationLayer;
import com.jonbott.knownspies.ModelLayer.Translation.TranslationLayerimpl;

import java.util.NoSuchElementException;

import io.realm.Realm;

public class DependencyRegistry {

    //region Shared Singleton instance of Registry
    public static DependencyRegistry shared = new DependencyRegistry();
    //endregion

    //region External Dependencies
    //region Gson
    private Gson gson = new Gson();
    //endregion

    //region Realm
    private Realm realm = Realm.getDefaultInstance();

    public Realm newRealmInstanceOnCurrentThread() {
        return Realm.getInstance(realm.getConfiguration());
    }
    //endregion
    //endregion

    //region Coordinators
    public RootCoordinator rootCoordinator = new RootCoordinator();
    //endregion

    //region Injected Singletons
    public SpyTranslator spyTranslator = new SpyTranslatorimpl();

    public TranslationLayer translationLayer = createTranslationLayer();
    private TranslationLayer createTranslationLayer() {
        return new TranslationLayerimpl(gson, spyTranslator);
    }

    public DataLayer dataLayer = createDataLayer();
    private DataLayer createDataLayer() {
        return new DataLayerimpl(realm, this::newRealmInstanceOnCurrentThread);
    }

    public NetworkLayer networkLayer = new NetworkLayerimpl();

    public ModelLayer modelLayer = createModelLayer();
    private ModelLayer createModelLayer() {
        return new ModelLayerimpl(networkLayer, dataLayer, translationLayer);
    }

    //endregion

    //region Injection Methods
    public void inject (SpyDetailsActivity activity, Bundle bundle) throws NoSuchElementException {
        int spyId = idFromBundle(bundle);
        //Instantiate the presenter, then inject the presenter into the activity
        SpyDetailsPresenter presenter = new SpyDetailsPresenterimpl(spyId, activity, modelLayer);
        activity.configureWith(presenter, rootCoordinator);
    }

    public void inject (SecretDetailsActivity activity, Bundle bundle) throws NoSuchElementException {
        int spyId = idFromBundle(bundle);
        //Instantiate the presenter, then inject the presenter into the activity
        SecretDetailsPresenter presenter = new SecretDetailsPresenterimpl(spyId, modelLayer);
        activity.configureWith(presenter, rootCoordinator);
    }

    public void inject (SpyListActivity activity) throws NoSuchElementException {
        SpyListPresenter presenter = new SpyListPresenterimpl(modelLayer);
        //Instantiate the presenter, then inject the presenter into the activity
        activity.configureWith(presenter, rootCoordinator);
    }
    //endregion

    //region Helper Methods
    private int idFromBundle(Bundle bundle) {
        if (bundle == null) throw new NoSuchElementException("Unable to get spy id from bundle");
        int spyId = bundle.getInt(Constants.spyIdKey);
        if (spyId == 0) throw new NoSuchElementException("Unable to get spy id from bundle");
        return spyId;
    }
    //endregion

}
