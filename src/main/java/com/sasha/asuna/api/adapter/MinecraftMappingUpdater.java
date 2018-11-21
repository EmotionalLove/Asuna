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

package com.sasha.asuna.api.adapter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Sasha at 3:34 PM on 9/18/2018
 * This class will download the MCP mappings from the MCPbot website.
 */
public class MinecraftMappingUpdater {

    /**
     * The MCP team doesn't allow developers distribute the MCP mappings, which basically means that
     * I can't just put them in the jarfile. The client has to download them from the website and
     * unzip the file and BLAH BLAH BLAH u get the idea :sleepy:
     */
    public static void updateMappings() {
        try {
            File dir = new File("mc_mappings");
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!dir.isDirectory()) {
                dir.delete();
                dir.mkdir();
            }
            for (File file : dir.listFiles()) {
                file.delete();
            }
            File file = new File("mc_mappings" + File.separator + "mappings_archive.zip");
            FileUtils.copyURLToFile(new URL("http://export.mcpbot.bspk.rs/mcp_snapshot/20180731-1.12/mcp_snapshot-20180731-1.12.zip"), file);
            unzip(file, dir);
            file.delete(); // delete the zip after unzipping it.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzip(File zipFile, File destDir) {
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir, fileName);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) fos.write(buffer, 0, len);
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
