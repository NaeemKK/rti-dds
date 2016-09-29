# Copyright (C) 2016 Chris Hallinan <challinan@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Example compiled from rtiddsgen output"
LICENSE = "CLOSED"
DDS_ARCH = "armv7aLinux3.12gcc4.9.3cortex-a9"

DDS_WKDIR_S = "${@os.path.basename('${S}')}"

SRC_URI = "file://dds-pubsub.tar.gz;subdir=${DDS_WKDIR_S} \
          file://debug.patch"
#            file://fix-makefile-arch.patch
#            file://remove-linker-flags-from-makefile.patch

do_configure() {
    mv ${S}/makefile_pmd_x64Linux3gcc4.8.2 ${S}/Makefile
}

do_install() {
    install -d ${D}/${bindir}
    install -m 0755 ${B}/objs/${DDS_ARCH}/pmd_publisher ${D}/${bindir}
    install -m 0755 ${B}/objs/${DDS_ARCH}/pmd_subscriber ${D}/${bindir}
}

EXTRA_OEMAKE = "NDDSHOME=${STAGING_DIR_TARGET}/opt/rti_connext_dds-5.2.3"
COMPILER = "${CXX}" 
LINKER = "${CXX}"
COMPILER_FLAGS = "${CXXFLAGS}"
LINKER_FLAGS = "${TARGET_LDFLAGS}"
export COMPILER
export LINKER
export COMPILER_FLAGS
export LINKER_FLAGS

FILES_${PN} += "pmd_publisher pmd_subscriber"
