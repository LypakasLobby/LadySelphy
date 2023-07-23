package com.lypaka.ladyselphy;

import java.util.Timer;
import java.util.TimerTask;

public class StorageSaveTask {

    public static Timer timer;

    public static void startTimer() {

        if (timer != null) {

            timer.cancel();

        }

        if (ConfigGetters.saveInterval == 0) return;

        long interval = ConfigGetters.saveInterval * 1000L;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                LadySelphy.configManager.getConfigNode(1, "Cooldown-Storage").setValue(ConfigGetters.cooldownStorageMap);
                LadySelphy.configManager.getConfigNode(1, "Storage").setValue(ConfigGetters.storageMap);
                LadySelphy.configManager.save();
                LadySelphy.logger.info("Saved storage!");

            }

        }, 0, interval);

    }

}
