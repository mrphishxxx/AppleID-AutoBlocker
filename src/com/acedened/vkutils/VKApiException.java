package com.acedened.vkutils;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

public class VKApiException extends Exception {
    public VKApiException(LinkedTreeMap errorData) {
        super((String) errorData.get("error_msg"));
    }
}
