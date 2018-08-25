package com.sasha.adorufu.remote.packet;



import com.sasha.adorufu.remote.PacketProcessor;

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
