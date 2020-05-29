package org.oren.plugin.xor

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.oren.plugin.Action
import org.oren.plugin.L
import org.oren.plugin.T

import java.nio.charset.StandardCharsets

class XORDirTask extends DefaultTask {

    static XORDir xorDir = null

    Action<List<XORProcess>> action = new XORAction()

    @TaskAction
    void process() {
        List<XORProcess> xorList = getXorList()
        if (action != null && xorList != null && xorList.size() > 0) {
            action.run(xorList)
        }
    }

    private static List<XORProcess> getXorList() {
        if (xorDir == null) {
            L.e("Please definition 'xor' or 'xorDir' params in build.gradle file !!!")
            return null
        }
        if (!xorDir.enable) {
            return null
        }
        List<XORProcess> listOfXORProcess = new ArrayList<>()
        listOfXORProcess.clear()
        if (xorDir != null) {
            List<XORProcess> list = processXORMultiple(xorDir)
            if (list != null) {
                listOfXORProcess.addAll(list)
            }
        }
        return listOfXORProcess
    }

    private static List<XORProcess> processXORMultiple(XORDir dir) {
        if (!dir.enable) {
            return null
        }
        if (T.isEmpty(dir.secret)) {
            L.e("Please definition 'XORDir.secret' params in build.gradle file !!!")
            return null
        }
        if (T.isEmpty(dir.inputDir)) {
            L.e("Please definition 'XORDir.inputDir' params in build.gradle file !!!")
            return null
        }
        File inputDirFile = new File(dir.inputDir)
        if (!inputDirFile.exists()) {
            L.e("${dir.inputDir} directory not exists !!!")
            return null
        }
        if (!inputDirFile.isDirectory()) {
            L.e("${dir.inputDir} not is directory!!!")
            return null
        }
        if (T.isEmpty(dir.outputDir)) {
            L.e("Please definition 'XORDir.outputDir' params in build.gradle file !!!")
            return null
        }
        File outputDirFile = new File(dir.outputDir)
        if (!outputDirFile.exists()) {
            boolean mkdirs = outputDirFile.mkdirs()
            if (mkdirs) {
                L.d("${dir.outputDir} make dirs success !!!")
            } else {
                L.e("${dir.outputDir} not exists and make dirs failed !!!")
            }
        }
        //-------------------
        List<XORProcess> list = new ArrayList<>()

        File[] files = inputDirFile.listFiles()
        for (File file : files) {
            if (!T.isEmpty(dir.inputFileSuffixName)) {
                if (file.getName().endsWith(dir.inputFileSuffixName)) {
                    list.add(fileToXORProcess(file, dir))
                }
            } else {
                list.add(fileToXORProcess(file, dir))
            }
        }
        return list
    }

    private static XORProcess fileToXORProcess(File file, XORDir xorDir) {
        XORProcess process = new XORProcess()
        process.secret = xorDir.secret.getBytes(StandardCharsets.UTF_8)
        process.logMsg = file.name
        process.isAvailable = true
        process.xorInputFile = file
        String outputFileName
        if (T.isEmpty(xorDir.outputFileSuffixName)) {
            outputFileName = file.name
        } else {
            outputFileName = file.name.substring(0, file.name.indexOf("."))
            if (!xorDir.outputFileSuffixName.startsWith(".")) {
                outputFileName = outputFileName + "."
            }
            outputFileName = outputFileName + xorDir.outputFileSuffixName
        }
        process.xorOutputFile = new File(xorDir.outputDir, outputFileName)
        return process
    }

}
