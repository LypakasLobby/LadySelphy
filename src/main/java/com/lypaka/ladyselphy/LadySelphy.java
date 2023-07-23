package com.lypaka.ladyselphy;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ladyselphy")
public class LadySelphy {

    public static final String MOD_ID = "ladyselphy";
    public static final String MOD_NAME = "LadySelphy";
    public static final Logger logger = LogManager.getLogger();
    public static BasicConfigManager configManager;

    public LadySelphy() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/ladyselphy"));
        String[] files = new String[]{"ladyselphy.conf"};
        configManager = new BasicConfigManager(files, dir, LadySelphy.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();

    }

}
