package org.oren.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.oren.plugin.xor.XOR
import org.oren.plugin.xor.XORDir
import org.oren.plugin.xor.XORTask

class PreparePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        L.d("-----PreparePlugin Start-----")

        xorTask(project)

        L.d("-----PreparePlugin End-----")
    }

    //XOR 任务
    private static void xorTask(Project project) {
        XOR xor = project.extensions.create("xor", XOR.class)
        XORDir xorDir = project.extensions.create("xorDir", XORDir.class)
        XORTask xorTask = project.tasks.create("xor", XORTask.class)
        xorTask.xor = xor
        xorTask.xorDir = xorDir
        xorTask.group = "xx"
    }
}
