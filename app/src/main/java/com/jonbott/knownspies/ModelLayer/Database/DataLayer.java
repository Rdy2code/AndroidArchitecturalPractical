package com.jonbott.knownspies.ModelLayer.Database;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.Realm;

public interface DataLayer {

    //region Database Methods
    void loadSpiesFromLocal(Function<Spy, SpyDTO> translationBlock,
                            Consumer<List<SpyDTO>> onNewResults) throws Exception;

    void clearSpies(Action finished) throws Exception;

    void persistDTOs(List<SpyDTO> dtos, BiFunction<SpyDTO, Realm, Spy> translationBlock);

    Spy spyForId(int spyId);

    Spy spyForName(String name);
}
