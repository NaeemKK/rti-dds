DESCRIPTION = "Patient Monitor Demo standalone data generator using DDS Publish/Subscribe"

HOMEPAGE = "http://www.mentor.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS += "qtbase liburcu rtidds"
DEPENDS_mx6q_appends += "imx-gpu-viv"

# These are target binary runtime packages from RTI
DEPENDS_mx6q += "rti-bundle-arm"
DEPENDS_intel-corei7-64 += "rti-bundle"

DDS_ARCH = "armv7aLinux3.12gcc4.9.3cortex-a9"
export DDS_ARCH


# Use this instead of SRC_URI = "git://..." for iterative development
# INHERIT += "externalsrc"
# EXTERNALSRC_pn-pub-server = "/scratch/sandbox/pub-server"
# SRC_URI = "file:///scratch/sandbox/pub-server"
# S = "/scratch/sandbox/pub-server"

# Use this for production
SRC_URI = "git://github.com/challinan/pub-server"
SRCREV = "318cabffa82ea92cfaf05cbf43ac3f915f1e9909"
S = "${WORKDIR}/git"

# --------  Do not change anything below this line ---------
inherit qmake5
# Set path of qt5 headers as qmake5_base.bbclass sets this to 
# just ${includedir} but actually it is ${includedir}/qt5
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

EXTRA_OEMAKE += "NDDSHOME=${STAGING_DIR_TARGET}/opt/rti_connext_dds-5.2.3"

do_install_append () {
	install -d -m 755 ${D}/${bindir}
	install -m 755 ${B}/pub-server ${D}/${bindir}
}

def remove_pmd_objs(d):
    dir = d.expand("${B}") + "/objs"
    bb.note("Removing " + dir)

python do_cleanall_append() {
    remove_pmd_objs(d)
}

#     oe.path.remove(dir)
# COMPILER = "${CXX}" 
# LINKER = "${CXX}"
# COMPILER_FLAGS = "${CXXFLAGS}"
# LINKER_FLAGS = "${TARGET_LDFLAGS}"
# export COMPILER
# export LINKER
# export COMPILER_FLAGS
# export LINKER_FLAGS
