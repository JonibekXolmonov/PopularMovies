#include <jni.h>

JNIEXPORT jstring JNICALL
        Java_com_example_android_1imperative_networking_Server_getProductionBaseUrl(JNIEnv
*env,
jobject instance
) {
return (*env)->
NewStringUTF(env,
"https://www.episodate.com/");
}
JNIEXPORT jstring JNICALL
        Java_com_example_android_1imperative_networking_Server_getDeploymentBaseUrl(JNIEnv
*env,
jobject instance
) {
return (*env)->
NewStringUTF(env,
"https://www.episodate.com/");
}