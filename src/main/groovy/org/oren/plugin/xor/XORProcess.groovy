package org.oren.plugin.xor

class XORProcess {
    byte[] secret
    File xorInputFile
    File xorOutputFile
    boolean isAvailable
    String logMsg


    @Override
    String toString() {
        return "XORProcess{" +
                "secret=" + secret +
                ", xorInputFile=" + xorInputFile +
                ", xorOutputFile=" + xorOutputFile +
                ", isAvailable=" + isAvailable +
                ", logMsg='" + logMsg + '\'' +
                '}'
    }
}
