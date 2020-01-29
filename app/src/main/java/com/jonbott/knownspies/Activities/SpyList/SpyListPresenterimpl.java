package com.jonbott.knownspies.Activities.SpyList;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Enums.Gender;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class SpyListPresenterimpl implements SpyListPresenter {

    private static final String TAG = SpyListPresenterimpl.class.getSimpleName();

    private ModelLayer modelLayer;
    private BehaviorSubject<List<SpyDTO>> spies = BehaviorSubject.create();

    public SpyListPresenterimpl(ModelLayer modelLayer) {
        this.modelLayer = modelLayer;
    }

    //region Presenter Methods

    @Override
    public void loadData(Consumer<Source> notifyDataReceived) {
        modelLayer.loadData(this::onDataLoaded, notifyDataReceived);
    }

    private void onDataLoaded(List<SpyDTO> spyList) {
        spies.onNext(spyList);      //push out old list in observable and add this new list
                                    //tell all subscribers that the list has changed
    }

    @Override
    public void addNewSpy() {
        String name = "Adam Smith";
        List<SpyDTO> newSpies = Arrays.asList(new SpyDTO(100,
                25,
                name,
                Gender.male,
                "wealth",
                "adamsmith",
                true));

        modelLayer.save(newSpies, () -> {
            SpyDTO adam = modelLayer.spyForName(name);
            List<SpyDTO> spyList = spies.getValue();
            spyList.add(0, adam);
            spies.onNext(spyList);
        });
    }

    @Override
    public BehaviorSubject<List<SpyDTO>> spies() {
        return spies;
    }

    //endregion


}
