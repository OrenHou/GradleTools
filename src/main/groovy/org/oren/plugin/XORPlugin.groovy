package org.oren.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.oren.plugin.xor.XOR
import org.oren.plugin.xor.XORDir
import org.oren.plugin.xor.XORDirTask
import org.oren.plugin.xor.XORTask

class XORPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        L.d("-----oren.gradle.plugin:XORPlugin-Start-----")

        xorTask(project)
        xorDirTask(project)

        L.d("-----oren.gradle.plugin:XORPlugin-End-----")
    }

    //XOR 任务
    private static void xorTask(Project project) {
        XOR xor = project.extensions.create("xor", XOR.class)
        XORTask xorTask = project.tasks.create("XOR", XORTask.class)
        xorTask.xor = xor
        xorTask.group = "oren"
    }

    //XORDir 任务
    private static void xorDirTask(Project project) {
        XORDir xorDir = project.extensions.create("xorDir", XORDir.class)
        XORDirTask xorDirTask = project.tasks.create("XORDir", XORDirTask.class)
        xorDirTask.xorDir = xorDir
        xorDirTask.group = "oren"
    }
}
