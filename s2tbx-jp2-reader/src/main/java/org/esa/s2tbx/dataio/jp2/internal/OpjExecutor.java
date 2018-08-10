/*
 * Copyright (C) 2014-2015 CS-SI (foss-contact@thor.si.c-s.fr)
 * Copyright (C) 2014-2015 CS-Romania (office@c-s.ro)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */

package org.esa.s2tbx.dataio.jp2.internal;

import org.esa.s2tbx.dataio.openjpeg.CommandOutput;
import org.esa.s2tbx.dataio.openjpeg.OpenJpegUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by kraftek on 7/15/2015.
 */
public class OpjExecutor {

    private Logger logger;
    private String exePath;
    private String lastError;
    private String lastOutput;

    public OpjExecutor(String executable) {
        logger = Logger.getLogger(OpjExecutor.class.getName());
        exePath = executable;
    }

    public int execute(Map<String, String> arguments) {
        int exitCode = 0;
        lastError = null;
        lastOutput = null;
        List<String> args = new ArrayList<>();
        args.add(exePath);
        for (String key : arguments.keySet()) {
            args.add(key);
            args.add(arguments.get(key));
        }
        ProcessBuilder builder = new ProcessBuilder(args);
        builder.redirectErrorStream(true);
        try {
            CommandOutput commandOutput = OpenJpegUtils.runProcess(builder);
            lastOutput = commandOutput.getTextOutput();
            lastError = commandOutput.getErrorOutput();
            exitCode = commandOutput.getErrorCode();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return exitCode;
    }

    public String getLastError() { return lastError; }

    public String getLastOutput() { return lastOutput; }
}
