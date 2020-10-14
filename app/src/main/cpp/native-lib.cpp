#include <jni.h>
#include <string>

#define LIB_NAME "unpaper"

extern "C" {
    #include "unpaper-lib/unpaper.h"
    int unpaper_execute(int argc, char *argv[]);
};

extern "C" JNIEXPORT jint JNICALL Java_com_minhdv_androidunpaper2_MobileUnpaper_nativeUnpaperExecute(JNIEnv* env, jobject, jobjectArray stringArray) {
    jstring *tempArray = NULL;
    int argumentCount = 1;
    char **argv = NULL;

    if (stringArray != NULL) {
        int programArgumentCount = env->GetArrayLength(stringArray);
        argumentCount = programArgumentCount + 1;

        tempArray = (jstring *) malloc(sizeof(jstring) * programArgumentCount);
    }

    argv = (char **)malloc(sizeof(char*) * (argumentCount));
    argv[0] = (char *)malloc(sizeof(char) * (strlen(LIB_NAME) + 1));
    strcpy(argv[0], LIB_NAME);

    // PREPARE
    if (stringArray != NULL) {
        for (int i = 0; i < (argumentCount - 1); i++) {
            tempArray[i] = (jstring) env->GetObjectArrayElement(stringArray, i);
            if (tempArray[i] != NULL) {
                argv[i + 1] = (char *) env->GetStringUTFChars(tempArray[i], 0);
            }
        }
    }

    // RUN
    int retCode = unpaper_execute(argumentCount, argv);

    // CLEANUP
    if (tempArray != NULL) {
        for (int i = 0; i < (argumentCount - 1); i++) {
            env->ReleaseStringUTFChars(tempArray[i], argv[i + 1]);
        }

        free(tempArray);
    }
    free(argv[0]);
    free(argv);

    return retCode;
}
