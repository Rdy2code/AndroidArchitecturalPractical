package com.jonbott.knownspies.ModelLayer.Translation;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.DTOType;

import java.util.List;

import io.realm.Realm;

public class TranslationLayerimpl implements TranslationLayer {

    private static final String TAG = "TranslationLayer";

    private Gson gson;

    private SpyTranslator spyTranslator;

    public TranslationLayerimpl(Gson gson, SpyTranslator spyTranslator) {
        this.gson = gson;
        this.spyTranslator = spyTranslator;
    }

    @Override
    public List<SpyDTO> convertJson(String json) {

        Log.d(TAG, "converting json to dtos");

        TypeToken<List<SpyDTO>> token = new TypeToken<List<SpyDTO>>(){};

        return gson.fromJson(json, token.getType());
    }

    @Override
    public SpyTranslator translatorFor(DTOType type) {
        switch (type) {
            case spy:
                return spyTranslator;
            default:
                return spyTranslator;
        }
    }

    @Override
    public SpyDTO translate(Spy spy) {
        return spyTranslator.translate(spy);
    }

    @Override
    public Spy translate(SpyDTO dto, Realm realm) {
        Spy spy = spyTranslator.translate(dto, realm);
        return  spy;
    }
}
