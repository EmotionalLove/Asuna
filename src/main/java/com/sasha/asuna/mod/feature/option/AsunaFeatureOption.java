/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

package com.sasha.asuna.mod.feature.option;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.misc.YMLParser;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class AsunaFeatureOption<T> { //todo figure out how to store these

    public static final String OPTIONS_DATA_NAME = "AsunaFeatureOptionsData.yml";

    private String identifer;
    private T status;

    private Consumer<T> consumer;

    public AsunaFeatureOption(String identifer, @Nonnull T def) {
        this.identifer = identifer;
        this.status = def;
        this.recover();
    }
    public AsunaFeatureOption(String identifer, @Nonnull T def, Consumer<T> consumer) {
        this.identifer = identifer;
        this.status = def;
        this.consumer = consumer;
        this.recover();
    }

    public T getStatus() {
        return status;
    }

    public void setStatus(T status) {
        this.status = status;
        if (this.consumer != null) consumer.accept(status);
        this.save();
    }

    public String getIdentifer() {
        return identifer;
    }

    private void save() {
        try {
            File file = new File(OPTIONS_DATA_NAME);
            if (!file.exists()) file.createNewFile();
            YMLParser parser = new YMLParser(file);
            parser.set(identifer, status);
            parser.save();
        } catch (IOException ex) {
            ex.printStackTrace();
            AsunaMod.logErr(true, "A severe error occured whilst saving the option " + identifer);
        }
    }

    private void recover() {
        try {
            File file = new File(OPTIONS_DATA_NAME);
            if (!file.exists()) file.createNewFile();
            YMLParser parser = new YMLParser(file);
            if (!parser.exists(identifer)) {
                parser.set(identifer, status);
                parser.save();
                return;
            }
            status = (T) parser.get(identifer);
        } catch (IOException ex) {
            ex.printStackTrace();
            AsunaMod.logErr(true, "A severe error occured whilst saving the option " + identifer);
        }
    }
}
