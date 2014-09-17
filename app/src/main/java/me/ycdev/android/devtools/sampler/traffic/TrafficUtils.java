package me.ycdev.android.devtools.sampler.traffic;

import java.io.File;
import java.io.IOException;

import me.ycdev.androidlib.utils.IoUtils;
import me.ycdev.androidlib.utils.StringUtils;

public class TrafficUtils {
    private static final String DEV_UID_RCV_FILE = "/proc/uid_stat/%d/tcp_rcv";
    private static final String DEV_UID_SND_FILE = "/proc/uid_stat/%d/tcp_snd";

    /**
     * Get traffic stats of specified UIDs.
     * @return null may be returned if failed to read the stat
     */
    public static AppTrafficStat getTrafficStat(int uid) {
        AppTrafficStat trafficStat = new AppTrafficStat(uid);
        try {
            String rcvFile = String.format(DEV_UID_RCV_FILE, uid);
            String rcvData = IoUtils.readAllLines(rcvFile);
            trafficStat.recvBytes = StringUtils.parseLong(rcvData, 0);

            String sndFile = String.format(DEV_UID_SND_FILE, uid);
            String sndData = IoUtils.readAllLines(sndFile);
            trafficStat.sendBytes = StringUtils.parseLong(sndData, 0);

            return trafficStat;
        } catch (IOException e) {
            // The files may not exist if no traffic happens
        }
        return trafficStat;
    }

}
