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

package com.sasha.asuna.mod.remote.packet;

import com.sasha.asuna.mod.remote.PacketData;
import com.sasha.asuna.mod.remote.PacketProcessor;

public class AttemptLoginPacket extends Packet.Outgoing {

    @PacketData
    private String username;
    @PacketData private String password; //todo encryption.

    public AttemptLoginPacket(PacketProcessor processor) {
        super(processor, 1);
    }

    public void setCredentials(String username, String passwd) {
        this.username = username;
        this.password = passwd;
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(AttemptLoginPacket.class, this));
    }
}
