/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.misc;

import com.sasha.asuna.mod.AsunaMod;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;

/**
 * Used for easily building the NBT for saving books
 * <p>
 * Created by Sasha at 3:27 PM on 9/16/2018
 */
public class AsunaBookNBTBuilder {

    private ItemStack book;

    private String author;
    private String title;
    private boolean sign;

    public AsunaBookNBTBuilder(ItemStack book) {
        this.book = book;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public void push() {
        this.book.setTagInfo("title", new NBTTagString(this.title));
        this.book.setTagInfo("author", new NBTTagString(this.author));
        if (AsunaMod.minecraft.getConnection() == null) {
            return;
        }
        PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
        packetbuffer.writeItemStack(this.book);
        AsunaMod.minecraft.getConnection().sendPacket(new CPacketCustomPayload(sign ? "MC|BSign" : "MC|BEdit", packetbuffer));
    }

}
