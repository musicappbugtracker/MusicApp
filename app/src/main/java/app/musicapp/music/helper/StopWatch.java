package app.musicapp.music.helper;

import java.util.Locale;

/**
 * Simple thread safe stop watch.
 */

/*
 * @author Karim Abou Zeid (kabouzeid)
 *
 * This file is part of Music App.

    Music App is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Music App is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Music App.  If not, see <https://www.gnu.org/licenses/>.
 */

public class StopWatch {

    /**
     * The time the stop watch was last started.
     */
    private long startTime;

    /**
     * The time elapsed before the current {@link #startTime}.
     */
    private long previousElapsedTime;

    /**
     * Whether the stop watch is currently running or not.
     */
    private boolean isRunning;

    /**
     * Starts or continues the stop watch.
     *
     * @see #pause()
     * @see #reset()
     */
    public void start() {
        synchronized (this) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    /**
     * Pauses the stop watch. It can be continued later from {@link #start()}.
     *
     * @see #start()
     * @see #reset()
     */
    public void pause() {
        synchronized (this) {
            previousElapsedTime += System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    /**
     * Stops and resets the stop watch to zero milliseconds.
     *
     * @see #start()
     * @see #pause()
     */
    public void reset() {
        synchronized (this) {
            startTime = 0;
            previousElapsedTime = 0;
            isRunning = false;
        }
    }

    /**
     * @return the total elapsed time in milliseconds
     */
    public final long getElapsedTime() {
        synchronized (this) {
            long currentElapsedTime = 0;
            if (isRunning) {
                currentElapsedTime = System.currentTimeMillis() - startTime;
            }
            return previousElapsedTime + currentElapsedTime;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d millis", getElapsedTime());
    }
}
