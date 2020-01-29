package com.jonbott.knownspies.ModelLayer.Translation;

import com.jonbott.knownspies.ModelLayer.Enums.Gender;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;

import io.realm.Realm;

/**
 * Created by j on 4/24/17.
 */

public class SpyTranslatorimpl implements SpyTranslator {

    @Override
    public SpyDTO translate(Spy from) {
        if (from == null) return null;

        return new SpyDTO(
                from.id,
                from.age,
                from.name,
                Gender.valueOf(from.gender),
                from.password,
                from.imageName,
                from.isIncognito);
    }

    @Override
    public Spy translate(SpyDTO dto, Realm realm) {
        if (dto == null) return null;

        realm.executeTransaction(realm1 -> {
            Spy spy = realm1.createObject(Spy.class);
            spy.id          = dto.id;
            spy.age         = dto.age;
            spy.name        = dto.name;
            spy.gender      = dto.gender.name();
            spy.password    = dto.password;
            spy.imageName   = dto.imageName;
            spy.isIncognito = dto.isIncognito;
        });

        Spy spy = realm.where(Spy.class).equalTo("name", dto.name).findFirst();

        return spy;
    }

}
