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

package com.sasha.adorufu.mod.remote.packet;

import com.sasha.adorufu.mod.remote.PacketProcessor;

import java.util.ArrayList;

public class Packet {
    public static abstract class Incoming {

        private int id;
        private PacketProcessor processor;

        /**
         * Sets up a packet that's expected to be incoming Packet
         * @param processor a relevant instance of a PacketProcessor
         * @param id the packet's numeric ID. For clients, incoming packets should be negative. For servers, incoming packets should be positive.
         */
        public Incoming(PacketProcessor processor, int id) {
            this.id = id;
            this.processor = processor;
        }

        public int getId() {
            return id;
        }

        public abstract void processIncomingPacket();
        public abstract void setDataVars(ArrayList<String> pckData);

        public PacketProcessor getProcessor() {
            return processor;
        }
    }
    public static abstract class Outgoing {
        private int id;
        private PacketProcessor processor;

        /**
         * Sets up a packet that's expected to be an outgoing Packet
         * @param processor a relevant instance of a PacketProcessor
         * @param id the packet's numeric ID. For clients, incoming packets should be negative. For servers, incoming packets should be positive.
         * The id for related pcks are always opposite of each other, so if the id for an incoming pck is 9, the pck related to it would be -9
         */
        public Outgoing(PacketProcessor processor, int id) {
            this.processor = processor;
            this.id = id;
        }
        public abstract void dispatchPck();

        public int getId() {
            return id;
        }

        public PacketProcessor getProcessor() {
            return processor;
        }
    }
}
