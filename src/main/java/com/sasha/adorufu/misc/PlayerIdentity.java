/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sasha.adorufu.AdorufuMod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class PlayerIdentity implements Serializable {
    private String displayName;
    private String stringUuid;
    private Long lastUpdated;

    public PlayerIdentity(String stringUuid) {
        String formattedUuid = stringUuid.replace("-", "");
        this.stringUuid = stringUuid;
        this.displayName = "Loading...";
        new Thread(() -> {
            this.displayName = getName(formattedUuid);
            AdorufuMod.DATA_MANAGER.identityCacheMap.put(this.getStringUuid(), this);
            try {
                AdorufuMod.DATA_MANAGER.savePlayerIdentity(this, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        this.lastUpdated = System.currentTimeMillis();
    }

    public String getDisplayName() {
        return this.displayName;
    }
    public String getStringUuid() {
        return this.stringUuid;
    }

    /**
     * run this on a seperate thread pls so u dont kill the game
     */
    public void updateDisplayName() {
        new Thread(() -> {
            PlayerIdentity identity = new PlayerIdentity(this.stringUuid);
            this.displayName = identity.getDisplayName();
            this.lastUpdated = System.currentTimeMillis();
            identity = null;
        }).start();
    }

    public boolean shouldUpdate() {
        return System.currentTimeMillis() - this.lastUpdated >= 6.048e+8 /* one week */;
    }

    private static String getName(String UUID) {
        LinkedHashMap<String, Long> nameHistories = new LinkedHashMap<>();
        try {
            URL e = new URL("https://api.mojang.com/user/profiles/" + UUID.replace("-", "") + "/names");
            URLConnection connection = e.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonb.append(line + "\n");
            }
            String formattedjson = jsonb.toString();
            reader.close();

            JsonArray array = new JsonParser().parse(formattedjson).getAsJsonArray();
            JsonObject obj = array.get(array.size() - 1).getAsJsonObject();
            String nameform = obj.get("name").getAsString();
            try {
                obj.get("changedToAt");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(obj.get("changedToAt").getAsLong());
                long changedAt = obj.get("changedToAt").getAsLong();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                //nameform = nameform + " @ " + getDateFormat(mMonth + 1, mDay, mYear);
                nameHistories.put(nameform, changedAt);
                return nameform;
            } catch (Exception ee) {
                return nameform;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("fuck");
        }
        return UUID;
    }
    private static String getDateFormat(int mm, int dd, int yyyy) {
        return mm + "/" + dd + "/" + yyyy;
    }
}
