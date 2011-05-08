/*
    DroidFish - An Android chess program.
    Copyright (C) 2011  Peter Österlund, peterosterlund2@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.petero.droidfish.engine;

public class NativePipedProcess implements UCIEngine {
    static {
        System.loadLibrary("jni");
    }

    private boolean processAlive;

    private int strength = 1000;

    NativePipedProcess() {
        processAlive = false;
    }

    /** Start process. */
    @Override
    public final void initialize() {
        if (!processAlive) {
            startProcess();
            processAlive = true;
        }
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
        writeLineToEngine(String.format("setoption name Skill Level value %d", strength/50));
    }

    @Override
    public String addStrengthToName() {
        return strength < 1000 ? String.format(" (%.1f%%)", strength * 0.1) : "";
    }

    /** Shut down process. */
    @Override
    public final void shutDown() {
        if (processAlive) {
            writeLineToEngine("quit");
            processAlive = false;
        }
    }

    /**
     * Read a line from the process.
     * @param timeoutMillis Maximum time to wait for data
     * @return The line, without terminating newline characters,
     *         or empty string if no data available,
     *         or null if I/O error.
     */
    @Override
    public final String readLineFromEngine(int timeoutMillis) {
        String ret = readFromProcess(timeoutMillis);
        if (ret == null)
            return null;
        if (ret.length() > 0) {
//          System.out.printf("Engine -> GUI: %s\n", ret);
        }
        return ret;
    }

    /** Write a line to the process. \n will be added automatically. */
    @Override
    public final synchronized void writeLineToEngine(String data) {
//      System.out.printf("GUI -> Engine: %s\n", data);
        writeToProcess(data + "\n");
    }

    /** Start the child process. */
    private final native void startProcess();

    /**
     * Read a line of data from the process.
     * Return as soon as there is a full line of data to return, 
     * or when timeoutMillis milliseconds have passed.
     */
    private final native String readFromProcess(int timeoutMillis);

    /** Write data to the process. */
    private final native void writeToProcess(String data);
}
