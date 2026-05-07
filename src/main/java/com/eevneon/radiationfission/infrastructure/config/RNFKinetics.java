package com.eevneon.radiationfission.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class RNFKinetics extends ConfigBase {
    public final RNFStress stressValues = this.nested(1, RNFStress::new, Comments.stress);

    @Override
    public String getName() {
        return "kinetics";
    }

    private static class Comments {
        static String stress = "Fine tune the kinetic stats of individual components";
    }
}
