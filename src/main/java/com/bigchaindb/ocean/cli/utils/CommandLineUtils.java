package com.bigchaindb.ocean.cli.utils;

import org.json.JSONObject;

public abstract class CommandLineUtils {

    public static String prettyJson(String json)    {
        return new JSONObject(json).toString(4);
    }
}
