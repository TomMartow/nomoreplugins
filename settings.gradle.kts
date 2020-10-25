rootProject.name = "nomoreplugins"

include(":AIOMarkers")
include(":InterfaceMarking")
include(":InventoryItemIndicators")
include(":MouseLogging")
include(":MyCharacterIndicators")
include(":NMUtils")
include(":NoMoreAgility")
include(":nomoregrounditems")
include(":nomoregroundmarkers")
include(":nomoreinventorytags")
include(":nomoremenuindicators")
include(":NoMoreMLM")
include(":nomorenpchighlight")
include(":nomoreobjectindicators")
include(":NoMoreWintertodt")
include(":StatRandomiser")
include(":TestPlugin")

for (project in rootProject.children) {
    project.apply {
        projectDir = file(name)
        buildFileName = "${name.toLowerCase()}.gradle.kts"

        require(projectDir.isDirectory) { "Project '${project.path} must have a $projectDir directory" }
        require(buildFile.isFile) { "Project '${project.path} must have a $buildFile build script" }
    }
}
