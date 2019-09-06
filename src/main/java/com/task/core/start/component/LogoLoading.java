package com.task.core.start.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 图标
 *
 * @author Frank
 */
public class LogoLoading {

    String logo = "  _____      _      ____    _  __                      __  __    ____ \n" +
            " |_   _|    / \\    / ___|  | |/ /                     |  \\/  |  / ___|\n" +
            "   | |     / _ \\   \\___ \\  | ' /     _____   _____    | |\\/| | | |    \n" +
            "   | |    / ___ \\   ___) | | . \\    |_____| |_____|   | |  | | | |___ \n" +
            "   |_|   /_/   \\_\\ |____/  |_|\\_\\                     |_|  |_|  \\____|\n" +
            "                                                                      ";
    String signature = "\n:: version:{version} ::                        Task Manage Core \n";

    String version = "1.0";

    public void out(){

        String outText = logo + signature.replace("{version}", version);

        System.out.println(outText);

    }


}
