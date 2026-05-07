package com.eevneon.radiationfission.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class RNFServer extends ConfigBase {
    public final RNFKinetics kinetics = this.nested(0, RNFKinetics::new, Comments.kinetics);

    @Override
    public String getName() {
        return "server";
    }

    private static class Comments {
        static String kinetics = "Parameters and abilities of RnF's kinetic mechanisms";
    }
}
