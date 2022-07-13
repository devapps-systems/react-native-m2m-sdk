#!/bin/bash
# this should come BEFORE the Fabric upload phase

# We do not strip the debug symbols from the built M2M Sdk
# therefore if COPY_PHASE_STRIP is NO in your project
# then line info, etc will be available without including the dSYM
if [ "${COPY_PHASE_STRIP}" != "YES" ] ; then
  exit 0
fi

FILE_PATH="${SOURCE_ROOT}/M2MSDK.framework.dSYM"
cp -r "${FILE_PATH}" "${BUILT_PRODUCTS_DIR}"

APP_PATH="${TARGET_BUILD_DIR}/${WRAPPER_NAME}"

thin_exec() {
    FRAMEWORK_EXECUTABLE_PATH="$1"
    EXTRACTED_ARCHS=()

    for ARCH in $ARCHS
    do
        echo "Extracting $ARCH from $FRAMEWORK_EXECUTABLE_NAME"
        lipo -extract "$ARCH" "$FRAMEWORK_EXECUTABLE_PATH" -o "$FRAMEWORK_EXECUTABLE_PATH-$ARCH"
        EXTRACTED_ARCHS+=("$FRAMEWORK_EXECUTABLE_PATH-$ARCH")
    done

    echo "Merging extracted architectures: ${ARCHS} for $FRAMEWORK_EXECUTABLE_PATH"
    lipo -o "$FRAMEWORK_EXECUTABLE_PATH-merged" -create "${EXTRACTED_ARCHS[@]}"
    rm "${EXTRACTED_ARCHS[@]}"

    echo "Replacing original executable with thinned version for $FRAMEWORK_EXECUTABLE_PATH"
    rm "$FRAMEWORK_EXECUTABLE_PATH"
    mv "$FRAMEWORK_EXECUTABLE_PATH-merged" "$FRAMEWORK_EXECUTABLE_PATH"
}

# This script loops through the frameworks embedded in the application and
# removes unused architectures.
find "$APP_PATH" -name '*.framework' -type d | while read -r FRAMEWORK
do
    FRAMEWORK_EXECUTABLE_NAME=$(defaults read "$FRAMEWORK/Info.plist" CFBundleExecutable)
    FRAMEWORK_EXECUTABLE_PATH="$FRAMEWORK/$FRAMEWORK_EXECUTABLE_NAME"
    if [ -f "$FRAMEWORK_EXECUTABLE_PATH" ]; then
        echo "Found executable at $FRAMEWORK_EXECUTABLE_PATH"
        thin_exec "$FRAMEWORK_EXECUTABLE_PATH"
    fi

    # now thin the dSYM as well
    DSYM_DWARF_PATH="${BUILT_PRODUCTS_DIR}/$(basename "$FRAMEWORK").dSYM/Contents/Resources/DWARF/$(basename "$FRAMEWORK_EXECUTABLE_PATH")"
    if [ -f "$DSYM_DWARF_PATH" ]; then
        echo "Found dSYM DWARF at $DSYM_DWARF_PATH"
        thin_exec "$DSYM_DWARF_PATH"
    fi
done