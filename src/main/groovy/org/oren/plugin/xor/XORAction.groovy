package org.oren.plugin.xor

import org.oren.plugin.Action
import org.oren.plugin.L


class XORAction implements Action<List<XORProcess>> {

    @Override
    void run(List<XORProcess> param) {
        param.forEach {
            xor(it)
        }
    }

    private static void xor(XORProcess process) {
        L.d(process.toString())
        L.d("---Start XOR Encrypt---")
        if (!process.isAvailable) {
            return
        }
        byte[] secret = process.secret
        FileInputStream inputStream = new FileInputStream(process.xorInputFile)
        FileOutputStream outputStream = new FileOutputStream(process.xorOutputFile)
        try {
            byte[] buffer = new byte[1024 * 16]
            int len
            int a = 0xbadaabb
            int p = 0
            while ((len = inputStream.read(buffer)) != -1) {
                for (int i = 0; i < buffer.length; i++) {
                    a = ((a >>> 24) & 0xff) ^ ((a >>> 16) & 0xff) ^ ((a >>> 8) & 0xff) ^ (a & 0xff) + p + i
                    buffer[i] ^= secret[(p + i) % secret.length] ^ a
                }
                p += len
                p %= secret.length
                outputStream.write(buffer, 0, len)
            }
        } catch (IOException e) {
            L.e(process.logMsg + ":" + e.toString())
        }
        L.d("---XOR Encrypt End---")
    }

}
