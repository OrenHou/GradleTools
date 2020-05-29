package org.oren.plugin.xor

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.oren.plugin.Action
import org.oren.plugin.L
import org.oren.plugin.T

import java.nio.charset.StandardCharsets

class XORTask extends DefaultTask {

    static XOR xor = null

    Action<List<XORProcess>> action = new XORAction()

    @TaskAction
    void process() {
        List<XORProcess> xorList = getXorList()
        if (action != null && xorList != null && xorList.size() > 0) {
            action.run(xorList)
        }
    }

    private static List<XORProcess> getXorList() {
        if (xor == null) {
            L.e("Please definition 'xor' or 'xorDir' params in build.gradle file !!!")
            return null
        }
        if (!xor.enable) {
            return null
        }
        List<XORProcess> listOfXORProcess = new ArrayList<>()
        listOfXORProcess.clear()
        if (xor != null) {
            XORProcess process = processXOR(xor)
            if (process != null) {
                listOfXORProcess.add(process)
            }
        }
        return listOfXORProcess
    }

    private static XORProcess processXOR(XOR xor) {
        XORProcess process = new XORProcess()
        process.isAvailable = false
        if (T.isEmpty(xor.secret)) {
            L.e("Please definition 'XOR.secret' params in build.gradle file !!!")
            return process
        }
        process.secret = xor.secret.getBytes(StandardCharsets.UTF_8)
        if (T.isEmpty(xor.inputDir)) {
            L.e("Please definition 'XOR.inputDir' params in build.gradle file !!!")
            return process
        }
        File inputDirFile = new File(xor.inputDir)
        if (!inputDirFile.isDirectory()) {
            L.e("${xor.inputDir} not is Dir!!!")
            return process
        }
        if (T.isEmpty(xor.inputFileName)) {
            L.e("Please definition 'XOR.inputFileName' params in build.gradle file !!!")
            return process
        }
        File inputFile = new File(xor.inputDir, xor.inputFileName)
        if (!inputFile.exists()) {
            L.e("${inputFile.getAbsolutePath()} file not exists !!!")
            return process
        }
        process.xorInputFile = inputFile
        process.logMsg = inputFile.name
        if (T.isEmpty(xor.outputDir)) {
            L.e("Please definition 'XOR.outputDir' params in build.gradle file !!!")
            return process
        }
        File outputDirFile = new File(xor.outputDir)
        if (!outputDirFile.exists()) {
            boolean mkdirs = outputDirFile.mkdirs()
            if (mkdirs) {
                L.d("${xor.outputDir} make dirs success !!!")
            } else {
                L.e("${xor.outputDir} not exists and make dirs failed !!!")
            }
        }
        if (T.isEmpty(xor.outputFileName)) {
            L.e("Please definition 'XOR.outputFileName' params in build.gradle file !!!")
            return process
        }
        process.xorOutputFile = new File(xor.outputDir, xor.outputFileName)
        process.isAvailable = true
        return process
    }
}
