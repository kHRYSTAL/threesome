package me.khrystal.threesome.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.text.ParseException;
import java.util.Date;

import me.khrystal.threesome.util.StringUtil;

public class DateDeserializer implements JsonDeserializer<Date> {

    private static final String TAG = "DateDeserializer";

    @Override
    public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {

        String s = json.getAsJsonPrimitive().getAsString();

        if (StringUtil.isNullOrEmpty(s)) {
            return null;
        }
        Date date = null;
        try {
            date = GsonHelper.DATE_FORMAT.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }
}