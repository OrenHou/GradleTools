# GradleTools 

### XOR（异或加密）任务
> 使用方式

 根build.gradle下添加：
 ```$xslt
 repositories {
    jcenter()
 }
 
 dependencies {
         classpath 'org.oren.plugin:gradle-plugin:+'
 }
```
使用的model（比如:app）下的build.gradle文件下添加：
```$xslt
 apply plugin: 'oren.prepare.xor'
```
在app下的gradle里面寻找任务组：<b>oren</b>，下面包含任务：<b>XOR</b> 和 <b>XORDir</b>

> 任务介绍

- XOR 定义参数
```$xslt
xor{
    enable （boolean） 是否开启
    secret （String） 加密key
    inputDir （String） 输入文件夹
    inputFileName （String） 输入的文件名
    outputDir （String） 输出的文件夹
    outputFileName （String） 输出的文件名
}
```
- XORDir 定义参数
```$xslt
xorDir{
    enable （boolean） 是否开启
    secret （String） 加密key
    inputDir （String） 输入文件夹
    inputFileSuffixName （String） 输入的文件名后缀，所有符合后缀的文件都会被加密
    outputDir （String） 输出的文件夹
    outputFileSuffixName （String） 输出的文件名后缀
}
```